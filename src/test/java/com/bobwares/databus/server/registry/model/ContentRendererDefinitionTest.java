package com.bobwares.databus.server.registry.model;

import com.bobwares.databus.common.renderer.ContentRenderer;
import com.bobwares.databus.server.renderer.ContentRendererTestObject;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ContentRendererDefinitionTest {

    public static final String CONTENT_RENDER_KEY = "contentRenderKey";

    @Test
    public void testGetContentRendererKey() throws Exception {
        ContentRenderer contentRenderer = new ContentRendererTestObject();
        ContentRendererDefinition contentRendererDefinition = new ContentRendererDefinition(CONTENT_RENDER_KEY, contentRenderer);
        assertNotNull(contentRendererDefinition.getContentRenderer());
        assertTrue(contentRendererDefinition.getContentRendererKey().equals(CONTENT_RENDER_KEY));
    }
}