package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.support.annotation.Nullable;

public class Device {

    private String name;
    private @Nullable String imagePath;
    private int vectorId;
    private String portTitle;
    private String portId;
    private boolean isOn;
    private boolean isLocked;
    private int id = -1;
    private boolean isSelected;

    private Device() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPortTitle() {
        return portTitle;
    }

    public String getPortId() {
        return portId;
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isLocked() {
        return isLocked;
    }

    @Nullable public String getImagePath() {
        return imagePath;
    }

    public int getVectorId() {
        return vectorId;
    }

    public int getId() {
        return id;
    }

    public boolean isSelected() {
        return isSelected();
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

    public static class Builder {

        private Device device;

        public Builder() {
            this.device = new Device();
        }

        public Builder setName(String name) {
            this.device.name = name;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.device.imagePath = imagePath;
            return this;
        }

        public Builder setVectorId(int vectorId) {
            this.device.vectorId = vectorId;
            return this;
        }

        public Builder setPortTitle(String portTitle) {
            this.device.portTitle = portTitle;
            return this;
        }

        public Builder setPortId(String portId) {
            this.device.portId = portId;
            return this;
        }

        public Builder setIsOn(boolean isOn) {
            this.device.isOn = isOn;
            return this;
        }

        public Builder setIsLocked(boolean isLocked) {
            this.device.isLocked = isLocked;
            return this;
        }

        public Builder setId(int id) {
            this.device.id = id;
            return this;
        }

        public Builder setIsSelected(boolean isSelected) {
            this.device.isSelected = isSelected;
            return this;
        }

        public Device build() {
            if (this.device.name == null) {
                throw new NullPointerException("Device name is empty");
            } else if (this.device.imagePath == null & this.device.vectorId == -1) {
                throw new NullPointerException("Device icon is empty");
            } else if (this.device.portTitle == null) {
                throw new NullPointerException("Device port title is empty");
            } else if (this.device.portId == null) {
                throw new NullPointerException("Device port id is null");
            } else if (this.device.id == -1) {
                throw new NullPointerException("Device id isn't specified");
            }
            return this.device;
        }

    }
}
