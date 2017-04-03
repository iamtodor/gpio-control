package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.support.annotation.Nullable;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Group extends RealmObject {

    private String name;
    private @Nullable String imagePath;
    private int vectorId = -1;
    private String portStatus;
    private String power;
    private boolean toggle;
    private Alarm alarm;
    @PrimaryKey
    private long id;
    private boolean isSelected;
    private boolean isOn;
    private RealmList<Group> groupList;
    private RealmList<Device> deviceList;
    private RealmList<Alarm> alarmList;

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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public List<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(RealmList<Group> groupList) {
        this.groupList = groupList;
    }

    public List<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList(RealmList<Device> deviceList) {
        this.deviceList = deviceList;
    }

    public RealmList<Alarm> getAlarmList() {
        return alarmList;
    }

    public void setAlarmList(RealmList<Alarm> alarmList) {
        this.alarmList = alarmList;
    }

    @Override public String toString() {
        return "Group{" +
                "name='" + name + '\'' +
                ", portStatus='" + portStatus + '\'' +
                ", power='" + power + '\'' +
                ", toggle=" + toggle +
                ", alarm=" + alarm +
                ", id=" + id +
                ", groupList=" + groupList +
                ", deviceList=" + deviceList +
                '}';
    }

}
