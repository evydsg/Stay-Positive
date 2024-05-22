package com.example.staypositive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeneralAffirmationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    generalAffirmationAdapter adapter;
    Button favoritesButton, shareButton;
    FirebaseAuth authProfile;
    DatabaseReference databaseReference;
    List<String> affirmations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_affirmations);

        recyclerView = findViewById(R.id.recyclerView);
        favoritesButton = findViewById(R.id.favoritesButton);
        shareButton = findViewById(R.id.shareButton);

        // Initialize Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("favorites");

        // Sample affirmations
        affirmations = new ArrayList<>();
        affirmations.add("You are strong.");
        affirmations.add("You are capable.");
        affirmations.add("You are loved.");
        affirmations.add("You can achieve your goals.");

        // Set up RecyclerView
        adapter = new generalAffirmationAdapter(affirmations);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAffirmationToFavorites(getCurrentAffirmation());
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement sharing functionality
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    displayNextAffirmation();
                }
            }
        });
    }

    private void displayNextAffirmation() {
        int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        int nextPosition = (currentPosition + 1) % affirmations.size();
        recyclerView.smoothScrollToPosition(nextPosition);
    }

    private String getCurrentAffirmation() {
        int currentPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
        return affirmations.get(currentPosition);
    }

    private void addAffirmationToFavorites(String affirmation) {
        String key = databaseReference.push().getKey();
        if (key != null) {
            databaseReference.child(key).setValue(affirmation);
        }
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

        if(id== android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(GeneralAffirmationsActivity.this);
        }
        else if(id == R.id.refresh_menu)
        {
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }else if(id ==R.id.update_profile)
        {
            Intent intent = new Intent(GeneralAffirmationsActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }else if(id ==R.id.update_email)
        {
            Intent intent = new Intent(GeneralAffirmationsActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
        }else if(id == R.id.settings)
        {
            Toast.makeText(GeneralAffirmationsActivity.this, "Menu Settings", Toast.LENGTH_LONG).show();
        }else if(id == R.id.change_password)
        {
            Intent intent = new Intent(GeneralAffirmationsActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.delete_account)
        {
            Intent intent = new Intent(GeneralAffirmationsActivity.this, DeleteAccount.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.logout)
        {
            authProfile.signOut();
            Toast.makeText(GeneralAffirmationsActivity.this, "Sign Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(GeneralAffirmationsActivity.this, MainActivity.class);

            //Clear stack to prevent user from coming back to MainProfile Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(GeneralAffirmationsActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}