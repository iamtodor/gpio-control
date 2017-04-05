package org.kaaproject.kaa.examples.gpiocontrol.storage;


import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmRepository implements Repository {

    private static final String TAG = RealmRepository.class.getSimpleName();
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
    public List<Group> getGroupList() {
        final List<Group> groupList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                groupList.addAll(realm.where(Group.class).findAll());
            }
        });
        return groupList;
    }

    @Override public Group getGroupById(final long id) {
        Group group = instance.where(Group.class).equalTo("id", id).findFirst();
        return instance.copyFromRealm(group);
    }

    @Override public RealmList<Alarm> getAlarmList(final long groupId) {
        Group group = getGroupById(groupId);
        return group.getAlarmList();
    }

    @Override public void addAlarmToGroup(final long groupId, final Alarm alarm) {
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                Group group = getGroupById(groupId);
                RealmList<Alarm> alarmList = group.getAlarmList();
                alarmList.add(alarm);
                group.setAlarmList(alarmList);
                realm.insertOrUpdate(group);
            }
        });
    }

    @Override public void addAlarmToGroupList(List<Long> groupIdList, Alarm alarm) {
        for (Long groupId : groupIdList) {
            addAlarmToGroup(groupId, alarm);
        }
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
