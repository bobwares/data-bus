package com.bobwares.databus.common.service;

import org.springframework.stereotype.Service;

@Service
public class RequestUriParserImpl implements RequestUriParser {

    public String parse(String uri) {
        if (uri.endsWith("/field")) {
            return uri.substring(uri.indexOf("databus") + 8, uri.indexOf("field") - 1);
        } else {
            return uri.substring(uri.indexOf("databus") + 8);
        }
    }
}
