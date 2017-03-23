package org.kaaproject.kaa.examples.gpiocontrol.model;


public class ListImageDevice {

    private ImageDevice imageDevice;
    private boolean isSelected;

    public ImageDevice getImageDevice() {
        return imageDevice;
    }

    public void setImageDevice(ImageDevice imageDevice) {
        this.imageDevice = imageDevice;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override public String toString() {
        return "ListImageDevice{" +
                "imageDevice=" + imageDevice +
                ", isSelected=" + isSelected +
                '}';
    }
}
