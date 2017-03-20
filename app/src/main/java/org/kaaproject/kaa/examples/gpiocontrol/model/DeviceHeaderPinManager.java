package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceHeaderPinManager<Controller> extends Header {

    private String name;
    private List<Controller> controllerList;
    private int id;
    private boolean selected;

    public DeviceHeaderPinManager(String name, int id, List<Controller> portList) {
        this.name = name;
        this.id = id;
        this.controllerList = portList;
    }

    @Override public String getName() {
        return name;
    }

    @Override public int childrenCount() {
        return controllerList.size();
    }

    @Override public Controller childAt(int childPosition) {
        return controllerList.get(childPosition);
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override public List<Controller> getChildList() {
        return controllerList;
    }

    @Override public int getId() {
        return id;
    }

    @Override public boolean isSelected() {
        return false;
    }

    @Override public int getChildSize() {
        return controllerList.size();
    }

    @Override public void cancelSelection() {
        selected = false;
    }

    @Override public String toString() {
        return "GroupPort{" +
                "name='" + name + '\'' +
                ", controllerList=" + controllerList +
                '}';
    }
}
