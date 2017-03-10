package org.kaaproject.kaa.examples.gpiocontrol.screen.pinManagement;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseFragment;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinManagementFragment extends BaseFragment implements PinManagementAdapter.OnItemClickListener {

    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);

        ButterKnife.bind(this, view);
        getSupportActionBar().setTitle("Device management");

        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setItemAnimator(itemAnimator);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        List<Controller> groupPinList = Utils.getMockedControllerList();

        PinManagementAdapter pinManagementAdapter = new PinManagementAdapter(groupPinList, this);
        recyclerView.setAdapter(pinManagementAdapter);

        if (groupPinList.isEmpty()) {
        showNoDevices();
        } else {
            showDevices();
        }

        return view;
    }

    @Override public void onItemClick(Controller controller) {

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
