package org.kaaproject.kaa.examples.gpiocontrol.screen.dialog;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement.OnDismissDialogListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement.DeviceSwitchManagementFragment.ADD_CONTROLLER_DIALOG;
import static org.kaaproject.kaa.examples.gpiocontrol.screen.deviceSwitchManagement.DeviceSwitchManagementFragment.ADD_GROUP_DIALOG;

public class AddControllerOrGroupDialog extends BaseDialog {

    @BindView(R.id.ic_add_group) ImageView addGroupImageView;
    @BindView(R.id.ic_add_controller) ImageView addControllerImageView;
    private Unbinder unbinder;

    private OnDismissDialogListener dismissListener;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_device_dialog, container, false);
        unbinder = ButterKnife.bind(this, view);

        final Context context = getContext();
        final int blueColor = ContextCompat.getColor(context, R.color.lightBlueActiveElement);

        final Drawable addGroupIcon = ContextCompat.getDrawable(context, R.drawable.add_group);
        addGroupIcon.setColorFilter(blueColor, PorterDuff.Mode.SRC_ATOP);
        addGroupImageView.setImageDrawable(addGroupIcon);

        final Drawable addControllerIcon = ContextCompat.getDrawable(context, R.drawable.add_controller);
        addControllerIcon.setColorFilter(blueColor, PorterDuff.Mode.SRC_ATOP);
        addControllerImageView.setImageDrawable(addControllerIcon);

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public AddControllerOrGroupDialog setOnDismissListener(OnDismissDialogListener onDismissListener) {
        this.dismissListener = onDismissListener;
        return this;
    }

    @OnClick(R.id.add_group)
    public void addGroup() {
        dismiss();
        dismissListener.onDismiss(ADD_GROUP_DIALOG);
    }

    @OnClick(R.id.add_controller)
    public void addController() {
        dismiss();
        dismissListener.onDismiss(ADD_CONTROLLER_DIALOG);
    }
}
