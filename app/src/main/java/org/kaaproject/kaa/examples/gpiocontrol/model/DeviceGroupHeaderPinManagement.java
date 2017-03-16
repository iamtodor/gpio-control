package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceGroupHeaderPinManagement extends Header {

    private String name;
    private int id;
    private List<Controller> controllerList;

    @Override public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeviceGroupHeaderPinManagement(String name, int id, List<Controller> controllerList) {
        this.name = name;
        this.id = id;
        this.controllerList = controllerList;
    }

    @Override public int childrenCount() {
        return controllerList.size();
    }

    @Override public Controller childAt(int childPosition) {
        return controllerList.get(childPosition);
    }

    @Override public List<Controller> getControllerList() {
        return controllerList;
    }

    @Override public String toString() {
        return "DeviceGroupHeaderPinManagement{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", controllerList=" + controllerList +
                '}';
    }
}
