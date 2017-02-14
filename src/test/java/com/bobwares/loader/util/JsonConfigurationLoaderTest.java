package com.bobwares.loader.util;

import com.bobwares.databus.common.model.JobConfiguration;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;


public class JsonConfigurationLoaderTest {

    @Test
    public void loadConfigurations() throws Exception {
        JsonJobConfigurationLoader jsonConfigurationLoader = new JsonJobConfigurationLoader();

        Map<String, JobConfiguration> jobs = jsonConfigurationLoader.loadConfigurations("jobs", JobConfiguration.class);
        assertNotNull(jobs.get("inventory-locations"));
    }

}