package com.bobwares.databus.common.renderer.pdf;

import com.google.common.base.MoreObjects;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.model.fieldtype.FieldTypeDefinition;
import com.bobwares.databus.common.util.FieldTypeUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PDFRendererServiceImpl implements PDFRendererService {

    // Page configuration
    private static final PDRectangle PAGE_SIZE = PDRectangle.LETTER;
    private static final float PAGE_MARGIN = 20;

    // Font configuration
    private static final PDFont TEXT_FONT = PDType1Font.HELVETICA;
    private static final PDFont COLUMN_LABEL_FONT = PDType1Font.HELVETICA_BOLD;
    private static final float FONT_SIZE = 10;

    // Table configuration
    private static final float ROW_HEIGHT = 15;
    private static final float CELL_MARGIN = 2;

    @Override
    public PDDocument renderAsPDF(ResourceConfiguration resourceConfiguration, List<PdfHeader> pdfHeaders, ResponseObject responseObject) throws IOException {

        // ignore hidden fields
        List<Field> fields =
            Lists.newArrayList(Iterables.filter(resourceConfiguration.getFields(), new Predicate<Field>() {
                @Override
                public boolean apply(Field field) {
                    return !field.isHidden();
                }
            }));

        List<Column> columns = new ArrayList<>();
        for (int i = 0; i < fields.size(); ++i) {
            columns.add(new Column(fields.get(i).mapOf(), i));
        }

        String orientation = resourceConfiguration.getOrientation();
        PagingObject pagingObject = (PagingObject) responseObject.getResultsModel();
        String[][] content = createContent(ImmutableList.copyOf(columns), ImmutableList.copyOf(pagingObject.getItems()));
        Table table = new TableBuilder()
            .setCellMargin(CELL_MARGIN)
            .setColumns(columns)
            .setContent(content)
            .setRowHeight(ROW_HEIGHT)
            .setPageMargin(PAGE_MARGIN)
            .setPageSize(PAGE_SIZE)
            .setOrientation(orientation == null ? Orientation.Portrait : Orientation.lookup(orientation))
            .setTextFont(TEXT_FONT)
            .setLabelFont(COLUMN_LABEL_FONT)
            .setFontSize(FONT_SIZE)
            .build();

        PDDocument pdDocument = new PDDocument();
        PDFReportGenerator.instance().drawTable(resourceConfiguration, pdfHeaders, pdDocument, table);

        return pdDocument;
    }

    private <T extends Map<String, Object>> String[][] createContent(List<Column> columns, List<T> records) {
        String[][] content = new String[records.size()][columns.size()];

        for (int i = 0; i < records.size(); ++i) {
            Map<String, Object> record = records.get(i);
            // column driven, since actual data may have extra columns not used here
            for (int j = 0; j < columns.size(); ++j) {
                Column c = columns.get(j);
                FieldTypeDefinition type = c.getType();
                Object value = record.get(c.getProperty());
                if (value == null) {
                    // convert to non-null string, that won't be formatted
                    value = MoreObjects.firstNonNull(c.getNullOption(), "");
                    type = FieldTypeUtil.getFieldType("string");
                }
                content[i][j] = Filters.filter(type, value);
            }
        }

        return content;
    }
}
