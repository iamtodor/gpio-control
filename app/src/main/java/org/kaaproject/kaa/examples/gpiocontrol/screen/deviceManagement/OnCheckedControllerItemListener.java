package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.BaseController;

interface OnCheckedControllerItemListener {
    void onChecked(boolean isChecked, BaseController controller);
}
