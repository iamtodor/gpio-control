package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.ViewDevice;

public interface OnCheckedDeviceItemListener {
    void onDeviceChecked(boolean isChecked, ViewDevice currentDevice);
}
