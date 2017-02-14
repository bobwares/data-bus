package com.bobwares.databus.server.service.locator;

import com.bobwares.databus.common.service.DataBusService;

public interface ServiceLocator {

	DataBusService getService(String serviceKey);
}
