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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UpdateProfile extends AppCompatActivity {
    private EditText updateName,updateEmail,updatePasswordTxt;
    private Button updateButton , updateEmailBtn , updatePasswordBtn;
    private String name , email , password;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser user = auth.getCurrentUser();
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().setTitle(" Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        updateName = findViewById(R.id.updateName);
        updateEmail = findViewById(R.id.updateEmail);
        updateButton = findViewById(R.id.updateButton);
        updateEmailBtn = findViewById(R.id.updateEmailBtn);

        updatePasswordTxt = findViewById(R.id.updatePasswordTxt);
        updatePasswordBtn = findViewById(R.id.updatePasswordBtn);

        pd = new ProgressDialog(this);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkUser();


            }
        });
        updatePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword();

            }
        });
        updateEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmail();
            }
        });


    }

    private void checkPassword() {
        password = updatePasswordTxt.getText().toString().trim();
        if (password.isEmpty()){
            Toast.makeText(UpdateProfile.this, "Input password", Toast.LENGTH_SHORT).show();

        }else{
            updatepasswordtask();
        }

    }

    private void updatepasswordtask() {
        pd.setTitle("Password update");
        pd.setMessage("Please wait...");
        pd.show();

        user.updatePassword(updatePasswordTxt.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateProfile.this, "Password-Updated successfully", Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            startActivity(new Intent(UpdateProfile.this,LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(UpdateProfile.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();

                    }
                });
    }

    private void checkEmail() {
        email = updateEmail.getText().toString();
        if (email.isEmpty()){
            Toast.makeText(UpdateProfile.this, "please provide Email", Toast.LENGTH_SHORT).show();
        }else{
            updateEmailTask();

        }


    }

    private void updateEmailTask() {
        pd.setTitle("Email update");
        pd.setMessage("Please wait...");
        pd.show();
        user.updateEmail(updateEmail.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(UpdateProfile.this, "Email-Updated successfully", Toast.LENGTH_SHORT).show();
                            auth.signOut();
                            startActivity(new Intent(UpdateProfile.this,LoginActivity.class));
                            finish();
                        }else {
                            Toast.makeText(UpdateProfile.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        pd.dismiss();

                    }
                });
}

    private void checkUser() {
       name = updateName.getText().toString();
       if (name.isEmpty()){
           Toast.makeText(UpdateProfile.this, "Please provide name", Toast.LENGTH_SHORT).show();
       }else{
           updateprofile();
       }
    }

    private void updateprofile() {
        pd.setTitle("Profile update");
        pd.setMessage("Please wait...");
        pd.show();

        UserProfileChangeRequest UpdateProfile = new UserProfileChangeRequest.Builder()
                .setDisplayName(updateName.getText().toString())
                .build();
        user.updateProfile(UpdateProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UpdateProfile.this,ProfileActivity.class));
                    finish();
                }else {
                    Toast.makeText(UpdateProfile.this, "Error :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }
        });

    }


}