package com.example.vasskob.videoreview.view.main;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.LinearLayout;

import com.example.vasskob.videoreview.R;
import com.example.vasskob.videoreview.view.fragments.VideoListFragment;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout) findViewById(R.id.linear_layout);
        if (!checkPermission()) {
            requestPermission();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (checkPermission()) {
            Fragment fragment = new VideoListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(android.R.id.content, fragment);
            fragmentTransaction.commit();
        } else {
            requestPermission();
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                    boolean dataReadAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (dataReadAccepted)
                        Snackbar.make(linearLayout, R.string.permission_granted, Snackbar.LENGTH_LONG).show();
                    else {
                        Snackbar.make(linearLayout, R.string.permission_denied, Snackbar.LENGTH_LONG).show();
                        if (!checkPermission()) {
                            requestPermission();
                        }
                    }
                }
                break;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }
}
