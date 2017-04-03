package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeader;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.screen.alarm.AlarmListActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedDeviceItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedGroupItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.main.MainActivity;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ExpandableSwitchManagementAdapter
        extends AbstractExpandableItemAdapter<ExpandableSwitchManagementAdapter.BaseHeaderViewHolder, ExpandableSwitchManagementAdapter.BaseItemViewHolder> {

    private static final String TAG = ExpandableSwitchManagementAdapter.class.getSimpleName();
    private static final int DEVICE_GROUP_HEADER_VIEW_TYPE = 1;
    private static final int DEVICE_LIST_HEADER_VIEW_TYPE = 2;

    private static final int DEVICE_GROUP_ITEM_VIEW_TYPE = 3;
    private static final int SINGLE_DEVICE_ITEM_VIEW_TYPE = 4;

    private final MainActivity context;
    private final List<Header> deviceGroupHeaderList = new ArrayList<>();

    private OnCheckedGroupItemListener onCheckedGroupItemListener;
    private OnCheckedDeviceItemListener onCheckedDeviceItemListener;

    ExpandableSwitchManagementAdapter(Context context, List<Header> deviceGroupHeaderList) {
        this.context = (MainActivity) context;
        // ExpandableItemAdapter requires stable ID, and also
        // have to implement the getGroupItemId()/getChildItemId() methods appropriately.
        setHasStableIds(true);
        updateAdapter(deviceGroupHeaderList);
    }

    void updateAdapter(List<Header> items) {
        deviceGroupHeaderList.clear();
        deviceGroupHeaderList.addAll(items);
        notifyDataSetChanged();
    }

    void setOnCheckedGroupItemListener(OnCheckedGroupItemListener onCheckedGroupItemListener) {
        this.onCheckedGroupItemListener = onCheckedGroupItemListener;
    }

    void setOnCheckedDeviceItemListener(OnCheckedDeviceItemListener onCheckedDeviceItemListener) {
        this.onCheckedDeviceItemListener = onCheckedDeviceItemListener;
    }

    @Override
    public int getGroupCount() {
        return deviceGroupHeaderList.size();
    }

    @Override
    public int getChildCount(int groupPosition) {
        return deviceGroupHeaderList.get(groupPosition).childrenCount();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return deviceGroupHeaderList.get(groupPosition).getId();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        if (deviceGroupHeaderList.get(groupPosition) instanceof GroupHeader) {
            final Group group = (Group) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            return group.getId();
        } else {
            final Device device = (Device) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            return device.getId();
        }
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        if (deviceGroupHeaderList.get(groupPosition) instanceof GroupHeader) {
            return DEVICE_GROUP_HEADER_VIEW_TYPE;
        } else
            return DEVICE_LIST_HEADER_VIEW_TYPE;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        if (deviceGroupHeaderList.get(groupPosition) instanceof GroupHeader) {
            return DEVICE_GROUP_ITEM_VIEW_TYPE;
        } else
            return SINGLE_DEVICE_ITEM_VIEW_TYPE;
    }

    @Override
    public BaseHeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == DEVICE_GROUP_HEADER_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.group_header_device_manager, parent, false);
            return new DeviceGroupHeaderViewHolder(v);
        } else if (viewType == DEVICE_LIST_HEADER_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.single_device_header_port_manager, parent, false);
            return new DeviceListHeaderViewHolder(v);
        }
        throw new RuntimeException("No match for onCreateGroupViewHolder" + viewType + ".");
    }

    @Override
    public BaseItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == DEVICE_GROUP_ITEM_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.group_item_device_switch_management, parent, false);
            return new GroupItemViewHolder(v);
        } else if (viewType == SINGLE_DEVICE_ITEM_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.device_item_device_switch_mangement, parent, false);
            return new DeviceItemViewHolder(v);
        }
        throw new RuntimeException("No match for onCreateChildViewHolder" + viewType + ".");
    }

    @Override
    public void onBindGroupViewHolder(BaseHeaderViewHolder holder, int groupPosition, int viewType) {
        if (viewType == DEVICE_GROUP_HEADER_VIEW_TYPE) {
            final Header item = deviceGroupHeaderList.get(groupPosition);
            holder.name.setText(item.getName());
            holder.droppedArrow.setClickable(true);

            final int expandState = holder.getExpandStateFlags();

            if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_UPDATED) != 0) {

                if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_EXPANDED) != 0) {
                    holder.droppedArrow.setRotation(180);
                } else {
                    holder.droppedArrow.setRotation(0);
                }
            }
        } else if (viewType == DEVICE_LIST_HEADER_VIEW_TYPE) {
            final Header item = deviceGroupHeaderList.get(groupPosition);
            holder.name.setText(item.getName());
            holder.droppedArrow.setClickable(true);

            final int expandState = holder.getExpandStateFlags();

            if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_UPDATED) != 0) {

                if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_EXPANDED) != 0) {
                    holder.droppedArrow.setRotation(180);
                } else {
                    holder.droppedArrow.setRotation(0);
                }
            }
        }
    }

    @Override
    public void onBindChildViewHolder(BaseItemViewHolder holder, int groupPosition, int childPosition, int viewType) {
        if (viewType == DEVICE_GROUP_ITEM_VIEW_TYPE) {
            final GroupItemViewHolder groupViewHolder = (GroupItemViewHolder) holder;
            final Group group = (Group) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);

            groupViewHolder.selection.setChecked(group.isSelected());
            Utils.loadImage(group, groupViewHolder.icon);
            groupViewHolder.name.setText(group.getName());
            groupViewHolder.status.setText(group.getPortStatus());
            groupViewHolder.switchOn.setChecked(group.isOn());

            groupViewHolder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onCheckedGroupItemListener.onGroupChecked(isChecked, group);
                }
            });

            groupViewHolder.swapState.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // TODO: 3/30/17 swap logic
                }
            });

            groupViewHolder.clock.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    Intent intent = new Intent(context, AlarmListActivity.class);
                    intent.putExtra(AlarmListActivity.GROUP_ID, group.getId());
                    context.startActivity(intent);
                }
            });
        } else if (viewType == SINGLE_DEVICE_ITEM_VIEW_TYPE) {
            final DeviceItemViewHolder singleDeviceViewHolder = (DeviceItemViewHolder) holder;
            final Device device = (Device) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);

            singleDeviceViewHolder.selection.setChecked(device.isSelected());
            Utils.loadImage(device, singleDeviceViewHolder.imagePort);
            singleDeviceViewHolder.name.setText(device.getName());
            singleDeviceViewHolder.port.setText(device.getPortId());
            singleDeviceViewHolder.switchCompat.setChecked(device.isOn());

            singleDeviceViewHolder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onCheckedDeviceItemListener.onDeviceChecked(isChecked, device);
                }
            });

            singleDeviceViewHolder.lock.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // TODO: 3/30/17 lock logic
                }
            });
        }
    }

    static class BaseHeaderViewHolder extends AbstractExpandableItemViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        BaseHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class DeviceGroupHeaderViewHolder extends BaseHeaderViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        DeviceGroupHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class DeviceListHeaderViewHolder extends BaseHeaderViewHolder {
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        DeviceListHeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class BaseItemViewHolder extends AbstractExpandableItemViewHolder {
        BaseItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class GroupItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {

        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.status) TextView status;
        @BindView(R.id.switch_on) SwitchCompat switchOn;
        @BindView(R.id.swap_state) ImageView swapState;
        @BindView(R.id.clock) ImageView clock;

        GroupItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View view) {
            selection.setChecked(!selection.isChecked());
        }
    }

    static class DeviceItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {

        @BindView(R.id.image) ImageView imagePort;
        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.status) TextView port;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;
        @BindView(R.id.lock) ImageView lock;

        DeviceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View view) {
            selection.setChecked(!selection.isChecked());
        }
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(BaseHeaderViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        return deviceGroupHeaderList.get(groupPosition).childrenCount() > 0;
    }
}
