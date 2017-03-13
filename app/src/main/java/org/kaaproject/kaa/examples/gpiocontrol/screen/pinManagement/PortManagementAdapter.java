package org.kaaproject.kaa.examples.gpiocontrol.screen.pinManagement;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class PortManagementAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Controller> controllerList = new ArrayList<>();
    private CompoundButton.OnCheckedChangeListener listener;
    private LayoutInflater itemInflater;
    private Context context;

    PortManagementAdapter(List<Controller> controllerList) {
        updateAdapter(controllerList);
    }

    void setOnCheckedHeaderListener(CompoundButton.OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    void updateAdapter(List<Controller> colorItems) {
        controllerList.clear();
        controllerList.addAll(colorItems);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            context = parent.getContext();
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_header, parent, false);
            return new HeaderViewHolder(layoutView, listener);
        } else if (viewType == TYPE_ITEM) {
            if (itemInflater == null) {
                itemInflater = LayoutInflater.from(parent.getContext());
            }
            return ItemViewHolder.create(itemInflater, parent);
        }
        throw new RuntimeException("No match for " + viewType + ".");
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            Controller controller = controllerList.get(position - 1);
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            Drawable drawable = VectorDrawableCompat.create(context.getResources(),
                    controller.getImagePortsDrawableId(), null);
            itemViewHolder.selection.setChecked(controller.isSelected());
            itemViewHolder.imagePort.setImageDrawable(drawable);
            itemViewHolder.name.setText(controller.getControllerId());
            itemViewHolder.port.setText(controller.getPortName());
            itemViewHolder.switchCompat.setChecked(controller.isActive());
        } else if (holder instanceof HeaderViewHolder) {
            HeaderViewHolder itemViewHolder = (HeaderViewHolder) holder;
            itemViewHolder.name.setText(context.getString(R.string.device_name_header, controllerList.size()));
        }
    }

    @Override public int getItemCount() {
        return controllerList.size() + 1;
    }

    @Override public int getItemViewType(int position) {
        if (isPositionHeader(position)) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final PopupMenu popup;

        @BindView(R.id.image) ImageView imagePort;
        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port) TextView port;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;
        @BindView(R.id.menu) ImageView imageViewOptions;

        static ItemViewHolder create(LayoutInflater inflater, ViewGroup parent) {
            return new ItemViewHolder(inflater.inflate(R.layout.device_item_port_management, parent, false));
        }

        ItemViewHolder(View itemView) {
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

    static class HeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.selection) CheckBox selection;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.dropped_arrow) ImageView droppedArrow;

        HeaderViewHolder(View itemView, CompoundButton.OnCheckedChangeListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
            droppedArrow.setOnClickListener(this);
            selection.setOnCheckedChangeListener(listener);
        }

        @Override public void onClick(View view) {
            if (view.getId() == R.id.dropped_arrow) {

            } else {

            }
        }

    }

}
