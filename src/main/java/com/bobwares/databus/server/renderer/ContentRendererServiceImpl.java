package com.bobwares.databus.server.renderer;

import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.util.PageFilter;
import com.bobwares.databus.server.registry.model.ContentRendererDefinition;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.databus.server.registry.service.RegistryService;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Map;

@Service
public class ContentRendererServiceImpl implements ContentRendererService {

    public static final String FORMAT_KEY = "format";

    private ResourceLocator resourceLocator;

    @Inject
    public void setResourceLocator(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    private RegistryService<ContentRendererDefinition> contentRendererRegistry;

    @Inject
    public void setContentRendererRegistry(RegistryService<ContentRendererDefinition> contentRendererRegistry) {
        this.contentRendererRegistry = contentRendererRegistry;
    }

    @Override
    public Object render(String resourceKey, ResponseObject responseObject, MultiValueMap<String, Object> parameters) throws IOException, NoSuchMethodException {
        ResourceDefinition resourceDefinition = resourceLocator.getResource(resourceKey);

        Map<String, String> filters = PageFilter.getFilters(parameters);
        if (filters.containsKey(FORMAT_KEY)) {
            String contentRendererKey = filters.get(FORMAT_KEY);
            ContentRendererDefinition contentRendererDefinition = contentRendererRegistry.getEntry(contentRendererKey);
            return contentRendererDefinition.getContentRenderer().render(resourceDefinition, responseObject, parameters);
        } else {
            return responseObject.getResultsModel();
        }
    }
}
