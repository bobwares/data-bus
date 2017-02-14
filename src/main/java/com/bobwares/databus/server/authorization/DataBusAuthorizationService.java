package com.bobwares.databus.server.authorization;

public interface DataBusAuthorizationService {
    boolean isAuthorized(String authKey);
}
