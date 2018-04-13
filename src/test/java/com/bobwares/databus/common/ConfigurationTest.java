package com.bobwares.databus.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.Info;
import com.bobwares.databus.common.util.ConfigurationLoader;
import com.bobwares.test.AbstractDatabaseTest;

public class ConfigurationTest extends AbstractDatabaseTest {

    @Inject ConfigurationLoader configurationLoader;

    Configuration configuration;


    @Before
    public void setup() {
        configuration = configurationLoader.loadConfiguration("databus/example-report.json", Configuration.class);
    }

    @Test
    public void getFilters()  {
        List<Field> filters = configuration.getFilters();

        assertEquals(1, filters.size());
        assertEquals("programId", filters.get(0).getProperty());
    }

    @Test
    public void testInfo(){
        Info info = configuration.getInfo();
        assertTrue(info.getDescription().equals("Test Resource Description"));
    }

    @Test
    public void getStoredProcedure() {
        assertEquals("TEST_STORED_PROC", configuration.getStoredProcedure());
    }

    @Test
    public void getTitle() {
        assertEquals("Example Report", configuration.getTitle());
    }

    @Test
    public void getOrientation() {
        assertEquals("landscape", configuration.getOrientation());
    }
    @Test
    public void getFileName() {
        assertEquals("filename", configuration.getFileName());
    }
    @Test
    public void getFields() {
        List<Field> fields = configuration.getFields();
        assertEquals(7, fields.size());

        List<String> properties = Lists.newArrayList(
            "monthEarned",
            "dataSource",
            "earnedAmount",
            "expirationDate",
            "balance",
            "moneyId",
            "paxId"
        );
        for (Field field: fields) {
            assertTrue(properties.contains(field.getProperty()));
        }
    }

    @Test
    public void mappedFields() {
        List<ImmutableMap<String, Object>> fields = configuration.mappedFields();
        for (ImmutableMap<String, Object> field: fields) {
            assertNotNull(field.get("readOnly"));
            assertNotNull(field.get("hidden"));
            assertNotNull(field.get("property"));
            assertNotNull(field.get("required"));
            assertNotNull(field.get("type"));
            assertNotNull(field.get("width"));
        }
    }
}