package com.bobwares.databus.common.transformer;

import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.transformer.transformers.ArrayPropertyColumnTransformer;
import com.bobwares.databus.common.transformer.transformers.ColumnTransformer;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class ArrayColumnTransformerTest {

    @Test
    public void testTransformer() {
        Configuration configuration;
        Field foo = new Field();
        Field bar = new Field();

        foo.setProperty("foo");
        foo.setType("array");

        bar.setProperty("barReadOnly");
        bar.setType("boolean");

        configuration = new Configuration();
        configuration.setFields(Arrays.asList(foo, bar));

        Map<String, Object> row = new HashMap<>();
        row.put("recordCount", 1);
        row.put(foo.getProperty() + "1", 100);
        row.put(foo.getProperty() + "2", true);
        row.put(foo.getProperty() + "3", "text");
        row.put(bar.getProperty(), true);

        ColumnTransformer columnTransformer = new ArrayPropertyColumnTransformer();
        Map<String, Object> transformedRow = columnTransformer.transform(configuration, row);

        assertNotNull(transformedRow);
        assertTrue((transformedRow.size()==3));
        Object foo1 = transformedRow.get("foo");
        assertTrue((transformedRow.size()==3));

    }
}
