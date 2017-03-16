package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public abstract class Header {

    public abstract String getName();

    public abstract int childrenCount();

    public abstract Controller childAt(int childPosition);

    public abstract List<Controller> getControllerList();

    public abstract int getId();

}
