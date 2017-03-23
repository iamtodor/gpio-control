package org.kaaproject.kaa.examples.gpiocontrol.model;


import io.realm.RealmObject;

public class Alarm extends RealmObject {

    private String time;
    private String action;
    private String name;
    private String iteration;
    private boolean isActive;

    public Alarm() {}

    public String getTime() {
        return time;
    }

    public Alarm setTime(String time) {
        this.time = time;
        return this;
    }

    public String getAction() {
        return action;
    }

    public Alarm setAction(String action) {
        this.action = action;
        return this;
    }

    public String getName() {
        return name;
    }

    public Alarm setName(String name) {
        this.name = name;
        return this;
    }

    public String getIteration() {
        return iteration;
    }

    public Alarm setIteration(String iteration) {
        this.iteration = iteration;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public Alarm setActive(boolean active) {
        isActive = active;
        return this;
    }

    @Override public String toString() {
        return "Alarm{" +
                "time='" + time + '\'' +
                ", action='" + action + '\'' +
                ", name='" + name + '\'' +
                ", iteration='" + iteration + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
