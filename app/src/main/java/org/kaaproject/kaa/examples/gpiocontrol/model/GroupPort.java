package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class GroupPort {

    private String name;
    private String alarm;
    private List<Port> portList;
    private boolean isLocked;

    public GroupPort(String name, String alarm, List<Port> portList, boolean isLocked) {
        this.name = name;
        this.alarm = alarm;
        this.portList = portList;
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

    public List<Port> getPortList() {
        return portList;
    }

    public void setPortList(List<Port> portList) {
        this.portList = portList;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override public String toString() {
        return "GroupPort{" +
                "name='" + name + '\'' +
                ", alarm='" + alarm + '\'' +
                ", portList=" + portList +
                ", isLocked=" + isLocked +
                '}';
    }
}
