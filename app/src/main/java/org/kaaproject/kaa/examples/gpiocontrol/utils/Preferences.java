package org.kaaproject.kaa.examples.gpiocontrol.utils;


public interface Preferences {

    void saveEmail(String email);
    String getEmail();
    boolean isEmailExists();
    void removeEmail();
    void cleanUp();
    void savePassword(String password);
    String getPassword();

}
