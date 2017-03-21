package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;

interface OnCheckedControllerItemListener {
    void onChecked(boolean isChecked, Controller controller);
}
