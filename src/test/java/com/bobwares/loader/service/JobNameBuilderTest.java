package com.bobwares.loader.service;


import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class JobNameBuilderTest {

    @Test
    public void testGetJobKey() throws Exception {
        JobNameBuilder jobNameBuilder = new JobNameBuilderImpl();
        String jobKey = jobNameBuilder.getJobKey("test.job_name_ext");
        assertTrue(jobKey.equals("job_name_ext"));
    }

    @Test
    public void testNoDotExtension() throws Exception {
        JobNameBuilder jobNameBuilder = new JobNameBuilderImpl();
        String jobKey = jobNameBuilder.getJobKey("test");
        assertNull(jobKey);
    }


    @Test
    public void testTwoDotExtensions() throws Exception {
        JobNameBuilder jobNameBuilder = new JobNameBuilderImpl();
        String jobKey = jobNameBuilder.getJobKey("test.dot+extension.job_name_ext");
        assertTrue(jobKey.equals("job_name_ext"));
    }

}
