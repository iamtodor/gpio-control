package org.kaaproject.kaa.examples.gpiocontrol.model;

public class Controller {

    private String controllerId;
    private String portName;
    private int imagePortsDrawableId;
    private boolean isActive;
    private boolean isSelected;
    private long id;

    public Controller(String controllerId, String portName, int imagePortsDrawableId,
                      boolean isActive, boolean isSelected, long id) {
        this.controllerId = controllerId;
        this.portName = portName;
        this.imagePortsDrawableId = imagePortsDrawableId;
        this.isActive = isActive;
        this.isSelected = isSelected;
        this.id = id;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
    }

    public String getControllerId() {
        return controllerId;
    }

    public String getPortName() {
        return portName;
    }

    public int getImagePortsDrawableId() {
        return imagePortsDrawableId;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override public String toString() {
        return "Controller{" +
                "controllerId='" + controllerId + '\'' +
                ", portName='" + portName + '\'' +
                ", imagePortsDrawableId=" + imagePortsDrawableId +
                ", isActive=" + isActive +
                ", isSelected=" + isSelected +
                ", id=" + id +
                '}';
    }
}
