package org.kaaproject.kaa.examples.gpiocontrol.model;


public class Alarm {

    private String time;
    private String action;
    private String name;
    private String iteration;
    private boolean isActive;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIteration() {
        return iteration;
    }

    public void setIteration(String iteration) {
        this.iteration = iteration;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
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
