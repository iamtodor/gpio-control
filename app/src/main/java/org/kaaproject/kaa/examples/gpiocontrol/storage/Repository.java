package org.kaaproject.kaa.examples.gpiocontrol.storage;


import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.List;

import io.realm.RealmObject;

public interface Repository {

    <T extends RealmObject> void saveModelToDB(final T object);
    <T extends RealmObject> void saveModelListToDB(final List<T> objectList);

    List<Controller> getControllersFromDB();
    List<Group> getDeviceGroupListFromDB();

    long getIdForModel(Class<? extends RealmObject> clazz);

}
