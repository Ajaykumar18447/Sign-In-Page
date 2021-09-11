package com.ajay.signinpage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {
    private TextView name, email;
    private Button logout,profileUpdate;
    FirebaseAuth auth ;
    FirebaseUser user ;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        getSupportActionBar().setTitle("Your Profile");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        name = findViewById(R.id.userName);
        email = findViewById(R.id.userEmail);
        logout = findViewById(R.id.userlogout);
        profileUpdate = findViewById(R.id.profileUpdate);
        pd = new ProgressDialog(this);

        if (user != null){
            name.setText(user.getDisplayName());
            email.setText(user.getEmail());
        }
        logout.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                auth.signOut();
                startActivity(new Intent(ProfileActivity.this ,LoginActivity.class));

                finish();

            }

        });
        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this , UpdateProfile.class));
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (user == null){
            startActivity(new Intent(this , LoginActivity.class));
            finish();
        }
    }
}