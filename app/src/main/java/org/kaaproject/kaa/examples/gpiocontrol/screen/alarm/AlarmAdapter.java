package org.kaaproject.kaa.examples.gpiocontrol.screen.alarm;


import android.content.Context;
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

class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolderPinGroupItem> {

    private LayoutInflater inflater;
    private List<Controller> controllerList = new ArrayList<>();
    private Context context;

    AlarmAdapter(List<Controller> controllerList) {
        updateAdapter(controllerList);
    }

    private void updateAdapter(List<Controller> colorItems) {
        controllerList.clear();
        controllerList.addAll(colorItems);
        notifyDataSetChanged();
    }

    @Override public AlarmAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            context = parent.getContext();
            inflater = LayoutInflater.from(parent.getContext());
        }
        return ViewHolderPinGroupItem.create(inflater, parent);
    }

    @Override public void onBindViewHolder(final ViewHolderPinGroupItem holder, int position) {
        Controller controller = controllerList.get(position);

        Drawable drawable = VectorDrawableCompat.create(context.getResources(),
                controller.getImagePortsDrawableId(), null);
        holder.imageView.setImageDrawable(drawable);
        holder.name.setText(controller.getControllerId());
        holder.port.setText(controller.getPortName());
        holder.switchCompat.setChecked(controller.isActive());
    }

    @Override public int getItemCount() {
        return controllerList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder {

        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.checkbox) CheckBox checkBox;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port) TextView port;
        @BindView(R.id.switch_active) SwitchCompat switchCompat;
        @BindView(R.id.menu) ImageView textViewOptions;

        static ViewHolderPinGroupItem create(LayoutInflater inflater, ViewGroup parent) {
            return new ViewHolderPinGroupItem(inflater.inflate(R.layout.device_item_pin_management, parent, false));
        }

        ViewHolderPinGroupItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
