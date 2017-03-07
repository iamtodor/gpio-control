package org.kaaproject.kaa.examples.gpiocontrol.Screen;


import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import org.kaaproject.kaa.examples.gpiocontrol.R;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddControllerFragment extends Fragment {

    private static final int REQUEST_IMAGE_PICK = 200;
    private static final int REQUEST_IMAGE_CAPTURE = 201;
    private final static String[] OPTIONS = new String[]{"Image from templates", "Image from gallery",
            "Take photo"};

    @BindView(R.id.controller_id) protected EditText controllerId;
    @BindView(R.id.ports_name) protected EditText portsName;
    @BindView(R.id.image_spinner) protected Spinner imageSpinner;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_controller_fragment, container, false);
        ButterKnife.bind(this, view);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, Arrays.asList(OPTIONS));

        imageSpinner.setAdapter(dataAdapter);

        imageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        pickImageFromTemplates();
                        break;
                    case 1:
                        pickPictureFromGallery();
                        break;
                    case 2:
                        dispatchTakePictureIntent();
                        break;
                }
            }

            @Override public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
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
}
