package org.kaaproject.kaa.examples.gpiocontrol.utils;


public interface Preferences {

    void saveEmail(String token);
    String getEmail();
    boolean isEmailExists();
    void removeEmail();

}
