package com.bobwares.databus.bus.service.endpoint;

import com.bobwares.databus.bus.model.ResourceMessage;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import com.bobwares.databus.server.service.request.RequestObject;
import com.bobwares.databus.server.service.request.RequestSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Splitter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@MessageEndpoint
public class SplitRequests {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private ResourceLocator resourceLocator;

    @Inject
    public void setResourceLocator(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    @Splitter(inputChannel = "splitterChannel", outputChannel = "serviceChannel")
    public List<ResourceMessage> handle(ResourceMessage originalResourceMessage) throws Exception {

        List<ResourceMessage> resourceMessages = new ArrayList<>();

        try {
            for (String resourceUri : originalResourceMessage.getResourceConfiguration().getResources()) {
                ResourceMessage newResourceMessage = buildMessage(originalResourceMessage, resourceUri);
                resourceMessages.add(newResourceMessage);
            }
        } catch(Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
        return resourceMessages;
    }

    private ResourceMessage buildMessage(ResourceMessage originalResourceMessage, String resourceUri) throws NoSuchMethodException {
        RequestObject requestObject = new RequestObject(originalResourceMessage.getRequestObject(),resourceUri, RequestSource.EXTERNAL);

        ResourceDefinition resourceDefinition = resourceLocator.getResource(resourceUri);

        ResourceMessage resourceMessage = new ResourceMessage(requestObject, resourceDefinition);
        return resourceMessage;
    }
}
