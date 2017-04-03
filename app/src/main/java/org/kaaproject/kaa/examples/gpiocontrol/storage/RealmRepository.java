package org.kaaproject.kaa.examples.gpiocontrol.storage;


import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

public class RealmRepository implements Repository {

    private Realm instance = Realm.getDefaultInstance();

    @Override
    public <T extends RealmObject> void saveModel(final T object) {
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
    }

    @Override
    public List<Controller> getControllerList() {
        final List<Controller> controllerList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                controllerList.addAll(realm.where(Controller.class).findAll());
            }
        });
        return controllerList;
    }

    @Override
    public <T extends RealmObject> void saveModelList(final List<T> modelList) {
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(modelList);
            }
        });
    }

    @Override
    public List<Group> getDeviceGroupList() {
        final List<Group> deviceGroupList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                deviceGroupList.addAll(realm.where(Group.class).findAll());
            }
        });
        return deviceGroupList;
    }

    @Override public List<Alarm> getAlarmList() {
        final List<Alarm> deviceGroupList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                deviceGroupList.addAll(realm.where(Alarm.class).findAll());
            }
        });
        return deviceGroupList;
    }

    /**
     * According to the auto increment issue https://github.com/realm/realm-java/issues/469
     * we need to work around by our own
     *
     * @param clazz instance of RealmObject model
     * @return actual id that can be setted to the object
     */
    @Override
    public long getIdForModel(Class<? extends RealmObject> clazz) {
        Number currentMax = instance.where(clazz).max("id");
        long nextId = 0;
        if (currentMax != null) {
            nextId = currentMax.longValue() + 1;
        }
        return nextId;
    }

}
