package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public abstract class BaseDeviceGroup extends SelectableItem {

    private String portStatus;
    private String power;
    private boolean toggle;
    private Alarm alarm;
    private boolean isSelected;
    private long id;
    private List<BaseDeviceGroup> baseDeviceGroupList;
    private List<Device> deviceList;

    BaseDeviceGroup(String name, String portStatus, String power,
                    boolean toggle, Alarm alarm, boolean isSelected, long id,
                    List<BaseDeviceGroup> baseDeviceGroupList, List<Device> deviceList) {
        super(name);
        this.portStatus = portStatus;
        this.power = power;
        this.toggle = toggle;
        this.alarm = alarm;
        this.isSelected = isSelected;
        this.id = id;
        this.baseDeviceGroupList = baseDeviceGroupList;
        this.deviceList = deviceList;
    }

    public String getPortStatus() {
        return portStatus;
    }

    public void setPortStatus(String portStatus) {
        this.portStatus = portStatus;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<BaseDeviceGroup> getBaseDeviceGroupList() {
        return baseDeviceGroupList;
    }

    public void setBaseDeviceGroupList(List<BaseDeviceGroup> baseDeviceGroupList) {
        this.baseDeviceGroupList = baseDeviceGroupList;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override public String toString() {
        return "BaseDeviceGroup{" +
                ", portStatus='" + portStatus + '\'' +
                ", power='" + power + '\'' +
                ", toggle=" + toggle +
                ", alarm=" + alarm +
                ", isSelected=" + isSelected +
                ", id=" + id +
                ", baseDeviceGroupList=" + baseDeviceGroupList +
                ", deviceList=" + deviceList +
                '}';
    }

}
