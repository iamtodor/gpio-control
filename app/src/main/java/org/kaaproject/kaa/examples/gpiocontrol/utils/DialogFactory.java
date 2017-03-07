package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.R;

public class DialogFactory {

    public static AlertDialog.Builder getAddDeviceDialog(Context context, View.OnClickListener groupListener,
                                                         View.OnClickListener controllerListener) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.add_device_dialog, null, false);

        dialogBuilder.setView(view);

        final TextView addGroup = (TextView) view.findViewById(R.id.add_group);
        final TextView addController = (TextView) view.findViewById(R.id.add_controller);

        final Drawable addGroupIcon = ContextCompat.getDrawable(context, R.drawable.add_group);
        addGroupIcon.setColorFilter(ContextCompat.getColor(context, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);

        final Drawable addControllerIcon = ContextCompat.getDrawable(context, R.drawable.add_controller);
        addControllerIcon.setColorFilter(ContextCompat.getColor(context, R.color.lightBlueActiveElement),
                PorterDuff.Mode.SRC_ATOP);

        addGroup.setCompoundDrawablesWithIntrinsicBounds(addGroupIcon, null, null, null);
        addController.setCompoundDrawablesWithIntrinsicBounds(addControllerIcon, null, null, null);

        addGroup.setOnClickListener(groupListener);
        addController.setOnClickListener(controllerListener);

        return dialogBuilder;
    }


}
