package com.bobwares.databus.common.transformer;

import com.bobwares.core.util.ObjectUtils;
import com.bobwares.core.util.ResourceUtils;
import com.bobwares.databus.common.model.PagingObject;
import com.bobwares.databus.common.transformer.service.ColumnTransformerService;
import com.bobwares.test.AbstractDatabaseTest;
import org.junit.Ignore;
import org.junit.Test;

import javax.inject.Inject;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ColumnTransformationServiceTest extends AbstractDatabaseTest {

    private static final String RESOURCE_KEY = "transformer-test";

    @Inject
    ColumnTransformerService columnTransformationService;

    PagingObject page;

@Ignore
    @Test
    public void test() {
        InputStream jsonStream = ResourceUtils.getResourceStream("test-column-transformation-service-page-object.json");
        page = ObjectUtils.jsonToObject(jsonStream, PagingObject.class);
        Collection<Map<String, Object>> transformedRow = columnTransformationService.transform(RESOURCE_KEY, page.getItems());
        assertNotNull("The transformed row should not be null.", transformedRow);
        Object[] columns = transformedRow.toArray();
        assertNotNull(columns);
        LinkedHashMap<String,Object> row = (LinkedHashMap<String, Object>) columns[0];
        assertNotNull(row);
        assertTrue("Row should have two columns.",row.size()==2);

        assertQualifierColumn(row);
        assertArrayColumn(row);

    }

    private void assertQualifierColumn(LinkedHashMap<String, Object> row) {

        Map<String,Object> columnTestQualifier = (Map<String, Object>) row.get("testQualifier");

        assertNotNull(columnTestQualifier);
        assertTrue("column has four entries", columnTestQualifier.size() == 1);

        String data = (String) columnTestQualifier.get("data");
        assertTrue("column has entry data", data.equals("12345"));

        String qualifier1 = (String) columnTestQualifier.get("qualifier1");
        assertTrue("column has entry qualifier1", qualifier1.equals("value of Qualifier 1"));

        String qualifier2 = (String) columnTestQualifier.get("qualifier2");
        assertTrue("column has entry qualifier2", qualifier2.equals("value of Qualifier 2"));

        String qualifier3 = (String) columnTestQualifier.get("qualifier3");
        assertTrue("column has entry qualifier3", qualifier3.equals("value of Qualifier 3"));
    }

    private void assertArrayColumn(LinkedHashMap<String, Object> row) {
        //Object column = (String) row.get("testArray");
        //assertNotNull(column);
    }

}
