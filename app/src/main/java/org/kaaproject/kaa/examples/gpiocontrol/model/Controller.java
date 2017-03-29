package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.support.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Controller extends RealmObject{

    private String controllerId;
    private @Nullable String imagePath;
    private int vectorId;
    private String portName;
    private boolean isActive;
    @PrimaryKey
    private long id;

    public String getControllerId() {
        return controllerId;
    }

    public void setControllerId(String controllerId) {
        this.controllerId = controllerId;
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

    public String getPortName() {
        return portName;
    }

    public void setPortName(String portName) {
        this.portName = portName;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override public String toString() {
        return "Controller{" +
                "controllerId='" + controllerId + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", vectorId=" + vectorId +
                ", portName='" + portName + '\'' +
                ", isActive=" + isActive +
                ", id=" + id +
                '}';
    }
}
