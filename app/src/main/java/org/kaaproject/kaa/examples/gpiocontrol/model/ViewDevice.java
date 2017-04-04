package org.kaaproject.kaa.examples.gpiocontrol.model;


public class ViewDevice {

    private Device device;
    private boolean isChecked;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
