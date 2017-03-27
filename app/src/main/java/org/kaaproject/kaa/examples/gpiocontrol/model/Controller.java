package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.support.annotation.Nullable;

public class Controller {

    private String controllerId;
    private @Nullable String imagePath;
    private int vectorId;
    private String portName;
    private boolean isActive;
    private long id;

    private Controller() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static class Builder {

        private Controller controller;

        public Builder() {
            this.controller = new Controller();
        }

        public Builder setControllerId(String controllerId) {
            this.controller.controllerId = controllerId;
            return this;
        }

        public Builder setImagePath(String imagePath) {
            this.controller.imagePath = imagePath;
            return this;
        }

        public Builder setVectorId(int vectorId) {
            this.controller.vectorId = vectorId;
            return this;
        }

        public Builder setPortName(String portName) {
            this.controller.portName = portName;
            return this;
        }

        public Builder setIsActive(boolean isActive) {
            this.controller.isActive = isActive;
            return this;
        }

        public Controller build() {
            if (this.controller.controllerId == null) {
                throw new NullPointerException("Controller id is empty");
            } else if (this.controller.imagePath == null & this.controller.vectorId == -1) {
                throw new NullPointerException("Controller icon is empty");
            } else if (this.controller.portName == null) {
                throw new NullPointerException("Controller port name is empty");
            }
            return this.controller;
        }

    }
}
