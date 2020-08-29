package com.hvtechnologies.womansecurityapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity2 extends AppCompatActivity {


    Button gTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);


        gTN = (Button)findViewById(R.id.GetStartedBtn);

        gTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent m = new Intent(HomeActivity2.this , LogInAct.class);
                startActivity(m);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


            }
        });



    }
}
