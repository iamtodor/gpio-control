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
import org.kaaproject.kaa.examples.gpiocontrol.screen.addController.AddControllerFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.main.MainActivity;
import org.kaaproject.kaa.examples.gpiocontrol.utils.ChangeFieldListener;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddControllerOrGroupDialog extends BaseDialog implements ChangeFieldListener {

    @BindView(R.id.ic_add_group) ImageView addGroupImageView;
    @BindView(R.id.ic_add_controller) ImageView addControllerImageView;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_device_dialog, container, false);
        ButterKnife.bind(this, view);

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

    @OnClick(R.id.add_group)
    public void addGroup() {
        dismiss();
        ChangeFieldDialog dialog = new ChangeFieldDialog()
                .setTitle(getString(R.string.add_group_dialog))
                .setHint(getString(R.string.group_name))
                .setAction(getString(R.string.add_group_dialog))
                .setChangeFieldListener(this);

        dialog.show(getActivity().getSupportFragmentManager());
    }

    @OnClick(R.id.add_controller)
    public void addController() {
        dismiss();
        ((MainActivity) getActivity()).showFragment(new AddControllerFragment(), DialogFactory.class.getSimpleName());
    }

    @Override public void onChanged(String newField) {

    }
}
