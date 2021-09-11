package com.ajay.signinpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPassword extends AppCompatActivity {
    private EditText forEmail;
    private Button forgot;
    private String email;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        forEmail = findViewById(R.id.forEmail);
        forgot = findViewById(R.id.forgot);
        pd = new ProgressDialog(this);

        forgot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                email = forEmail.getText().toString();

                if (email.isEmpty()){
                    Toast.makeText(forgotPassword.this, "Please provide Email", Toast.LENGTH_SHORT).show();
                }else {
                    resetPassword();
                }
            }
        });
    }

    private void resetPassword() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(forgotPassword.this, "reset password link has been send to your register Email -Id", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(forgotPassword.this,LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(forgotPassword.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}