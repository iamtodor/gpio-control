package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

import java.util.List;

import io.realm.RealmModel;

public abstract class BaseDeviceGroup implements RealmModel{

    private String name;
    private String portStatus;
    private String power;
    private boolean toggle;
    private Alarm alarm;
    private boolean isSelected;
    private long id;
    private List<BaseDeviceGroup> baseDeviceGroupList;
    private List<BaseDevice> deviceList;

    public BaseDeviceGroup() {
    }

    BaseDeviceGroup(String name, String portStatus, String power,
                    boolean toggle, Alarm alarm, boolean isSelected, long id,
                    List<BaseDeviceGroup> baseDeviceGroupList, List<BaseDevice> deviceList) {
        this.name = name;
        this.portStatus = portStatus;
        this.power = power;
        this.toggle = toggle;
        this.alarm = alarm;
        this.isSelected = isSelected;
        this.id = id;
        this.baseDeviceGroupList = baseDeviceGroupList;
        this.deviceList = deviceList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<BaseDevice> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<BaseDevice> deviceList) {
        this.deviceList = deviceList;
    }

    public abstract void loadImage(ImageView imageView);

    @Override public String toString() {
        return "BaseDeviceGroup{" +
                "name='" + name + '\'' +
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
