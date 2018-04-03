package com.bobwares.databus.common.renderer.pdf;

import com.bobwares.databus.common.model.ResourceConfiguration;
import com.google.common.base.Strings;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;
import java.util.*;

public class PDFReportGenerator {

    private static final PDFReportGenerator instance = new PDFReportGenerator();
    private static final int PD_TEXT_SPACE_UNITS = 1000;

    private PDFReportGenerator() {}

    public static PDFReportGenerator instance() {
        return instance;
    }

    // Configures basic setup for the table and draws it page by page
    public void drawTable(ResourceConfiguration resourceConfiguration, List<PdfHeader> pdfHeaders, PDDocument doc, Table table) throws IOException {

        int rowsProcessed = 0;
        int pageNum = 1;
        do {
            // each report page is segmented into multiple pages (or report page segments)
            // when the number of columns (i.e. total width) exceeds the available width for a single page
            List<PDPageContentStream> contentStreams = new ArrayList<>();

            int numPageSegments = table.getPagedColumns().size();
            for (int i = 0; i < numPageSegments; i++) {
                PDPage page = generatePage(doc, table);
                PDPageContentStream contentStream = generateContentStream(doc, page, table);
                contentStreams.add(contentStream);
            }

            rowsProcessed += processPage(resourceConfiguration, pdfHeaders, table, pageNum, rowsProcessed, contentStreams);
            for (PDPageContentStream contentStream : contentStreams) {
                contentStream.close();
            }
            ++pageNum;
        } while (rowsProcessed < table.getRowCount());
    }

    private PDPage generatePage(PDDocument doc, Table table) {
        PDPage page = new PDPage();
        page.setMediaBox(table.getPageSize());
        page.setRotation(table.getOrientation() == Orientation.Portrait ? 0 : 90);
        doc.addPage(page);
        return page;
    }

    private PDPageContentStream generateContentStream(PDDocument doc, PDPage page, Table table) throws IOException {
        PDPageContentStream contentStream = new PDPageContentStream(doc, page, PDPageContentStream.AppendMode.OVERWRITE, false);
        // User transformation matrix to change the reference when drawing.
        // This is necessary for the landscape position to draw correctly
        if (table.getOrientation() == Orientation.Landscape) {
        	contentStream.transform(new Matrix(0, 1, -1, 0, table.getPageSize().getWidth(), 0));
        }
        contentStream.setFont(table.getTextFont(), table.getFontSize());
        return contentStream;
    }

    private int processPage(ResourceConfiguration resourceConfiguration, List<PdfHeader> pdfHeaders, Table table, int pageNum, int startRow,
                            List<PDPageContentStream> contentStreams) throws IOException {

        // start recording Y-coordinates for horizontal lines in the grid
        float tableTopY = drawReportTitle(table, resourceConfiguration.getTitle(), contentStreams);
        tableTopY -= 2 * table.getRowHeight();

        if (pageNum == 1) {
            int rowsWritten = drawReportHeader(pdfHeaders, table, contentStreams.get(0), table.getPageMargin(), tableTopY);
            tableTopY -= rowsWritten * table.getRowHeight();
        }

        float lastGridY = tableTopY;
        List<Float> gridYPositions = new ArrayList<>();
        gridYPositions.add(lastGridY);

        // position cursor to start drawing content
        float nextTextX = table.getPageMargin() + table.getCellMargin();
        // Calculate center vertical alignment for text in cell considering font height
        float nextTextY = tableTopY - (table.getRowHeight() / 2)
            - ((table.getTextFont().getFontDescriptor().getFontBoundingBox().getHeight()
            / PD_TEXT_SPACE_UNITS * table.getFontSize()) / 4);

        // write column header groups, if they exist
        float labelGroupHeight = drawColumnGroupLabels(table, contentStreams, nextTextX, nextTextY);
        if (labelGroupHeight > 0) {
            lastGridY -= labelGroupHeight;
            gridYPositions.add(lastGridY);
            nextTextY -= labelGroupHeight;
            nextTextX = table.getPageMargin() + table.getCellMargin();
        }

        // Write column headers
        float labelHeight = drawColumnLabels(table, contentStreams, nextTextX, nextTextY);
        // add for grid line below header
        lastGridY -= labelHeight;
        gridYPositions.add(lastGridY);

        nextTextY -= labelHeight;
        nextTextX = table.getPageMargin() + table.getCellMargin();

        float leading =
            table.getTextFont().getBoundingBox().getHeight() / PD_TEXT_SPACE_UNITS * table.getFontSize();

        int currentRow = startRow;
        while (currentRow < table.getRowCount()) {
            List<List<String>> rowData = getTextWrappedRowData(table, currentRow);
            // find column with most wrapped lines, for row height
            int wrappedRows = 0;
            for (List<String> columnData : rowData) {
                wrappedRows = Math.max(wrappedRows, columnData.size());
            }

            float tableRowHeight = table.getRowHeight();
            float rowHeight = tableRowHeight + (leading * (wrappedRows - 1));
            if (lastGridY - rowHeight - (tableRowHeight * 2) <= table.getPageMargin()) {
                // won't fit on page with
                break;
            }

            drawRowData(table, rowData, contentStreams, nextTextX, nextTextY);
            currentRow++;
            lastGridY -= rowHeight;
            gridYPositions.add(lastGridY);
            nextTextY -= rowHeight;
            nextTextX = table.getPageMargin() + table.getCellMargin();
        }
        drawTableGrid(table, gridYPositions, contentStreams);
        String timeValue = System.getProperty("test.pdfreport.timestamp");
        Date timeStamp = timeValue == null ? new Date() : new Date(Long.parseLong(timeValue));
        drawFooter(table, pageNum, Filters.filter("datetime", timeStamp), contentStreams);

        // return number of rows processed
        return currentRow - startRow;
    }

    private float drawReportTitle(Table table, String text, List<PDPageContentStream> contentStreams) throws IOException {

        int titleFontSize = 14;
        float fontHeight = table.getLabelFont().getFontDescriptor().getFontBoundingBox().getHeight()
            / PD_TEXT_SPACE_UNITS * titleFontSize;
        float textX = table.getPageMargin();
        float pageHeight = table.getOrientation() == Orientation.Portrait
            ? table.getPageSize().getHeight()
            : table.getPageSize().getWidth();
        float textY = pageHeight - table.getPageMargin() - fontHeight;

        for (PDPageContentStream contentStream : contentStreams) {
            contentStream.setFont(table.getLabelFont(), titleFontSize);
            PDFUtils.drawString(text, contentStream, textX, textY);
            contentStream.setFont(table.getTextFont(), table.getFontSize());
        }

        return textY;
    }

    private int drawReportHeader(List<PdfHeader> pdfHeaders, Table table, PDPageContentStream contentStream,
                                 final float startTextX, final float startTextY) throws IOException {
        int rowsWritten = 0;
        float nextTextY = startTextY;

        for (PdfHeader header : pdfHeaders) {
            float nextTextX = startTextX;

            String headerTitle = header.getTitle();
            contentStream.setFont(table.getLabelFont(), table.getFontSize());
            PDFUtils.drawString(headerTitle, contentStream, nextTextX, nextTextY);
            contentStream.setFont(table.getTextFont(), table.getFontSize());
            nextTextY -= table.getRowHeight();
            ++rowsWritten;

            Map<String, String[]> headers = header.getHeaders();
            float labelsWidth = PDFUtils.getMaxWidth(table.getTextFont(), table.getFontSize(), headers.keySet());

            for (Map.Entry<String, String[]> entry : headers.entrySet()) {
                nextTextX = startTextX;
                String label = entry.getKey() + ":";
                PDFUtils.drawString(label, contentStream, nextTextX, nextTextY);
                nextTextX += labelsWidth + table.getPageMargin();
                for (String value : entry.getValue()) {
                    PDFUtils.drawString(value, contentStream, nextTextX, nextTextY);
                    nextTextY -= table.getRowHeight();
                    ++rowsWritten;
                }
            }
            nextTextY -= table.getRowHeight();
            ++rowsWritten;
        }

        // return number of rows written
        return rowsWritten;
    }

    private float drawColumnGroupLabels(Table table, List<PDPageContentStream> contentStreams,
                                        float startTextX, float startTextY) throws IOException {

        List<List<ColumnGroup>> pagedColumnGroups = table.getPagedColumnGroups();
        if (pagedColumnGroups.isEmpty()) {
            return 0;
        }

        float leading =
            table.getLabelFont().getBoundingBox().getHeight() / PD_TEXT_SPACE_UNITS * table.getFontSize();
        int maxLinesWrapped = 0;

        for (int pageSegment = 0; pageSegment < contentStreams.size(); ++pageSegment) {
            PDPageContentStream contentStream = contentStreams.get(pageSegment);
            contentStream.setFont(table.getLabelFont(), table.getFontSize());
            float nextTextX = startTextX;
            List<ColumnGroup> columnGroups = pagedColumnGroups.get(pageSegment);
            for (ColumnGroup columnGroup : columnGroups) {
                float textDisplayWidth = columnGroup.getWidth() - (table.getCellMargin() * 2);
                List<String> wrappedLabel = PDFUtils.wrapText(
                    Strings.nullToEmpty(columnGroup.getName()),
                    table.getLabelFont(),
                    table.getFontSize(),
                    textDisplayWidth);
                maxLinesWrapped = Math.max(maxLinesWrapped, wrappedLabel.size());
                PDFUtils.drawString(wrappedLabel, contentStream, table.getLabelFont(), table.getFontSize(),
                    nextTextX, startTextY, leading, textDisplayWidth, Alignment.CENTER
                );
                nextTextX += columnGroup.getWidth();
            }
            contentStream.setFont(table.getTextFont(), table.getFontSize());
        }

        // row height is table row height plus leading for each extra [wrapped] text line
        return table.getRowHeight() + (leading * (maxLinesWrapped - 1));
    }

    // Writes the content for the column header
    private float drawColumnLabels(Table table, List<PDPageContentStream> contentStreams,
                                   float startTextX, float startTextY) throws IOException {
        float leading =
            table.getLabelFont().getBoundingBox().getHeight() / PD_TEXT_SPACE_UNITS * table.getFontSize();
        int maxLinesWrapped = 0;

        for (int pageSegment = 0; pageSegment < contentStreams.size(); ++pageSegment) {
            PDPageContentStream contentStream = contentStreams.get(pageSegment);
            contentStream.setFont(table.getLabelFont(), table.getFontSize());
            float nextTextX = startTextX;
            List<Column> pageColumns = table.getPagedColumns().get(pageSegment);

            for (Column column : pageColumns) {
                float textDisplayWidth = column.getWidth() - (table.getCellMargin() * 2);
                List<String> wrappedLabel = PDFUtils.wrapText(
                    Strings.nullToEmpty(column.getName()),
                    table.getLabelFont(),
                    table.getFontSize(),
                    textDisplayWidth);
                maxLinesWrapped = Math.max(maxLinesWrapped, wrappedLabel.size());
                PDFUtils.drawString(wrappedLabel, contentStream, table.getLabelFont(), table.getFontSize(),
                    nextTextX, startTextY, leading, textDisplayWidth, Alignment.CENTER
                );
                nextTextX += column.getWidth();
            }
            contentStream.setFont(table.getTextFont(), table.getFontSize());
        }

        // row height is table row height plus leading for each extra [wrapped] text line
        return table.getRowHeight() + (leading * (maxLinesWrapped - 1));
    }

    // Writes the content for one line
    private void drawRowData(Table table, List<List<String>> rowData,
                             List<PDPageContentStream> contentStreams, float startTextX, float startTextY)
        throws IOException {
        float leading =
            table.getTextFont().getBoundingBox().getHeight() / PD_TEXT_SPACE_UNITS * table.getFontSize();

        for (int pageSegment = 0; pageSegment < contentStreams.size(); ++pageSegment) {
            PDPageContentStream contentStream = contentStreams.get(pageSegment);
            float nextTextX = startTextX;

            List<Column> pageColumns = table.getPagedColumns().get(pageSegment);
            for (Column column : pageColumns) {
                float textDisplayWidth = column.getWidth() - (table.getCellMargin() * 2);
                Alignment alignment = column.getAlignment();
                List<String> lines = rowData.get(column.getIndex());
                PDFUtils.drawString(lines, contentStream, table.getTextFont(), table.getFontSize(),
                    nextTextX, startTextY, leading, textDisplayWidth, alignment);
                nextTextX += column.getWidth();
            }
        }
    }

    // transform the String[] row data where each String becomes a List<String> of wrapped lines
    private List<List<String>> getTextWrappedRowData(Table table, int row) throws IOException {
        String[] data = table.getRow(row);
        List<List<String>> wrappedData = new ArrayList<>();
        for (int i = 0; i < data.length; ++i) {
            String text = Strings.nullToEmpty(data[i]);
            Column column = table.getColumn(i);
            float displayWidth = column.getWidth() - (table.getCellMargin() * 2);
            List<String> wrappedText = PDFUtils.wrapText(text, table.getTextFont(), table.getFontSize(), displayWidth);
            wrappedData.add(wrappedText);
        }
        return wrappedData;
    }

    // draw the borders for the table cells
    private void drawTableGrid(Table table, List<Float> gridYPositions, List<PDPageContentStream> contentStreams)
        throws IOException {

        List<List<ColumnGroup>> pagedColumnGroups = table.getPagedColumnGroups();

        for (int pageSegment = 0; pageSegment < contentStreams.size(); ++pageSegment) {
            PDPageContentStream contentStream = contentStreams.get(pageSegment);
            List<Column> pageColumns = table.getPagedColumns().get(pageSegment);
            List<ColumnGroup> pageColumnGroups = pagedColumnGroups.isEmpty()
                ? Collections.<ColumnGroup>emptyList() : pagedColumnGroups.get(pageSegment);

            // Draw row lines
            float tableWidth = table.getWidth(pageColumns);
            float nextY = 0;
            for (float gridY : gridYPositions) {
            	drawLine(contentStream, table.getPageMargin(), gridY, table.getPageMargin() + tableWidth, gridY);
                nextY = gridY;
            }

            // Draw column lines
            float tableTopY = gridYPositions.get(0);
            float columnGroupBottomY = gridYPositions.get(1);
            final float tableBottomY = nextY;

            float nextX = table.getPageMargin();
            for (ColumnGroup columnGroup : pageColumnGroups) {
                drawLine(contentStream, nextX, tableTopY, nextX, columnGroupBottomY);
                nextX += columnGroup.getWidth();
            }

            float topY = pageColumnGroups.isEmpty() ? tableTopY : columnGroupBottomY;
            nextX = table.getPageMargin();
            for (Column column : pageColumns) {
            	drawLine(contentStream, nextX, topY, nextX, tableBottomY);
                nextX += column.getWidth();
            }
            drawLine(contentStream, nextX, tableTopY, nextX, tableBottomY);
        }
    }

    private void drawFooter(Table table, int page, String text,
                            List<PDPageContentStream> contentStreams) throws IOException {
        for (int pageSegment = 0; pageSegment < contentStreams.size(); ++pageSegment) {
            PDPageContentStream contentStream = contentStreams.get(pageSegment);

            // draw timestamp, left aligned
            float textX = table.getPageMargin();
            float textY = table.getPageMargin();
            PDFUtils.drawString(text, contentStream, textX, textY);

            // draw page number, right aligned aligned
            String pageNumStr = "Page " + page;
            if (contentStreams.size() > 1) {
                pageNumStr += "-" + (pageSegment + 1);
            }
            float textWidth = table.getTextFont().getStringWidth(pageNumStr) / PD_TEXT_SPACE_UNITS * table.getFontSize();
            float pageWidth = table.getOrientation() == Orientation.Portrait
                ? table.getPageSize().getWidth()
                : table.getPageSize().getHeight();
            textX = pageWidth - table.getPageMargin() - textWidth;

            PDFUtils.drawString(pageNumStr, contentStream, textX, textY);
        }
    }

    protected void drawLine(PDPageContentStream pdPageContentStream, float xStart, float yStart, float xEnd, float yEnd) throws IOException {
    	pdPageContentStream.moveTo(xStart, yStart);
    	pdPageContentStream.lineTo(xEnd, yEnd);
    	pdPageContentStream.stroke();
    }
}
