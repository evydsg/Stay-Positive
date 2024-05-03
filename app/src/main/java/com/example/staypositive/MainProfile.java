package com.example.staypositive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import com.squareup.picasso.Picasso;

public class MainProfile extends AppCompatActivity {

    Button homeButton, deleteAccountButton;
    TextView textViewWelcome, textViewName, textViewEmail, textViewGender, textViewDOB;
    ProgressBar progressBar;
    String fullName, email, DoB, gender;
    ImageView imageView;
    FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_profile);

        getSupportActionBar().setTitle("Profile");

        textViewWelcome = findViewById(R.id.welcome_text);
        textViewName = findViewById(R.id.full_name);
        textViewEmail = findViewById(R.id.email_address);
        textViewDOB = findViewById(R.id.date_of_birth);
        textViewGender = findViewById(R.id.gender);

        homeButton = findViewById(R.id.home_button);

        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        progressBar = findViewById(R.id.progressBar);

        //Set OnClickListener on ImageView to Open UploadProfilePicActivity
        imageView = findViewById(R.id.profile_pic);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainProfile.this, UploadProfilePicActivity.class);
                startActivity(intent);
            }
        });

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
                    gender = readUserDetails.gender;
                    DoB = readUserDetails.doB; // Assigning DOB from database
                    textViewDOB.setText(DoB); // Setting DOB to textViewDOB


                    textViewWelcome.setText("Welcome, "+ fullName + "!");
                    textViewName.setText(fullName);
                    textViewEmail.setText(email);
                    textViewGender.setText(gender);

                    //Set User Profile Picture (After it has been uploaded)
                    Uri uri = firebaseUser.getPhotoUrl();

                    Picasso.get().load(uri).into(imageView);
                }else {
                    Toast.makeText(MainProfile.this,"Something went wrong!", Toast.LENGTH_LONG).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //Inflate Menu Items
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //When any option is selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.refresh_menu)
        {
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }else if(id ==R.id.update_profile)
        {
            Intent intent = new Intent(MainProfile.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }else if(id ==R.id.update_email)
        {
            Intent intent = new Intent(MainProfile.this, UpdateEmailActivity.class);
            startActivity(intent);
        }else if(id == R.id.settings)
        {
           Toast.makeText(MainProfile.this, "Menu Settings", Toast.LENGTH_LONG).show();
        }else if(id == R.id.change_password)
        {
            Intent intent = new Intent(MainProfile.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        }else if(id == R.id.delete_account)
        {
            Intent intent = new Intent(MainProfile.this, DeleteAccount.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.logout)
        {
            authProfile.signOut();
            Toast.makeText(MainProfile.this, "Sign Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainProfile.this, MainActivity.class);


            //Clear stack to prevent user from coming back to MainProfile Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(MainProfile.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}