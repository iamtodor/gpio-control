package org.kaaproject.kaa.examples.gpiocontrol.model;

public class Controller {

    private String controllerId;
    private String portName;
    private int imagePortsDrawableId;

    public Controller(String controllerId, String portName, int imagePortsDrawableId) {
        this.controllerId = controllerId;
        this.portName = portName;
        this.imagePortsDrawableId = imagePortsDrawableId;
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

    @Override public String toString() {
        return "Controller{" +
                "controllerId='" + controllerId + '\'' +
                ", portName='" + portName + '\'' +
                ", imagePortsDrawableId=" + imagePortsDrawableId +
                '}';
    }
}
