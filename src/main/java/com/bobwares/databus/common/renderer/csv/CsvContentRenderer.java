package com.bobwares.databus.common.renderer.csv;

import com.bobwares.core.util.stream.DelimitedMapStreamingOutput;
import com.bobwares.core.util.stream.StreamingOutput;
import com.bobwares.databus.common.annotation.ContentRendererComponent;
import com.bobwares.databus.common.model.ResourceConfiguration;
import com.bobwares.databus.common.model.Field;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.renderer.ContentRenderer;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ContentRendererComponent
public class CsvContentRenderer implements ContentRenderer {

    @Override
    public Object render(ResourceDefinition resourceDefinition, ResponseObject page, MultiValueMap<String, String> parameters) throws IOException {
        List<Map<String, Object>> items = page.getResultsModel().getItems();
        String fileName = getFileName(resourceDefinition);
        HttpHeaders httpHeaders = setHttpHeaders(fileName);
        List<String> headerRow = buildHeaderRow(resourceDefinition.getResourceConfiguration());
        return new ResponseEntity<StreamingOutput>(getStreamingOutput(items, headerRow), httpHeaders, HttpStatus.OK);
    }

    private String getFileName(ResourceDefinition resourceDefinition) {
        if (resourceDefinition.getResourceConfiguration().getFileName() != null) {
            return resourceDefinition.getResourceConfiguration().getFileName();
        } else {
            String resourceUri = resourceDefinition.getResourceUri();
            return resourceUri.substring(resourceUri.lastIndexOf("/")+1);
        }
    }

    private List<String> buildHeaderRow(ResourceConfiguration configuration) {
        List<String> headers = new ArrayList<>();
        List<Field> fields = configuration.getFields();
        for (Field field : fields) {
           headers.add(field.getLabel());
        }
        return headers;
    }

    private HttpHeaders setHttpHeaders(String fileName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "attachment; filename=" + fileName + ".csv");
        httpHeaders.setContentType(MediaType.parseMediaType("text/csv"));
        return httpHeaders;
    }

    private StreamingOutput getStreamingOutput(List<Map<String, Object>> rows, List<String> headers) {
        return new DelimitedMapStreamingOutput<>(rows).setHeaders(headers);
    }
}
