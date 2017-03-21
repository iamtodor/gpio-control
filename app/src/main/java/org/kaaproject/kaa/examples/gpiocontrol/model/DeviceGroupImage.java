package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DeviceGroupImage extends SelectableItem {

    private String imagePath;
    private String portStatus;
    private String power;
    private boolean toggle;
    private Alarm alarm;
    private boolean isSelected;
    private long id;
    private List<DeviceGroup> deviceGroupList;
    private List<Device> deviceList;

    public DeviceGroupImage(String name, String imagePath, String portStatus, String power,
                            boolean toggle, Alarm alarm, boolean isSelected, long id,
                            List<DeviceGroup> deviceGroupList, List<Device> deviceList) {
        super(name);
        this.imagePath = imagePath;
        this.portStatus = portStatus;
        this.power = power;
        this.toggle = toggle;
        this.alarm = alarm;
        this.isSelected = isSelected;
        this.id = id;
        this.deviceGroupList = deviceGroupList;
        this.deviceList = deviceList;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public List<DeviceGroup> getDeviceGroupList() {
        return deviceGroupList;
    }

    public void setDeviceGroupList(List<DeviceGroup> deviceGroupList) {
        this.deviceGroupList = deviceGroupList;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<Device> deviceList) {
        this.deviceList = deviceList;
    }

    @Override public String toString() {
        return "DeviceGroup{" +
                ", imagePath='" + imagePath + '\'' +
                ", portStatus='" + portStatus + '\'' +
                ", power='" + power + '\'' +
                ", toggle=" + toggle +
                ", alarm=" + alarm +
                ", isSelected=" + isSelected +
                ", id=" + id +
                ", deviceGroupList=" + deviceGroupList +
                ", deviceList=" + deviceList +
                '}';
    }

    @Override public void loadImage(ImageView imageView) {
        Picasso.with(imageView.getContext()).load(imagePath).fit().centerCrop().into(imageView);
    }

}
