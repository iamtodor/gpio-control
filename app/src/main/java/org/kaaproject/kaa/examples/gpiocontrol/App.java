package org.kaaproject.kaa.examples.gpiocontrol;

import android.app.Application;

import org.kaaproject.kaa.examples.gpiocontrol.utils.PreferencesImpl;

public class App extends Application {

    @Override public void onCreate() {
        super.onCreate();
        PreferencesImpl.init(getApplicationContext());
    }
}
