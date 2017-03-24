package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import java.util.List;

import io.realm.RealmModel;

public class VectorDeviceGroup extends BaseDeviceGroup implements RealmModel {

    private int iconId;

    public VectorDeviceGroup() {}

    public VectorDeviceGroup(String name, int iconId, String portStatus, String power,
                             boolean toggle, Alarm alarm, boolean isSelected, long id,
                             List<BaseDeviceGroup> deviceGroupVectorList, List<BaseDevice> baseDeviceList) {
        super(name, portStatus, power, toggle, alarm, isSelected, id, deviceGroupVectorList, baseDeviceList);
        this.iconId = iconId;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    @Override public void loadImage(ImageView imageView) {
        final Drawable drawable = VectorDrawableCompat.create(imageView.getContext().getResources(),
                iconId, null);
        imageView.setImageDrawable(drawable);
    }

    @Override public String toString() {
        return "VectorDeviceGroup{" +
                "iconId=" + iconId +
                '}';
    }
}
