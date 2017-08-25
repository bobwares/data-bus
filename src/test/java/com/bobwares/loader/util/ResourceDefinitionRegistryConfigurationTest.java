package com.bobwares.loader.util;

import com.bobwares.databus.LoadResourceConfiguration;
import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.Info;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class ResourceDefinitionRegistryConfigurationTest {

    private ResourceConfiguration resourceConfiguration;

    @Before
    public void setup() {
        resourceConfiguration = LoadResourceConfiguration.loadConfiguration("location", "databus");
    }

    @Test
    public void getParameters()  {
        List<Field> filters = resourceConfiguration.getParameters();

        assertEquals(1, filters.size());
        assertEquals("programId", filters.get(0).getProperty());
    }

    @Test
    public void testInfo(){
        Info info = resourceConfiguration.getInfo();
        assertTrue(info.getDescription().equals("Test Resource Description"));
    }


    @Test
    public void getFields() {
        List<Field> fields = resourceConfiguration.getFields();
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


}