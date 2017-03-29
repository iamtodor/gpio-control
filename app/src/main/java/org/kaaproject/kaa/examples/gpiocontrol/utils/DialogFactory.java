package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.mapper.AddControllerImageTemplateMapper;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addController.ImagePortsDrawableListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.dialog.ChangeFieldDialog;

public class DialogFactory {

    public static void showChooseTemplateImageDialog(final Activity activity,
                                                     final ImagePortsDrawableListener listener) {
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
        final ImageView fan = (ImageView) view.findViewById(R.id.fan);
        final ImageView refrigerator = (ImageView) view.findViewById(R.id.refrigerator);
        final ImageView wash = (ImageView) view.findViewById(R.id.wash);
        final ImageView window = (ImageView) view.findViewById(R.id.window);

        final AlertDialog dialog = dialogBuilder.show();

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override public void onClick(View view) {
                int drawableId = AddControllerImageTemplateMapper.DeviceItem
                        .getDeviceItemById(view.getId()).getDrawableId();
                listener.onImageClick(drawableId);
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
        fan.setOnClickListener(onClickListener);
        refrigerator.setOnClickListener(onClickListener);
        wash.setOnClickListener(onClickListener);
        window.setOnClickListener(onClickListener);
    }

    public static AlertDialog.Builder getConfirmationDialog(final Context context, final String message, final String positiveButtonText,
                                                            final DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(positiveButtonText, listener);
    }

    public static ChangeFieldDialog getChangeFieldDialog(String title, String message, String editText,
                                                         String hint, String action, ChangeFieldListener changeFieldListener) {
        return new ChangeFieldDialog()
                .setTitle(title)
                .setMessage(message)
                .setEditText(editText)
                .setHint(hint)
                .setAction(action)
                .setChangeFieldListener(changeFieldListener);
    }
}
