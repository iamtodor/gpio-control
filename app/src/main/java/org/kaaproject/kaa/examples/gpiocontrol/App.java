package org.kaaproject.kaa.examples.gpiocontrol;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.kaaproject.kaa.examples.gpiocontrol.utils.PreferencesImpl;

import io.realm.Realm;

public class App extends Application {

    private RefWatcher refWatcher;

    @Override public void onCreate() {
        super.onCreate();
        PreferencesImpl.init(getApplicationContext());
        Realm.init(this);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static RefWatcher getRefWatcher(Context context) {
        App application = (App) context.getApplicationContext();
        return application.refWatcher;
    }
}
