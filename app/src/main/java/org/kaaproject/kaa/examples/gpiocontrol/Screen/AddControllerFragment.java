package org.kaaproject.kaa.examples.gpiocontrol.screen;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.kaaproject.kaa.examples.gpiocontrol.BaseFragment;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.Controller;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddControllerFragment extends BaseFragment {

    private static final int REQUEST_IMAGE_PICK = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 201;

    @BindView(R.id.controller_id) protected EditText controllerId;
    @BindView(R.id.ports_name) protected EditText portsName;
    @BindView(R.id.image_for_ports) protected ImageView imageForPorts;

    private int imagePortsDrawableId = R.drawable.no_image_selected;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_controller_fragment, container, false);
        ButterKnife.bind(this, view);

        registerForContextMenu(imageForPorts);

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

    @Override public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.template_photo:
                pickImageFromTemplates();
                break;
            case R.id.gallery_photo:
                pickPictureFromGallery();
                break;
            case R.id.take_photo:
                dispatchTakePictureIntent();
                break;
        }
        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @OnClick(R.id.image_for_ports)
    public void pickImage() {
        this.registerForContextMenu(imageForPorts);
        this.getActivity().openContextMenu(imageForPorts);
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

    private void pickImageFromTemplates() {
        DialogFactory.showChooseTemplateImageDialog(getBaseActivity(), imageForPorts, new ImagePortsDrawableListener() {
            @Override public void onImageClick(int drawableId) {
                imagePortsDrawableId = drawableId;
            }
        });
    }

    private void pickPictureFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_picture)),
                REQUEST_IMAGE_PICK);
    }

    private void addController() {
        Controller controller = new Controller(controllerId.getText().toString(),
                portsName.getText().toString(), imagePortsDrawableId);
    }

    private boolean isInfoValid() {
        if (TextUtils.isEmpty(controllerId.getText().toString())) {
            controllerId.setError("Can't be empty");
            return false;
        } else if (TextUtils.isEmpty(portsName.getText().toString())) {
            portsName.setError("Can't be empty");
            return false;
        }
        return true;
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                Uri path = Uri.fromFile(Utils.getFile(getContext(), bitmap));
                loadPicture(path);
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                Uri imageUri = data.getData();
                loadPicture(imageUri);
            }
        }
    }

    private void loadPicture(Uri path) {
        Picasso.with(getContext()).load(path).fit().centerCrop().into(imageForPorts);
    }

}

