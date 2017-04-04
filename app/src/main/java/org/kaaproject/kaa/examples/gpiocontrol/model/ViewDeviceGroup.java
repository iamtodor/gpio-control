package org.kaaproject.kaa.examples.gpiocontrol.model;


public class ViewDeviceGroup {

    private Group group;
    private boolean isSelected;

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

}
