package org.kaaproject.kaa.examples.gpiocontrol.screen.dialog;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addController.ImagePortsDrawableListener;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.Utils;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseImageDialog extends BaseDialog {

    private static final int REQUEST_IMAGE_PICK = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 201;

    private ChooseImageListener listener;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.choose_image_dialog, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.templates)
    public void templates() {
        pickImageFromTemplates();
    }

    @OnClick(R.id.gallery)
    public void gallery() {
        pickPictureFromGallery();
    }

    @OnClick(R.id.take_photo)
    public void takePhoto() {
        dispatchTakePictureIntent();
    }

    public ChooseImageDialog setChooseImageListener(ChooseImageListener listener) {
        this.listener = listener;
        return this;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void pickImageFromTemplates() {
        DialogFactory.showChooseTemplateImageDialog(getActivity(), new ImagePortsDrawableListener() {
            @Override public void onImageClick(int drawableId) {
                listener.onImageChosen(drawableId);
            }
        });
        dismiss();
    }

    private void pickPictureFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(intent, getString(R.string.select_picture)),
                REQUEST_IMAGE_PICK);
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");
                Uri path = Uri.fromFile(Utils.getFile(getContext(), bitmap));
                listener.onImageChosen(path);
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                Uri imageUri = data.getData();
                listener.onImageChosen(imageUri);
            }
            dismiss();
        }
    }

}
