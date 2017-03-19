package org.kaaproject.kaa.examples.gpiocontrol.screen.dialog;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;

public class BaseDialog extends DialogFragment {

    public void show(FragmentManager manager) {
        show(manager, getClass().getSimpleName());
    }

}
