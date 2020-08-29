package com.hvtechnologies.womansecurityapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

import static android.net.sip.SipErrorCode.TIME_OUT;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String user_id ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Handler handler = new Handler();



        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {

            Toast.makeText(getApplicationContext(), "Internet Not Available", Toast.LENGTH_SHORT).show();

        }

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {

            //Go to login

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms


                    Intent homeintent = new Intent(MainActivity.this , HomeActivity1.class);
                    startActivity(homeintent);
                    finish();

                }
            }, 3000);
        }
        else{

            String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPrefs.edit();
            edit.putString("USERID" , uid );
            edit.apply();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms

                    Intent m2 = new Intent(MainActivity.this ,  Act1.class );
                    startActivity(m2);
                    finish();

                }
            }, 1000);




        }



    }


}
