package com.example.staypositive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GeneralAffirmationsActivity extends AppCompatActivity {

    FirebaseAuth authProfile;
    ProgressBar progressBar;
    ScrollView scrollView;
    int previousScrolly = 0;
    int currentAffirmationIndex = 0;
    String generalAffirmations;
    TextView generalAffirmationsTextView;
    List<String> selectedAffirmations;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_affirmations);

        FirebaseApp.initializeApp(this);

        //generalAffirmations
        generalAffirmations = getResources().getString(R.string.general_affirmations);
        generalAffirmationsTextView = findViewById(R.id.general_affirmations_textView);
        //Split the list by "\n"
        List<String> affirmationsList = new ArrayList<>(Arrays.asList(generalAffirmations.split("\n")));

        //Shuffle the list
        Collections.shuffle(affirmationsList);


        //Select the first 20 affirmations
        selectedAffirmations = affirmationsList.subList(0, 20);
        Log.d("Affirmations", "Size: " + selectedAffirmations.size());
        displayNextAffirmation();

        //Determinate progress
        progressBar = findViewById(R.id.progress_Bar);
        progressBar.setVisibility(View.VISIBLE);

        scrollView = findViewById(R.id.scroll_view);

        //Increasing the progressBar by 10 every swipe

        scrollView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            int scrollY = scrollView.getScrollY();
            int bottom = scrollView.getChildAt(0).getHeight() - scrollView.getHeight();

            if(scrollY >= bottom){
                loadMoreAffirmations();
                displayNextAffirmation();
            }
            if (scrollY < previousScrolly) {
                progressBar.incrementProgressBy(10);
            }
            previousScrolly = scrollY;
        });
        
    }

    public void displayNextAffirmation() {

        if (currentAffirmationIndex < selectedAffirmations.size()) {
            currentAffirmationIndex++;
            Log.d("Affirmations", "Displaying affirmation at index: " + currentAffirmationIndex);
            Log.d("Affirmations", "Affirmation: " + selectedAffirmations.get(currentAffirmationIndex));
            generalAffirmationsTextView.setText(selectedAffirmations.get(currentAffirmationIndex));

        }
    }

    public void loadMoreAffirmations() {
        // Load more affirmations if available
        if (currentAffirmationIndex < selectedAffirmations.size()) {
            displayNextAffirmation();
        } else {
            // Display a message indicating that all affirmations have been shown
            Toast.makeText(this, "No more affirmations", Toast.LENGTH_SHORT).show();
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