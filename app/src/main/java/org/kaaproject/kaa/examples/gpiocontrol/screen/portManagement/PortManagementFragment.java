package org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeaderPinManager;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseListFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.AddControllerOrGroupDialog;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortManagementFragment extends BaseListFragment implements CompoundButton.OnCheckedChangeListener, RecyclerViewExpandableItemManager.OnGroupExpandListener, RecyclerViewExpandableItemManager.OnGroupCollapseListener, AddItemListener {

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;

    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;
    private ExpandableExampleAdapter myItemAdapter;
    private List<Header> deviceGroupHeaderList;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.device_list_fragment, container, false);

        ButterKnife.bind(this, view);
        getSupportActionBar().setTitle("Port management");

        mLayoutManager = new LinearLayoutManager(getContext());

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        recyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        recyclerViewExpandableItemManager.setOnGroupCollapseListener(this);

        //adapter
        deviceGroupHeaderList = Utils.getMockedHeaderList();
        myItemAdapter = new ExpandableExampleAdapter(getContext(), deviceGroupHeaderList);
        myItemAdapter.setOnSelectedHeaderListener(this);

        // wrap for expanding
        mWrappedAdapter = recyclerViewExpandableItemManager.createWrappedAdapter(myItemAdapter);

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        // Change animations are enabled by default since support-v7-recyclerview v22.
        // Need to disable them when using animation indicator.
        animator.setSupportsChangeAnimations(false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mWrappedAdapter);  // requires *wrapped* adapter
        recyclerView.setItemAnimator(animator);
        recyclerView.setHasFixedSize(false);

        recyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(getContext(), R.drawable.list_divider_h), true));

        recyclerViewExpandableItemManager.attachRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save current state to support screen rotation, etc...
        if (recyclerViewExpandableItemManager != null) {
            outState.putParcelable(
                    SAVED_STATE_EXPANDABLE_ITEM_MANAGER,
                    recyclerViewExpandableItemManager.getSavedState());
        }
    }

    @Override
    public void onDestroyView() {
        if (recyclerViewExpandableItemManager != null) {
            recyclerViewExpandableItemManager.release();
            recyclerViewExpandableItemManager = null;
        }

        if (recyclerView != null) {
            recyclerView.setItemAnimator(null);
            recyclerView.setAdapter(null);
            recyclerView = null;
        }

        if (mWrappedAdapter != null) {
            WrapperAdapterUtils.releaseAll(mWrappedAdapter);
            mWrappedAdapter = null;
        }
        mLayoutManager = null;

        super.onDestroyView();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        AddControllerOrGroupDialog dialog = new AddControllerOrGroupDialog().setAddItemListener(this);
        dialog.show(getBaseActivity().getSupportFragmentManager());
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
            setSelected(true);
        } else {
            setSelected(false);
        }
        myItemAdapter.updateAdapter(deviceGroupHeaderList);
    }

    private void setSelected(boolean isSelected) {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof DeviceHeaderPinManager) {
                for (Object object : deviceGroupHeader.getControllerList()) {
                    Controller controller = (Controller) object;
                    controller.setSelected(isSelected);
                }
            } else if (deviceGroupHeader instanceof DeviceGroupHeaderPinManagement) {
                for (Object object : deviceGroupHeader.getControllerList()) {
                    DeviceGroup deviceGroup = (DeviceGroup) object;
                    deviceGroup.setSelected(isSelected);
                }
            }
        }
    }

    @Override public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {

    }

    @Override public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {

    }

    @Override public void onItemAdded() {

    }
}
