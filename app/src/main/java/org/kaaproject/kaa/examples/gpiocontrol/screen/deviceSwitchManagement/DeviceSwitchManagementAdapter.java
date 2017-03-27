package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

class DeviceSwitchManagementAdapter extends RecyclerView.Adapter<DeviceSwitchManagementAdapter.ViewHolderPinGroupItem> {

    private List<Group> baseDeviceGroupList = new ArrayList<>();

    void updateAdapter(List<Group> baseDeviceGroupList) {
        this.baseDeviceGroupList.clear();
        this.baseDeviceGroupList.addAll(baseDeviceGroupList);
        notifyDataSetChanged();
    }

    @Override public DeviceSwitchManagementAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderPinGroupItem(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.device_item_device_switch_mangement, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolderPinGroupItem holder, int position) {
        Group group = baseDeviceGroupList.get(position);
        holder.bind(group);
    }

    @Override public int getItemCount() {
        return baseDeviceGroupList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        ViewHolderPinGroupItem(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override public void onClick(View v) {

        }

        void bind(Group group) {

        }
    }

}
