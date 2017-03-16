package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceHeaderPinManager extends Header{

    private String name;
    private String alarm;
    private List<Controller> controllerList;
    private boolean isLocked;

    public DeviceHeaderPinManager(String name, String alarm, List<Controller> portList, boolean isLocked) {
        this.name = name;
        this.alarm = alarm;
        this.controllerList = portList;
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

    public List<Controller> getControllerList() {
        return controllerList;
    }

    public void setControllerList(List<Controller> controllerList) {
        this.controllerList = controllerList;
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
                ", controllerList=" + controllerList +
                ", isLocked=" + isLocked +
                '}';
    }
}
