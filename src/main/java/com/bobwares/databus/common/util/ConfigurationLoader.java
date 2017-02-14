package com.bobwares.databus.common.util;

import java.util.Map;

public interface ConfigurationLoader {

	<T> Map<String,T> loadConfigurations(String folder, Class<T> configurationType);

    <T> T loadConfiguration(String fileName, Class<T> configurationType);
}
