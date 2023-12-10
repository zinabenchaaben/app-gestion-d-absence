package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;

import java.util.List;

public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;
    FirebaseAuth mAuth;
    Button buttonLogIn, buttonPresence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.Password);
        buttonLogIn = findViewById(R.id.buttonLogIn);

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkEmailAndLogin();
                startActivity(new Intent(Login.this, Presence.class));
            }
        });

        // Example button for navigating to the Presence activity

    }

    private void checkEmailAndLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            List<String> signInMethods = result.getSignInMethods();

                            if (signInMethods != null && !signInMethods.isEmpty()) {
                                // Email is registered, proceed with login
                                loginUser(email, password);
                            } else {
                                // Email is not registered
                                Toast.makeText(Login.this, "Email is not registered.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle the error
                            Toast.makeText(Login.this, "Error checking email: " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Check if the user's email is verified
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null && user.isEmailVerified()) {
                                // Login success and email is verified
                                Toast.makeText(Login.this, "Login success.",
                                        Toast.LENGTH_SHORT).show();

                                // Navigate to another activity or perform other actions here
                            } else {
                                // Email is not verified
                                Toast.makeText(Login.this, "Please verify your email first.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If login fails, display a message to the user.
                            Toast.makeText(Login.this, "Login failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
