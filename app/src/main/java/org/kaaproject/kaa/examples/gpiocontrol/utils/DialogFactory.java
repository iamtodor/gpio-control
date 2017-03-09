package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.Screen.AddControllerFragment;
import org.kaaproject.kaa.examples.gpiocontrol.model.mapper.AddControllerImageTemplateMapper;

public class DialogFactory {

    public static void showAddDeviceDialog(final BaseActivity activity) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        final View view = LayoutInflater.from(activity).inflate(R.layout.add_device_dialog, null, false);

        dialogBuilder.setView(view);

        final TextView addGroup = (TextView) view.findViewById(R.id.add_group);
        final TextView addController = (TextView) view.findViewById(R.id.add_controller);

        final Drawable addGroupIcon = ContextCompat.getDrawable(activity, R.drawable.add_group);
        addGroupIcon.setColorFilter(ContextCompat.getColor(activity, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);

        final Drawable addControllerIcon = ContextCompat.getDrawable(activity, R.drawable.add_controller);
        addControllerIcon.setColorFilter(ContextCompat.getColor(activity, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);

        addGroup.setCompoundDrawablesWithIntrinsicBounds(addGroupIcon, null, null, null);
        addController.setCompoundDrawablesWithIntrinsicBounds(addControllerIcon, null, null, null);

        final AlertDialog alertDialog = dialogBuilder.show();

        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        addController.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                alertDialog.dismiss();
                activity.showFragment(new AddControllerFragment());
            }
        });
    }

    public static void showChooseTemplateImageDialog(final BaseActivity activity,
                                                     final ImageView imageForPorts) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        final View view = LayoutInflater.from(activity).inflate(R.layout.choose_template_image_dialog, null, false);

        dialogBuilder.setView(view);

        final ImageView noImageSelected = (ImageView) view.findViewById(R.id.no_image_selected);
        final ImageView flatTv = (ImageView) view.findViewById(R.id.flat_tv);
        final ImageView microwave = (ImageView) view.findViewById(R.id.microwave);
        final ImageView kitchen = (ImageView) view.findViewById(R.id.kitchen);
        final ImageView gasStove = (ImageView) view.findViewById(R.id.gas_stove);
        final ImageView cond = (ImageView) view.findViewById(R.id.cond);
        final ImageView lamp = (ImageView) view.findViewById(R.id.lamp);
        final ImageView tableLamp = (ImageView) view.findViewById(R.id.table_lamp);

        final AlertDialog dialog = dialogBuilder.show();

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override public void onClick(View view) {
                int drawableId = AddControllerImageTemplateMapper.DeviceItem
                        .getDeviceItemById(view.getId()).getDrawableId();
                Drawable drawable = VectorDrawableCompat.create(activity.getResources(), drawableId, null);
                imageForPorts.setImageDrawable(drawable);
                dialog.dismiss();
            }
        };

        noImageSelected.setOnClickListener(onClickListener);
        flatTv.setOnClickListener(onClickListener);
        microwave.setOnClickListener(onClickListener);
        kitchen.setOnClickListener(onClickListener);
        gasStove.setOnClickListener(onClickListener);
        cond.setOnClickListener(onClickListener);
        lamp.setOnClickListener(onClickListener);
        tableLamp.setOnClickListener(onClickListener);
    }
}
