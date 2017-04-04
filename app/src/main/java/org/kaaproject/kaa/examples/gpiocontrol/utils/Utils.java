package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.util.LruCache;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeader;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeader;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.model.ViewDevice;
import org.kaaproject.kaa.examples.gpiocontrol.model.ViewDeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class Utils {

    private static final int IMAGE_MEM_CACHE_MAX_SIZE = 10;
    private static final String DELIMITER = ";";
    private static LruCache<Integer, Drawable> vectorHashMap = new LruCache<>(IMAGE_MEM_CACHE_MAX_SIZE);

    private static RealmList<Device> getMockedDeviceList() {
        RealmList<Device> deviceList = new RealmList<>();
        for (int i = 0; i < 100; i++) {
            Device device = new Device();
            if (i % 2 == 0) {
                device.setName("Device " + i);
                device.setPortTitle("Port title " + i + 5);
                device.setPortId("Port id " + i + 1);
                device.setVectorId(R.drawable.kitchen);
                device.setOn(false);
                device.setId(i);
            } else {
                device.setName("Device " + i);
                device.setPortTitle("Port title " + i + 5);
                device.setPortId("Port id " + i + 1);
                device.setVectorId(R.drawable.fan);
                device.setOn(true);
                device.setId(i);
            }
            deviceList.add(device);
        }
        return deviceList;
    }

    public static List<Group> getMockedGroupList() {
        RealmList<Device> deviceList = getMockedDeviceList();
        List<Group> groupList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Group group = new Group();
            if (i % 2 == 0) {
                group.setName("Group" + i);
                group.setVectorId(R.drawable.empty_group_icon);
                group.setPortStatus("Status ok" + i + 12 / 2);
                group.setPower("Power");
                group.setId(i);
                group.setDeviceList(deviceList);
            } else {
                group.setName("Group" + i);
                group.setImagePath("https://avatars.yandex.net/get-music-content/97284/4583694d.a.4229094-1/400x400");
                group.setPortStatus("Status ok" + i + 12 / 2);
                group.setPower("Power");
                group.setId(i);
                group.setDeviceList(deviceList);
            }
            groupList.add(group);
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

    public static List<Header> getMockedHeaderList(Repository repository) {
        List<Header> deviceGroupHeaderList = new ArrayList<>();

        List<Group> groupList = repository.getGroupList();
        List<ViewDeviceGroup> viewDeviceGroupList = getViewGroupList(groupList);

        List<Device> deviceList = getMockedDeviceList();
        List<ViewDevice> viewDeviceList = getViewDeviceList(deviceList);

        deviceGroupHeaderList.add(new GroupHeader<>("Groups (" + viewDeviceGroupList.size() + ")",
                0, viewDeviceGroupList));
        deviceGroupHeaderList.add(new DeviceHeader<>("Devices (" + viewDeviceList.size() + ")",
                1, viewDeviceList));
        return deviceGroupHeaderList;
    }

    private static List<ViewDeviceGroup> getViewGroupList(List<Group> groupList) {
        List<ViewDeviceGroup> viewDeviceGroupList = new ArrayList<>();
        for(Group group : groupList) {
            ViewDeviceGroup viewDeviceGroup = new ViewDeviceGroup();
            viewDeviceGroup.setGroup(group);
            viewDeviceGroupList.add(viewDeviceGroup);
        }
        return viewDeviceGroupList;
    }

    private static List<ViewDevice> getViewDeviceList(List<Device> deviceList) {
        List<ViewDevice> viewDeviceList = new ArrayList<>();
        for (Device device : deviceList) {
            ViewDevice viewDevice = new ViewDevice();
            viewDevice.setDevice(device);
            viewDeviceList.add(viewDevice);
        }
        return viewDeviceList;
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

    /**
     * according to the issue https://github.com/realm/realm-java/issues/575
     * we worked with the one string where each word is divided by delimiter
     *
     * @param iterationList list of iteration (days)
     * @return single string, that will be stored into alarm model
     */
    public static String getIterationStringFromList(List<String> iterationList) {
        return TextUtils.join(DELIMITER, iterationList);
    }

}
