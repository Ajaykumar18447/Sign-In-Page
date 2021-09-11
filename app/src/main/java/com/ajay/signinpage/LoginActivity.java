package com.ajay.signinpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText logEmail, logPass;
    private Button login;
    private TextView register , forgotPassword;

    private String email, pass;

    FirebaseAuth auth;

    private ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();

        logEmail = findViewById(R.id.logEmail);
        logPass = findViewById(R.id.logPass);
        login = findViewById(R.id.userLogin);
        register = findViewById(R.id.openRegister);
        forgotPassword = findViewById(R.id.resetPassword);

        pd = new ProgressDialog(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, com.ajay.signinpage.forgotPassword.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                validateUser();
            }
        });
    }
    @Override
    protected void onStart() {

        super.onStart();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(this , ProfileActivity.class));
            finish();
        }
    }


    private void validateUser() {
        email = logEmail.getText().toString();
        pass = logPass.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Enter your Email ", Toast.LENGTH_SHORT).show();
        } if (TextUtils.isEmpty(pass)) {
            Toast.makeText(this, "Enter  Password ", Toast.LENGTH_SHORT).show();
        } else {
            loginUser();
        }

    }

    private void loginUser() {
        pd.setTitle("Login");
        pd.setMessage("Login...");
        pd.show();
        auth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, ProfileActivity.class));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();

                    }
                });
    }
}