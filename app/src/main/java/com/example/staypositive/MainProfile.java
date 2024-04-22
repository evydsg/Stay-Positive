package com.example.staypositive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainProfile extends AppCompatActivity {

    Button homeButton, settingsButton, deleteAccountButton;
    TextView textViewWelcome, textViewName, textViewEmail, textViewGender, textViewDOB;
    ProgressBar progressBar;
    String fullName, email, DOB, gender;
    ImageView imageView;
    FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        textViewWelcome = findViewById(R.id.welcome_text);
        textViewName = findViewById(R.id.full_name);
        textViewEmail = findViewById(R.id.email_address);
        textViewDOB = findViewById(R.id.date_of_birth);
        textViewGender = findViewById(R.id.gender);

        homeButton = findViewById(R.id.home_button);
        settingsButton = findViewById(R.id.settings_button);
        deleteAccountButton = findViewById(R.id.delete_account_button);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        progressBar = findViewById(R.id.progressBar);

        if (firebaseUser == null)
        {
            Toast.makeText(MainProfile.this, "Something went wrong! User's details are not available at the moment.", Toast.LENGTH_LONG).show();

        }else {
            progressBar.setVisibility(View.VISIBLE);
            showUserProfile(firebaseUser);
        }
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        deleteAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, DeleteAccount.class);
                startActivity(intent);
            }
        });

    }
    private void showUserProfile(FirebaseUser firebaseUser)
    {
        String userID = firebaseUser.getUid();

        //Extracting User Reference from Database for Registered Users

        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        referenceProfile.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                ReadWriteUserDetails readUserDetails = snapshot.getValue(ReadWriteUserDetails.class);

                if(readUserDetails != null)
                {
                    fullName = firebaseUser.getDisplayName();
                    email = firebaseUser.getEmail();
                    DOB = readUserDetails.DoB;
                    gender = readUserDetails.gender;

                    textViewWelcome.setText("Welcome, "+ fullName + "!");
                    textViewName.setText(fullName);
                    textViewEmail.setText(email);
                    textViewGender.setText(gender);
                    textViewDOB.setText(DOB);
                }

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {
                    Toast.makeText(MainProfile.this,"Something went wrong!", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
            }
        });

    }
}