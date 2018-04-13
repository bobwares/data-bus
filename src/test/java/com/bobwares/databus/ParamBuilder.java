package com.bobwares.databus;

import com.bobwares.core.web.util.HttpQueryParameters;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

public class ParamBuilder {

    public static MultiValueMap<String, Object> build() {
        return build("1","-1", "", "false");
    }

    public static MultiValueMap<String, Object> build(String pageNum, String pageSize, String sort, String reverse) {
        MultiValueMap<String, Object> allFilters = new LinkedMultiValueMap<>();

        allFilters.add("af-value_1_key", "value_1");
        allFilters.add("filter-value_2_key", "value_2");

        if (pageNum != null) allFilters.add("pageNum", pageNum);
        if (pageSize != null) allFilters.add("pageSize", pageSize);
        if (sort != null) allFilters.add("sort", sort);
        if (reverse != null) allFilters.add("reverse", reverse);

        return allFilters;
    }

    public static Map<String, String[]> toRequestParamMap(MultiValueMap<String, String> params) {
    	HttpQueryParameters qp = new HttpQueryParameters();
    	for (Map.Entry<String, List<String>> entry : params.entrySet()) {
    		for (String value : entry.getValue()) {
    			qp.setParameter(entry.getKey(), value);
    		}

    	}
    	return qp.getParameterMap();
    }

    public static MultiValueMap<String, Object> build2() {
        MultiValueMap<String, Object> allFilters = new LinkedMultiValueMap<>();

        allFilters.add("name", "Unit Operations Lab");
        allFilters.add("superLocation", "Building Q");
        allFilters.add("geotype", "Building");


        return allFilters;
    }
}
