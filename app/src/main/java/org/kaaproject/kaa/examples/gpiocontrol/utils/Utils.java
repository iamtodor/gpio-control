package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.BaseController;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.model.ImageController;
import org.kaaproject.kaa.examples.gpiocontrol.model.VectorController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private static List<Device> getMockedDeviceList() {
        List<Device> deviceList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Device device = new Device.Builder().setName("ImageDevice " + i)
                    .setPortTitle("Port title " + i + 5)
                    .setPortId("Port id " + i + 1)
                    .setVectorId(R.drawable.group_icon)
                    .build();
            deviceList.add(device);
        }
        return deviceList;
    }

    public static List<Group> getMockedGroupList() {
        List<Device> deviceList = getMockedDeviceList();
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                Group group = new Group.Builder()
                        .setName("Group vector" + i)
                        .setVectorImage(R.drawable.empty_group_icon)
                        .setPortStatus("Status ok" + i + 12 / 2)
                        .setPortPower("Power")
                        .setId(i)
                        .setDeviceList(deviceList)
                        .build();
                groupList.add(group);
            } else {
                Group group = new Group.Builder()
                        .setName("Group vector" + i)
                        .setImagePath("https://avatars.yandex.net/get-music-content/97284/4583694d.a.4229094-1/400x400")
                        .setPortStatus("Status ok" + i + 12 / 2)
                        .setPortPower("Power")
                        .setId(i)
                        .setDeviceList(deviceList)
                        .build();
                groupList.add(group);
            }
        }
        return groupList;
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
        List<Group> baseDeviceGroupList = Utils.getMockedGroupList();

        deviceGroupHeaderList.add(new GroupHeaderPinManagement<>("Device groups (" + baseDeviceGroupList.size() + ")",
                0, baseDeviceGroupList));
        deviceGroupHeaderList.add(new DeviceHeaderPinManagement<>("Devices (" + controllerList.size() + ")",
                1, controllerList));
        return deviceGroupHeaderList;
    }

    public static void loadImage(Group group, ImageView imageView) {
        if (group.getImagePath() != null) {
            Picasso.with(imageView.getContext()).load(group.getImagePath()).fit().centerCrop().into(imageView);
        } else if (group.getVectorId() != -1) {
            final Drawable drawable = VectorDrawableCompat.create(imageView.getContext().getResources(),
                    group.getVectorId(), null);
            imageView.setImageDrawable(drawable);
        } else {
            throw new RuntimeException("Group should has either vector or photo");
        }
    }

}
