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
    private String portTitle;
    private String portId;
    @Ignore
    private boolean isTurnOn;
    private boolean hasAlarm;
    @Ignore
    private boolean isLocked;
    @PrimaryKey
    private int id = -1;
    @Ignore
    private GpioStatus gpioStatus;

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

    public String getPortTitle() {
        return portTitle;
    }

    public void setPortTitle(String portTitle) {
        this.portTitle = portTitle;
    }

    public String getPortId() {
        return portId;
    }

    public void setPortId(String portId) {
        this.portId = portId;
    }

    public boolean isTurnOn() {
        return isTurnOn;
    }

    public void setTurnOn(boolean turnOn) {
        isTurnOn = turnOn;
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

    @Override public String toString() {
        return "BaseDevice{" +
                "name='" + name + '\'' +
                ", portTitle='" + portTitle + '\'' +
                ", portId='" + portId + '\'' +
                ", setAlarmTurnOn=" + isTurnOn +
                ", isLocked=" + isLocked +
                '}';
    }

}
