package com.bobwares.databus.server.service.locator;

import com.bobwares.databus.server.registry.model.ResourceDefinition;

public interface ResourceLocator {

    ResourceDefinition getResource(String resourceKey);
}
