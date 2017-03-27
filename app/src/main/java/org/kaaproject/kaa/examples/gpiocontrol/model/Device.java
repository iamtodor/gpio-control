package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class Device {

    private String name;
    private @Nullable String imagePath;
    private int vector;
    private String portTitle;
    private String portId;
    private boolean isOn;
    private boolean isLocked;
    private String iconType;

    public Device(String name, String portTitle, String portId, boolean isOn, boolean isLocked,
                  @Nullable String imagePath, int vector, String iconType) {
        this.name = name;
        this.portTitle = portTitle;
        this.portId = portId;
        this.isOn = isOn;
        this.isLocked = isLocked;
        this.imagePath = imagePath;
        this.vector = vector;
        this.iconType = iconType;
    }

    public String getName() {
        return name;
    }

    public Device setName(String name) {
        this.name = name;
        return this;
    }

    public String getPortTitle() {
        return portTitle;
    }

    public Device setPortTitle(String portTitle) {
        this.portTitle = portTitle;
        return this;
    }

    public String getPortId() {
        return portId;
    }

    public Device setPortId(String portId) {
        this.portId = portId;
        return this;
    }

    public boolean isOn() {
        return isOn;
    }

    public Device setOn(boolean on) {
        isOn = on;
        return this;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public Device setLocked(boolean locked) {
        isLocked = locked;
        return this;
    }

    public void setIconTo(ImageView imageView) {
        IconType.valueOf(iconType).setIconTo(this, imageView);
    }

    private enum IconType {
        URL {
            @Override void setIconTo(Device device, ImageView imageView) {
                Picasso.with(imageView.getContext()).load(device.imagePath).fit().centerCrop().into(imageView);
            }
        },
        VECTOR_RES {
            @Override void setIconTo(Device device, ImageView imageView) {
                final Drawable drawable = VectorDrawableCompat.create(imageView.getContext().getResources(),
                        device.vector, null);
                imageView.setImageDrawable(drawable);
            }
        };

        abstract void setIconTo(Device device, ImageView imageView);
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
