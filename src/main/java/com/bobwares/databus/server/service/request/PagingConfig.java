package com.bobwares.databus.server.service.request;

import com.bobwares.databus.common.util.PageFilter;
import org.springframework.util.MultiValueMap;

public class PagingConfig {
    private final int pageNum;
    private final int pageSize;
    private final String sort;
    private final boolean reverse;

    public PagingConfig( MultiValueMap<String, String> parameters) {
        String pageNumParam = PageFilter.getKey(parameters, "pageNum");
        if (pageNumParam == null || pageNumParam.isEmpty()) {
            this.pageNum = 1;
        } else {
            this.pageNum = Integer.parseInt(pageNumParam);
        }

        String pageSizeParam = PageFilter.getKey(parameters, "pageSize");
        if (pageSizeParam == null || pageSizeParam.isEmpty()) {
            this.pageSize = -1;
        } else {
            this.pageSize = Integer.parseInt(pageSizeParam);
        }

        this.sort = PageFilter.getKey(parameters, "sort");

        String reverseParam = PageFilter.getKey(parameters, "reverse");
        if (reverseParam == null || reverseParam.isEmpty()) {
            this.reverse = false;
        } else {
            this.reverse = Boolean.valueOf(reverseParam);
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getSort() {
        return sort;
    }

    public boolean isReverse() {
        return reverse;
    }
}
