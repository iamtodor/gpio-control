package org.kaaproject.kaa.examples.gpiocontrol.screen.DeviceList;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupPin;
import org.kaaproject.kaa.examples.gpiocontrol.screen.MainActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseFragment;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceListFragment extends BaseFragment implements DeviceListAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;

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

//        if (groupPinList.isEmpty()) {
        showNoDevices();
//        } else {
//            showDevices();
//        }

        return view;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        DialogFactory.showAddDeviceDialog(getBaseActivity());
    }

    @Override public void onItemClick(GroupPin groupPin) {

    }

    private void showNoDevices() {
        recyclerView.setVisibility(View.GONE);
        noDeviceMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        recyclerView.setVisibility(View.VISIBLE);
        noDeviceMessage.setVisibility(View.GONE);
    }

}
