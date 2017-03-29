package com.bobwares.loader.util;


import com.bobwares.core.util.ObjectUtils;
import com.bobwares.databus.common.model.JobConfiguration;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertNotNull;

public class ObjectUtilsTest {


    @Test
    public void test() throws IOException {

        InputStream inputStream = new ClassPathResource("jobs/inventory-locations.json").getInputStream();

        //        new FileInputStream("jobs/inventory-locations.json");

        JobConfiguration jobConfiguration = ObjectUtils.jsonToObject(inputStream, JobConfiguration.class);
        assertNotNull(jobConfiguration);
    }
}
