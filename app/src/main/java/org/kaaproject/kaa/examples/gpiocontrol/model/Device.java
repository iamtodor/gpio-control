package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Device extends SelectableItem {

    private String name;
    private String imagePath;
    private String portTitle;
    private String portId;
    private boolean isOn;
    private boolean isLocked;

    public Device(String name) {
        super(name);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public void loadImage(ImageView imageView) {
        Picasso.with(imageView.getContext()).load(imagePath).fit().centerCrop().into(imageView);
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    @Override public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", portTitle='" + portTitle + '\'' +
                ", portId='" + portId + '\'' +
                ", isOn=" + isOn +
                ", isLocked=" + isLocked +
                '}';
    }
}
