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

    List<Controller> getControllerList();

    List<Group> getGroupList();
    Group getGroupById(long id);

    void addAlarmToGroup(final long groupId, final Alarm alarm);
    void addAlarmToGroupList(List<Long> groupIdList, final Alarm alarm);

    void turnOnGroup(long groupId, boolean turnOn);

    List<Device> getDeviceListFromGroup(final long groupId);
    RealmList<Alarm> getAlarmListFromGroup(final long groupId);

    long getIdForModel(Class<? extends RealmObject> clazz);

}
