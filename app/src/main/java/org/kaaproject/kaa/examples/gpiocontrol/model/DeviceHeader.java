package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceHeader<T> extends Header {

    private String name;
    private List<T> controllerList;
    private int id;

    public DeviceHeader(String name, int id, List<T> portList) {
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

    @Override public T childAt(int childPosition) {
        return controllerList.get(childPosition);
    }

    @Override public List<T> getChildList() {
        return controllerList;
    }

    @Override public int getId() {
        return id;
    }

    @Override public int getChildSize() {
        return controllerList.size();
    }

    @Override public void cancelSelection() {
        for (T controllerToCastObject : controllerList) {
            Device device = (Device) controllerToCastObject;
            device.setSelected(false);
        }
    }

    @Override public String toString() {
        return "GroupPort{" +
                "name='" + name + '\'' +
                ", controllerList=" + controllerList +
                '}';
    }
}
