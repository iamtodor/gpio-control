package org.kaaproject.kaa.examples.gpiocontrol.screen.addController;


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

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
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageListener;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.RealmList;

public class AddControllerActivity extends BaseActivity implements ChooseImageListener {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.controller_id) protected EditText controllerId;
    @BindView(R.id.ports_name) protected EditText portsName;
    @BindView(R.id.image_for_ports) protected ImageView imageForPorts;
    private String imagePath;
    private int vectorId = R.drawable.no_image_selected;
    private KaaManager kaaManager;
    private Controller controller;
    private Repository repository;
    private AlertDialog loader;
    private Handler handler;

    private final RemoteControlECF.Listener mRemoteControlECFListener = new RemoteControlECF.Listener() {
        @Override
        public void onEvent(DeviceInfoResponse deviceInfoResponse, String endpointId) {
            handler.removeCallbacksAndMessages(null);
            Log.d(TAG, "onEvent() called with: deviceInfoResponse = " + deviceInfoResponse + ", endpointId = " + endpointId);
            List<GpioStatus> gpioStatusList = deviceInfoResponse.getGpioStatus();
            RealmList<Device> deviceList = new RealmList<>();
            for (GpioStatus gpioStatus : gpioStatusList) {
                Device device = new Device();
                device.setName(controllerId.getText().toString());
                if (imagePath != null) {
                    device.setImagePath(imagePath);
                } else {
                    device.setVectorId(vectorId);
                }
                device.setId(gpioStatus.getId());
                device.setVisibleId("Port " + gpioStatus.getId());
                deviceList.add(device);
            }
            repository.addDeviceListToController(controller.getId(), deviceList);
            runOnUiThread(new Runnable() {
                @Override public void run() {
                    hideLoader();
                    DialogFactory.getConfirmationDialog(AddControllerActivity.this, getString(R.string.controller_was_added),
                            getString(R.string.ok), null).show();
                }
            });
        }
    };

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_controller_activity);
        ButterKnife.bind(this);

        kaaManager = ((App) (getApplication())).getKaaManager();

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_controller);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
    }

    @Override protected void onResume() {
        super.onResume();
        kaaManager.addEventListener(mRemoteControlECFListener);
    }

    @Override protected void onPause() {
        super.onPause();
        kaaManager.removeEventListener(mRemoteControlECFListener);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        menu.setHeaderTitle(R.string.group_picture);
        inflater.inflate(R.menu.group_change_photo_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @OnClick(R.id.image_for_ports)
    public void pickImage() {
        ChooseImageDialog dialog = new ChooseImageDialog()
                .setChooseImageListener(this);
        dialog.show(getSupportFragmentManager());
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        onBackPressed();
    }

    @OnClick(R.id.add)
    public void addControllerClick() {
        if (isInfoValid()) {
            addController();
        }
    }

    @Override public void onImageChosen(Uri path) {
        imagePath = path.getPath();
        Picasso.with(this).load(path).fit().centerCrop().into(imageForPorts);
    }

    @Override public void onImageChosen(int drawableId) {
        Drawable drawable = VectorDrawableCompat.create(this.getResources(), drawableId, null);
        vectorId = drawableId;
        imageForPorts.setImageDrawable(drawable);
    }

    private void addController() {
        showLoader();
        controller = new Controller();
        repository = ((App) (getApplication())).getRealmRepository();
        controller.setControllerId(controllerId.getText().toString());
        controller.setPortName(portsName.getText().toString());
        if (imagePath != null) {
            controller.setImagePath(imagePath);
        } else {
            controller.setVectorId(vectorId);
        }
        controller.setId(repository.getIdForModel(Controller.class));
        repository.saveModel(controller);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hideLoader();
                DialogFactory.getConfirmationDialog(AddControllerActivity.this, getString(R.string.controller_adding_error),
                        getString(R.string.ok), null).show();
            }
        }, 7000);
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
    }

    private boolean isInfoValid() {
        if (TextUtils.isEmpty(controllerId.getText().toString())) {
            controllerId.setError(getString(R.string.edit_text_cant_be_empty_error));
            return false;
        } else if (TextUtils.isEmpty(portsName.getText().toString())) {
            portsName.setError(getString(R.string.edit_text_cant_be_empty_error));
            return false;
        }
        return true;
    }

    protected void showLoader() {
        if (loader == null) {
            loader = DialogFactory.getLoadingDialog(this).create();
        }
        loader.show();
    }

    protected void hideLoader() {
        if (loader != null) {
            loader.hide();
        }
    }
}

