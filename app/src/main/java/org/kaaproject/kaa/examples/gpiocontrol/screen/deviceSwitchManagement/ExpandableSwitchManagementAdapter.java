package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.expandable.ExpandableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractExpandableItemViewHolder;
import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedDeviceItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedGroupItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChangeFieldDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.main.MainActivity;
import org.kaaproject.kaa.examples.gpiocontrol.utils.ChangeFieldListener;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
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
        if (deviceGroupHeaderList.get(groupPosition) instanceof GroupHeaderPinManagement) {
            final Group group = (Group) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            return group.getId();
        } else {
            final Device device = (Device) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            return device.getId();
        }
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        if (deviceGroupHeaderList.get(groupPosition) instanceof GroupHeaderPinManagement) {
            return DEVICE_GROUP_HEADER_VIEW_TYPE;
        } else
            return DEVICE_LIST_HEADER_VIEW_TYPE;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        if (deviceGroupHeaderList.get(groupPosition) instanceof GroupHeaderPinManagement) {
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
            final View v = inflater.inflate(R.layout.group_item_device_management, parent, false);
            return new DeviceGroupItemViewHolder(v);
        } else if (viewType == SINGLE_DEVICE_ITEM_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.single_device_item_port_manager, parent, false);
            return new SingleDeviceItemViewHolder(v);
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
            final DeviceGroupItemViewHolder deviceGroupViewHolder = (DeviceGroupItemViewHolder) holder;
            final Group group = (Group) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);

            deviceGroupViewHolder.selection.setChecked(group.isSelected());
            Utils.loadImage(group, deviceGroupViewHolder.icon);
            deviceGroupViewHolder.name.setText(group.getName());
            deviceGroupViewHolder.port.setText(group.getPortStatus());

            deviceGroupViewHolder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onCheckedGroupItemListener.onChange(isChecked, group);
                }
            });

            deviceGroupViewHolder.imageViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    final PopupMenu deviceGroupItemPopup = new PopupMenu(context, deviceGroupViewHolder.imageViewOptions);
                    deviceGroupItemPopup.inflate(R.menu.group_item_popup_menu);

                    deviceGroupItemPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit_image:
                                    ChooseImageDialog chooseImageDialog = new ChooseImageDialog().setChooseImageListener(new ChooseImageListener() {
                                        @Override public void onImageChosen(Uri path) {
                                            Picasso.with(context)
                                                    .load(path)
                                                    .fit()
                                                    .centerCrop()
                                                    .into(deviceGroupViewHolder.icon);
                                        }

                                        @Override public void onImageChosen(int drawableId) {
                                            Drawable drawable = VectorDrawableCompat.create(context.getResources(), drawableId, null);
                                            deviceGroupViewHolder.icon.setImageDrawable(drawable);
                                        }
                                    });
                                    chooseImageDialog.show(context.getSupportFragmentManager());
                                    break;
                                case R.id.edit_name:
                                    ChangeFieldDialog dialog = DialogFactory.getChangeFieldDialog(context.getString(R.string.edit_name),
                                            null, group.getName(), context.getString(R.string.group_name),
                                            context.getString(R.string.edit_name), new ChangeFieldListener() {
                                                @Override public void onChanged(String newField) {
                                                    group.setName(newField);
                                                    notifyDataSetChanged();
                                                }
                                            });
                                    dialog.show(context.getSupportFragmentManager());
                                    break;
                                case R.id.add_to_group:
                                    // TODO: 3/10/17 add add to group
                                    break;
                                case R.id.remove_from_group:
                                    // TODO: 3/10/17 add remove from group
                                    break;
                                case R.id.duplicate:
                                    // TODO: 3/10/17 add duplicated
                                    break;
                                case R.id.ungroup:
                                    // TODO: 3/10/17 add ungroup
                                    break;
                            }
                            return false;
                        }
                    });

                    deviceGroupItemPopup.show();
                }
            });
        } else if (viewType == SINGLE_DEVICE_ITEM_VIEW_TYPE) {
            final SingleDeviceItemViewHolder singleDeviceViewHolder = (SingleDeviceItemViewHolder) holder;
            final Device device = (Device) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);

            singleDeviceViewHolder.selection.setChecked(device.isSelected());
            Utils.loadImage(device, singleDeviceViewHolder.imagePort);
            singleDeviceViewHolder.name.setText(device.getName());
            singleDeviceViewHolder.port.setText(device.getPortId());
            singleDeviceViewHolder.switchCompat.setChecked(device.isOn());

            singleDeviceViewHolder.selection.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    onCheckedDeviceItemListener.onChecked(isChecked, device);
                }
            });

            singleDeviceViewHolder.imageViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    final PopupMenu singleDeviceItemPopup = new PopupMenu(context, singleDeviceViewHolder.imageViewOptions);
                    singleDeviceItemPopup.inflate(R.menu.device_item_popup_menu);

                    singleDeviceItemPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.edit_image:
                                    ChooseImageDialog chooseImageDialog = new ChooseImageDialog().setChooseImageListener(new ChooseImageListener() {
                                        @Override public void onImageChosen(Uri path) {
                                            Picasso.with(context)
                                                    .load(path)
                                                    .fit()
                                                    .centerCrop()
                                                    .into(singleDeviceViewHolder.imagePort);
                                        }

                                        @Override public void onImageChosen(int drawableId) {
                                            Drawable drawable = VectorDrawableCompat.create(context.getResources(), drawableId, null);
                                            singleDeviceViewHolder.imagePort.setImageDrawable(drawable);
                                        }
                                    });
                                    chooseImageDialog.show(context.getSupportFragmentManager());
                                    break;
                                case R.id.edit_name:
                                    ChangeFieldDialog dialog = DialogFactory.getChangeFieldDialog(context.getString(R.string.edit_name),
                                            null, device.getName(), context.getString(R.string.controller_id),
                                            context.getString(R.string.edit_name), new ChangeFieldListener() {
                                                @Override public void onChanged(String newField) {
                                                    device.setName(newField);
                                                    notifyDataSetChanged();
                                                }
                                            });
                                    dialog.show(context.getSupportFragmentManager());
                                    break;
                                case R.id.add_to_group:
                                    // TODO: 3/10/17 add add to group
                                    break;
                            }
                            return false;
                        }
                    });
                    singleDeviceItemPopup.show();
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

    static class DeviceGroupItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {

        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port_status) TextView port;
        @BindView(R.id.menu) ImageView imageViewOptions;

        DeviceGroupItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View view) {
            selection.setChecked(!selection.isChecked());
        }
    }

    static class SingleDeviceItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {

        @BindView(R.id.image) ImageView imagePort;
        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port_status) TextView port;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;
        @BindView(R.id.menu) ImageView imageViewOptions;

        SingleDeviceItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            imageViewOptions.setOnClickListener(this);
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
