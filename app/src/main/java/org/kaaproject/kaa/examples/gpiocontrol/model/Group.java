package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.support.annotation.Nullable;

import java.util.List;

public class Group {

    private String name;
    private @Nullable String imagePath;
    private int vectorId = -1;
    private String portStatus;
    private String power;
    private boolean toggle;
    private Alarm alarm;
    private long id;
    private List<Group> groupList;
    private List<Device> deviceList;

    public String getName() {
        return name;
    }

    public String getPortStatus() {
        return portStatus;
    }

    public String getPower() {
        return power;
    }

    public boolean isToggle() {
        return toggle;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public long getId() {
        return id;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    @Nullable public String getImagePath() {
        return imagePath;
    }

    public int getVectorId() {
        return vectorId;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    @Override public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", portStatus='" + portStatus + '\'' +
                ", power='" + power + '\'' +
                ", toggle=" + toggle +
                ", alarm=" + alarm +
                ", id=" + id +
                ", groupList=" + groupList +
                ", deviceList=" + deviceList +
                '}';
    }

    public static class Builder {

        private Group group;

        public Builder() {
            this.group = new Group();
        }

        public Builder setName(String name) {
            this.group.name = name;
            return this;
        }

        public Builder setImagePath(String path) {
            this.group.imagePath = path;
            return this;
        }

        public Builder setVectorImage(int vectorId) {
            this.group.vectorId = vectorId;
            return this;
        }

        public Builder setPortStatus(String portStatus) {
            this.group.portStatus = portStatus;
            return this;
        }

        public Builder setPortPower(String power) {
            this.group.power = power;
            return this;
        }

        public Builder setToggle(boolean isToggle) {
            this.group.toggle = isToggle;
            return this;
        }

        public Builder setAlarm(Alarm alarm) {
            this.group.alarm = alarm;
            return this;
        }

        public Builder setId(int id) {
            this.group.id = id;
            return this;
        }

        public Builder setGroupList(List<Group> groupList) {
            this.group.groupList = groupList;
            return this;
        }

        public Builder setDeviceList(List<Device> deviceList) {
            group.deviceList = deviceList;
            return this;
        }

        public Group build() {
            if (this.group.name == null) {
                throw new NullPointerException("Group name is empty");
            } else if (this.group.imagePath == null & this.group.vectorId == -1) {
                throw new NullPointerException("Group icon is empty");
            } else if (this.group.portStatus == null) {
                throw new NullPointerException("Group port status is empty");
            } else if (this.group.power == null) {
                throw new NullPointerException("Group power is empty");
            } else if (this.group.deviceList == null) {
                throw new NullPointerException("Group device list is null");
            }
            return this.group;
        }
    }

}
