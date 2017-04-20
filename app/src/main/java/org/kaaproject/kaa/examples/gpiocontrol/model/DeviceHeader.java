package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceHeader<T> extends Header {

    private String name;
    private List<T> deviceList;
    private int id;

    public DeviceHeader(String name, int id, List<T> deviceList) {
        this.name = name;
        this.id = id;
        this.deviceList = deviceList;
    }

    @Override public String getName() {
        return name;
    }

    @Override public int childrenCount() {
        return deviceList.size();
    }

    @Override public T childAt(int childPosition) {
        return deviceList.get(childPosition);
    }

    @Override public List<T> getChildList() {
        return deviceList;
    }

    @Override public int getId() {
        return id;
    }

    @Override public int getChildSize() {
        return deviceList.size();
    }

    @Override public void cancelSelection() {
        for (T viewDeviceToCastObject : deviceList) {
            ViewDevice viewDevice = (ViewDevice) viewDeviceToCastObject;
            viewDevice.setSelected(false);
        }
    }

    @Override public String toString() {
        return "DeviceHeader{" +
                "name='" + name + '\'' +
                ", deviceList=" + deviceList +
                '}';
    }
}
