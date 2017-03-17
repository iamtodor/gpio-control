package org.kaaproject.kaa.examples.gpiocontrol.screen.dialog;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.screen.main.ChangePasswordListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangeFieldDialog extends DialogFragment {

    private ChangePasswordListener changePasswordListener;
    private String titleValue;
    private String messageValue;
    private String editTextValue;
    private String hint;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.edit_text) EditText editText;
    @BindView(R.id.text_input_layout) TextInputLayout textInputLayout;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.change_field_dialog, container, false);
        ButterKnife.bind(this, rootView);

        title.setText(titleValue);
        if (!TextUtils.isEmpty(messageValue)) {
            message.setText(messageValue);
            message.setVisibility(View.VISIBLE);
        }
        editText.setText(editTextValue);
        textInputLayout.setHint(hint);

        return rootView;
    }

    public ChangeFieldDialog setChangePasswordListener(ChangePasswordListener changePasswordListener) {
        this.changePasswordListener = changePasswordListener;
        return this;
    }

    public ChangeFieldDialog setTitle(String titleValue) {
        this.titleValue = titleValue;
        return this;
    }

    public ChangeFieldDialog setMessage(String messageValue) {
        this.messageValue = messageValue;
        return this;
    }

    public ChangeFieldDialog setEditText(String editTextValue) {
        this.editTextValue = editTextValue;
        return this;
    }

    public ChangeFieldDialog setHint(String hint) {
        this.hint = hint;
        return this;
    }

    public void show(FragmentManager manager) {
        show(manager, getClass().getSimpleName());
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        getDialog().cancel();
    }

    @OnClick(R.id.submit)
    public void submit() {
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            changePasswordListener.onChanged(editText.getText().toString());
        } else {
            editText.setError(getContext().getString(R.string.edit_text_cant_be_empty_error));
        }
    }

}
