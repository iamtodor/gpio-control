package org.kaaproject.kaa.examples.gpiocontrol.model;


public class DeviceGroup {

    private String name;
    private boolean isSelected;
    private int iconId;
    private String portStatus;
    private long id;

    public DeviceGroup(String name, boolean isSelected, int iconId, String portStatus, long id) {
        this.name = name;
        this.isSelected = isSelected;
        this.iconId = iconId;
        this.portStatus = portStatus;
        this.id = id;
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

    @Override public String toString() {
        return "DeviceGroup{" +
                "name='" + name + '\'' +
                ", isSelected=" + isSelected +
                ", iconId=" + iconId +
                ", portStatus='" + portStatus + '\'' +
                '}';
    }
}
