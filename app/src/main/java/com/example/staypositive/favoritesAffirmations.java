package com.example.staypositive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class favoritesAffirmations extends AppCompatActivity {

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
        setContentView(R.layout.activity_favorites_affirmations);

        // Retrieve the general affirmations string
        generalAffirmations = getResources().getString(R.string.general_affirmations);

        // Split the affirmations by newline characters
        String[] affirmationsArray = generalAffirmations.split("\\n");

        // Initialize the TextView
        generalAffirmationsTextView = findViewById(R.id.general_affirmations_textView);

        // Create a StringBuilder to build the final display text
        StringBuilder formattedAffirmations = new StringBuilder();

        Log.d("Array", Arrays.toString(affirmationsArray));
        // Loop through the array and append each affirmation to the StringBuilder
        for (String affirmation : affirmationsArray) {
            formattedAffirmations.append(affirmation).append("\n");
        }

        // Set the formatted affirmations text to the TextView
        generalAffirmationsTextView.setText(formattedAffirmations.toString());

        Log.d("Affirmations", Arrays.toString(affirmationsArray));
    }
}
