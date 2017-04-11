package org.kaaproject.kaa.examples.gpiocontrol.storage;


import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;

public class RealmRepository implements Repository {

    private static final String TAG = RealmRepository.class.getSimpleName();

    @Override
    public <T extends RealmObject> void saveModel(final T object) {
        final Realm instance = Realm.getDefaultInstance();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(object);
            }
        });
        instance.close();
    }

    @Override
    public List<Controller> getControllerList() {
        final Realm instance = Realm.getDefaultInstance();
        final List<Controller> controllerList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                controllerList.addAll(realm.where(Controller.class).findAll());
            }
        });
        instance.close();
        return controllerList;
    }

    @Override
    public <T extends RealmObject> void saveModelList(final List<T> modelList) {
        final Realm instance = Realm.getDefaultInstance();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                realm.copyToRealmOrUpdate(modelList);
            }
        });
        instance.close();
    }

    @Override
    public List<Group> getGroupList() {
        final Realm instance = Realm.getDefaultInstance();
//        List<Group> groupList = instance.copyFromRealm(instance.where(Group.class).findAll());
        List<Group> groupList = instance.where(Group.class).findAll();
        instance.close();
        return groupList;
    }

    @Override public RealmList<Alarm> getAlarmListFromGroup(final long groupId) {
        Group group = getGroupById(groupId);
        return group.getAlarmList();
    }

    @Override public Group getGroupById(final long groupId) {
        final Realm instance = Realm.getDefaultInstance();
        Group group = instance.copyFromRealm(instance.where(Group.class).equalTo("id", groupId).findFirst());
        instance.close();
        return group;
    }

    @Override public void addAlarmToGroupList(List<Long> groupIdList, Alarm alarm) {
        for (Long groupId : groupIdList) {
            addAlarmToGroup(groupId, alarm);
        }
    }

    @Override public void addAlarmToGroup(final long groupId, final Alarm alarm) {
        final Realm instance = Realm.getDefaultInstance();
        final Group group = getGroupById(groupId);
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                RealmList<Alarm> alarmList = group.getAlarmList();
                alarmList.add(alarm);
                group.setAlarmList(alarmList);
                RealmList<Device> deviceList = group.getDeviceList();
                for (Device device : deviceList) {
                    device.setHasAlarm(true);
                }
                realm.insertOrUpdate(group);
            }
        });
        instance.close();
    }

    @Override public void turnOnGroup(final long groupId, final boolean turnOn) {
        final Realm instance = Realm.getDefaultInstance();
        final Group group = getGroupById(groupId);
        final List<Device> deviceList = getDeviceListFromGroup(groupId);
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                group.setTurnOn(turnOn);
                for (Device device : deviceList) {
                    device.setTurnOn(turnOn);
                }
            }
        });
        instance.close();
    }

    @Override public void toggleGroup(long groupId) {
        final Realm instance = Realm.getDefaultInstance();
        final List<Device> deviceList = getDeviceListFromGroup(groupId);
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                for (Device device : deviceList) {
                    device.setTurnOn(device.isTurnOn());
                }
            }
        });
        instance.close();
    }

    @Override public List<Device> getDeviceListFromGroup(long groupId) {
        final Realm instance = Realm.getDefaultInstance();
        final List<Device> deviceList = new ArrayList<>();
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                deviceList.addAll(realm.where(Device.class).findAll());
            }
        });
        instance.close();
        return deviceList;
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
        final Realm instance = Realm.getDefaultInstance();
        Number currentMax = instance.where(clazz).max("id");
        long nextId = 0;
        if (currentMax != null) {
            nextId = currentMax.longValue() + 1;
        }
        instance.close();
        return nextId;
    }

    @Override public void turnOnDevice(long deviceId, final boolean turnOn) {
        final Realm instance = Realm.getDefaultInstance();
        final Device device = getDeviceById(deviceId);
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                device.setTurnOn(turnOn);
            }
        });
        instance.close();
    }

    @Override public void setDeviceHasAlarm(long deviceId, final boolean hasAlarm) {
        final Realm instance = Realm.getDefaultInstance();
        final Device device = getDeviceById(deviceId);
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                device.setHasAlarm(hasAlarm);
            }
        });
        instance.close();
    }

    @Override public void turnOnAlarm(final long alarmId, final boolean turnOn) {
        final Realm instance = Realm.getDefaultInstance();
        final Alarm alarm = getAlarmById(alarmId);
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                alarm.setActive(turnOn);
            }
        });
        instance.close();
    }

    @Override public Alarm getAlarmById(long alarmId) {
        final Realm instance = Realm.getDefaultInstance();
        Alarm alarm = instance.where(Alarm.class).equalTo("id", alarmId).findFirst();
        instance.close();
        return alarm;
    }

    @Override public boolean lockDevice(long deviceId) {
        final Realm instance = Realm.getDefaultInstance();
        final Device device = getDeviceById(deviceId);
        instance.executeTransaction(new Realm.Transaction() {
            @Override public void execute(Realm realm) {
                device.setLocked(!device.isLocked());
            }
        });
        boolean isDeviceLocked = device.isLocked();
        instance.close();
        return isDeviceLocked;
    }

    @Override public Device getDeviceById(long deviceId) {
        final Realm instance = Realm.getDefaultInstance();
        Device device = instance.where(Device.class).equalTo("id", deviceId).findFirst();
        instance.close();
        return device;
    }


}
