package com.hvtechnologies.womansecurityapp;


import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class MyLocationUsingLocationAPI extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, ActivityCompat.OnRequestPermissionsResultCallback,


        PermisssionUtil.PermissionResultCallback {

    @Override
    public void onConnectionFailed(ConnectionResult result) {


    }

    @Override
    public void onConnected(Bundle arg0) {

    }


    @Override
    public void onConnectionSuspended(int arg0) {

        //mGoogleApiClient.connect();

    }


    @Override
    public void PermissionGranted(int request_code) {

    }

    @Override
    public void PartialPermissionGranted(int request_code, ArrayList<String> granted_permissions) {

    }

    @Override
    public void PermissionDenied(int request_code) {

    }

    @Override
    public void NeverAskAgain(int request_code) {

    }
}
