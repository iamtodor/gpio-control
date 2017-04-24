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
