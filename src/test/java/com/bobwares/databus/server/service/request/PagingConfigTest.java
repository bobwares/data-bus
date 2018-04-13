package com.bobwares.databus.server.service.request;

import com.bobwares.databus.ParamBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.MultiValueMap;

import static org.junit.Assert.assertTrue;


public class PagingConfigTest {


    private PagingConfig pagingConfig;

    @Before
    public void setup() {

    }

    @Test
    public void testGetPageNum() throws Exception {
        MultiValueMap<String, String> parameters = ParamBuilder.build("2", "20", "firstName", "true");
        pagingConfig = new PagingConfig(parameters);
        int pageNum = pagingConfig.getPageNum();
        assertTrue(pageNum == 2);

        int pageSize = pagingConfig.getPageSize();
        assertTrue(pageSize == 20);

        String sort = pagingConfig.getSort();
        assertTrue(sort.equals("firstName"));

        boolean reverse = pagingConfig.isReverse();
        assertTrue(reverse == true);

        parameters = ParamBuilder.build("2", "20", "firstName", "false");
        pagingConfig = new PagingConfig(parameters);
        reverse = pagingConfig.isReverse();
        assertTrue(reverse == false);
    }

    @Test
    public void testNulls() throws Exception {
        MultiValueMap<String, String> parameters = ParamBuilder.build("", "", "", "");
        pagingConfig = new PagingConfig(parameters);
        int pageNum = pagingConfig.getPageNum();
        assertTrue(pageNum == 1);

    }
    @Test
    public void testNulls2() throws Exception {
        MultiValueMap<String, String> parameters = ParamBuilder.build(null, null, null, null);
        pagingConfig = new PagingConfig(parameters);
        int pageNum = pagingConfig.getPageNum();
        assertTrue(pageNum == 1);

    }


}