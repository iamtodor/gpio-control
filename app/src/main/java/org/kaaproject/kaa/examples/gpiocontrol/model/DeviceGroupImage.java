package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DeviceGroupImage extends BaseDeviceGroup {

    private String imagePath;

    public DeviceGroupImage(String name, String imagePath, String portStatus, String power,
                            boolean toggle, Alarm alarm, boolean isSelected, long id,
                            List<DeviceGroup> deviceGroupList, List<Device> deviceList) {
        super(name, portStatus, power, toggle, alarm, isSelected, id, deviceGroupList, deviceList);
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override public void loadImage(ImageView imageView) {
        Picasso.with(imageView.getContext()).load(imagePath).fit().centerCrop().into(imageView);
    }

    @Override public String toString() {
        return "DeviceGroup{" +
                ", imagePath='" + imagePath + '\'' +
                ", portStatus='" + getPortStatus() + '\'' +
                ", power='" + getPower() + '\'' +
                ", toggle=" + isToggle() +
                ", alarm=" + getAlarm() +
                ", isSelected=" + isSelected() +
                ", id=" + getId() +
                ", deviceGroupList=" + getDeviceGroupList() +
                ", deviceList=" + getDeviceList() +
                '}';
    }

}
