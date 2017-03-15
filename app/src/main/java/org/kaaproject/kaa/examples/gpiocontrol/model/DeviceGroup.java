package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class DeviceGroup {

    private String name;
    private int id;
    private long mNextChildId;
    private List<Controller> controllerList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public DeviceGroup(String name, int id, List<Controller> controllerList) {
        this.name = name;
        this.id = id;
        this.controllerList = controllerList;
        mNextChildId = 0;
    }

    public long generateNewChildId() {
        final long id = mNextChildId;
        mNextChildId += 1;
        return id;
    }

    @Override public String toString() {
        return "DeviceGroup{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }

    public int childrenCount() {
        return controllerList.size();
    }

    public Controller childAt(int childPosition) {
        return controllerList.get(childPosition);
    }
}
