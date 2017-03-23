package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.R;

import java.util.List;

public class ImageDeviceGroup extends BaseDeviceGroup {

    private String imagePath;
    private int drawableId = R.drawable.xboxone_frontview_stockblack;

    public ImageDeviceGroup(String name, String imagePath, String portStatus, String power,
                            boolean toggle, Alarm alarm, boolean isSelected, long id,
                            List<BaseDeviceGroup> baseDeviceGroupList, List<BaseDevice> deviceList) {
        super(name, portStatus, power, toggle, alarm, isSelected, id, baseDeviceGroupList, deviceList);
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override public void loadImage(ImageView imageView) {
        Picasso.with(imageView.getContext()).load(drawableId).fit().centerCrop().into(imageView);
    }

    @Override public String toString() {
        return "ImageDeviceGroup{" +
                "imagePath='" + imagePath + '\'' +
                ", drawableId=" + drawableId +
                '}';
    }
}
