package com.bobwares.databus.server.renderer;

import com.bobwares.databus.ParamBuilder;
import com.bobwares.databus.common.model.ResourceBusObject;
import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.databus.common.model.ResultsModel;
import com.bobwares.databus.server.registry.service.ContentRendererRegistry;
import com.bobwares.databus.server.service.locator.ResourceLocator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertNotNull;

public class ContentRendererServiceImplTest {

    private ContentRendererServiceImpl contentRendererService;

    private static final String RESOURCE_KEY_1 = "resourceKey_1";

    private static final String PAGE_NUM = "1";
    private static final String PAGE_SIZE = "1";
    private static final String SORT = "";
    private static final String REVERSE = "false";

    @Mock
    ContentRendererRegistry contentRendererRegistry;

    @Mock
    ResourceLocator resourceLocator;

    private MultiValueMap<String, String> params;

    @Before
    public void setUp() throws Exception {
        params = ParamBuilder.build(PAGE_NUM, PAGE_SIZE, SORT, REVERSE);
        MockitoAnnotations.initMocks(this);
        contentRendererService = new ContentRendererServiceImpl();
        contentRendererService.setContentRendererRegistry(contentRendererRegistry);
        contentRendererService.setResourceLocator(resourceLocator);
    }

    @Test
    public void test() throws Exception {
        ResponseObject page = new ResponseObject();
        ResultsModel resultsModel = new ResourceBusObject();
        page.setResultsModel(resultsModel);
        Object render = contentRendererService.render(RESOURCE_KEY_1, page, params);
        assertNotNull(render);
    }
}