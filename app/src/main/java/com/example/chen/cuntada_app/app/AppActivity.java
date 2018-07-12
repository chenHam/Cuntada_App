package com.example.chen.cuntada_app.app;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chen.cuntada_app.app.View.ExitPopupDialog;
import com.google.firebase.auth.FirebaseAuth;

public class AppActivity extends AppCompatActivity implements ExitPopupDialog.ExitPopupDialogDelegate{

    @Override
    public void onYesExit() {
        finish();
        moveTaskToBack(false);
//        MyApplication.sharedPref.edit().putBoolean("exit", true).apply();
    }

    @Override
    public void onClose(DialogFragment dialogFragment) {
        dialogFragment.dismissAllowingStateLoss();
    }
//    public void setFragment(Fragment fragment) {
//        FragmentTransaction tran = getSupportFragmentManager().beginTransaction();
//        tran.replace(R.id.main_app_content, fragment);
//        tran.commit();
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.toolbar_my_profile:
//                setFragment(showProfileFragment);
//                break;
//            case R.id.toolbar_logout:
//                FirebaseAuth.getInstance().signOut();
//                finish();
//                MyApplication.sharedPref.edit().putBoolean("logout", true).apply();
//                break;
//        }
//        return true;
//    }

}
