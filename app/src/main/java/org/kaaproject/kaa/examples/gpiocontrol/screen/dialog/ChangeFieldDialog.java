package org.kaaproject.kaa.examples.gpiocontrol.screen.dialog;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.utils.ChangeFieldListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ChangeFieldDialog extends BaseDialog {

    private ChangeFieldListener changeFieldListener;
    private String titleValue;
    private String messageValue;
    private String editTextValue;
    private String hint;
    private String action;
    private Unbinder unbinder;

    @BindView(R.id.title) TextView title;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.edit_text) EditText editText;
    @BindView(R.id.text_input_layout) TextInputLayout textInputLayout;
    @BindView(R.id.submit) Button button;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.change_field_dialog, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        title.setText(titleValue);
        if (!TextUtils.isEmpty(messageValue)) {
            message.setText(messageValue);
            message.setVisibility(View.VISIBLE);
        }
        editText.setText(editTextValue);
        textInputLayout.setHint(hint);
        button.setText(action);

        return rootView;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public ChangeFieldDialog setChangeFieldListener(ChangeFieldListener changeFieldListener) {
        this.changeFieldListener = changeFieldListener;
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

    public ChangeFieldDialog setAction(String action) {
        this.action = action;
        return this;
    }

    @OnClick(R.id.cancel)
    public void cancel() {
        getDialog().dismiss();
    }

    @OnClick(R.id.submit)
    public void submit() {
        if (!TextUtils.isEmpty(editText.getText().toString())) {
            changeFieldListener.onChanged(editText.getText().toString());
            getDialog().dismiss();
        } else {
            editText.setError(getContext().getString(R.string.edit_text_cant_be_empty_error));
        }
    }

}
