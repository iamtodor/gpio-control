package org.kaaproject.kaa.examples.gpiocontrol.Screen.DeviceList;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kaaproject.kaa.examples.gpiocontrol.MainActivity;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupPin;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceListFragment extends Fragment implements DeviceListAdapter.OnItemClickListener {

    @BindView(R.id.recyclerView) protected RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);
        ButterKnife.bind(this, view);
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Device switch management");

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<GroupPin> groupPinList = Utils.getMockedGroupList();
        DeviceListAdapter deviceListAdapter = new DeviceListAdapter(groupPinList, this);
        recyclerView.setAdapter(deviceListAdapter);

        return view;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {

    }

    @Override public void onItemClick(GroupPin groupPin) {

    }
}
