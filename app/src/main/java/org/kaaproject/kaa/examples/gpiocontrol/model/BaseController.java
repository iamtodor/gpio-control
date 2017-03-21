package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

public abstract class BaseController {

    private String controllerId;
    private String portName;
    private boolean isActive;
    private boolean isSelected;
    private long id;

    public BaseController(String controllerId, String portName, boolean isActive,
                          boolean isSelected, long id) {
        this.controllerId = controllerId;
        this.portName = portName;
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

    public abstract void loadImage(ImageView imageView);

}
