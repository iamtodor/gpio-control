package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceGroup {

    private String name;
    private boolean isSelected;
    private int iconId;
    private String portStatus;
    private long id;
    private List<DeviceGroup> deviceGroupList;

    public DeviceGroup(String name, boolean isSelected, int iconId, String portStatus, long id,
                       List<DeviceGroup> deviceGroupList) {
        this.name = name;
        this.isSelected = isSelected;
        this.iconId = iconId;
        this.portStatus = portStatus;
        this.id = id;
        this.deviceGroupList = deviceGroupList;
    }

    public String getName() {
        return name;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public int getIconId() {
        return iconId;
    }

    public String getPortStatus() {
        return portStatus;
    }

    public long getId() {
        return id;
    }

    public List<DeviceGroup> getDeviceGroupList() {
        return deviceGroupList;
    }

    @Override public String toString() {
        return "DeviceGroup{" +
                "name='" + name + '\'' +
                ", isSelected=" + isSelected +
                ", iconId=" + iconId +
                ", portStatus='" + portStatus + '\'' +
                ", id=" + id +
                ", deviceGroupList=" + deviceGroupList +
                '}';
    }
}
