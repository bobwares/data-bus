package com.bobwares.databus.common.util;

import org.springframework.util.Assert;

public class DataBusDatabaseContext {

    private static final ThreadLocal<String> dataBusContextHolder = new ThreadLocal<>();

    public static void setDatabase(String dataSourceName) {
        Assert.notNull(dataSourceName, "dataSourceName cannot be null");
        dataBusContextHolder.set(dataSourceName);
    }

    public static String getDatabase() {
        return dataBusContextHolder.get();
    }

    public static void clearDatabase() {
        dataBusContextHolder.remove();
    }
}
