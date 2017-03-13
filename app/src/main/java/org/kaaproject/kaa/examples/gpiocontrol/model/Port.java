package org.kaaproject.kaa.examples.gpiocontrol.model;


public class Port {

    private boolean isActive;

    public Port(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override public String toString() {
        return "Port{" +
                "isActive=" + isActive +
                '}';
    }
}
