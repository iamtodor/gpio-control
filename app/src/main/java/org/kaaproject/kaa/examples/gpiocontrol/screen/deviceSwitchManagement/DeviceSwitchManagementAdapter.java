package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

class DeviceSwitchManagementAdapter extends RecyclerView.Adapter<DeviceSwitchManagementAdapter.ViewHolderPinGroupItem> {

    private LayoutInflater inflater;
    private List<DeviceGroup> groupPortList = new ArrayList<>();

    DeviceSwitchManagementAdapter(List<DeviceGroup> groupPortList) {
        updateAdapter(groupPortList);
    }

    private void updateAdapter(List<DeviceGroup> colorItems) {
        groupPortList.clear();
        groupPortList.addAll(colorItems);
        notifyDataSetChanged();
    }

    @Override public DeviceSwitchManagementAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return ViewHolderPinGroupItem.create(inflater, parent);
    }

    @Override public void onBindViewHolder(ViewHolderPinGroupItem holder, int position) {
        DeviceGroup groupPort = groupPortList.get(position);
    }

    @Override public int getItemCount() {
        return groupPortList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        static ViewHolderPinGroupItem create(LayoutInflater inflater, ViewGroup parent) {
            return new ViewHolderPinGroupItem(inflater.inflate(R.layout.device_item_device_switch_mangement, parent, false));
        }

        ViewHolderPinGroupItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {

        }
    }

}
