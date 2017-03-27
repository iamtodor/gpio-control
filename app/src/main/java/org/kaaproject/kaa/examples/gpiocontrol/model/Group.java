package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Group {

    private String name;
    private @Nullable String imagePath;
    private int vector;
    private String iconType;
    private String portStatus;
    private String power;
    private boolean toggle;
    private Alarm alarm;
    private boolean isSelected;
    private long id;
    private List<Group> groupList;
    private List<BaseDevice> deviceList;

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

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<Group> groupList) {
        this.groupList = groupList;
    }

    @Nullable public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@Nullable String imagePath) {
        this.imagePath = imagePath;
    }

    public int getVector() {
        return vector;
    }

    public void setVector(int vector) {
        this.vector = vector;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public void setIconTo(ImageView imageView) {
        IconType.valueOf(iconType).setIconTo(this, imageView);
    }

    private enum IconType {
        URL {
            @Override void setIconTo(Group group, ImageView imageView) {
                Picasso.with(imageView.getContext()).load(group.imagePath).fit().centerCrop().into(imageView);
            }
        },
        VECTOR_RES {
            @Override void setIconTo(Group group, ImageView imageView) {
                final Drawable drawable = VectorDrawableCompat.create(imageView.getContext().getResources(),
                        group.vector, null);
                imageView.setImageDrawable(drawable);
            }
        };

        abstract void setIconTo(Group group, ImageView imageView);
    }

    public List<BaseDevice> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(List<BaseDevice> deviceList) {
        this.deviceList = deviceList;
    }

    @Override public String toString() {
        return "BaseDeviceGroup{" +
                "name='" + name + '\'' +
                ", portStatus='" + portStatus + '\'' +
                ", power='" + power + '\'' +
                ", toggle=" + toggle +
                ", alarm=" + alarm +
                ", isSelected=" + isSelected +
                ", id=" + id +
                ", groupList=" + groupList +
                ", deviceList=" + deviceList +
                '}';
    }

    public static class ICON_TYPE {
        public static final String URL = "URL";
        public static final String VECTOR_RES = "VECTOR_RES";
    }
}
