package org.kaaproject.kaa.examples.gpiocontrol;

import android.app.Application;

import org.kaaproject.kaa.examples.gpiocontrol.storage.RealmRepository;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;
import org.kaaproject.kaa.examples.gpiocontrol.utils.PreferencesImpl;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application {

    private Repository realmRepository;

    @Override public void onCreate() {
        super.onCreate();
        PreferencesImpl.init(getApplicationContext());
        Realm.init(this);
        realmRepository = new RealmRepository();
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public Repository getRealmRepository() {
        return realmRepository;
    }

}
