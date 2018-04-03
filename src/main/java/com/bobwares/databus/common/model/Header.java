package com.bobwares.databus.common.model;

import java.util.List;

public class Header {
	
    String title;
    List<HeaderField> fields;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HeaderField> getFields() {
        return fields;
    }

    public void setFields(List<HeaderField> fields) {
        this.fields = fields;
    }
}
