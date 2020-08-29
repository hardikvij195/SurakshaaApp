package com.hvtechnologies.womansecurityapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddContactBtnActivity extends AppCompatActivity {


    private static final int REQUEST_CALL = 1 ;
    Button AddBtn ;
    EditText Na , Nu ;
    ListView ContList ;
    private FirebaseAuth mAuth;

    private FirebaseDatabase database;

    private ContactsEmergencyAdapter adapterListView2 ;
    private List<ContactsEmergencyClass> mContsList2 ;


    private DatabaseReference ClassRef ;
    private DatabaseReference ClassRef2 ;

    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact_btn);


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mContsList2 = new ArrayList<>();
        adapterListView2 = new ContactsEmergencyAdapter(getApplicationContext(), mContsList2);

        ContList = (ListView)findViewById(R.id.ContAddList);
        Na = (EditText)findViewById(R.id.EnterNameTxt);
        Nu = (EditText)findViewById(R.id.EnterNumTxt);

        AddBtn = (Button)findViewById(R.id.btnAddCont);

        uid = mAuth.getCurrentUser().getUid();

        ContList.setAdapter(adapterListView2);

        //SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
        //uid = sharedPrefs.getString("USERID" , "");

        ClassRef2 = FirebaseDatabase.getInstance().getReference("Contacts/" + uid + "/") ;
        ClassRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mContsList2.clear();
                adapterListView2.notifyDataSetChanged();

                if(dataSnapshot.exists()){

                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){

                        String name = dataSnapshot1.child("Name").getValue(String.class);
                        String contact = dataSnapshot1.child("Number").getValue(String.class);
                        mContsList2.add( new ContactsEmergencyClass( contact , name));
                        adapterListView2.notifyDataSetChanged();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ContList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                String ph = mContsList2.get(position).getPhNumber();
                Toast.makeText(AddContactBtnActivity.this , ph , Toast.LENGTH_SHORT).show();
                makePhoneCall(ph);


            }
        });


        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = Na.getText().toString();
                String b = Nu.getText().toString();

                if(!a.isEmpty() && !b.isEmpty()){

                    if(b.trim().length() == 10)
                    {

                        mContsList2.add( new ContactsEmergencyClass( a , b));

                        addContact(a , b);
                        adapterListView2.notifyDataSetChanged();
                        Na.setText("");
                        Nu.setText("");


                    }else {


                        Toast.makeText(AddContactBtnActivity.this , "Number Should be of 10 Digits" , Toast.LENGTH_SHORT).show();
                    }


                }


            }
        });

    }

    private void addContact( String number , String name) {


        ClassRef = FirebaseDatabase.getInstance().getReference("Contacts/" + uid) ;
        HashMap<String,String> dataMap = new HashMap<String, String>();
        dataMap.put("Number" , number );
        dataMap.put("Name", name );
        ClassRef.push().setValue(dataMap);


    }

    private void makePhoneCall(String number) {

        if (number.trim().length() > 0) {

            if (ContextCompat.checkSelfPermission(AddContactBtnActivity.this,
                    android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(AddContactBtnActivity.this,
                        new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_CALL);
            } else {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);



            }

        } else {
            Toast.makeText(AddContactBtnActivity.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
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

        Intent m2 = new Intent(AddContactBtnActivity.this , Act1.class);
        startActivity(m2);
        overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


    }


}
