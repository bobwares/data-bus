package com.bobwares.databus.common.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class InfoTest {

    private static final String TEST_DESCRIPTION = "test description";
    public static final String TEST_TITLE = "test title";
    public static final String TEST_VERSION = "test version";

    @Test
    public void test() throws Exception {
        Info info = new Info();
        info.setDescription(TEST_DESCRIPTION);
        info.setTitle(TEST_TITLE);
        info.setVersion(TEST_VERSION);
        assertTrue(info.getDescription().equals(TEST_DESCRIPTION));
        assertTrue(info.getTitle().equals(TEST_TITLE));
        assertTrue(info.getVersion().equals(TEST_VERSION));
    }

}