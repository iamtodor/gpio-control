package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;

interface OnCheckedGroupHeaderListener {
    void onChecked(boolean isChecked, GroupHeaderPinManagement groupHeaderPinManagement);
}
