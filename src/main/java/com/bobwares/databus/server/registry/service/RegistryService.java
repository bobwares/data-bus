package com.bobwares.databus.server.registry.service;

import java.util.List;
import java.util.Map;

public interface RegistryService<T> {

    T getEntry(String key);

    Map<String, T> getEntries();

    List<T> getList();
}
