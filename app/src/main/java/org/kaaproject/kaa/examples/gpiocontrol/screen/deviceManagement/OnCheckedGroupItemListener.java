package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup;

interface OnCheckedGroupItemListener {
    void onChange(boolean isChecked, DeviceGroup deviceGroup);
}
