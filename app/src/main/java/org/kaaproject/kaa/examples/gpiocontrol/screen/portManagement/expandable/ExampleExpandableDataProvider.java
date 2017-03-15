package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement.expandable;


import android.support.v4.util.Pair;

import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeader;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.LinkedList;
import java.util.List;

public class ExampleExpandableDataProvider {

    private List<Pair<DeviceHeader, List<Controller>>> mData;

    public ExampleExpandableDataProvider() {
        mData = new LinkedList<>();
        List<Controller> controllerList = Utils.getMockedControllerList();

        DeviceHeader group = new DeviceHeader("Device groups (" + (controllerList.size() + 100) + ")", 0);
        mData.add(new Pair<>(group, controllerList));

        group = new DeviceHeader("Devices (" + controllerList.size() + ")", 1);
        mData.add(new Pair<>(group, controllerList));
    }

    public int getGroupCount() {
        return mData.size();
    }

    public int getChildCount(int groupPosition) {
        return mData.get(groupPosition).second.size();
    }

    public DeviceHeader getGroupItem(int groupPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        return mData.get(groupPosition).first;
    }

    public Controller getChildItem(int groupPosition, int childPosition) {
        if (groupPosition < 0 || groupPosition >= getGroupCount()) {
            throw new IndexOutOfBoundsException("groupPosition = " + groupPosition);
        }

        final List<Controller> children = mData.get(groupPosition).second;

        if (childPosition < 0 || childPosition >= children.size()) {
            throw new IndexOutOfBoundsException("childPosition = " + childPosition);
        }

        return children.get(childPosition);
    }

}