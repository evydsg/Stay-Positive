package com.example.staypositive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.Month;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    
    private EditText editTextRegisterName, editTextRegisterEmail, editTextRegisterDOB, editTextregisterPassword,
                    editTextregisterConfirmPassword;
    
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;
    private static final String TAG="RegsisterActivity";

    private DatePickerDialog picker;
    
    
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

        editTextRegisterDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                //Date PickerDialog

                picker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        editTextRegisterDOB.setText((month + 1) + "/" + dayOfMonth + '/' + year);

                    }
                }, year, month, day);
                picker.show();
            }
        });

        progressBar = findViewById(R.id.progressBar);
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
                String textConfirmPwd = editTextregisterConfirmPassword.getText().toString();
                String textGender;

                if (TextUtils.isEmpty(textName)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your name.", Toast.LENGTH_LONG).show();
                    editTextRegisterName.setError("Name is required.");
                    editTextRegisterName.requestFocus();
                } else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your email address.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email address is required.");
                    editTextRegisterEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email address.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid email address is required.");
                    editTextRegisterEmail.requestFocus();
                } else if (TextUtils.isEmpty(textDOB)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your date of birth.", Toast.LENGTH_LONG).show();
                    editTextRegisterDOB.setError("Date of birth is required.");
                    editTextRegisterDOB.requestFocus();
                } else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Please select your gender.", Toast.LENGTH_LONG).show();
                    radioButtonRegisterGenderSelected.setError("Gender is required.");
                    radioButtonRegisterGenderSelected.requestFocus();
                } else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your password.", Toast.LENGTH_LONG).show();
                    editTextregisterPassword.setError("Password is required.");
                    editTextregisterPassword.requestFocus();
                } else if (textPwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password should be at least 6 digits.", Toast.LENGTH_LONG).show();
                    editTextregisterPassword.setError("Password is too weak.");
                    editTextregisterPassword.requestFocus();
                } else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please confirm your password.", Toast.LENGTH_LONG).show();
                    editTextregisterConfirmPassword.setError("Password confirmation is required.");
                    editTextregisterConfirmPassword.requestFocus();
                } else if (!(textPwd.equals(textConfirmPwd))) {
                    Toast.makeText(RegisterActivity.this, "Please enter same password", Toast.LENGTH_LONG).show();
                    editTextregisterConfirmPassword.setError("Password confirmation is required.");
                    editTextregisterConfirmPassword.requestFocus();
                    //Clear the entered password
                    editTextregisterPassword.clearComposingText();
                    editTextregisterConfirmPassword.clearComposingText();
                } else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textName, textEmail, textDOB, textGender, textPwd);
                }
            }
        });
    }

        //Register User using credentials user entered during registration
        private void registerUser(String textName, String textEmail, String textDOB, String textGender, String textPwd)
        {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(RegisterActivity.this, "User registered successfully.", Toast.LENGTH_LONG).show();

                        FirebaseUser firebaseUser = auth.getCurrentUser();

                        //Update Display Name of User
                        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(textName).build();
                        firebaseUser.updateProfile(profileChangeRequest);

                        //Enter User Data into the Firebase Realtime Dataase
                        ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(textDOB, textGender);

                        //Extracting user reference from database for registered users
                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                        referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful())
                                {
                                    //Send Verification Email
                                    firebaseUser.sendEmailVerification();

                                    Toast.makeText(RegisterActivity.this, "User registered successfully. Please verify your email.", Toast.LENGTH_LONG).show();

                                    //Open User Profile after successful registration
                                    Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                                    //To prevent User from returning back to Register Activity on pressing back button after registration
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                                    startActivity(intent);
                                    finish();
                                }else {

                                    Toast.makeText(RegisterActivity.this, "User registration failed. Please try again.", Toast.LENGTH_LONG).show();
                                }
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    }else {
                        try{
                            throw task.getException();
                        }catch (FirebaseAuthWeakPasswordException e)
                        {
                            editTextregisterPassword.setError("Your password is too weak. Kindly use of mix of alphabets, numbers and special number.");
                            editTextregisterPassword.requestFocus();
                        }catch (FirebaseAuthInvalidCredentialsException e)
                        {
                            editTextregisterPassword.setError("Your email is invalid or already in use. Kindly re-enter.");
                            editTextregisterPassword.requestFocus();
                        }catch (FirebaseAuthUserCollisionException e)
                        {
                            editTextRegisterEmail.setError("User is already registered with this email. Use another email.");
                            editTextRegisterEmail.requestFocus();
                        }catch (Exception e)
                        {
                            Log.e(TAG, e.getMessage());
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        progressBar.setVisibility(View.GONE);

                    }
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
            NavUtils.navigateUpFromSameTask(RegisterActivity.this);
        }

        return super.onOptionsItemSelected(item);
    }
    }
