package com.example.staypositive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AffirmationsMainActivity extends AppCompatActivity {

    FirebaseAuth authProfile;
    Button general_Button, favoritesButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affirmations_main);
        authProfile = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = authProfile.getCurrentUser();
        setTitle("Affirmations");

        general_Button = findViewById(R.id.generalButton);
        general_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmationsMainActivity.this, GeneralAffirmationsActivity.class);
                startActivity(intent);
            }
        });
        favoritesButton = findViewById(R.id.favoritesButton);
        favoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AffirmationsMainActivity.this, GeneralAffirmationsActivity.class);
                startActivity(intent);
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

        if (id == android.R.id.home)
        {
            NavUtils.navigateUpFromSameTask(AffirmationsMainActivity.this);
        }
        else if(id == R.id.refresh_menu)
        {
            //Refresh Activity
            startActivity(getIntent());
            finish();
            overridePendingTransition(0,0);
        }else if(id ==R.id.update_profile)
        {
            Intent intent = new Intent(AffirmationsMainActivity.this, UpdateProfileActivity.class);
            startActivity(intent);
            finish();
        }else if(id ==R.id.update_email)
        {
            Intent intent = new Intent(AffirmationsMainActivity.this, UpdateEmailActivity.class);
            startActivity(intent);
        }else if(id == R.id.settings)
        {
            Toast.makeText(AffirmationsMainActivity.this, "Menu Settings", Toast.LENGTH_LONG).show();
        }else if(id == R.id.change_password)
        {
            Intent intent = new Intent(AffirmationsMainActivity.this, ChangePasswordActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.delete_account)
        {
            Intent intent = new Intent(AffirmationsMainActivity.this, DeleteAccount.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.logout)
        {
            authProfile.signOut();
            Toast.makeText(AffirmationsMainActivity.this, "Sign Out", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(AffirmationsMainActivity.this, MainActivity.class);

            //Clear stack to prevent user from coming back to MainProfile Activity
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(AffirmationsMainActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}