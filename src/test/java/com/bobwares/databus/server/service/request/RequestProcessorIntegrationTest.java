package com.bobwares.databus.server.service.request;

import com.bobwares.databus.common.model.ResponseObject;
import com.bobwares.test.AbstractRestTest;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.inject.Inject;
import java.util.LinkedList;

import static junit.framework.TestCase.assertNotNull;

public class RequestProcessorIntegrationTest extends AbstractRestTest {

    private static final String RESOURCE_KEY_1 = "request-processor-test";
    private static final String RESOURCE_KEY_2 = "bus-tests/text-welcome-header";

    private MultiValueMap<String, String> allFilters;

    @Inject
    private RequestProcessor requestProcessor;

    @Before
    public void setup() {
        buildParameters();
    }

    private void buildParameters() {
        allFilters = new LinkedMultiValueMap<>();
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("value_1");
        allFilters.put("af-value_1_key",linkedList);

        LinkedList<String> linkedList2 = new LinkedList<>();
        linkedList2.add("value_2");
        allFilters.put("filter-value_2_key",linkedList2);

        linkedList = new LinkedList<>();
        linkedList.add("1");
        allFilters.put("pageNum",linkedList);

        linkedList = new LinkedList<>();
        linkedList.add("-1");
        allFilters.put("pageSize",linkedList);

        linkedList = new LinkedList<>();
        linkedList.add("");
        allFilters.put("sort",linkedList);

        linkedList = new LinkedList<>();
        linkedList.add("");
        allFilters.put("reverse",linkedList);
    }

    @Test
    @Ignore
    public void test() throws NoSuchMethodException {
        ResponseObject page = requestProcessor.processRequest(new RequestObject(allFilters, RESOURCE_KEY_1, RequestSource.EXTERNAL));
        assertNotNull(page);
    }

    @Ignore
    @Test(expected = NoSuchMethodException.class)
    public void testScopePrivate() throws NoSuchMethodException {
        requestProcessor.processRequest(new RequestObject(allFilters, RESOURCE_KEY_2, RequestSource.EXTERNAL));
    }
}
