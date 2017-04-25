package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.support.annotation.Nullable;

import org.kaaproject.kaa.examples.gpiocontrol.GpioStatus;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class Device extends RealmObject {

    private String name;
    private @Nullable String imagePath;
    private int vectorId;
    private String visibleId;
    private boolean hasAlarm;
    @Ignore
    private boolean isLocked;
    @PrimaryKey
    private int id = -1;
    @Ignore
    private GpioStatus gpioStatus;
    @Ignore
    private String endpointId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@Nullable String imagePath) {
        this.imagePath = imagePath;
    }

    public int getVectorId() {
        return vectorId;
    }

    public void setVectorId(int vectorId) {
        this.vectorId = vectorId;
    }

    public String getVisibleId() {
        return visibleId;
    }

    public void setVisibleId(String visibleId) {
        this.visibleId = visibleId;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isHasAlarm() {
        return hasAlarm;
    }

    public void setHasAlarm(boolean hasAlarm) {
        this.hasAlarm = hasAlarm;
    }

    public GpioStatus getGpioStatus() {
        return gpioStatus;
    }

    public void setGpioStatus(GpioStatus gpioStatus) {
        this.gpioStatus = gpioStatus;
    }

    public String getEndpointId() {
        return endpointId;
    }

    public void setEndpointId(String endpointId) {
        this.endpointId = endpointId;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;

        Device device = (Device) o;

        if (getVectorId() != device.getVectorId()) return false;
        if (isHasAlarm() != device.isHasAlarm()) return false;
        if (getId() != device.getId()) return false;
        if (getName() != null ? !getName().equals(device.getName()) : device.getName() != null)
            return false;
        if (getImagePath() != null ? !getImagePath().equals(device.getImagePath()) : device.getImagePath() != null)
            return false;
        return getVisibleId() != null ? getVisibleId().equals(device.getVisibleId()) : device.getVisibleId() == null;

    }

    @Override public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getImagePath() != null ? getImagePath().hashCode() : 0);
        result = 31 * result + getVectorId();
        result = 31 * result + (getVisibleId() != null ? getVisibleId().hashCode() : 0);
        result = 31 * result + (isHasAlarm() ? 1 : 0);
        result = 31 * result + getId();
        return result;
    }

    @Override public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", vectorId=" + vectorId +
                ", visibleId='" + visibleId + '\'' +
                ", hasAlarm=" + hasAlarm +
                ", isLocked=" + isLocked +
                ", id=" + id +
                ", gpioStatus=" + gpioStatus +
                ", endpointId='" + endpointId + '\'' +
                '}';
    }
}
