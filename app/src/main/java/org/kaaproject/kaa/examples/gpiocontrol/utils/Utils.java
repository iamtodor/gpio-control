package org.kaaproject.kaa.examples.gpiocontrol.utils;


import org.kaaproject.kaa.examples.gpiocontrol.model.GroupPin;
import org.kaaproject.kaa.examples.gpiocontrol.model.Pin;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static List<Pin> getMockedPinList() {
        List<Pin> pinList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                pinList.add(new Pin(true));
            } else {
                pinList.add(new Pin(false));
            }
        }
        return pinList;
    }

    public static List<GroupPin> getMockedGroupList() {
        List<Pin> pinList = getMockedPinList();
        List<GroupPin> groupPinList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 9 == 0) {
                groupPinList.add(new GroupPin("Group" + i, "alarm", pinList, true));
            } else {
                groupPinList.add(new GroupPin("Group" + i, "alarm", pinList, false));
            }
        }
        return groupPinList;
    }

}
