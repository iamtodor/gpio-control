package org.kaaproject.kaa.examples.gpiocontrol;

import android.app.Application;

import org.kaaproject.kaa.examples.gpiocontrol.storage.RealmRepository;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;
import org.kaaproject.kaa.examples.gpiocontrol.utils.PreferencesImpl;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private Repository realmRepository;
    private KaaManager kaaManager;

    @Override public void onCreate() {
        super.onCreate();
        PreferencesImpl.init(getApplicationContext());
        Realm.init(this);
        initKaa();
        realmRepository = new RealmRepository();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public Repository getRealmRepository() {
        return realmRepository;
    }

    private void initKaa() {
        try {
            kaaManager = KaaManager.newInstance(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KaaManager getKaaManager() {
        return kaaManager;
    }

}
