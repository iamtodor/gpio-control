package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceHeaderPinManager<Controller> extends Header {

    private String name;
    private List<Controller> controllerList;
    private int id;

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

    @Override public List<Controller> getControllerList() {
        return controllerList;
    }

    @Override public int getId() {
        return id;
    }

    @Override public String toString() {
        return "GroupPort{" +
                "name='" + name + '\'' +
                ", controllerList=" + controllerList +
                '}';
    }
}
