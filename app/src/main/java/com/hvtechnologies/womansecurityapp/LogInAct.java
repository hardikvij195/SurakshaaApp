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
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

public class LogInAct extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private ProgressDialog mLoginProgress;

    EditText Num  , Cv;
    Button B1 , B2;
    String user_id ;

    String  CodeSent ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        Num = (EditText)findViewById(R.id.Number);
        Cv = (EditText)findViewById(R.id.CodeV);
        B1 = (Button)findViewById(R.id.Code);
        B2 = (Button)findViewById(R.id.Okey);

        mLoginProgress = new ProgressDialog(this);
        database = FirebaseDatabase.getInstance();



        mAuth = FirebaseAuth.getInstance();



        FirebaseUser currentUser = mAuth.getCurrentUser();


        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();

        if ((info == null || !info.isConnected() || !info.isAvailable())) {

            Toast.makeText(getApplicationContext(), "Internet Not Available", Toast.LENGTH_SHORT).show();

        }

        if (currentUser != null) {


            mLoginProgress.setTitle("Logging In");
            mLoginProgress.setMessage("Please Wait While We Log Into Your Account");
            mLoginProgress.setCanceledOnTouchOutside(false);
            mLoginProgress.show();
            user_id = currentUser.getUid();

            SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPrefs.edit();
            edit.putString("USERID" , user_id );
            edit.apply();

            Intent m2 = new Intent(LogInAct.this , Act1.class );
            startActivity(m2);


        }





        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final String num = Num.getText().toString();
                //String P = Pas.getText().toString();

                //String num = Number.getText().toString();

                if (num.isEmpty()) {

                    Toast.makeText(LogInAct.this, "Number Field Cannot Be Empty", Toast.LENGTH_SHORT).show();

                } else if (num.length() < 10) {

                    Toast.makeText(LogInAct.this, "Number Cannot Be less than 10 digits", Toast.LENGTH_SHORT).show();


                } else {

                    sendVerificationCode(num);

                }



            }
        });


        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mLoginProgress.setTitle("Logging In");
                mLoginProgress.setMessage("Please Wait While We Log Into Your Account");
                mLoginProgress.setCanceledOnTouchOutside(false);
                mLoginProgress.show();

                VerifyCode();



            }
        });



    }


    private void VerifyCode() {


        String code = Cv.getText().toString() ;
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(CodeSent , code);

        signInWithPhoneAuthCredential(credential);






    }
    private void sendVerificationCode(String phoneNumber) {



        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                mCallbacks);



    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            mLoginProgress.dismiss();
            Toast.makeText(LogInAct.this , "CODE - " + e.getMessage() , Toast.LENGTH_SHORT ).show();

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);


            CodeSent = s ;
            Toast.makeText(LogInAct.this , "CODE SENT" , Toast.LENGTH_SHORT ).show();



        }
    };



    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "signInWithCredential:success");

                            Toast.makeText(LogInAct.this , "Login Successful" , Toast.LENGTH_SHORT).show();

                            SharedPreferences sharedPrefs = getSharedPreferences("userinfo" , Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPrefs.edit();
                            edit.putString("Ph" , Num.getText().toString() );
                            edit.apply();

                            Intent m2 = new Intent(LogInAct.this , Act1.class);
                            startActivity(m2);
                            finish();
                            mLoginProgress.dismiss();


                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(LogInAct.this , "Wrong Code" , Toast.LENGTH_SHORT).show();

                                mLoginProgress.dismiss();

                            }
                        }
                    }
                });
    }





}
