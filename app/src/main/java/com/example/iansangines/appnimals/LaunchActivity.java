package com.example.iansangines.appnimals;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


public class LaunchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        new CountDownTimer(3000,1000){
            public void onTick(long millisUntilFinished){
                Log.d("countdowntimer:", Long.toString(millisUntilFinished));
            }
            public void onFinish(){
                Intent petlist = new Intent(LaunchActivity.this,PetListActivity.class);
                petlist.putExtra("Random", "Holamon");
                startActivity(petlist);
            }
        }.start();
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}
