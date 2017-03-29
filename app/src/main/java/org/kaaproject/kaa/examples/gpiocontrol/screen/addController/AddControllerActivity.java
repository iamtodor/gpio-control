package org.kaaproject.kaa.examples.gpiocontrol.screen.addController;


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.App;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.AddItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageListener;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddControllerActivity extends BaseActivity implements ChooseImageListener {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    @BindView(R.id.controller_id) protected EditText controllerId;
    @BindView(R.id.ports_name) protected EditText portsName;
    @BindView(R.id.image_for_ports) protected ImageView imageForPorts;
    private AddItemListener listener;
    private String imagePath;
    private int vectorId = R.drawable.no_image_selected;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_controller_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.add_controller);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
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

    public void setAddItemListener(AddItemListener listener) {
        this.listener = listener;
    }

    private void addController() {
        Controller controller = new Controller();
        Repository repository = ((App) (getApplication())).getRealmRepository();
        controller.setControllerId(controllerId.getText().toString());
        controller.setPortName(portsName.getText().toString());
        if (imagePath != null) {
            controller.setImagePath(imagePath);
        } else {
            controller.setVectorId(vectorId);
        }
        controller.setId(repository.getIdForModel(Controller.class));
        repository.saveModelToDB(controller);
        listener.onItemAdded();
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

    @Override public void onImageChosen(Uri path) {
        imagePath = path.getPath();
        Picasso.with(this).load(path).fit().centerCrop().into(imageForPorts);
    }

    @Override public void onImageChosen(int drawableId) {
        Drawable drawable = VectorDrawableCompat.create(this.getResources(), drawableId, null);
        vectorId = drawableId;
        imageForPorts.setImageDrawable(drawable);
    }
}

