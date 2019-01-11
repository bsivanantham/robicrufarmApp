package com.robicrufarm.camaron;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.AccessTokenTracker;
import com.facebook.login.LoginManager;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.robicrufarm.camaron.Login.PhoneAuthActivity;

public class MainActivity extends RobicRufarm {

    private static final String TAG = "MainActivity";
    private TextView mTextMessage;
    private FirebaseAuth firebaseAuth;
    GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private AccessTokenTracker accessTokenTracker;
    private LinearLayout weather;
    private ConstraintLayout dashboard;
    private DatabaseReference mFirebaseDatabase;
    private FirebaseDatabase mFirebaseInstance;

    private DatabaseReference phValue;
    private DatabaseReference doValue;
    private DatabaseReference tempValue;

    private ArcProgress phMeter;
    private ArcProgress tempMeter;
    private ArcProgress doMeter;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    weather.setVisibility(View.VISIBLE);
                    dashboard.setVisibility(View.INVISIBLE);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    weather.setVisibility(View.INVISIBLE);
                    dashboard.setVisibility(View.VISIBLE);
/*                    fragment = new ProfileFragment();
                    loadFragment(fragment);*/
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    weather.setVisibility(View.INVISIBLE);
                    dashboard.setVisibility(View.INVISIBLE);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = findViewById(R.id.message);
        dashboard = findViewById(R.id.constraintLayout);
        weather = findViewById(R.id.weather);

        this.phMeter = findViewById(R.id.phArcValue);
        this.tempMeter = findViewById(R.id.tempArcValue);
        this.doMeter = findViewById(R.id.doArcValue);

        mFirebaseInstance = FirebaseDatabase.getInstance();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        this.phValue = mFirebaseInstance.getReference("ph");
        this.tempValue = mFirebaseInstance.getReference("temp");
        this.doValue = mFirebaseInstance.getReference("do");

        setPhValueDB(0.00);
        setTempValueDB(0.00);
        setDoValueDB(0.00);

        this.phValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                setPhValueDB(dataSnapshot.getValue(Double.class));

                //energyMeter.setProgress(Integer.parseInt(dataSnapshot.getValue(String.class)));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        phMeter.setProgress(getPhValueDB().intValue());
                        tempMeter.setProgress(getTempValueDB().intValue());
                        doMeter.setProgress(getDoValueDB().intValue());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        this.tempValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //energyMeter.setProgress(Integer.parseInt(dataSnapshot.getValue(String.class)));
                setTempValueDB(dataSnapshot.getValue(Double.class));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        phMeter.setProgress(getPhValueDB().intValue());
                        tempMeter.setProgress(getTempValueDB().intValue());
                        doMeter.setProgress(getDoValueDB().intValue());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        this.doValue.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //energyMeter.setProgress(Integer.parseInt(dataSnapshot.getValue(String.class)));
                setDoValueDB(dataSnapshot.getValue(Double.class));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        phMeter.setProgress(getPhValueDB().intValue());
                        tempMeter.setProgress(getTempValueDB().intValue());
                        doMeter.setProgress(getDoValueDB().intValue());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        int id1 = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id1 == R.id.signout) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                // Name, email address, and profile photo Url
                String name = user.getDisplayName();
                String email = user.getEmail();
                Uri photoUrl = user.getPhotoUrl();

                // Check if user's email is verified
                boolean emailVerified = user.isEmailVerified();

                String uid = user.getUid();

                FirebaseAuth.getInstance().signOut();

                mGoogleSignInClient.signOut().addOnCompleteListener(this,
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                startActivity(new Intent(getApplicationContext(), PhoneAuthActivity.class));
                            }
                        });

                LoginManager.getInstance().logOut();

            }
            startActivity(new Intent(getApplicationContext(), PhoneAuthActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
