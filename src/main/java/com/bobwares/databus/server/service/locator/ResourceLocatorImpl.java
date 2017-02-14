package com.bobwares.databus.server.service.locator;


import com.bobwares.databus.common.rest.NotFoundException;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.registry.service.RegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ResourceLocatorImpl implements ResourceLocator {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private RegistryService<ResourceDefinition> resourceRegistry;

    @Inject
    public void setResourceRegistry(RegistryService<ResourceDefinition> resourceRegistry) {
        this.resourceRegistry = resourceRegistry;
    }

    @Override
    public ResourceDefinition getResource(String resourceKey) {
        ResourceDefinition resourceDefinition = resourceRegistry.getEntry(resourceKey);
        if (resourceDefinition == null) throw new NotFoundException("resourceKey not found:" + resourceKey);
        return resourceDefinition;
    }
}
