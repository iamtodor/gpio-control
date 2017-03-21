package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup;

public interface OnCheckedGroupItemListener {
    void onChange(boolean isChecked, DeviceGroup deviceGroup);
}
