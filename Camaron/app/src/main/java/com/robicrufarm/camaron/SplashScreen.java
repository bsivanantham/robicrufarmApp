package com.robicrufarm.camaron;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.robicrufarm.camaron.Login.PhoneAuthActivity;
import com.robicrufarm.camaron.Login.RegisterActivity;

/**
 * Created by Balavivek on 21-June-2018.
 */

public class SplashScreen extends RobicRufarm {
    //Handler handler;
    Button signInButton, registerButton ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash);

        registerButton = findViewById(R.id.Register);
        signInButton = findViewById(R.id.girisButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PhoneAuthActivity.class, getApplicationContext());
            }
        });

    }
}
