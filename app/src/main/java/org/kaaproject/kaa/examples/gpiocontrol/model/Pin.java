package org.kaaproject.kaa.examples.gpiocontrol.model;


public class Pin {

    private boolean isActive;

    public Pin(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override public String toString() {
        return "Pin{" +
                "isActive=" + isActive +
                '}';
    }
}
