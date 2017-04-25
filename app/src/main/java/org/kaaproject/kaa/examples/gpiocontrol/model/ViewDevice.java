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

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ViewDevice)) return false;

        ViewDevice that = (ViewDevice) o;

        if (isSelected() != that.isSelected()) return false;
        return getDevice() != null ? getDevice().equals(that.getDevice()) : that.getDevice() == null;

    }

    @Override public int hashCode() {
        int result = getDevice() != null ? getDevice().hashCode() : 0;
        result = 31 * result + (isSelected() ? 1 : 0);
        return result;
    }
}
