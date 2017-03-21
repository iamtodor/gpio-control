package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import java.util.List;

public class DeviceGroup extends BaseDeviceGroup {

    private int iconId;

    public DeviceGroup(String name, int iconId, String portStatus, String power,
                       boolean toggle, Alarm alarm, boolean isSelected, long id,
                       List<DeviceGroup> deviceGroupList, List<Device> deviceList) {
        super(name, portStatus, power, toggle, alarm, isSelected, id, deviceGroupList, deviceList);
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
}
