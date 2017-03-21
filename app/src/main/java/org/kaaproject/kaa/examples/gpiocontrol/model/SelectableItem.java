package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

public abstract class SelectableItem {

    private String name;

    public SelectableItem(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void loadImage(ImageView imageView);
}
