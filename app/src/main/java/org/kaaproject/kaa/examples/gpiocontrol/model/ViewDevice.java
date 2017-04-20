package org.kaaproject.kaa.examples.gpiocontrol.model;


public class ViewDevice {

    private Device device;
    private boolean isSelected;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override public String toString() {
        return "ViewDevice{" +
                "device=" + device +
                ", isSelected=" + isSelected +
                '}';
    }
}
