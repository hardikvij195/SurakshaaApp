package com.hvtechnologies.womansecurityapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CallingAct extends AppCompatActivity {


    private static final int REQUEST_CALL = 1 ;
    private ContactsEmergencyAdapter adapterListView ;
    private List<ContactsEmergencyClass> mContsList ;

    ListView l1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling);


        mContsList = new ArrayList<>();

        l1 = (ListView)findViewById(R.id.list1);

        adapterListView = new ContactsEmergencyAdapter(getApplicationContext(), mContsList);


        mContsList.add( new ContactsEmergencyClass( "POLICE" , "100"));
        mContsList.add( new ContactsEmergencyClass( "AMBULANCE" , "102"));
        mContsList.add( new ContactsEmergencyClass( "DISASTER MANAGEMENT" , "108"));
        mContsList.add( new ContactsEmergencyClass( "WOMAN'S HELPLINE" , "181"));
        mContsList.add( new ContactsEmergencyClass( "CHILD ABUSE HELPLINE" , "1098"));
        mContsList.add( new ContactsEmergencyClass( "WOMAN HELPLINE" , "1091"));
        mContsList.add( new ContactsEmergencyClass( "Centralized Accident Trauma Service (CATS) Delhi" , "1099"));
        mContsList.add( new ContactsEmergencyClass( "All India Institute Of Medical Sciences (AIIMS) Delhi" , "011-26594405"));
        mContsList.add( new ContactsEmergencyClass( "Tourist Helpline" , "1363"));
        mContsList.add( new ContactsEmergencyClass( "Blood Requirement" , "104"));
        mContsList.add( new ContactsEmergencyClass( "Ambulance Network (Emergency & Non- Emergency)" , "09343180000"));



        l1.setAdapter(adapterListView);


        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                String ph = mContsList.get(position).getPhNumber();
                Toast.makeText(CallingAct.this , ph , Toast.LENGTH_SHORT).show();
                makePhoneCall(ph);



            }
        });






    }

    private void makePhoneCall(String number) {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(CallingAct.this,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CallingAct.this,
                        new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);



            }

        } else {
            Toast.makeText(CallingAct.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhoneCall("NUM");
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent m2 = new Intent(CallingAct.this , Act1.class);
        startActivity(m2);
        overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


    }

}
