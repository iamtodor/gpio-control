package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

public interface OnCheckedGroupItemListener {
    void onGroupChecked(boolean isChecked, Group currentGroup);
}
