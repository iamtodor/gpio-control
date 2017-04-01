package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeader;

interface OnCheckedGroupHeaderListener {
    void onChecked(boolean isChecked, GroupHeader groupHeader);
}
