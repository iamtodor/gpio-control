package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceGroupHeaderPinManagement<GroupPort> extends Header {

    private String name;
    private int id;
    private List<GroupPort> controllerList;
    private boolean selected;

    @Override public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public int getId() {
        return id;
    }

    @Override public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeviceGroupHeaderPinManagement(String name, int id, List<GroupPort> controllerList) {
        this.name = name;
        this.id = id;
        this.controllerList = controllerList;
    }

    @Override public int childrenCount() {
        return controllerList.size();
    }

    @Override public GroupPort childAt(int childPosition) {
        return controllerList.get(childPosition);
    }

    @Override public List<GroupPort> getControllerList() {
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
