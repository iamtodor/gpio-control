package org.kaaproject.kaa.examples.gpiocontrol.screen.DeviceList;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupPin;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.ViewHolderPinGroupItem> {

    private LayoutInflater inflater;
    private List<GroupPin> groupPinList = new ArrayList<>();
    private final OnItemClickListener clickListener;

    public DeviceListAdapter(List<GroupPin> groupPinList, OnItemClickListener clickListener) {
        this.clickListener = clickListener;
        updateAdapter(groupPinList);
    }

    private void updateAdapter(List<GroupPin> colorItems) {
        groupPinList.clear();
        groupPinList.addAll(colorItems);
        notifyDataSetChanged();
    }

    @Override public DeviceListAdapter.ViewHolderPinGroupItem onCreateViewHolder(ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        return ViewHolderPinGroupItem.create(inflater, parent, clickListener);
    }

    @Override public void onBindViewHolder(ViewHolderPinGroupItem holder, int position) {
        GroupPin groupPin = groupPinList.get(position);
        holder.bind(groupPin);
    }

    @Override public int getItemCount() {
        return groupPinList.size();
    }

    static class ViewHolderPinGroupItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClickListener clickListener;
        private GroupPin item;

        static ViewHolderPinGroupItem create(LayoutInflater inflater, ViewGroup parent, OnItemClickListener clickListener) {
            return new ViewHolderPinGroupItem(inflater.inflate(R.layout.device_item, parent, false), clickListener);
        }

        ViewHolderPinGroupItem(View itemView, OnItemClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        void bind(GroupPin item) {
            this.item = item;
        }

        @Override public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(item);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(GroupPin groupPin);
    }

}
