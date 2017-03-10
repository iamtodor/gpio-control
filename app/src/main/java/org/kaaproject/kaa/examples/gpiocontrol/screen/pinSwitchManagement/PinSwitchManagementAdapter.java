package org.kaaproject.kaa.examples.gpiocontrol.screen.pinSwitchManagement;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupPin;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

class PinSwitchManagementAdapter extends RecyclerView.Adapter<PinSwitchManagementAdapter.ViewHolderPinGroupItem> {

    private LayoutInflater inflater;
    private List<GroupPin> groupPinList = new ArrayList<>();

    PinSwitchManagementAdapter(List<GroupPin> groupPinList) {
        updateAdapter(groupPinList);
    }

    private void updateAdapter(List<GroupPin> colorItems) {
        groupPinList.clear();
        groupPinList.addAll(colorItems);
        notifyDataSetChanged();
    }

    @Override public PinSwitchManagementAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return ViewHolderPinGroupItem.create(inflater, parent);
    }

    @Override public void onBindViewHolder(ViewHolderPinGroupItem holder, int position) {
        GroupPin groupPin = groupPinList.get(position);
    }

    @Override public int getItemCount() {
        return groupPinList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        static ViewHolderPinGroupItem create(LayoutInflater inflater, ViewGroup parent) {
            return new ViewHolderPinGroupItem(inflater.inflate(R.layout.device_item_pin_switch_mangement, parent, false));
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
