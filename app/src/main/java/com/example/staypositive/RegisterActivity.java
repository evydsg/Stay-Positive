package com.example.staypositive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    
    private EditText editTextRegisterName, editTextRegisterEmail, editTextRegisterDOB, editTextregisterPassword,
                    editTextregisterConfirmPassword;
    
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        editTextRegisterName = findViewById(R.id.editText_register_name);
        editTextRegisterEmail = findViewById(R.id.editText_register_email);
        editTextRegisterDOB = findViewById(R.id.editText_register_dob);
        editTextregisterPassword = findViewById(R.id.editText_register_password);
        editTextregisterConfirmPassword = findViewById(R.id.editText_register_confirm_password);

        //RadioButton for Gender
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        Button buttonRegister = findViewById(R.id.signUp);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioButtonRegisterGenderSelected = findViewById(selectedGenderId);

                //Obtain the entered data

                String textName = editTextRegisterName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textDOB = editTextRegisterDOB.getText().toString();
                String textPwd = editTextregisterPassword.getText().toString();
                String textConfirmPwd= editTextregisterConfirmPassword.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textName))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your name.", Toast.LENGTH_LONG).show();
                    editTextRegisterName.setError("Name is required.");
                    editTextRegisterName.requestFocus();
                }else if(TextUtils.isEmpty(textEmail))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your email address.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email address is required.");
                    editTextRegisterEmail.requestFocus();
                } else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid email address is required.");
                    editTextRegisterEmail.requestFocus();
                }else if(TextUtils.isEmpty(textDOB))
                {
                    Toast.makeText(RegisterActivity.this, "Please enter your date of birth.", Toast.LENGTH_SHORT).show();
                    editTextRegisterDOB.setError("Date of birth is required.");
                    editTextRegisterDOB.requestFocus();
                }
            }
        });
    }
}