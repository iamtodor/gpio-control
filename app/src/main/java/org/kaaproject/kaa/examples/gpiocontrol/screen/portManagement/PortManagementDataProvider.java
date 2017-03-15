package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement;


import android.support.v4.util.Pair;

import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeader;

import java.util.List;

public class PortManagementDataProvider {

    private List<Pair<DeviceHeader, List<Controller>>> mData;

    // for undo group item
    private Pair<DeviceHeader, List<Controller>> mLastRemovedGroup;
    private int mLastRemovedGroupPosition = -1;

    // for undo child item
    private Controller mLastRemovedChild;
    private long mLastRemovedChildParentGroupId = -1;
    private int mLastRemovedChildPosition = -1;

    public PortManagementDataProvider(List<Pair<DeviceHeader, List<Controller>>> data) {
        this.mData = data;
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

    public void removeGroupItem(int groupPosition) {
        mLastRemovedGroup = mData.remove(groupPosition);
        mLastRemovedGroupPosition = groupPosition;

        mLastRemovedChild = null;
        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
    }

    public void removeChildItem(int groupPosition, int childPosition) {
        mLastRemovedChild = mData.get(groupPosition).second.remove(childPosition);
        mLastRemovedChildParentGroupId = mData.get(groupPosition).first.getId();
        mLastRemovedChildPosition = childPosition;

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;
    }

    public long undoLastRemoval() {
        if (mLastRemovedGroup != null) {
            return undoGroupRemoval();
        } else if (mLastRemovedChild != null) {
            return undoChildRemoval();
        } else {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }
    }

    private long undoGroupRemoval() {
        int insertedPosition;
        if (mLastRemovedGroupPosition >= 0 && mLastRemovedGroupPosition < mData.size()) {
            insertedPosition = mLastRemovedGroupPosition;
        } else {
            insertedPosition = mData.size();
        }

        mData.add(insertedPosition, mLastRemovedGroup);

        mLastRemovedGroup = null;
        mLastRemovedGroupPosition = -1;

        return RecyclerViewExpandableItemManager.getPackedPositionForGroup(insertedPosition);
    }

    private long undoChildRemoval() {
        Pair<DeviceHeader, List<Controller>> group = null;
        int groupPosition = -1;

        // find the group
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).first.getId() == mLastRemovedChildParentGroupId) {
                group = mData.get(i);
                groupPosition = i;
                break;
            }
        }

        if (group == null) {
            return RecyclerViewExpandableItemManager.NO_EXPANDABLE_POSITION;
        }

        int insertedPosition;
        if (mLastRemovedChildPosition >= 0 && mLastRemovedChildPosition < group.second.size()) {
            insertedPosition = mLastRemovedChildPosition;
        } else {
            insertedPosition = group.second.size();
        }

        group.second.add(insertedPosition, mLastRemovedChild);

        mLastRemovedChildParentGroupId = -1;
        mLastRemovedChildPosition = -1;
        mLastRemovedChild = null;

        return RecyclerViewExpandableItemManager.getPackedPositionForChild(groupPosition, insertedPosition);
    }
}
