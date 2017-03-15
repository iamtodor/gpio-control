package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeader;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseListFragment;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortManagementFragment extends BaseListFragment implements CompoundButton.OnCheckedChangeListener, RecyclerViewExpandableItemManager.OnGroupExpandListener, RecyclerViewExpandableItemManager.OnGroupCollapseListener {

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager mRecyclerViewExpandableItemManager;

    @BindView(R.id.recycler_view) protected RecyclerView mRecyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;

    private List<Controller> controllerList;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);

        ButterKnife.bind(this, view);
        getSupportActionBar().setTitle("Device management");

        setupRecyclerView(mRecyclerView, fab);

        controllerList = Utils.getMockedControllerList();

        List<Pair<DeviceHeader, List<Controller>>> data = new LinkedList<>();

        data.add(new Pair<>(new DeviceHeader("Devices " + controllerList.size(), 0), controllerList));

        PortManagementExpandableAdapterNew adapter = new PortManagementExpandableAdapterNew(new ExampleExpandableDataProvider());
//        adapter.setOnCheckedHeaderListener(this);

        mLayoutManager = new LinearLayoutManager(getContext());

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        mRecyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        mRecyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        mRecyclerViewExpandableItemManager.setOnGroupCollapseListener(this);

        mWrappedAdapter = mRecyclerViewExpandableItemManager.createWrappedAdapter(adapter);       // wrap for expanding

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Need to disable them when using animation indicator.
        animator.setSupportsChangeAnimations(false);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        mRecyclerView.setItemAnimator(animator);
        mRecyclerView.setHasFixedSize(false);

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
        mRecyclerView.setVisibility(View.GONE);
        noDeviceMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        mRecyclerView.setVisibility(View.VISIBLE);
        noDeviceMessage.setVisibility(View.GONE);
    }

    @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            setActive(true);
        } else {
            setActive(false);
        }
//        pinManagementAdapter.updateAdapter(controllerList);
    }

    private void setActive(boolean active) {
        for (Controller controller : controllerList) {
            controller.setSelected(active);
        }
    }

    @Override public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {

    }

    @Override public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {
        if (fromUser) {
            adjustScrollPositionOnGroupExpanded(groupPosition);
        }
    }

    private void adjustScrollPositionOnGroupExpanded(int groupPosition) {
        int childItemHeight = getActivity().getResources().getDimensionPixelSize(R.dimen.list_item_height);
        int topMargin = (int) (getActivity().getResources().getDisplayMetrics().density * 16); // top-spacing: 16dp
        int bottomMargin = topMargin; // bottom-spacing: 16dp

        mRecyclerViewExpandableItemManager.scrollToGroup(groupPosition, childItemHeight, topMargin, bottomMargin);
    }
}
