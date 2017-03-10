package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupPin;
import org.kaaproject.kaa.examples.gpiocontrol.model.Pin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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

    public static List<Controller> getMockedControllerList() {
        List<Controller> controllerList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if(i%2==0) {
                controllerList.add(new Controller("Controller " + i, "Port name " + i, R.drawable.flat_tv, true));
            } else if(i%3==0){
                controllerList.add(new Controller("Controller " + i, "Port name " + i, R.drawable.kitchen, true));
            }else {
                controllerList.add(new Controller("Controller " + i, "Port name " + i, R.drawable.fan, false));
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

}
