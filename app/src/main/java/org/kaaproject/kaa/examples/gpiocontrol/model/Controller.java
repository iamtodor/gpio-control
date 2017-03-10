package org.kaaproject.kaa.examples.gpiocontrol.model;

public class Controller {

    private String controllerId;
    private String portName;
    private int imagePortsDrawableId;
    private boolean isActive;

    public Controller(String controllerId, String portName, int imagePortsDrawableId, boolean isActive) {
        this.controllerId = controllerId;
        this.portName = portName;
        this.imagePortsDrawableId = imagePortsDrawableId;
        this.isActive = isActive;
    }

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public int getImagePortsDrawableId() {
        return imagePortsDrawableId;
    }

    public void setImagePortsDrawableId(int imagePortsDrawableId) {
        this.imagePortsDrawableId = imagePortsDrawableId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override public String toString() {
        return "Controller{" +
                "controllerId='" + controllerId + '\'' +
                ", portName='" + portName + '\'' +
                ", imagePortsDrawableId=" + imagePortsDrawableId +
                ", isActive=" + isActive +
                '}';
    }
}
