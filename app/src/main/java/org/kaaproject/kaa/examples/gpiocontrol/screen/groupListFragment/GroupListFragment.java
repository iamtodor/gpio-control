package org.kaaproject.kaa.examples.gpiocontrol.screen.groupListFragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseListFragment;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GroupListFragment extends BaseListFragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.no_group_message) TextView noGroupMessage;
    private GroupListAdapter adapter = new GroupListAdapter();

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.group_list_fragment, container, false);
        ButterKnife.bind(this, view);
        setupRecyclerView(recyclerView, fab);
        recyclerView.setAdapter(adapter);

        List<Group> groupList = Utils.getMockedGroupList();
        adapter.updateAdapter(groupList);

        if(groupList.isEmpty()) {
            showNoDevices();
        } else {
            showDevices();
        }

        return view;
    }

    private void showNoDevices() {
        recyclerView.setVisibility(View.GONE);
        noGroupMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        recyclerView.setVisibility(View.VISIBLE);
        noGroupMessage.setVisibility(View.GONE);
    }

    @OnClick(R.id.fab)
    public void addGroup() {

    }
}
