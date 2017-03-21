package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class GroupHeaderPinManagement<DeviceGroup> extends Header {

    private String name;
    private int id;
    private List<DeviceGroup> deviceGroupList;

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
        for (DeviceGroup deviceGroupToCastObject : deviceGroupList) {
            org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup deviceGroup1 =
                    (org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup) deviceGroupToCastObject;
            deviceGroup1.setSelected(false);
        }
    }

    @Override public int childrenCount() {
        return deviceGroupList.size();
    }

    @Override public DeviceGroup childAt(int childPosition) {
        return deviceGroupList.get(childPosition);
    }

    @Override public List<DeviceGroup> getChildList() {
        return deviceGroupList;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GroupHeaderPinManagement(String name, int id, List<DeviceGroup> deviceGroupList) {
        this.name = name;
        this.id = id;
        this.deviceGroupList = deviceGroupList;
    }

    @Override public String toString() {
        return "GroupHeaderPinManagement{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", deviceGroupList=" + deviceGroupList +
                '}';
    }
}
