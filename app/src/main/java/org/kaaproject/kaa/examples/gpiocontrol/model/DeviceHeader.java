package org.kaaproject.kaa.examples.gpiocontrol.model;


public class DeviceHeader {

    private String name;
    private int id;
    private long mNextChildId;

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

    public DeviceHeader(String name, int id) {
        this.name = name;
        this.id = id;
        mNextChildId = 0;
    }

    public long generateNewChildId() {
        final long id = mNextChildId;
        mNextChildId += 1;
        return id;
    }

    @Override public String toString() {
        return "DeviceHeader{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
