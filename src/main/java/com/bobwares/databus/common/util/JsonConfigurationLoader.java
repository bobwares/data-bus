package com.bobwares.databus.common.util;

import com.bobwares.core.util.ObjectUtils;
import com.bobwares.core.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class JsonConfigurationLoader implements ConfigurationLoader {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
	public <T> Map<String,T> loadConfigurations(String folder, Class<T> configurationType) {
    	Map<String,T> configurationMap = new HashMap<>();

    	int prefixLen = folder.length() + 1;

    	try {
	        Map<String,Resource> resourceMap = ResourceUtils.findClasspathResources(folder, ".json");
	    	for (Map.Entry<String, Resource> entry : resourceMap.entrySet()) {
	    		T configuration = loadResource(entry.getValue(), configurationType);
                if (configuration != null) {
                	//strip off the folder and extension from the resource name before storing in the configuration map
                	String resourceName = entry.getKey();
                	configurationMap.put(resourceName.substring(prefixLen, resourceName.length()-5), configuration);
                }
	    	}
        }
        catch (IOException e) {
        	logger.error("Failed to scan folder {}", folder, e);
        	throw new RuntimeException(e);
        }

    	return configurationMap;
    }

    @Override
	public <T> T loadConfiguration(String fileName, Class<T> configurationType) {
    	return loadResource(new ClassPathResource(fileName), configurationType);
    }

	protected <T> T loadResource(Resource resource, Class<T> configurationType) {
        try (
        	InputStream inputStream = resource.getInputStream();
        ){
            T configuration = ObjectUtils.jsonToObject(inputStream, configurationType);
            Assert.notNull(configuration, "Configuration not found for resource "  + resource);

            return configuration;
        }
        catch (Exception e) {
            logger.error("Failed to load configuration for {}", resource, e);
            return null;
        }
    }

}
