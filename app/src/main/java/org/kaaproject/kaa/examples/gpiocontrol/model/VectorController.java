package org.kaaproject.kaa.examples.gpiocontrol.model;

import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

public class VectorController extends BaseController{

    private int iconId;

    public VectorController(String controllerId, String portName, boolean isActive,
                            boolean isSelected, long id, int iconId) {
        super(controllerId, portName, isActive, isSelected, id);
        this.iconId = iconId;
    }

    @Override public void loadImage(ImageView imageView) {
        final Drawable drawable = VectorDrawableCompat.create(imageView.getContext().getResources(),
                iconId, null);
        imageView.setImageDrawable(drawable);
    }
}
