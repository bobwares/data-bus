package com.bobwares.databus.common.service;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class RequestUriParserImplTest {

    @Test
    public void testParse() throws Exception {
        RequestUriParser requestUriParser = new RequestUriParserImpl();
        String resourceUri = requestUriParser.parse("databus/uri1/uri2");
        assertTrue(resourceUri.equals("uri1/uri2"));


    }  @Test
       public void testParseField() throws Exception {
        RequestUriParser requestUriParser = new RequestUriParserImpl();
        String resourceUri = requestUriParser.parse("databus/uri1/uri2/field");
        assertTrue(resourceUri.equals("uri1/uri2"));


    }
}