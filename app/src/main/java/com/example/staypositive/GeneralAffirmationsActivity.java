package com.example.staypositive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class GeneralAffirmationsActivity extends AppCompatActivity {

    FirebaseAuth authProfile;
    ProgressBar progressBar;
    ScrollView scrollView;
    int previousScrolly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_affirmations);


        //Determinate progress
        progressBar = findViewById(R.id.progress_Bar);
        progressBar.setVisibility(View.VISIBLE);

        scrollView = findViewById(R.id.scroll_view);

        //Increasing the progressBar by 10 every swipe

        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                int scrollY = scrollView.getScrollY();

                if(scrollY < previousScrolly){
                    progressBar.incrementProgressBy(10);
                }
                previousScrolly = scrollY;
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