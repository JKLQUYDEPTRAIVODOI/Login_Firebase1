package com.example.login_firebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth authProfile;
    ProgressBar progressBar;
    TextView textView;

    private static final String TAG= "LoginActivity";

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
       if(authProfile.getCurrentUser()!=null){
           Toast.makeText(Login.this,"Already Log In!",Toast.LENGTH_LONG).show();

           startActivity(new Intent(Login.this,UserProfile.class));
           finish();
       }else{
           Toast.makeText(Login.this,"You can login now",Toast.LENGTH_LONG).show();
       }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        authProfile= FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        buttonLogin=findViewById(R.id.btn_login);
        progressBar=findViewById(R.id.progressBar);
        textView=findViewById(R.id.RegisterNow);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),Register.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                String email=editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(Login.this, "Please re-enter your email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Valid Email is required");
                    editTextEmail.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Login.this, "Please enter your password", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Password is required");
                    editTextEmail.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(email, password);
                }


                // mAuth.signInWithEmailAndPassword(email, password)
//                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
//                            @Override
//                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                progressBar.setVisibility(View.GONE);
//                                if (task.isSuccessful()) {
//                                    Toast.makeText(Login.this, "Login Successful",Toast.LENGTH_SHORT).show();
//                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                                    startActivity(intent);
//                                    finish();
//
//                                } else {
//                                    try {
//                                        throw task.getException();
//
//                                    }catch (FirebaseAuthInvalidUserException e){
//                                        editTextEmail.setError("User does not exists or is no longer valid");
//                                        editTextEmail.requestFocus();
//                                    }catch (FirebaseAuthInvalidCredentialsException e){
//                                        editTextEmail.setError("Invalid credentials");
//                                        editTextEmail.requestFocus();
//                                    }catch (Exception e){
//                                        Log.e(TAG,e.getMessage());
//                                        Toast.makeText(Login.this,e.getMessage(),
//                                                Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                                progressBar.setVisibility(View.GONE);
//                            }
//
//
//                        });
//            }
            }
        });
    }


    private void loginUser(String email, String password) {
        authProfile.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                //progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser firebaseUser = authProfile.getCurrentUser();
                                    Toast.makeText(Login.this, "Already Log In!", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(Login.this, UserProfile.class));
                                    finish();
//                                    Toast.makeText(Login.this, "Login Successful",Toast.LENGTH_SHORT).show();
//                                    Intent intent=new Intent(getApplicationContext(),MainActivity.class);
//                                    startActivity(intent);
//                                    finish();

                                } else {
                                    try {
                                        throw task.getException();

                                    } catch (FirebaseAuthInvalidUserException e) {
                                        editTextEmail.setError("User does not exists or is no longer valid");
                                        editTextEmail.requestFocus();
                                    } catch (FirebaseAuthInvalidCredentialsException e) {
                                        editTextEmail.setError("Invalid credentials");
                                        editTextEmail.requestFocus();
                                    } catch (Exception e) {
                                        Log.e(TAG, e.getMessage());
                                        Toast.makeText(Login.this, e.getMessage(),
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }
                                progressBar.setVisibility(View.GONE);
                            }



                        });
    }


}