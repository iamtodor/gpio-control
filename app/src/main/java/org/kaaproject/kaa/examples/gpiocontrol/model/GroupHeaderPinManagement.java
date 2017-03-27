package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class GroupHeaderPinManagement<T> extends Header {

    private String name;
    private int id;
    private List<T> deviceGroupList;

    @Override public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public int getId() {
        return id;
    }

    @Override public int getChildSize() {
        return deviceGroupList.size();
    }

    @Override public void cancelSelection() {
        for (T deviceGroupToCastObject : deviceGroupList) {
            Group group = (Group) deviceGroupToCastObject;
            group.setSelected(false);
        }
    }

    public void addGroup(int index, T group) {
        deviceGroupList.add(index, group);
    }

    @Override public int childrenCount() {
        return deviceGroupList.size();
    }

    @Override public T childAt(int childPosition) {
        return deviceGroupList.get(childPosition);
    }

    @Override public List<T> getChildList() {
        return deviceGroupList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GroupHeaderPinManagement(String name, int id, List<T> deviceGroupList) {
        this.name = name;
        this.id = id;
        this.deviceGroupList = deviceGroupList;
    }

    public List<T> getDeviceGroupList() {
        return deviceGroupList;
    }

    @Override public String toString() {
        return "GroupHeaderPinManagement{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", deviceGroupList=" + deviceGroupList +
                '}';
    }
}
