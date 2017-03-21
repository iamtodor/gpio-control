package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.decoration.SimpleListDividerDecorator;
import com.h6ah4i.android.widget.advrecyclerview.expandable.RecyclerViewExpandableItemManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.WrapperAdapterUtils;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseListFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.AddControllerOrGroupDialog;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceManagementFragment extends BaseListFragment implements
        RecyclerViewExpandableItemManager.OnGroupExpandListener,
        RecyclerViewExpandableItemManager.OnGroupCollapseListener, AddItemListener {

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";

    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;
    @BindView(R.id.selection_menu) protected LinearLayout selectionMenu;
    @BindView(R.id.create_group) protected LinearLayout createGroup;
    @BindView(R.id.add_to_group) protected LinearLayout addGroup;
    @BindView(R.id.duplicate) protected LinearLayout duplicateGroup;
    @BindView(R.id.ungroup) protected LinearLayout ungroup;
    @BindView(R.id.selected_count_value) protected TextView selectedCountedValue;

    @BindViews({R.id.ic_create_group, R.id.ic_add_to_group,
            R.id.ic_duplicate, R.id.ic_ungroup}) protected ImageView[] imageViews;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    private ExpandableExampleAdapter myItemAdapter;
    private List<Header> deviceGroupHeaderList;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.device_list_fragment, container, false);
        final Context context = getContext();

        ButterKnife.bind(this, view);
        getSupportActionBar().setTitle(getString(R.string.device_management));

        final Drawable addGroupIcon = ContextCompat.getDrawable(context, R.drawable.add_group);
        addGroupIcon.setColorFilter(ContextCompat.getColor(context, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);

        for (ImageView imageView : imageViews) {
            imageView.setImageDrawable(addGroupIcon);
        }

        mLayoutManager = new LinearLayoutManager(context);

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);
        recyclerViewExpandableItemManager.setOnGroupExpandListener(this);
        recyclerViewExpandableItemManager.setOnGroupCollapseListener(this);

        //adapter
        deviceGroupHeaderList = Utils.getMockedHeaderList();
        myItemAdapter = new ExpandableExampleAdapter(context, deviceGroupHeaderList);

        myItemAdapter.setOnCheckedGroupItemListener(new OnCheckedGroupItemListener() {
            @Override public void onChange(boolean isChecked, DeviceGroup deviceGroup) {
                for (Header deviceGroupHeader : deviceGroupHeaderList) {
                    if (deviceGroupHeader instanceof GroupHeaderPinManagement) {
                        for (Object object : deviceGroupHeader.getChildList()) {
                            DeviceGroup deviceGroupObject = (DeviceGroup) object;
                            if (deviceGroup == deviceGroupObject) {
                                deviceGroupObject.setSelected(isChecked);
                            }
                        }
                    }
                }
                showOrHideSelectionMenu();
            }
        });

        myItemAdapter.setOnCheckedControllerItemListener(new OnCheckedControllerItemListener() {
            @Override public void onChecked(boolean isChecked, Controller controller) {
                for (Header deviceGroupHeader : deviceGroupHeaderList) {
                    if (deviceGroupHeader instanceof DeviceHeaderPinManagement) {
                        for (Object object : deviceGroupHeader.getChildList()) {
                            Controller controllerObject = (Controller) object;
                            if (controller == controllerObject) {
                                controllerObject.setSelected(isChecked);
                            }
                        }
                    }
                }
                showOrHideSelectionMenu();
            }
        });

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

        recyclerView.addItemDecoration(new SimpleListDividerDecorator(ContextCompat.getDrawable(context, R.drawable.list_divider_h), true));

        recyclerViewExpandableItemManager.attachRecyclerView(recyclerView);

        return view;
    }

    private void showOrHideSelectionMenu() {
        boolean isSelected = false;
        int totalSize = 0;
        int selectedSize = 0;
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeaderPinManagement) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    DeviceGroup deviceGroup = (DeviceGroup) object;
                    if (deviceGroup.isSelected()) {
                        totalSize = deviceGroupHeader.getChildSize();
                        selectedSize++;
                        isSelected = true;
                    }
                }
            } else if(deviceGroupHeader instanceof DeviceHeaderPinManagement) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    Controller controller = (Controller) object;
                    if (controller.isSelected()) {
                        totalSize = deviceGroupHeader.getChildSize();
                        selectedSize++;
                        isSelected = true;
                    }
                }
            }
        }
        if (isSelected) {
            selectedCountedValue.setText("Selected " + selectedSize + "/" + totalSize);
            selectionMenu.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        } else {
            selectionMenu.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void showOrHideSelectionMenu(GroupHeaderPinManagement groupHeaderPinManagement) {
        boolean isSelected = false;
        int totalSize = groupHeaderPinManagement.getChildSize();
        int selectedSize = 0;
        for (Object object : groupHeaderPinManagement.getChildList()) {
            DeviceGroup deviceGroup = (DeviceGroup) object;
            if (deviceGroup.isSelected()) {
                selectedSize++;
                isSelected = true;
            }
        }
        if (isSelected) {
            selectedCountedValue.setText("Selected " + selectedSize + "/" + totalSize);
            selectionMenu.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        } else {
            selectionMenu.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
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

    @OnClick(R.id.cancel_selection)
    public void cancelSelection() {
        for (Header header : deviceGroupHeaderList) {
            header.cancelSelection();
        }
        myItemAdapter.updateAdapter(deviceGroupHeaderList);
    }

    private void showNoDevices() {
        recyclerView.setVisibility(View.GONE);
        noDeviceMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        recyclerView.setVisibility(View.VISIBLE);
        noDeviceMessage.setVisibility(View.GONE);
    }

    @Override public void onGroupExpand(int groupPosition, boolean fromUser, Object payload) {

    }

    @Override public void onGroupCollapse(int groupPosition, boolean fromUser, Object payload) {

    }

    @Override public void onItemAdded() {
        myItemAdapter.notifyDataSetChanged();
    }
}
