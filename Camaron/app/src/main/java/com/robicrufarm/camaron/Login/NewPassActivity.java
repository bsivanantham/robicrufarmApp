package com.robicrufarm.camaron.Login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.robicrufarm.camaron.R;

public class NewPassActivity extends AppCompatActivity {

    EditText email;
    Button btnNewPass;
    FirebaseAuth firebaseAuth;

    private String emailText ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email = (EditText) findViewById(R.id.uyeEmail);
        btnNewPass = (Button) findViewById(R.id.yeniParolaGonder);

        firebaseAuth = FirebaseAuth.getInstance();
        emailText = email.getText().toString();

        btnNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(emailText)) {
                    Toast.makeText(getApplicationContext(), "Please fill e-mail", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.sendPasswordResetEmail(emailText)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "Password reset link was sent your email address", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Mail sending error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });


    }
}