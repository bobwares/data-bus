package com.bobwares.databus.bus.service.endpoint;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;
import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ParametersTransformer {

    @Transformer(inputChannel="requestChannel", outputChannel = "splitterChannel")
    public ResourceMessage handle(ResourceMessage resourceMessage) {
        Map<String, String> parameters = resourceMessage.getFilters();

        ResourceConfiguration configuration = resourceMessage.getResourceConfiguration();

        if (configuration.getParameters() != null) {
            for (Field field : configuration.getParameters()) {
                //apply the default value if there is one
                if (field.getValue() != null) {
                    String filter = (String) parameters.get(field.getProperty());
                    if (filter==null) {
                        parameters.put(field.getProperty(),field.getValue());
                    } else {
                        filter=field.getValue();
                    }
                }
            }
        }
        return resourceMessage;
    }
}
