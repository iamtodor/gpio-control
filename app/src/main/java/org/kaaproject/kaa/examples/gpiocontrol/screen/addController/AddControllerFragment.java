package org.kaaproject.kaa.examples.gpiocontrol.screen.addController;


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.App;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceManagement.AddItemListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageDialog;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChooseImageListener;
import org.kaaproject.kaa.examples.gpiocontrol.storage.Repository;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddControllerFragment extends BaseFragment implements ChooseImageListener {

    @BindView(R.id.controller_id) protected EditText controllerId;
    @BindView(R.id.ports_name) protected EditText portsName;
    @BindView(R.id.image_for_ports) protected ImageView imageForPorts;
    private AddItemListener listener;
    private String imagePath;
    private int vectorId = R.drawable.no_image_selected;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_controller_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        menu.setHeaderTitle(R.string.group_picture);
        inflater.inflate(R.menu.group_change_photo_menu, menu);
    }

    @OnClick(R.id.image_for_ports)
    public void pickImage() {
        ChooseImageDialog dialog = new ChooseImageDialog()
                .setChooseImageListener(this);
        dialog.show(getBaseActivity().getSupportFragmentManager());
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        getBaseActivity().onBackPressed();
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
        Repository repository = ((App) (getBaseActivity().getApplication())).getRealmRepository();
        controller.setControllerId(controllerId.getText().toString());
        controller.setPortName(portsName.getText().toString());
        if(imagePath != null) {
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
        Picasso.with(getContext()).load(path).fit().centerCrop().into(imageForPorts);
    }

    @Override public void onImageChosen(int drawableId) {
        Drawable drawable = VectorDrawableCompat.create(getBaseActivity().getResources(), drawableId, null);
        imageForPorts.setImageDrawable(drawable);
    }
}

