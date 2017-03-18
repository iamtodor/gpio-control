package org.kaaproject.kaa.examples.gpiocontrol.screen.dialog;

import android.net.Uri;

public interface ChooseImageListener {
    void onImageChosen(Uri path);
    void onImageChosen(int drawableId);
}
