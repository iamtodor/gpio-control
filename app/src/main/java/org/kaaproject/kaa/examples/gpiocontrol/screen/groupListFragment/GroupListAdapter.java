package org.kaaproject.kaa.examples.gpiocontrol.screen.groupListFragment;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class GroupListAdapter extends RecyclerView.Adapter<GroupListAdapter.GroupViewHolder> {

    private List<Group> deviceGroupList = new ArrayList<>();

    void updateAdapter(List<Group> groupList) {
        deviceGroupList.clear();
        deviceGroupList.addAll(groupList);
        notifyDataSetChanged();
    }

    @Override public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_group_item, parent, false);
        return new GroupViewHolder(view);
    }

    @Override public void onBindViewHolder(GroupViewHolder holder, int position) {
        Group group = deviceGroupList.get(position);
        holder.bind(group);
    }

    @Override public int getItemCount() {
        return deviceGroupList.size();
    }

    static class GroupViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.icon) ImageView icon;
        @BindView(R.id.name) TextView name;
        @BindView(R.id.port_status) TextView portStatus;

        GroupViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }

        void bind(Group group) {
            name.setText(group.getName());
            portStatus.setText(group.getPortStatus());
            group.setIconTo(icon);
        }
    }
}
