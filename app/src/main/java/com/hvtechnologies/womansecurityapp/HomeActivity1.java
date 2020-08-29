package com.hvtechnologies.womansecurityapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class HomeActivity1 extends AppCompatActivity {


    Button Next;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);





        Next  =(Button)findViewById(R.id.Btn_Next);

        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent m = new Intent(HomeActivity1.this , HomeActivity2.class);
                startActivity(m);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

            }
        });






    }
}
