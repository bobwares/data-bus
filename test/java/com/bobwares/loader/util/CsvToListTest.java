package com.bobwares.loader.util;

import com.bobwares.databus.common.model.JobConfiguration;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

public class CsvToListTest {


    @Test
    public void test() throws IOException, URISyntaxException {

        JsonJobConfigurationLoader jsonConfigurationLoader = new JsonJobConfigurationLoader();

        Map<String, JobConfiguration> jobs = jsonConfigurationLoader.loadConfigurations("jobs", JobConfiguration.class);
        assertNotNull(jobs.get("inventory-locations"));
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.inventory-locations").getFile());

        //File file = new File("test.inventory-locations");
        CsvToListImpl csvToJson = new CsvToListImpl();
        List<MultiValueMap<String, Object>> convert = csvToJson.convert(file, jobs.get("inventory-locations").getFields());
        assertNotNull(convert);
    }

}