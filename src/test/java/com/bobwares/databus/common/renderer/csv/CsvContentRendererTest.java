package com.bobwares.databus.common.renderer.csv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import com.bobwares.databus.LoadConfiguration;
import com.bobwares.databus.LoadPagingObject;
import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.common.model.Configuration;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.model.ResultsModel;
import com.bobwares.databus.server.registry.model.ResourceDefinition;
import com.bobwares.test.AbstractRestTest;

public class CsvContentRendererTest extends AbstractRestTest{

    private static final String PATH_DATABUS = "databus";
    private static final String TEST_CONFIGURATION = "example-report";

    Configuration configuration;
    ResourceDefinition resourceDefinition;
    MultiValueMap<String, String> parameters;

    @Before
    public void setup() {
        configuration = LoadConfiguration.loadConfiguration(TEST_CONFIGURATION, PATH_DATABUS);
        assertNotNull(configuration);
        resourceDefinition = new ResourceDefinition("example-report", configuration);
        parameters = ParamBuilder.build();
    }

    @Test
    public void render() throws Exception {
        CsvContentRenderer csvContentRenderer = new CsvContentRenderer();
        ResponseObject responseObject = new ResponseObject();
        ResultsModel pagingObject = LoadPagingObject.loadFromJson("paging-object.json");
        responseObject.setResultsModel(pagingObject);
        ResponseEntity<?> render = (ResponseEntity<?>) csvContentRenderer.render(resourceDefinition, responseObject, parameters);

        assertTrue(render.getHeaders().get("Content-Type").get(0).equals("text/csv"));
    }

}