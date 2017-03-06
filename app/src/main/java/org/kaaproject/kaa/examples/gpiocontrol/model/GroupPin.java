package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class GroupPin {

    private String name;
    private String alarm;
    private List<Pin> pinList;
    private boolean isLocked;

    public GroupPin(String name, String alarm, List<Pin> pinList, boolean isLocked) {
        this.name = name;
        this.alarm = alarm;
        this.pinList = pinList;
        this.isLocked = isLocked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public List<Pin> getPinList() {
        return pinList;
    }

    public void setPinList(List<Pin> pinList) {
        this.pinList = pinList;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override public String toString() {
        return "GroupPin{" +
                "name='" + name + '\'' +
                ", alarm='" + alarm + '\'' +
                ", pinList=" + pinList +
                ", isLocked=" + isLocked +
                '}';
    }
}
