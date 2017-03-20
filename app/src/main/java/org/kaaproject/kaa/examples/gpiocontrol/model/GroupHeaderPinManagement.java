package org.kaaproject.kaa.examples.gpiocontrol.model;


import java.util.List;

public class GroupHeaderPinManagement<GroupPort> extends Header {

    private String name;
    private int id;
    private List<GroupPort> groupPortList;
    private boolean selected;

    @Override public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override public int getId() {
        return id;
    }

    @Override public boolean isSelected() {
        return selected;
    }

    @Override public int getChildSize() {
        return groupPortList.size();
    }

    @Override public int childrenCount() {
        return groupPortList.size();
    }

    @Override public GroupPort childAt(int childPosition) {
        return groupPortList.get(childPosition);
    }

    @Override public List<GroupPort> getChildList() {
        return groupPortList;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setId(int id) {
        this.id = id;
    }

    public GroupHeaderPinManagement(String name, int id, List<GroupPort> groupPortList) {
        this.name = name;
        this.id = id;
        this.groupPortList = groupPortList;
    }

    @Override public String toString() {
        return "GroupHeaderPinManagement{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", groupPortList=" + groupPortList +
                '}';
    }
}
