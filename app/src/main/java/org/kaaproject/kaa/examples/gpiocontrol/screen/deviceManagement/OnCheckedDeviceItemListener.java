package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.Device;

public interface OnCheckedDeviceItemListener {
    void onDeviceChecked(boolean isChecked, Device currentDevice);
}
