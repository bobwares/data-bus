package com.bobwares.databus.common.renderer.pdf;

import java.util.Collections;
import java.util.Map;

public class PdfHeader {

    String title;
    Map<String, String[]> headers;

    public void setHeaders(Map<String, String[]> headers) {
        this.headers = headers;
    }

    public Map<String, String[]> getHeaders() {
        return headers != null ? headers : Collections.<String, String[]>emptyMap();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
