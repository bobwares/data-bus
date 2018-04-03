package com.bobwares.databus.common.renderer.pdf;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {

    // Table attributes
    private float pageMargin;
    private PDRectangle pageSize;
    private Orientation orientation;
    private float rowHeight;

    // font attributes
    private PDFont textFont;
    private PDFont labelFont;
    private float fontSize;
    // Content attributes
    private List<Column> columns;
    private String[][] content;
    private float cellMargin;

    public int getColumnCount() {
        return getColumns().size();
    }

    public Column getColumn(int column) {
        return columns.get(column);
    }

    public float getWidth() {
        float tableWidth = 0f;
        for (Column column : columns) {
            tableWidth += column.getWidth();
        }
        return tableWidth;
    }

    public float getWidth(List<Column> columns) {
        float tableWidth = 0f;
        for (Column column : columns) {
            tableWidth += column.getWidth();
        }
        return tableWidth;
    }

    public float getPageMargin() {
        return pageMargin;
    }

    public void setPageMargin(float pageMargin) {
        this.pageMargin = pageMargin;
    }

    public PDRectangle getPageSize() {
        return pageSize;
    }

    public void setPageSize(PDRectangle pageSize) {
        this.pageSize = pageSize;
    }

    public PDFont getTextFont() {
        return textFont;
    }

    public void setTextFont(PDFont textFont) {
        this.textFont = textFont;
    }

    public void setLabelFont(PDFont labelFont) {
        this.labelFont = labelFont;
    }

    public float getFontSize() {
        return fontSize;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public String[] getColumnsNamesAsArray() {
        return Lists.transform(columns, new Function<Column, String>() {
            @Override
            public String apply(Column input) {
                return input.getName();
            }
        }).toArray(new String[columns.size()]);
    }

    public List<Column> getColumns() {
        return columns != null ? columns : Collections.<Column>emptyList();
    }

    public List<List<Column>> getPagedColumns() {
        if (pageSize == null) {
            return Collections.singletonList(getColumns());
        }
        List<List<Column>> pagedColumns = new ArrayList<>();
        float pageContentWidth = orientation == Orientation.Portrait
            ? pageSize.getWidth() - (2 * pageMargin)
            : pageSize.getHeight() - (2 * pageMargin);
        float pageColumnWidth = 0;
        List<Column> pageColumns = new ArrayList<>();
        pagedColumns.add(pageColumns);
        for (Column column : columns) {
            float columnWidth = column.getWidth();
            pageColumnWidth += columnWidth;
            if (pageColumnWidth > pageContentWidth) {
                // column exceeds content width, start new page
                pageColumns = new ArrayList<>();
                pagedColumns.add(pageColumns);
                pageColumnWidth = columnWidth;
            }
            pageColumns.add(column);
        }

        return pagedColumns;
    }

    public List<List<ColumnGroup>> getPagedColumnGroups() {
        // collect column groups in order (there might be unnamed groups)
        boolean hasGroups = false;
        List<List<ColumnGroup>> pagedColumnGroups = new ArrayList<>();

        for (List<Column> columns: getPagedColumns()) {
            List<ColumnGroup> columnGroups = new ArrayList<>();
            String groupName = "";
            float width = 0;

            for (Column column: columns) {
                String group = MoreObjects.firstNonNull(column.getGroup(), "");
                if (!group.equals(groupName)) {
                    if (width > 0) {
                        columnGroups.add(new ColumnGroup(groupName, width));
                        width = 0;
                    }
                    hasGroups = true;
                    groupName = group;
                }
                width += column.getWidth();
            }
            columnGroups.add(new ColumnGroup(groupName, width));
            pagedColumnGroups.add(columnGroups);
        }
        // one unnamed group means no grouping, discard it
        return hasGroups ? pagedColumnGroups : Collections.<List<ColumnGroup>>emptyList();
    }


    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public int getRowCount() {
        if (content == null) {
            return 0;
        }
        return content.length;
    }

    public float getHeight() {
        return orientation == Orientation.Portrait
            ? pageSize.getHeight() - (2 * pageMargin)
            : pageSize.getWidth() - (2 * pageMargin);
    }

    public String[] getRow(int row) {
        return content[row];
    }

    public float getRowHeight() {
        return rowHeight;
    }

    public void setRowHeight(float rowHeight) {
        this.rowHeight = rowHeight;
    }

    public String[][] getContent() {
        return content;
    }

    public void setContent(String[][] content) {
        this.content = content;
    }

    public float getCellMargin() {
        return cellMargin;
    }

    public void setCellMargin(float cellMargin) {
        this.cellMargin = cellMargin;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public PDFont getLabelFont() {
        return labelFont;
    }
}
