package com.bobwares.databus.bus.model;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ResponseMessageTest {

    @Test
    public void test() throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();

        Map<String,Object> nodes= new HashMap<>();

        responseMessage.setNodes(nodes);
        responseMessage.setErrorMessage("Error Message");
        assertNotNull(responseMessage.getNodes());
        assertTrue(responseMessage.getErrorMessage().equals("Error Message"));

    }
}