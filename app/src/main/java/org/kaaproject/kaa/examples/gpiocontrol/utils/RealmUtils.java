package org.kaaproject.kaa.examples.gpiocontrol.utils;


import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class RealmUtils {

    private Realm instance;

    public void saveControllersToDB(final List<Controller> controllerList) {
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(controllerList);
            }
        });
    }

    public List<Controller> getControllersFromDB() {
        final List<Controller> controllerList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
//                controllerList.addAll(realm.where(BaseController.class).findAll());
            }
        });
        return controllerList;
    }

    public void saveDeviceGroupListToDB(final List<Group> deviceGroupList) {
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(deviceGroupList);
            }
        });
    }

    public List<Group> getDeviceGroupListFromDB() {
        final List<Group> deviceGroupList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
//                deviceGroupList.addAll(realm.where(BaseDeviceGroup.class).findAll());
            }
        });
        return deviceGroupList;
    }

}
