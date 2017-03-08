package org.kaaproject.kaa.examples.gpiocontrol.utils;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.kaaproject.kaa.examples.gpiocontrol.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.Screen.AddControllerFragment;

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


}
