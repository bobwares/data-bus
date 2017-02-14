package com.bobwares.databus.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

public class PagingObject implements ResultsModel {

	private List<Map<String, Object>> items;
	private int startIndex;
	private int endIndex;
	private Integer pageNum; // page number - 1 based
    private Integer pageSize; // selected page size
    private int itemCount;   // total number of record possible if no filtering is performed

    public PagingObject() {
    }

    public PagingObject(Integer pageNum, Integer pageSize, List<Map<String, Object>> items) {
        this.itemCount = getItemCount(items);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.items = items;
        setStartIndex();
        setEndIndex();
    }

    public int getStartIndex() {
    	return startIndex;
    }

    public int getEndIndex() {
    	return endIndex;
    }

    public Integer getPageNum() {
    	return pageNum;
    }

    public Integer getPageSize() {
    	return pageSize;
    }

    public int getItemCount() {
    	return itemCount;
    }

    private void setEndIndex() {
        endIndex = pageSize == -1 ? itemCount : Math.min(itemCount, startIndex + pageSize);
    }

    private void setStartIndex() {
        if (pageSize == null) {
            pageSize = -1;    //all pages
            pageNum = 1;
        } else if (pageNum == null) {
            pageNum = 1;
        }
        startIndex = Math.max(0, (pageNum - 1) * pageSize);
    }

    @Override
	@JsonInclude(JsonInclude.Include.NON_NULL)
    public List<Map<String, Object>> getItems() {
        return items;
    }

    @Override
    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    private int getItemCount(List<Map<String, Object>> items) {
        int itemCount = 0;

        if (items != null && !items.isEmpty() && items.get(0) != null) {
            Integer recordCount = (Integer) items.get(0).get("recordCount");
            if (recordCount != null) itemCount = recordCount.intValue();
        }

        return itemCount;
    }

}
