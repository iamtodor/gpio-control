package org.kaaproject.kaa.examples.gpiocontrol.utils;


import org.kaaproject.kaa.examples.gpiocontrol.model.BaseController;
import org.kaaproject.kaa.examples.gpiocontrol.model.BaseDeviceGroup;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class RealmUtils {

    private Realm instance = Realm.getDefaultInstance();

    public void saveControllersToDB(final List<BaseController> controllerList) {
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
//                realm.copyToRealmOrUpdate(controllerList);
            }
        });
    }

    public List<BaseController> getControllersFromDB() {
        final List<BaseController> controllerList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
//                controllerList.addAll(realm.where(BaseController.class).findAll());
            }
        });
        return controllerList;
    }

    public void saveDeviceGroupListToDB(final List<BaseDeviceGroup> deviceGroupList) {
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(deviceGroupList);
            }
        });
    }

    public List<BaseDeviceGroup> getDeviceGroupListFromDB() {
        final List<BaseDeviceGroup> deviceGroupList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                deviceGroupList.addAll(realm.where(BaseDeviceGroup.class).findAll());
            }
        });
        return deviceGroupList;
    }

}
