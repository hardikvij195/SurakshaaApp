package com.hvtechnologies.womansecurityapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Looper;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.seismic.ShakeDetector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;






import android.Manifest;

import android.content.DialogInterface;

import android.content.pm.PackageManager;

import android.location.Location;

import android.os.Build;

import android.os.Bundle;



import android.widget.TextView;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;

import com.google.android.gms.common.GoogleApiAvailability;

import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.location.LocationListener;

import com.google.android.gms.location.LocationRequest;

import com.google.android.gms.location.LocationServices;



import java.util.ArrayList;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;


public class Act1 extends AppCompatActivity implements ShakeDetector.Listener , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {


    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private Location location;


    private TextView locationTv;

    private GoogleApiClient googleApiClient;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private LocationRequest locationRequest;


    // lists for permissions

    private ArrayList<String> permissionsToRequest;

    private ArrayList<String> permissionsRejected = new ArrayList<>();

    private ArrayList<String> permissions = new ArrayList<>();

    // integer for permissions results request

    private static final int ALL_PERMISSIONS_RESULT = 1011;



    private BroadcastReceiver mBat = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL , 0);

            if(level <10){

                sendSos();
            }

        }
    };

    private static final int PLAY_SERVICES_REQUEST = 1;
    Button Ad ;
    CardView b1 , Call_list , Videos , tracking ;
    TextView ctText ;
    String C1 , C2 , C3 ;

    private Location mLastLocation;


    int shakeing = 0 ;

    double latitude , longitude ;
    private DatabaseReference ClassRef1 ;

    private DatabaseReference ClassRef ;
    private DatabaseReference ClassRef2 ;

    String uid;
    private List<ContactsEmergencyClass> mContsList3 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act1);


        locationTv = (TextView)findViewById(R.id.tctV);



        this.registerReceiver(this.mBat ,  new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = permissionsToRequest(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0) {

                requestPermissions(permissionsToRequest.toArray(

                        new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);

            }

        }

        // we build google api client

        googleApiClient = new GoogleApiClient.Builder(this).
                addApi(LocationServices.API).
                addConnectionCallbacks(this).
                addOnConnectionFailedListener(this).build();


        tracking = (CardView)findViewById(R.id.Cd1);
        Ad = (Button)findViewById(R.id.Addbtn);
        mContsList3 = new ArrayList<>();
        SensorManager sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        ShakeDetector shakeDetector = new ShakeDetector(this);
        shakeDetector.start(sensorManager);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();


        ClassRef2 = FirebaseDatabase.getInstance().getReference("Contacts/" + uid + "/") ;
        ClassRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    mContsList3.clear();
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        String name = dataSnapshot1.child("Name").getValue(String.class);
                        String contact = dataSnapshot1.child("Number").getValue(String.class);
                        mContsList3.add( new ContactsEmergencyClass( contact , name));

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        b1 = (CardView)findViewById(R.id.Cd4);
        Call_list = (CardView)findViewById(R.id.Cd2);
        Videos = (CardView)findViewById(R.id.Cd3);
        //ctText = (TextView)findViewById(R.id.ContactText);



        if(ContextCompat.checkSelfPermission(Act1.this , Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){


            if(ActivityCompat.shouldShowRequestPermissionRationale(Act1.this , Manifest.permission.SEND_SMS)){

                ActivityCompat.requestPermissions(Act1.this , new String[]{Manifest.permission.SEND_SMS} , 1);

            }else {

                ActivityCompat.requestPermissions(Act1.this , new String[]{Manifest.permission.SEND_SMS} , 1);
            }

        }else {
            //do nothing------------------------------------

        }

        Ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                Intent m1 = new Intent(Act1.this , AddContactBtnActivity.class);
                startActivity(m1);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);



            }
        });


        tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent m2 = new Intent(Act1.this , GoogleMapsActivity.class);
                startActivity(m2);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


            }
        });


        Call_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent m3 = new Intent(Act1.this , CallingAct.class);
                startActivity(m3);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


            }
        });

        Videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent m4 = new Intent(Act1.this , VideosActivity.class);
                startActivity(m4);
                overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendSos();

            }
        });








    }



    @Override
    public void hearShake() {

        shakeing++ ;

        if(shakeing%3 == 0){

            Toast.makeText(Act1.this, "Shake is detected  "  + shakeing , Toast.LENGTH_SHORT).show();


            sendSos();

        }


    }


    private void sendSos() {


        String message = "I need your Help. Please Contact Them if you cant contact me \nMy Location : Latitude : "+ latitude + " Longitude : "+ longitude+ "\n"  ;

        String abc = "\n" ;

        int count = mContsList3.size();

        for(int i = 0 ; i< count ; i++){

            String name = mContsList3.get(i).getName() ;
            String num = mContsList3.get(i).getPhNumber();
            abc = abc + name + " - " + num + "\n";

        }

        String message_new = message + abc  ;


        try{

            SmsManager smsManager = SmsManager.getDefault();

            int count1 = mContsList3.size();
            for(int i = 0 ; i< count1 ; i++){

                smsManager.sendTextMessage(mContsList3.get(i).getPhNumber() , null , message_new ,null , null);
                Toast.makeText(Act1.this , "Sent" , Toast.LENGTH_SHORT).show();

            }



        }catch (Exception e){

            Toast.makeText(Act1.this , e.getMessage() , Toast.LENGTH_SHORT).show();

        }



    }



    public Address getAddress(double latitude,double longitude)
    {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(latitude,longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Toast.makeText(Act1.this , addresses.toString() , Toast.LENGTH_SHORT).show();
            return addresses.get(0);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }


    public void updateLOC(){


        GoogleApiClient mGoogleApiClient;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();


        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi
                .checkLocationSettings(mGoogleApiClient, builder.build());


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private ArrayList<String> permissionsToRequest(ArrayList<String> wantedPermissions) {

        ArrayList<String> result = new ArrayList<>();



        for (String perm : wantedPermissions) {

            if (!hasPermission(perm)) {

                result.add(perm);

            }

        }



        return result;

    }



    private boolean hasPermission(String permission) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;

        }



        return true;

    }



    @Override

    protected void onStart() {

        super.onStart();



        if (googleApiClient != null) {

            googleApiClient.connect();

        }

    }



    @Override

    protected void onResume() {

        super.onResume();



        if (!checkPlayServices()) {

            locationTv.setText("You need to install Google Play Services to use the App properly");

        }

    }



    @Override

    protected void onPause() {

        super.onPause();



        // stop location updates

        if (googleApiClient != null  &&  googleApiClient.isConnected()) {

            //LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, (LocationListener) this);
            googleApiClient.disconnect();

        }

    }



    private boolean checkPlayServices() {

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);



        if (resultCode != ConnectionResult.SUCCESS) {

            if (apiAvailability.isUserResolvableError(resultCode)) {

                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST);

            } else {

                finish();

            }



            return false;

        }



        return true;

    }



    @Override

    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return ;
        }

        // Permissions ok, we get last location
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();

            locationTv.setText("My Dashboard \n Latitude : " + location.getLatitude() + "\nLongitude : " + location.getLongitude());

        }


        startLocationUpdates();

    }



    private void startLocationUpdates() {

        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setFastestInterval(FASTEST_INTERVAL);



        if (ActivityCompat.checkSelfPermission(this,

                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                &&  ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "You need to enable permissions to display location !", Toast.LENGTH_SHORT).show();

        }



    }



    @Override

    public void onConnectionSuspended(int i) {

    }



    @Override

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch(requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perm : permissionsToRequest) {
                    if (!hasPermission(perm)) {
                        permissionsRejected.add(perm);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            new AlertDialog.Builder(Act1.this).

                                    setMessage("These permissions are mandatory to get your location. You need to allow them.").

                                    setPositiveButton("OK", new DialogInterface.OnClickListener() {

                                        @Override

                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                                                requestPermissions(permissionsRejected.

                                                        toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);

                                            }

                                        }

                                    }).setNegativeButton("Cancel", null).create().show();



                            return;

                        }

                    }

                } else {

                    if (googleApiClient != null) {

                        googleApiClient.connect();

                    }

                }



                break;

        }

    }




}



