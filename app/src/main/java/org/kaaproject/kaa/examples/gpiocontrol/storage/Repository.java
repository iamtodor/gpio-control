package org.kaaproject.kaa.examples.gpiocontrol.storage;


import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public interface Repository {

    <T extends RealmObject> void saveModel(final T object);
    <T extends RealmObject> void saveModelList(final List<T> objectList);

    Controller getControllerById(long controllerId);
    List<Controller> getControllerList();
    void addDeviceListToController(long controllerId, RealmList<Device> deviceList);

    List<Group> getGroupList();
    Group getGroupById(long groupId);
    void removeGroup(long groupId);
    void removeGroupList(List<Long> groupIdList);

    void addAlarmToGroup(final long groupId, final Alarm alarm);
    void addAlarmToGroupList(List<Long> groupIdList, final Alarm alarm);

    void turnOnGroup(long groupId, boolean turnOn);
    void toggleGroup(long groupId);

    List<Device> getDeviceListFromGroup(final long groupId);
    RealmList<Alarm> getAlarmListFromGroup(final long groupId);

    long getIdForModel(Class<? extends RealmObject> clazz);

    Device getDeviceById(long deviceId);
    void setDeviceHasAlarm(long deviceId, final boolean hasAlarm);

    void turnOnAlarm(long alarmId, boolean turnOn);
    Alarm getAlarmById(long alarmId);

    List<Device> getDeviceList();
}
