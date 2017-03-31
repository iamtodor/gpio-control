package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeaderPinManagement;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addController.AddControllerActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedDeviceItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedGroupItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.AddControllerOrGroupDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChangeFieldDialog;
import org.kaaproject.kaa.examples.gpiocontrol.utils.ChangeFieldListener;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DeviceSwitchManagementFragment extends BaseFragment implements OnDismissDialogListener, ChangeFieldListener, OnCheckedDeviceItemListener, OnCheckedGroupItemListener {

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";
    private static final int ADD_CONTROLLER_CODE = 1111;
    public static final int ADD_GROUP_DIALOG = 777;
    public static final int ADD_CONTROLLER_DIALOG = 666;

    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;
    @BindView(R.id.selection_menu) protected LinearLayout selectionMenu;
    @BindView(R.id.selected_count_value) protected TextView selectedCountedValue;
    @BindViews({R.id.ic_create_group, R.id.ic_add_to_group}) protected ImageView[] imageViews;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    private ExpandableSwitchManagementAdapter adapter;
    private Unbinder unbinder;
    private List<Header> deviceGroupHeaderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.device_list_fragment, container, false);
        final Context context = getContext();
        unbinder = ButterKnife.bind(this, view);
        getSupportActionBar().setTitle(getString(R.string.device_switch_management));

        setupSelectionMenuIcons(context);
        setupRecyclerView(context, savedInstanceState);

        return view;
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
        unbinder.unbind();
        super.onDestroyView();
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

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_CONTROLLER_CODE) {
                adapter.notifyDataSetChanged();
            }
        }
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        AddControllerOrGroupDialog dialog = new AddControllerOrGroupDialog()
                .setOnDismissListener(this);
        dialog.show(getBaseActivity().getSupportFragmentManager());
    }

    @OnClick(R.id.create_group)
    public void createGroup() {
        ChangeFieldDialog dialogFactory = DialogFactory.getChangeFieldDialog(getString(R.string.create_group),
                null, null, getString(R.string.group_name), getString(R.string.create_group), new ChangeFieldListener() {
                    @Override public void onChanged(String newField) {
                        for (Header header : deviceGroupHeaderList) {
                            if (header instanceof GroupHeaderPinManagement) {
                                List<Group> selectedDeviceGroupList = new ArrayList<>();
                                int index = -1;
                                for (int i = 0; i < header.getChildList().size(); i++) {
                                    Group group = (Group) header.getChildList().get(i);
                                    if (group.isSelected()) {
                                        if (index == -1) {
                                            index = i;
                                        }
                                        selectedDeviceGroupList.add(group);
                                    }
                                }
                                List<Group> baseDeviceGroupList = ((GroupHeaderPinManagement) header).getDeviceGroupList();
                                for (Iterator<Group> it = baseDeviceGroupList.iterator(); it.hasNext(); ) {
                                    Group baseDeviceGroup = it.next();
                                    for (Group selectedGroup : selectedDeviceGroupList) {
                                        if (baseDeviceGroup == selectedGroup) {
                                            it.remove();
                                        }
                                    }
                                }
                                GroupHeaderPinManagement groupHeaderPinManagement = (GroupHeaderPinManagement) header;
                                Group group = new Group();
                                group.setName(newField);
                                group.setVectorId(R.drawable.empty_group_icon);
                                group.setPortStatus("Port status");
                                group.setPower("Power");
                                groupHeaderPinManagement.addGroup(index, group);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        dialogFactory.show(getBaseActivity().getSupportFragmentManager());
    }

    @OnClick(R.id.add_to_group)
    public void addToGroup() {
        // TODO: 3/31/17 add to group logic
    }

    @Override public void onDismiss(int dialog) {
        if (dialog == ADD_GROUP_DIALOG) {
            ChangeFieldDialog changeFieldDialog = new ChangeFieldDialog()
                    .setTitle(getString(R.string.add_group))
                    .setHint(getString(R.string.group_name))
                    .setAction(getString(R.string.add_group))
                    .setChangeFieldListener(this);

            changeFieldDialog.show(getActivity().getSupportFragmentManager());
        } else if (dialog == ADD_CONTROLLER_DIALOG) {
            startActivityForResult(new Intent(getActivity(), AddControllerActivity.class), ADD_CONTROLLER_CODE);
        }
    }

    @Override public void onChanged(String newField) {
        adapter.notifyDataSetChanged();
    }

    @Override public void onChange(boolean isChecked, Group currentGroup) {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeaderPinManagement) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    Group deviceGroup = (Group) object;
                    if (currentGroup == deviceGroup) {
                        deviceGroup.setSelected(isChecked);
                    }
                }
            }
        }
        showOrHideSelectionMenu();
    }

    @Override public void onChecked(boolean isChecked, Device currentSelectedDevice) {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof DeviceHeaderPinManagement) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    Device selectableDevice = (Device) object;
                    if (currentSelectedDevice == selectableDevice) {
                        selectableDevice.setSelected(isChecked);
                    }
                }
            }
        }
        showOrHideSelectionMenu();
    }

    private void setupRecyclerView(Context context, Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(context);

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);

        deviceGroupHeaderList = Utils.getMockedHeaderList();
        adapter = new ExpandableSwitchManagementAdapter(context, deviceGroupHeaderList);
        adapter.setOnCheckedDeviceItemListener(this);
        adapter.setOnCheckedGroupItemListener(this);

        // wrap for expanding
        mWrappedAdapter = recyclerViewExpandableItemManager.createWrappedAdapter(adapter);

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

        if (deviceGroupHeaderList.isEmpty()) {
            showNoDevices();
        } else {
            showDevices();
        }
    }

    private void showNoDevices() {
        recyclerView.setVisibility(View.GONE);
        noDeviceMessage.setVisibility(View.VISIBLE);
    }

    private void showDevices() {
        recyclerView.setVisibility(View.VISIBLE);
        noDeviceMessage.setVisibility(View.GONE);
    }

    private void setupSelectionMenuIcons(Context context) {
        final Drawable addGroupIcon = ContextCompat.getDrawable(context, R.drawable.add_group);
        addGroupIcon.setColorFilter(ContextCompat.getColor(context, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);

        for (ImageView imageView : imageViews) {
            imageView.setImageDrawable(addGroupIcon);
        }
    }

    private void showOrHideSelectionMenu() {
        boolean isSelected = false;
        int totalSize = 0;
        int selectedSize = 0;
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeaderPinManagement) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    Group group = (Group) object;
                    if (group.isSelected()) {
                        totalSize = deviceGroupHeader.getChildSize();
                        selectedSize++;
                        isSelected = true;
                    }
                }
            } else if (deviceGroupHeader instanceof DeviceHeaderPinManagement) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    Device device = (Device) object;
                    if (device.isSelected()) {
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
}
