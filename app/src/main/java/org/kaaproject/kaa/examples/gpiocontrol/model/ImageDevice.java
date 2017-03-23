package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageDevice extends BaseDevice {

    private String imagePath;

    public ImageDevice(String name, String portTitle, String portId, boolean isOn, boolean isLocked,
                       String imagePath) {
        super(name, portTitle, portId, isOn, isLocked);
        this.imagePath = imagePath;
    }

    @Override public void loadImage(ImageView imageView) {
        Picasso.with(imageView.getContext()).load(imagePath).fit().centerCrop().into(imageView);
    }

    @Override public String toString() {
        return "ImageDevice{" +
                ", imagePath='" + imagePath + '\'' +
                '}';
    }
}
