package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.BaseDeviceGroup;

interface OnCheckedGroupItemListener {
    void onChange(boolean isChecked, BaseDeviceGroup baseDeviceGroup);
}
