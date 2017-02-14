package com.bobwares.databus.bus.util;

import com.bobwares.databus.bus.model.ResourceMessage;

import java.util.List;
import java.util.Map;


public interface NodeBuilder {

    Map<String,Object> build(List<ResourceMessage> resourceMessages);

}
