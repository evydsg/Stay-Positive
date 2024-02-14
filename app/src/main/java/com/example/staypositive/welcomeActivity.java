package com.example.staypositive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class welcomeActivity extends AppCompatActivity {

    Button registerButton, loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        registerButton = findViewById(R.id.registerButton);
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(welcomeActivity.this, RegisterActivity.class);
                    startActivity(intent);
                }
            });

        loginButton = findViewById(R.id.loginButton);

           loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle click here
                    Intent intent = new Intent(welcomeActivity.this, LoginActivity.class);

                    // Start the new activity
                    startActivity(intent);
                }
            });
        }



    }
