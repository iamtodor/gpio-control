package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Device extends RealmObject {

    private String name;
    private @Nullable String imagePath;
    private int vectorId;
    private String portTitle;
    private String portId;
    private boolean isOn;
    private boolean isLocked;
    @PrimaryKey
    private int id = -1;

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

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
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

    @Override public String toString() {
        return "BaseDevice{" +
                "name='" + name + '\'' +
                ", portTitle='" + portTitle + '\'' +
                ", portId='" + portId + '\'' +
                ", isOn=" + isOn +
                ", isLocked=" + isLocked +
                '}';
    }
}
