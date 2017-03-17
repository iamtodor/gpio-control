package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.model.mapper.AddControllerImageTemplateMapper;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addController.AddControllerFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.addController.ImagePortsDrawableListener;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.main.ChangeStringListener;

public class DialogFactory {

    //Default screen margins, per the Android Design guidelines.
    private static final int SIDE_MARGIN = 16;

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
                activity.showFragment(new AddControllerFragment(), DialogFactory.class.getSimpleName());
            }
        });
    }

    public static void showChooseTemplateImageDialog(final BaseActivity activity,
                                                     final ImageView imageForPorts,
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
        fan.setOnClickListener(onClickListener);
        refrigerator.setOnClickListener(onClickListener);
        wash.setOnClickListener(onClickListener);
        window.setOnClickListener(onClickListener);
    }

    public static AlertDialog.Builder getConfirmationDialog(final Context context, final String message,
                                                            final DialogInterface.OnClickListener listener) {
        return new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton(R.string.log_out, listener)
                .setNegativeButton(R.string.cancel, null);
    }

    public static AlertDialog getChangePasswordDialog(final Context context, final String title,
                                                      final String message,
                                                      final ChangeStringListener listener) {
        final EditText editText = new EditText(context);
        final FrameLayout container = new FrameLayout(context);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        float margin = ViewUtils.dpToPx(context, SIDE_MARGIN);
        lp.setMargins(Math.round(margin), 0, Math.round(margin), 0);
        editText.setLayoutParams(lp);
        editText.setHint(R.string.input_password);
        container.addView(editText);
        final AlertDialog builder = new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setView(container)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.change_password, null)
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (editText.getText().toString().isEmpty()) {
                            editText.setError(context.getString(R.string.edit_text_cant_be_empty_error));
                        } else {
                            listener.onChanged(editText.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return builder;
    }

    public static AlertDialog getEditNameDialog(final Context context, final String title, String currentName,
                                                final ChangeStringListener listener) {
        final EditText editText = new EditText(context);
        final FrameLayout container = new FrameLayout(context);
        final FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        float margin = ViewUtils.dpToPx(context, SIDE_MARGIN);
        lp.setMargins(Math.round(margin), 0, Math.round(margin), 0);
        editText.setLayoutParams(lp);
        editText.setText(currentName);
        container.addView(editText);
        final AlertDialog builder = new AlertDialog.Builder(context)
                .setTitle(title)
                .setCancelable(false)
                .setView(container)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.change_password, null)
                .create();

        builder.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        if (editText.getText().toString().isEmpty()) {
                            editText.setError(context.getString(R.string.edit_text_cant_be_empty_error));
                        } else {
                            listener.onChanged(editText.getText().toString());
                            dialog.dismiss();
                        }
                    }
                });
            }
        });

        return builder;
    }
}
