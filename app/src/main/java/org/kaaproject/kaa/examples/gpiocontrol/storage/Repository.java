package org.kaaproject.kaa.examples.gpiocontrol.storage;


import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmObject;

public interface Repository {

    <T extends RealmObject> void saveModel(final T object);
    <T extends RealmObject> void saveModelList(final List<T> objectList);

    List<Controller> getControllerList();
    List<Group> getGroupList();
    List<Group> getGroupListById(ArrayList<Long> idList);
    Group getGroupById(long id);

    List<Alarm> getAlarmList();

    long getIdForModel(Class<? extends RealmObject> clazz);

}
