package org.kaaproject.kaa.examples.gpiocontrol.screen.pinManagement;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseListFragment;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinManagementFragment extends BaseListFragment implements CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;

    private List<Controller> controllerList;
    private PortManagementAdapter pinManagementAdapter;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);

        ButterKnife.bind(this, view);
        getSupportActionBar().setTitle("Device management");

        setupRecyclerView(recyclerView, fab);

        controllerList = Utils.getMockedControllerList();

        pinManagementAdapter = new PortManagementAdapter(controllerList);
        pinManagementAdapter.setOnCheckedHeaderListener(this);

        recyclerView.setAdapter(pinManagementAdapter);

        if (controllerList.isEmpty()) {
            showNoDevices();
        } else {
            showDevices();
        }

        return view;
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        DialogFactory.showAddDeviceDialog(getBaseActivity());
    }

    private void showNoDevices() {
        recyclerView.setVisibility(View.GONE);
        noDeviceMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        recyclerView.setVisibility(View.VISIBLE);
        noDeviceMessage.setVisibility(View.GONE);
    }

    @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            setActive(true);
        } else {
            setActive(false);
        }
        pinManagementAdapter.updateAdapter(controllerList);
    }

    private void setActive(boolean active) {
        for (Controller controller : controllerList) {
            controller.setSelected(active);
        }
    }
}
