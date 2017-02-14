package com.bobwares.databus;

import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.util.JsonConfigurationLoader;


public class LoadResourceConfiguration {

    public static ResourceConfiguration loadConfiguration(String key, String path) {
        String configurationFileName = path + "/" + key + ".json";
        JsonConfigurationLoader jsonConfigurationLoader = new JsonConfigurationLoader();
        return jsonConfigurationLoader.loadConfiguration(configurationFileName, ResourceConfiguration.class);
    }
}
