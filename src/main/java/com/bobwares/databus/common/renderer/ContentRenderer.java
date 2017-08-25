package com.bobwares.databus.common.renderer;

import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import org.springframework.util.MultiValueMap;

import java.io.IOException;

public interface ContentRenderer {

    Object render(ResourceDefinition resourceDefinition, ResponseObject page, MultiValueMap<String, String> parameters) throws IOException;

}
