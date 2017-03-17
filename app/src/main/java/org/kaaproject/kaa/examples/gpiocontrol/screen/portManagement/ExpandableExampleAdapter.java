package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement;


import android.content.Context;
import android.graphics.drawable.Drawable;
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

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChangeFieldDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.main.MainActivity;
import org.kaaproject.kaa.examples.gpiocontrol.utils.ChangeFieldListener;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ExpandableExampleAdapter
        extends AbstractExpandableItemAdapter<ExpandableExampleAdapter.BaseHeaderViewHolder, ExpandableExampleAdapter.BaseItemViewHolder> {

    private static final String TAG = ExpandableExampleAdapter.class.getSimpleName();
    private static final int HEADER_VIEW_TYPE = 1;
    private static final int DEVICE_GROUP_ITEM_VIEW_TYPE = 2;

    private static final int SINGLE_DEVICE_ITEM_VIEW_TYPE = 4;

    private final MainActivity context;
    private final List<Header> deviceGroupHeaderList = new ArrayList<>();
    private CompoundButton.OnCheckedChangeListener onSelectedGroupListener;

    ExpandableExampleAdapter(Context context, List<Header> deviceGroupHeaderList) {
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

    void setOnSelectedHeaderListener(CompoundButton.OnCheckedChangeListener onSelectedGroupListener) {
        this.onSelectedGroupListener = onSelectedGroupListener;
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
        if (deviceGroupHeaderList.get(groupPosition) instanceof DeviceGroupHeaderPinManagement) {
            final DeviceGroup deviceGroup = (DeviceGroup) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            return deviceGroup.getId();
        } else {
            final Controller controller = (Controller) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            return controller.getId();
        }
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return HEADER_VIEW_TYPE;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        if (deviceGroupHeaderList.get(groupPosition) instanceof DeviceGroupHeaderPinManagement) {
            return DEVICE_GROUP_ITEM_VIEW_TYPE;
        } else
            return SINGLE_DEVICE_ITEM_VIEW_TYPE;
    }

    @Override
    public BaseHeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View v = inflater.inflate(R.layout.device_group_header_port_manager, parent, false);
        return new BaseHeaderViewHolder(v, onSelectedGroupListener);
    }

    @Override
    public BaseItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        if (viewType == DEVICE_GROUP_ITEM_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.device_group_item_port_manager, parent, false);
            return new DeviceGroupItemViewHolder(v);
        } else if (viewType == SINGLE_DEVICE_ITEM_VIEW_TYPE) {
            final View v = inflater.inflate(R.layout.single_device_item_port_manager, parent, false);
            return new SingleDeviceItemViewHolder(v);
        }
        throw new RuntimeException("No match for onCreateChildViewHolder" + viewType + ".");
    }

    @Override
    public void onBindGroupViewHolder(BaseHeaderViewHolder holder, int groupPosition, int viewType) {
        final Header item = deviceGroupHeaderList.get(groupPosition);
        holder.name.setText(item.getName());
        holder.droppedArrow.setClickable(true);

        // set background resource (target view ID: container)
        final int expandState = holder.getExpandStateFlags();

        if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_UPDATED) != 0) {

            if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_EXPANDED) != 0) {
                holder.droppedArrow.setRotation(180);
            } else {
                holder.droppedArrow.setRotation(0);
            }
        }
    }

    @Override
    public void onBindChildViewHolder(BaseItemViewHolder holder, int groupPosition, int childPosition, int viewType) {
        if (viewType == DEVICE_GROUP_ITEM_VIEW_TYPE) {
            final DeviceGroupItemViewHolder deviceGroupViewHolder = (DeviceGroupItemViewHolder) holder;
            final DeviceGroup deviceGroup = (DeviceGroup) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            final Drawable drawable = VectorDrawableCompat.create(context.getResources(),
                    deviceGroup.getIconId(), null);

            deviceGroupViewHolder.selection.setChecked(deviceGroup.isSelected());
            deviceGroupViewHolder.folder.setImageDrawable(drawable);
            deviceGroupViewHolder.name.setText(deviceGroup.getName());
            deviceGroupViewHolder.port.setText(deviceGroup.getPortStatus());

            final PopupMenu deviceGroupItemPopup = new PopupMenu(context, deviceGroupViewHolder.imageViewOptions);
            deviceGroupItemPopup.inflate(R.menu.group_item_popup_menu);

            deviceGroupItemPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit_image:
                            // TODO: 3/10/17 add edit image
                            break;
                        case R.id.edit_name:
                            ChangeFieldDialog dialog = DialogFactory.getChangeFieldDialog(context.getString(R.string.edit_name),
                                    null, deviceGroup.getName(), context.getString(R.string.group_name),
                                    context.getString(R.string.edit_name), new ChangeFieldListener() {
                                        @Override public void onChanged(String newField) {
                                            deviceGroup.getName();
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

            deviceGroupViewHolder.imageViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    deviceGroupItemPopup.show();
                }
            });
        } else if (viewType == SINGLE_DEVICE_ITEM_VIEW_TYPE) {
            final SingleDeviceItemViewHolder singleDeviceViewHolder = (SingleDeviceItemViewHolder) holder;
            final Controller controller = (Controller) deviceGroupHeaderList.get(groupPosition).childAt(childPosition);
            final Drawable drawable = VectorDrawableCompat.create(context.getResources(),
                    controller.getImagePortsDrawableId(), null);

            singleDeviceViewHolder.selection.setChecked(controller.isSelected());
            singleDeviceViewHolder.imagePort.setImageDrawable(drawable);
            singleDeviceViewHolder.name.setText(controller.getControllerId());
            singleDeviceViewHolder.port.setText(controller.getPortName());
            singleDeviceViewHolder.switchCompat.setChecked(controller.isActive());

            final PopupMenu singleDeviceItemPopup = new PopupMenu(context, singleDeviceViewHolder.imageViewOptions);
            singleDeviceItemPopup.inflate(R.menu.device_item_popup_menu);

            singleDeviceItemPopup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit_image:
                            // TODO: 3/10/17 add edit image
                            break;
                        case R.id.edit_name:
                            // TODO: 3/10/17 add edit name
                            ChangeFieldDialog dialog = DialogFactory.getChangeFieldDialog(context.getString(R.string.edit_name),
                                    null, controller.getControllerId(), context.getString(R.string.controller_id),
                                    context.getString(R.string.edit_name), new ChangeFieldListener() {
                                        @Override public void onChanged(String newField) {
                                            controller.setControllerId(newField);
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

            singleDeviceViewHolder.imageViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    singleDeviceItemPopup.show();
                }
            });
        }
    }

    static class BaseHeaderViewHolder extends AbstractExpandableItemViewHolder {

        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        BaseHeaderViewHolder(View itemView, CompoundButton.OnCheckedChangeListener onSelectedGroupListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            selection.setOnCheckedChangeListener(onSelectedGroupListener);
        }
    }

    static class BaseItemViewHolder extends AbstractExpandableItemViewHolder {

        BaseItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class DeviceGroupHeaderViewHolder extends BaseHeaderViewHolder {
        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        DeviceGroupHeaderViewHolder(View itemView, CompoundButton.OnCheckedChangeListener onSelectedGroupListener) {
            super(itemView, onSelectedGroupListener);
            ButterKnife.bind(this, itemView);
            selection.setOnCheckedChangeListener(onSelectedGroupListener);
        }
    }

    static class DeviceGroupItemViewHolder extends BaseItemViewHolder implements View.OnClickListener {

        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.icon) ImageView folder;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port_status) TextView port;
        @BindView(R.id.menu) ImageView imageViewOptions;

        DeviceGroupItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View view) {
            if (view.getId() == R.id.menu) {

            } else {
                selection.setChecked(!selection.isChecked());
            }
        }
    }

    static class SingleDeviceHeaderViewHolder extends BaseHeaderViewHolder implements View.OnClickListener {
        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        SingleDeviceHeaderViewHolder(View itemView, CompoundButton.OnCheckedChangeListener onSelectedGroupListener) {
            super(itemView, onSelectedGroupListener);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            droppedArrow.setOnClickListener(this);
            selection.setOnCheckedChangeListener(onSelectedGroupListener);
        }

        @Override public void onClick(View view) {
            if (view.getId() == R.id.selection) {
                // TODO: 3/14/17 add hiding and showing devices
            } else {

            }
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
