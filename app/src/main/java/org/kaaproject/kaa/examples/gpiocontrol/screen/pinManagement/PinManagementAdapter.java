package org.kaaproject.kaa.examples.gpiocontrol.screen.pinManagement;


import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class PinManagementAdapter extends RecyclerView.Adapter<PinManagementAdapter.ViewHolderPinGroupItem> {

    private LayoutInflater inflater;
    private List<Controller> controllerList = new ArrayList<>();
    private final OnItemClickListener clickListener;

    PinManagementAdapter(List<Controller> controllerList, OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        updateAdapter(controllerList);
    }

    private void updateAdapter(List<Controller> colorItems) {
        controllerList.clear();
        controllerList.addAll(colorItems);
        notifyDataSetChanged();
    }

    @Override public PinManagementAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return ViewHolderPinGroupItem.create(inflater, parent, clickListener);
    }

    @Override public void onBindViewHolder(ViewHolderPinGroupItem holder, int position) {
        Controller controller = controllerList.get(position);

        Drawable drawable = VectorDrawableCompat.create(holder.checkBox.getContext().getResources(),
                controller.getImagePortsDrawableId(), null);
        holder.imageView.setImageDrawable(drawable);
        holder.name.setText(controller.getControllerId());
        holder.port.setText(controller.getPortName());
        holder.switchCompat.setChecked(controller.isActive());

        holder.bind(controller);
    }

    @Override public int getItemCount() {
        return controllerList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClickListener clickListener;
        private Controller item;
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.checkbox) CheckBox checkBox;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port) TextView port;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;
        @BindView(R.id.text_view_options) TextView textViewOptions;

        static ViewHolderPinGroupItem create(LayoutInflater inflater, ViewGroup parent, OnItemClickListener clickListener) {
            return new ViewHolderPinGroupItem(inflater.inflate(R.layout.device_item_pin_management, parent, false), clickListener);
        }

        ViewHolderPinGroupItem(View itemView, OnItemClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        void bind(Controller item) {
            this.item = item;
        }

        @Override public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(item);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(Controller groupPin);
    }

}
