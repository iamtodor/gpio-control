package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;

interface OnCheckedGroupHeaderListener {
    void onChange(boolean isChecked, GroupHeaderPinManagement groupHeaderPinManagement);
}
