package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

interface OnCheckedGroupItemListener {
    void onChange(boolean isChecked, Group currentGroup);
}
