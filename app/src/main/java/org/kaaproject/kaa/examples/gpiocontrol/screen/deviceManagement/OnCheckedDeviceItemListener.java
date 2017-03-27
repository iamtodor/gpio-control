package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.Device;

interface OnCheckedDeviceItemListener {
    void onChecked(boolean isChecked, Device currentDevice);
}
