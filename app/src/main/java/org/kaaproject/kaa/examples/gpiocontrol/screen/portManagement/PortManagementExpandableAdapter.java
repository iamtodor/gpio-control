package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
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
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeader;

import butterknife.BindView;
import butterknife.ButterKnife;

class PortManagementExpandableAdapter extends AbstractExpandableItemAdapter<PortManagementExpandableAdapter.MyGroupViewHolder, PortManagementExpandableAdapter.MyChildViewHolder> {

    private static final String TAG = PortManagementAdapter.class.getSimpleName();

    private CompoundButton.OnCheckedChangeListener listener;
    private Context context;

    private PortManagementDataProvider provider;

    PortManagementExpandableAdapter(PortManagementDataProvider provider) {
        this.provider = provider;
        setHasStableIds(true);
    }

    void setOnCheckedHeaderListener(CompoundButton.OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    @Override public int getGroupCount() {
        return provider.getGroupCount();
    }

    @Override public int getChildCount(int groupPosition) {
        return provider.getChildCount(groupPosition);
    }

    @Override public long getGroupId(int groupPosition) {
        return provider.getGroupItem(groupPosition).getId();
    }

    @Override public long getChildId(int groupPosition, int childPosition) {
        return provider.getChildItem(groupPosition, childPosition).getId();
    }

    @Override public MyGroupViewHolder onCreateGroupViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        context = parent.getContext();
        View layoutView = LayoutInflater.from(context).inflate(R.layout.device_header, parent, false);
        return new MyGroupViewHolder(layoutView, listener);
    }

    @Override public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        return new MyChildViewHolder(LayoutInflater.from(context).inflate(R.layout.device_item_port_management, parent, false));
    }

    @Override public void onBindGroupViewHolder(MyGroupViewHolder holder, int groupPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        DeviceHeader header = provider.getGroupItem(groupPosition);
        holder.name.setText(header.getName());

        // mark as clickable
        holder.itemView.setClickable(true);

        // set background resource (target view ID: container)
        final int expandState = holder.getExpandStateFlags();

        if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_UPDATED) != 0) {
            if ((expandState & ExpandableItemConstants.STATE_FLAG_IS_EXPANDED) != 0) {
                holder.droppedArrow.setRotation(180);
            } else {

            }
        }
    }

    @Override
    public int getGroupItemViewType(int groupPosition) {
        return 0;
    }

    @Override
    public int getChildItemViewType(int groupPosition, int childPosition) {
        return 0;
    }

    @Override public void onBindChildViewHolder(MyChildViewHolder holder, int groupPosition, int childPosition, @IntRange(from = -8388608L, to = 8388607L) int viewType) {
        Controller controller = provider.getChildItem(groupPosition, childPosition);
        Drawable drawable = VectorDrawableCompat.create(context.getResources(),
                controller.getImagePortsDrawableId(), null);
        holder.selection.setChecked(controller.isSelected());
        holder.imagePort.setImageDrawable(drawable);
        holder.name.setText(controller.getControllerId());
        holder.port.setText(controller.getPortName());
        holder.switchCompat.setChecked(controller.isActive());
    }

    @Override
    public boolean onCheckCanExpandOrCollapseGroup(MyGroupViewHolder holder, int groupPosition, int x, int y, boolean expand) {
        // check the item is *not* pinned
//        if (provider.getGroupItem(groupPosition).isPinned()) {
//            // return false to raise View.OnClickListener#onClick() event
//            return false;
//        }
//
        // check is enabled
//        if (!(holder.itemView.isEnabled() && holder.itemView.isClickable())) {
//            return false;
//        }

        return provider.getChildCount(groupPosition) > 0;
    }

    static class MyChildViewHolder extends AbstractExpandableItemViewHolder implements View.OnClickListener {

        private final PopupMenu popup;

        @BindView(R.id.image) ImageView imagePort;
        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port) TextView port;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;
        @BindView(R.id.menu) ImageView imageViewOptions;

        MyChildViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            imageViewOptions.setOnClickListener(this);

            popup = new PopupMenu(itemView.getContext(), imageViewOptions);
            popup.inflate(R.menu.device_item_popup_menu);
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.edit_image:
                            // TODO: 3/10/17 add edit image
                            break;
                        case R.id.edit_name:
                            // TODO: 3/10/17 add edit name
                            break;
                        case R.id.add_to_group:
                            // TODO: 3/10/17 add add to group
                            break;
                    }
                    return false;
                }
            });
        }

        @Override public void onClick(View view) {
            if (view.getId() == R.id.menu) {
                popup.show();
            } else {
                selection.setChecked(!selection.isChecked());
            }
        }
    }

    static class MyGroupViewHolder extends AbstractExpandableItemViewHolder implements View.OnClickListener {

        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        MyGroupViewHolder(View itemView, CompoundButton.OnCheckedChangeListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
//            droppedArrow.setOnClickListener(this);
            selection.setOnCheckedChangeListener(listener);
        }

        @Override public void onClick(View view) {
//            if (view.getId() == R.id.dropped_arrow) {
//                // TODO: 3/14/17 add hiding and showing devices
//            } else {
//
//            }
        }

    }

}
