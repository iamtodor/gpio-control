package org.kaaproject.kaa.examples.gpiocontrol.model;


import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.R;

public class ImageController extends BaseController {

    private String imagePath;
    private int drawableId = R.drawable.xboxone_frontview_stockblack;

    public ImageController(String controllerId, String portName, boolean isActive,
                           boolean isSelected, long id, String imagePath) {
        super(controllerId, portName, isActive, isSelected, id);
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
}
