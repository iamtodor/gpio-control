package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.ViewDeviceGroup;

public interface OnCheckedGroupItemListener {
    void onGroupChecked(boolean isChecked, ViewDeviceGroup currentGroup);
}
