package com.bobwares.databus.server.renderer;

import java.io.IOException;

import org.springframework.util.MultiValueMap;

import com.bobwares.databus.common.model.ResponseObject;

public interface ContentRendererService {

    Object render(String resourceKey, ResponseObject page, MultiValueMap<String, Object> parameters) throws IOException, NoSuchMethodException;
}
