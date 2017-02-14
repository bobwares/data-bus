package com.bobwares.loader.service;

import com.bobwares.databus.common.model.JobExecutionMessage;
import com.bobwares.loader.service.endpoints.JobBuilder;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;


public class JobBuilderTest {

    @Test
    public void build() throws Exception {
        File file = new File("test.inventory-locations");
        JobBuilder jobBuilder = new JobBuilder();
        JobExecutionMessage jobExecutionMessage = jobBuilder.build(file);
        assertNotNull(jobExecutionMessage);
    }

}