package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.BaseController;
import org.kaaproject.kaa.examples.gpiocontrol.model.BaseDevice;
import org.kaaproject.kaa.examples.gpiocontrol.model.BaseDeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.model.ImageController;
import org.kaaproject.kaa.examples.gpiocontrol.model.ImageDevice;
import org.kaaproject.kaa.examples.gpiocontrol.model.ImageDeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.model.VectorController;
import org.kaaproject.kaa.examples.gpiocontrol.model.VectorDeviceGroup;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static List<BaseDevice> getMockedDeviceList() {
        List<BaseDevice> deviceList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            deviceList.add(new ImageDevice("ImageDevice " + i, "Port title " + i + 5, "Port id " + i + 1, false, false, "smth"));
            ;
        }
        return deviceList;
    }

    public static List<BaseDeviceGroup> getMockedDeviceGroupList() {
        List<BaseDevice> deviceList = getMockedDeviceList();
        List<BaseDeviceGroup> baseDeviceGroupList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                baseDeviceGroupList.add(new VectorDeviceGroup("Group vector" + i, R.drawable.empty_group_icon, "Norm", "power",
                        false, new Alarm(), false, i, null, deviceList));
            } else {
                baseDeviceGroupList.add(new ImageDeviceGroup("Group" + i, "some path", "Norm", "power",
                        false, new Alarm(), false, i, null, deviceList));
            }
        }
        return baseDeviceGroupList;
    }

    private static List<BaseController> getMockedControllerList() {
        List<BaseController> controllerList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                controllerList.add(new VectorController("VectorController " + i, "Device name " + i,
                        true, false, i, R.drawable.flat_tv));
            } else if (i % 3 == 0) {
                controllerList.add(new VectorController("VectorController " + i, "Device name " + i,
                        true, false, i, R.drawable.kitchen));
            } else {
                controllerList.add(new ImageController("ImageController " + i, "Device name " + i, false, false, i, "some path"));
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
                arrayList.add(new Alarm());
            } else {
                arrayList.add(new Alarm());
            }
        }
        return arrayList;
    }

    public static List<Header> getMockedHeaderList() {
        List<Header> deviceGroupHeaderList = new ArrayList<>();
        List<BaseController> controllerList = Utils.getMockedControllerList();
        List<BaseDeviceGroup> baseDeviceGroupList = Utils.getMockedDeviceGroupList();

        deviceGroupHeaderList.add(new GroupHeaderPinManagement<>("Device groups (" + baseDeviceGroupList.size() + ")",
                0, baseDeviceGroupList));
        deviceGroupHeaderList.add(new DeviceHeaderPinManagement<>("Devices (" + controllerList.size() + ")",
                1, controllerList));
        return deviceGroupHeaderList;
    }

}
