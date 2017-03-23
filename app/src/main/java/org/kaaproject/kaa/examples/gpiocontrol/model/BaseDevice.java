package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

public abstract class BaseDevice {

    private String name;
    private String portTitle;
    private String portId;
    private boolean isOn;
    private boolean isLocked;

    public BaseDevice(String name, String portTitle, String portId, boolean isOn, boolean isLocked) {
        this.name = name;
        this.portTitle = portTitle;
        this.portId = portId;
        this.isOn = isOn;
        this.isLocked = isLocked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    abstract public void loadImage(ImageView imageView);

    public String getPortTitle() {
        return portTitle;
    }

    public void setPortTitle(String portTitle) {
        this.portTitle = portTitle;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override public String toString() {
        return "BaseDevice{" +
                "name='" + name + '\'' +
                ", portTitle='" + portTitle + '\'' +
                ", portId='" + portId + '\'' +
                ", isOn=" + isOn +
                ", isLocked=" + isLocked +
                '}';
    }
}
