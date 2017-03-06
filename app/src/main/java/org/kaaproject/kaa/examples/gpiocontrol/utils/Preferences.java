package org.kaaproject.kaa.examples.gpiocontrol.utils;


public interface Preferences {

    void saveGoogleToken(String token);
    String getToken();
    boolean isTokenExists();

}
