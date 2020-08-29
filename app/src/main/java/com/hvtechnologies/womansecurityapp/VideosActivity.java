package com.hvtechnologies.womansecurityapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.BackingStoreException;

public class VideosActivity extends AppCompatActivity {



    ListView listtips ;
    private TipsAdapter tipsAdapter ;

    List<TipsClass> mTip ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        mTip = new ArrayList<>();
        listtips = (ListView)findViewById(R.id.ListTips);
        tipsAdapter = new TipsAdapter(getApplicationContext() , mTip);




        mTip.add(new TipsClass("Keep to busy roads"  ,"Always drive with all your doors locked and windows rolled up. If you are being followed on the road, remember to stay on busy streets with lots of people around so that help is imminent when needed."));
        mTip.add(new TipsClass("Keep an inflatable tyre kit in your car"  ,"Dealing with a flat tyre in the middle of nowhere can be quite the nightmare."));
        mTip.add(new TipsClass("Push back & yell loudly"  ,"This the first thing you need to do as soon you realize that you are being stalked. If you sense trouble, act promptly. If attacked, push your assaulter aside and shout as loudly as you can."));
        mTip.add(new TipsClass("Use the heel of your palm to strike at the nose"  ,"When you're cornered in a deserted area, a swift blow to the nose will help you escape."));
        mTip.add(new TipsClass("Use everyday objects as weapons"  ,"You can fashion weapons out of anything around you. Like any other weapons, a set of keys wedged between your fingers like brass knuckles can deliver a crippling blow."));
        mTip.add(new TipsClass("Get out of a wrist-hold"  ,"If your attacker has you in a wrist-hold, here's an easy way to break free. Instead of struggling, squat down, lean forward and then bend your elbows all the way towards their arm, till they can no longer hold on."));
        mTip.add(new TipsClass("Keep a bag of essentials in your car"  ,"A car survival kit can be your best friend when you're driving long distances. Keep items like first aid kit, jumper cables, flashlight, a fully-charged power bank, matchbox, a thick rope etc."));
        mTip.add(new TipsClass("Hit them with your elbows"  ,"If the attacker is trying to pin your arms down, this can be a useful strike to incapacitate them with. So yank your arms free of their grapple and hit their head with your elbows as hard as you can."));
        mTip.add(new TipsClass("Use pepper spray"  ,"Nothing works more effectively than pepper spray to the face. Some formulations are powerful enough to cause tearing, irritation and even temporary blindness when sprayed directly into the eyes."));
        mTip.add(new TipsClass("Phone for help when your car breaks down"  ,"A car breaking down in the middle of nowhere is the textbook example of a difficult situation, but with a little bit of common sense you can easily find a safe way out of the problem. When your car breaks down and you cannot figure it out on your own, simply get back inside, leave your headlights on, lock the doors, stay in & phone for help. Do not step out of the car till proper help has arrived."));


        listtips.setAdapter(tipsAdapter);









    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent m2 = new Intent(VideosActivity.this , Act1.class);
        startActivity(m2);
        overridePendingTransition(android.R.anim.fade_in , android.R.anim.fade_out);


    }
}
