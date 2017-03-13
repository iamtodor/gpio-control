package org.kaaproject.kaa.examples.gpiocontrol.screen.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import org.kaaproject.kaa.examples.gpiocontrol.R;
import org.kaaproject.kaa.examples.gpiocontrol.screen.base.BaseActivity;
import org.kaaproject.kaa.examples.gpiocontrol.screen.portManagement.PortManagementFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.portSwitchManagement.PortSwitchManagementFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.resetDevices.ResetDevicesFragment;
import org.kaaproject.kaa.examples.gpiocontrol.screen.signIn.SingInActivity;
import org.kaaproject.kaa.examples.gpiocontrol.utils.DialogFactory;
import org.kaaproject.kaa.examples.gpiocontrol.utils.PreferencesImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar) protected Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        defineApplicationFlow();
    }

    private void defineApplicationFlow() {
        if (PreferencesImpl.getInstance().isEmailExists()) {
            navigationView.getMenu().getItem(0).setChecked(true);
            showFragment(new PortSwitchManagementFragment());
        } else {
            showSignInActivity();
        }
    }

    private void showSignInActivity() {
        Intent intent = new Intent(MainActivity.this, SingInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pin_switch_management) {
            showFragment(new PortSwitchManagementFragment());
        } else if (id == R.id.pin_management) {
            showFragment(new PortManagementFragment());
        } else if (id == R.id.edit_lock_password) {
            showEditPasswordDialog();
        } else if (id == R.id.reset_devices) {
            showFragment(new ResetDevicesFragment());
        } else if (id == R.id.log_out) {
            showLogoutDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showEditPasswordDialog() {
        String currentPassword = PreferencesImpl.getInstance().getPassword();
        String dialogTitle;
        String dialogMessage;
        if (TextUtils.isEmpty(currentPassword)) {
            dialogTitle = getString(R.string.create_password_title);
            dialogMessage = getString(R.string.create_password_message);
        } else {
            dialogTitle = getString(R.string.change_password_title);
            dialogMessage = getString(R.string.change_password_message) + currentPassword;
        }

        DialogFactory.getChangePasswordDialog(this, dialogTitle, dialogMessage, new ChangePasswordListener() {
            @Override public void onChanged(String newPassword) {
                PreferencesImpl.getInstance().savePassword(newPassword);
            }
        }).show();
    }

    private void showLogoutDialog() {
        DialogFactory.getConfirmationDialog(this, "Log out?", new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialog, int which) {
                PreferencesImpl.getInstance().cleanUp();
                showSignInActivity();
            }
        }).show();
    }
}
