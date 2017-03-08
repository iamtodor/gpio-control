package org.kaaproject.kaa.examples.gpiocontrol.Screen;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddControllerFragment extends BaseFragment {

    private static final int REQUEST_IMAGE_PICK = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 201;

    @BindView(R.id.controller_id) protected EditText controllerId;
    @BindView(R.id.ports_name) protected EditText portsName;
    @BindView(R.id.image_for_ports) protected ImageView imageForPorts;

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

    @OnClick(R.id.image_for_ports)
    public void pickImage() {
        this.registerForContextMenu(imageForPorts);
        this.getActivity().openContextMenu(imageForPorts);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                Uri path = Uri.fromFile(getFile(bitmap));
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

    private void pickImageFromTemplates() {

    }

    private void pickPictureFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_picture)),
                REQUEST_IMAGE_PICK);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @NonNull
    public File getFile(Bitmap bitmap) {
        File file = new File(getContext().getCacheDir().getPath(), String.valueOf(System.currentTimeMillis()));
        FileOutputStream out;
        try {
            if (!file.exists())
                file.createNewFile();
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
