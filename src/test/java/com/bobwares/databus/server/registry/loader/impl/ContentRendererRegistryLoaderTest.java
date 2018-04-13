package com.bobwares.databus.server.registry.loader.impl;

import com.bobwares.core.bean.Registry;
import com.bobwares.databus.common.renderer.ContentRenderer;
import com.bobwares.databus.common.renderer.pdf.PdfContentRenderer;
import com.bobwares.databus.server.registry.model.ContentRendererDefinition;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class ContentRendererRegistryLoaderTest {

    @Mock
    private ApplicationContext applicationContext;

    private Registry<ContentRendererDefinition> contentRendererDefinitionRegistry;

    private ContentRendererRegistryLoader contentRendererRegistryLoader;


    @Before
    public void setup() {
        contentRendererDefinitionRegistry = new Registry<>();
        contentRendererRegistryLoader = new ContentRendererRegistryLoader();
        MockitoAnnotations.initMocks(this);
        contentRendererRegistryLoader.setApplicationContext(applicationContext);
        contentRendererRegistryLoader.setContentRendererRegistry(contentRendererDefinitionRegistry);

    }

    @Test
    public void test() {
        ContentRenderer contentRenderer = new PdfContentRenderer();
        when(applicationContext.getBean("pdfContentRenderer")).thenReturn(contentRenderer);
        contentRendererRegistryLoader.load();
        assertNotNull(contentRendererDefinitionRegistry);
    }
}
