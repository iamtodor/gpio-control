package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public abstract class Header<T> {

    public abstract String getName();

    public abstract int childrenCount();

    public abstract T childAt(int childPosition);

    public abstract List<T> getChildList();

    public abstract int getId();

    public abstract boolean isSelected();

    public abstract int getChildSize();

}
