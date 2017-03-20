package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeaderPinManager;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupPort;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.model.Port;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static List<Port> getMockedPinList() {
        List<Port> portList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                portList.add(new Port(true));
            } else {
                portList.add(new Port(false));
            }
        }
        return portList;
    }

    public static List<GroupPort> getMockedGroupList() {
        List<Port> portList = getMockedPinList();
        List<GroupPort> groupPortList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 9 == 0) {
                groupPortList.add(new GroupPort("Group" + i, "alarm", portList, true));
            } else {
                groupPortList.add(new GroupPort("Group" + i, "alarm", portList, false));
            }
        }
        return groupPortList;
    }

    private static List<Controller> getMockedControllerList() {
        List<Controller> controllerList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                controllerList.add(new Controller("Controller " + i, "Port name " + i, R.drawable.flat_tv,
                        true, false, i));
            } else if (i % 3 == 0) {
                controllerList.add(new Controller("Controller " + i, "Port name " + i, R.drawable.kitchen,
                        true, false, i));
            } else {
                controllerList.add(new Controller("Controller " + i, "Port name " + i, R.drawable.fan,
                        false, false, i));
            }
        }
        return controllerList;
    }

    @NonNull
    public static File getFile(Context context, Bitmap bitmap) {
        File file = new File(context.getCacheDir().getPath(), String.valueOf(System.currentTimeMillis()));
        FileOutputStream out;
        try {
            if (!file.exists())
                file.createNewFile();
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static List<Alarm> getMockedAlarmList() {
        List<Alarm> arrayList = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            if (i % 2 == 0) {
                arrayList.add(new Alarm("12:" + i * 10, "Turn on", "Alarm " + i,
                        "Every sunday", true));
            } else {
                arrayList.add(new Alarm("12:" + i * 10, "Turn off", "Alarm " + i,
                        "Once", false));
            }
        }
        return arrayList;
    }

    private static List<DeviceGroup> getMockedDeviceGroupList() {
        List<DeviceGroup> deviceGroupList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            deviceGroupList.add(new DeviceGroup("Group " + i, false, R.drawable.empty_group_icon,
                    "3/12 selected", i, null));
        }
        return deviceGroupList;
    }

    public static List<Header> getMockedHeaderList() {
        List<Header> deviceGroupHeaderList = new ArrayList<>();
        List<Controller> controllerList = Utils.getMockedControllerList();
        List<DeviceGroup> deviceGroupList = Utils.getMockedDeviceGroupList();

        deviceGroupHeaderList.add(new GroupHeaderPinManagement<>("Device groups (" + deviceGroupList.size() + ")",
                0, deviceGroupList));
        deviceGroupHeaderList.add(new DeviceHeaderPinManager<>("Devices (" + controllerList.size() + ")",
                1, controllerList));
        return deviceGroupHeaderList;
    }

}
