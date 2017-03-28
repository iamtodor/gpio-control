package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Alarm;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    private final static int IMAGE_MEM_CACHE_MAX_SIZE = 10;
    private static LruCache<Integer, Drawable> vectorHashMap = new LruCache<>(IMAGE_MEM_CACHE_MAX_SIZE);

    private static List<Device> getMockedDeviceList() {
        List<Device> deviceList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                Device device = new Device.Builder().setName("Device " + i)
                        .setPortTitle("Port title " + i + 5)
                        .setPortId("Port id " + i + 1)
                        .setVectorId(R.drawable.kitchen)
                        .setIsOn(true)
                        .setId(i)
                        .build();
                deviceList.add(device);
            } else {
                Device device = new Device.Builder().setName("Device " + i)
                        .setPortTitle("Port title " + i + 5)
                        .setPortId("Port id " + i + 1)
                        .setVectorId(R.drawable.fan)
                        .setIsOn(false)
                        .setId(i)
                        .build();
                deviceList.add(device);
            }
        }
        return deviceList;
    }

    public static List<Group> getMockedGroupList() {
        List<Device> deviceList = getMockedDeviceList();
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i % 2 == 0) {
                Group group = new Group.Builder()
                        .setName("Group" + i)
                        .setVectorImage(R.drawable.empty_group_icon)
                        .setPortStatus("Status ok" + i + 12 / 2)
                        .setPower("Power")
                        .setId(i)
                        .setDeviceList(deviceList)
                        .build();
                groupList.add(group);
            } else {
                Group group = new Group.Builder()
                        .setName("Group" + i)
                        .setImagePath("https://avatars.yandex.net/get-music-content/97284/4583694d.a.4229094-1/400x400")
                        .setPortStatus("Status ok" + i + 12 / 2)
                        .setPower("Power")
                        .setId(i)
                        .setDeviceList(deviceList)
                        .build();
                groupList.add(group);
            }
        }
        return groupList;
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
        List<Device> deviceList = Utils.getMockedDeviceList();
        List<Group> baseDeviceGroupList = Utils.getMockedGroupList();

        deviceGroupHeaderList.add(new GroupHeaderPinManagement<>("Device groups (" + baseDeviceGroupList.size() + ")",
                0, baseDeviceGroupList));
        deviceGroupHeaderList.add(new DeviceHeaderPinManagement<>("Devices (" + deviceList.size() + ")",
                1, deviceList));
        return deviceGroupHeaderList;
    }

    public static void loadImage(Group group, ImageView imageView) {
        if (group.getImagePath() != null) {
            Picasso.with(imageView.getContext()).load(group.getImagePath()).fit().centerCrop().into(imageView);
        } else if (group.getVectorId() != -1) {
            Drawable drawable = vectorHashMap.get(group.getVectorId());
            if (drawable == null) {
                drawable = VectorDrawableCompat.create(imageView.getContext().getResources(),
                        group.getVectorId(), null);
                vectorHashMap.put(group.getVectorId(), drawable);
            }
            imageView.setImageDrawable(drawable);
        } else {
            throw new RuntimeException("Group should has either vector or photo");
        }
    }

    public static void loadImage(Device device, ImageView imageView) {
        if (device.getImagePath() != null) {
            Picasso.with(imageView.getContext()).load(device.getImagePath()).fit().centerCrop().into(imageView);
        } else if (device.getVectorId() != -1) {
            Drawable drawable = vectorHashMap.get(device.getVectorId());
            if (drawable == null) {
                drawable = VectorDrawableCompat.create(imageView.getContext().getResources(),
                        device.getVectorId(), null);
                vectorHashMap.put(device.getVectorId(), drawable);
            }
            imageView.setImageDrawable(drawable);
        } else {
            throw new RuntimeException("Group should has either vector or photo");
        }
    }

}
