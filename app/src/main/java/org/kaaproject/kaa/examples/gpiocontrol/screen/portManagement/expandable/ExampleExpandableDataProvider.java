package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement.expandable;


import android.support.v4.util.Pair;

import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ExampleExpandableDataProvider {
    private List<Pair<DeviceHeader, List<Controller>>> mData;

    public ExampleExpandableDataProvider() {
        final String groupItems = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String childItems = "abc";

        mData = new LinkedList<>();

        for (int i = 0; i < groupItems.length(); i++) {
            final DeviceHeader group = new DeviceHeader("Group" + i, i);
            final List<Controller> controllerList = new ArrayList<>();

            for (int j = 0; j < childItems.length(); j++) {
                final long childId = group.generateNewChildId();

                controllerList.add(new Controller("Controller " + j, "Port name " + j, android.R.drawable.alert_dark_frame,
                        true, false, childId));
            }

            mData.add(new Pair<>(group, controllerList));
        }
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