package com.ajay.signinpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

public class RegisterActivity extends AppCompatActivity {
    private EditText regName, regEmail, regPass;
    private Button register;
    private TextView login;

    private String name, email, pass;

    private FirebaseAuth auth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Registration");

        regName = findViewById(R.id.regName);
        regEmail = findViewById(R.id.regEmail);
        regPass = findViewById(R.id.regPass);
        register = findViewById(R.id.userRegister);
        login = findViewById(R.id.openLogin);
        auth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);




        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
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

        name = regName.getText().toString();
        email = regEmail.getText().toString();
        pass = regPass.getText().toString();

        if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        } else {
            registerUser();
        }


    }

    private void registerUser() {
        pd.setTitle("Register");
        pd.setMessage("Please wait....");
        pd.show();
        auth.createUserWithEmailAndPassword(email,pass)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "User created Successfully", Toast.LENGTH_SHORT).show();
                   
                    updateUser();
                }else {
                    Toast.makeText(RegisterActivity.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();


            }
        });
    }

    private void updateUser() {
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        auth.getCurrentUser().updateProfile(changeRequest);
        auth.signOut();
        
        openLogin();


    }

    private void openLogin() {
        startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
        finish();

    }
}