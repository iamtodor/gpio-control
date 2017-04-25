package org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import org.kaaproject.kaa.client.event.EndpointKeyHash;
import org.kaaproject.kaa.client.event.registration.OnAttachEndpointOperationCallback;
import org.kaaproject.kaa.common.endpoint.gen.SyncResponseResultType;
import org.kaaproject.kaa.examples.gpiocontrol.App;
import org.kaaproject.kaa.examples.gpiocontrol.DeviceInfoResponse;
import org.kaaproject.kaa.examples.gpiocontrol.GpioStatus;
import org.kaaproject.kaa.examples.gpiocontrol.KaaManager;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.RemoteControlECF;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.model.Device;
import org.kaaproject.kaa.examples.gpiocontrol.model.DeviceHeader;
import org.kaaproject.kaa.examples.gpiocontrol.model.Group;
import org.kaaproject.kaa.examples.gpiocontrol.model.GroupHeader;
import org.kaaproject.kaa.examples.gpiocontrol.model.Header;
import org.kaaproject.kaa.examples.gpiocontrol.model.ViewDevice;
import org.kaaproject.kaa.examples.gpiocontrol.model.ViewDeviceGroup;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addAlarm.AddAlarmActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addController.AddControllerActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedDeviceItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.OnCheckedGroupItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.AddControllerOrGroupDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChangeFieldDialog;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;
import org.kaaproject.kaa.examples.gpiocontrol.utils.ChangeFieldListener;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

public class DeviceSwitchManagementFragment extends BaseFragment implements OnDismissDialogListener,
        ChangeFieldListener, OnCheckedDeviceItemListener, OnCheckedGroupItemListener {

    private static final String SAVED_STATE_EXPANDABLE_ITEM_MANAGER = "RecyclerViewExpandableItemManager";
    private static final int UPDATE_ADAPTER_CODE = 1111;
    public static final int ADD_GROUP_DIALOG = 777;
    public static final int ADD_CONTROLLER_DIALOG = 666;
    public static final String LIST_ID = "idList";

    @BindView(R.id.recycler_view) protected RecyclerView recyclerView;
    @BindView(R.id.no_device_message) protected TextView noDeviceMessage;
    @BindView(R.id.fab) protected FloatingActionButton fab;
    @BindView(R.id.selection_menu) protected LinearLayout selectionMenu;
    @BindView(R.id.device_lock_container) protected LinearLayout deviceLockContainer;
    @BindView(R.id.alarm) protected LinearLayout alarm;
    @BindView(R.id.selected_count_value) protected TextView selectedCountedValue;
    @BindView(R.id.ic_power_on) protected ImageView icPowerOn;
    @BindView(R.id.ic_power_off) protected ImageView icPowerOff;
    @BindView(R.id.ic_lock) protected ImageView icLock;
    @BindView(R.id.ic_unlock) protected ImageView icUnlock;
    @BindView(R.id.ic_alarm) protected ImageView icAlarm;

    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewExpandableItemManager recyclerViewExpandableItemManager;
    private ExpandableSwitchManagementAdapter adapter;
    private Unbinder unbinder;
    private List<Header> deviceGroupHeaderList;
    private Repository repository;
    private KaaManager kaaManager;

    private final RemoteControlECF.Listener mRemoteControlECFListener = new RemoteControlECF.Listener() {
        @Override
        public void onEvent(DeviceInfoResponse deviceInfoResponse, String endpointId) {
            Log.d(TAG, "onEvent() called with: deviceInfoResponse = " + deviceInfoResponse + ", endpointId = " + endpointId);
            List<GpioStatus> gpioStatusList = deviceInfoResponse.getGpioStatus();

            for (GpioStatus gpioStatus : gpioStatusList) {
                for (Header deviceGroupHeader : deviceGroupHeaderList) {
                    if (deviceGroupHeader instanceof DeviceHeader) {
                        for (Object object : deviceGroupHeader.getChildList()) {
                            ViewDevice selectableViewDevice = (ViewDevice) object;
                            Device device = selectableViewDevice.getDevice();
                            if (device.getId() == gpioStatus.getId()) {
                                device.setGpioStatus(gpioStatus);
                                device.setEndpointId(endpointId);
                            }
                        }
                    }
                }
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override public void run() {
                    adapter.updateAdapter(deviceGroupHeaderList);
                }
            });
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.device_switch_management_fragment, container, false);
        final Context context = getContext();
        unbinder = ButterKnife.bind(this, view);
        getSupportActionBar().setTitle(getString(R.string.device_switch_management));
        repository = ((App) (getBaseActivity().getApplication())).getRealmRepository();

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

    @Override public void onResume() {
        super.onResume();
        kaaManager.addEventListener(mRemoteControlECFListener);
    }

    @Override public void onPause() {
        super.onPause();
        kaaManager.removeEventListener(mRemoteControlECFListener);
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
            if (requestCode == UPDATE_ADAPTER_CODE) {
                deviceGroupHeaderList = Utils.getHeaderList(repository);
                adapter.updateAdapter(deviceGroupHeaderList);
            }
        }
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
            startActivityForResult(new Intent(getActivity(), AddControllerActivity.class), UPDATE_ADAPTER_CODE);
        }
    }

    @Override public void onChanged(String newField) {
        adapter.notifyDataSetChanged();
    }

    @Override public void onGroupChecked(boolean isChecked, ViewDeviceGroup currentGroup) {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDeviceGroup selectableViewGroup = (ViewDeviceGroup) object;
                    if (currentGroup == selectableViewGroup) {
                        selectableViewGroup.setSelected(isChecked);
                    }
                }
            }
        }
        showOrHideGroupSelectionMenu();
    }

    @Override public void onDeviceChecked(boolean isChecked, ViewDevice currentSelectedDevice) {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof DeviceHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDevice selectableViewDevice = (ViewDevice) object;
                    if (selectableViewDevice.equals(currentSelectedDevice)) {
                        selectableViewDevice.setSelected(isChecked);
                    }
                }
            }
        }
        showOrHideDeviceSelectionMenu();
    }

    @OnClick(R.id.fab)
    public void onFabClick() {
        AddControllerOrGroupDialog dialog = new AddControllerOrGroupDialog()
                .setOnDismissListener(this);
        dialog.show(getBaseActivity().getSupportFragmentManager());
    }

    @OnClick(R.id.power_on)
    public void powerOnClick() {
        setIsOn(true);
    }

    @OnClick(R.id.power_off)
    public void powerOffClick() {
        setIsOn(false);
    }

    @OnClick(R.id.toggle)
    public void toggleClick() {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDeviceGroup viewDeviceGroup = (ViewDeviceGroup) object;
                    Group group = viewDeviceGroup.getGroup();
                    if (viewDeviceGroup.isSelected()) {
                        group.setTurnOn(!group.isTurnOn());
                        for (Device device : group.getDeviceList())
                            kaaManager.turnOnDevice(device, !device.getGpioStatus().getStatus());
                    }
                }
            }
            if (deviceGroupHeader instanceof DeviceHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDevice viewDevice = (ViewDevice) object;
                    if (viewDevice.isSelected()) {
                        Device device = viewDevice.getDevice();
                        kaaManager.turnOnDevice(device, !device.getGpioStatus().getStatus());
                    }
                }
            }
        }
        adapter.updateAdapter(deviceGroupHeaderList);
    }

    @OnClick(R.id.lock)
    public void lockClick() {
        setLock(true);
    }

    @OnClick(R.id.unlock)
    public void unlockClick() {
        setLock(false);
    }

    @OnClick(R.id.alarm)
    public void alarmClick() {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeader) {
                ArrayList<Long> idList = new ArrayList<>();
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDeviceGroup viewDeviceGroup = (ViewDeviceGroup) object;
                    Group group = viewDeviceGroup.getGroup();
                    if (viewDeviceGroup.isSelected()) {
                        idList.add(group.getId());
                    }
                }
                Intent intent = new Intent(getContext(), AddAlarmActivity.class);
                intent.putExtra(LIST_ID, idList);
                startActivity(intent);
            }
        }
    }

    @OnClick(R.id.cancel_selection)
    public void cancelSelection() {
        for (Header header : deviceGroupHeaderList) {
            header.cancelSelection();
        }
        adapter.updateAdapter(deviceGroupHeaderList);
    }

    private void setupRecyclerView(Context context, Bundle savedInstanceState) {
        mLayoutManager = new LinearLayoutManager(context);

        final Parcelable eimSavedState = (savedInstanceState != null) ? savedInstanceState.getParcelable(SAVED_STATE_EXPANDABLE_ITEM_MANAGER) : null;
        recyclerViewExpandableItemManager = new RecyclerViewExpandableItemManager(eimSavedState);

        kaaManager = ((App) (getBaseActivity().getApplication())).getKaaManager();
        adapter = new ExpandableSwitchManagementAdapter(context, kaaManager);
        deviceGroupHeaderList = Utils.getHeaderList(repository);

//        adapter.updateAdapter(deviceGroupHeaderList);
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

        kaaManager.attachEndpoint("token", new OnAttachEndpointOperationCallback() {
            @Override
            public void onAttach(SyncResponseResultType result, final EndpointKeyHash resultContext) {
                if (result == SyncResponseResultType.SUCCESS) {
                    kaaManager.sendDeviceInfoRequestToAll();
                    Log.d(TAG, "onAttach: " + result.toString() + "; " + resultContext.getKeyHash());
                } else {
                    Log.e(TAG, "onAttach: " + result.toString());
                }
            }
        });

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
        icPowerOn.setColorFilter(ContextCompat.getColor(context, R.color.customGreen),
                PorterDuff.Mode.SRC_ATOP);
        icPowerOff.setColorFilter(ContextCompat.getColor(context, R.color.customRed),
                PorterDuff.Mode.SRC_ATOP);
        icLock.setColorFilter(ContextCompat.getColor(context, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);
        icUnlock.setColorFilter(ContextCompat.getColor(context, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);
        icAlarm.setColorFilter(ContextCompat.getColor(context, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);
    }

    private void showOrHideGroupSelectionMenu() {
        alarm.setVisibility(View.VISIBLE);
        deviceLockContainer.setVisibility(View.GONE);

        boolean isSelected = false;
        int totalSize = 0;
        int selectedSize = 0;
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDeviceGroup viewGroup = (ViewDeviceGroup) object;
                    if (viewGroup.isSelected()) {
                        totalSize = deviceGroupHeader.getChildSize();
                        selectedSize++;
                        isSelected = true;
                    }
                }
            }
        }
        if (isSelected && selectedSize > 1) {
            selectedCountedValue.setText("Selected " + selectedSize + "/" + totalSize);
            selectionMenu.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        } else {
            selectionMenu.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void showOrHideDeviceSelectionMenu() {
        alarm.setVisibility(View.GONE);
        deviceLockContainer.setVisibility(View.VISIBLE);

        boolean isSelected = false;
        int totalSize = 0;
        int selectedSize = 0;
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof DeviceHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDevice viewDevice = (ViewDevice) object;
                    if (viewDevice.isSelected()) {
                        totalSize = deviceGroupHeader.getChildSize();
                        selectedSize++;
                        isSelected = true;
                    }
                }
            }
        }
        if (isSelected && selectedSize > 1) {
            selectedCountedValue.setText("Selected " + selectedSize + "/" + totalSize);
            selectionMenu.setVisibility(View.VISIBLE);
            fab.setVisibility(View.GONE);
        } else {
            selectionMenu.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
    }

    private void setIsOn(boolean turnOn) {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof GroupHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDeviceGroup viewGroup = (ViewDeviceGroup) object;
                    Group group = viewGroup.getGroup();
                    if (viewGroup.isSelected()) {
                        group.setTurnOn(turnOn);
                        for (Device device : group.getDeviceList()) {
                            kaaManager.turnOnDevice(device, turnOn);
                        }
                    }
                }
            }
            if (deviceGroupHeader instanceof DeviceHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDevice viewDevice = (ViewDevice) object;
                    if (viewDevice.isSelected()) {
                        Device device = viewDevice.getDevice();
                        kaaManager.turnOnDevice(device, turnOn);
                    }
                }
            }
        }
        adapter.updateAdapter(deviceGroupHeaderList);
    }

    private void setLock(boolean isLocked) {
        for (Header deviceGroupHeader : deviceGroupHeaderList) {
            if (deviceGroupHeader instanceof DeviceHeader) {
                for (Object object : deviceGroupHeader.getChildList()) {
                    ViewDevice viewDevice = (ViewDevice) object;
                    if (viewDevice.isSelected()) {
                        Device device = viewDevice.getDevice();
                        device.setLocked(isLocked);
                    }
                }
            }
        }
        adapter.updateAdapter(deviceGroupHeaderList);
    }
}
