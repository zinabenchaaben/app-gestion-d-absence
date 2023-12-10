package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Register extends AppCompatActivity {
    TextInputEditText textName, textEmailAddress, textPhoneNumber, textPassword, textVerifyPassword;
    TextInputLayout layoutName, layoutEmail, layoutPhoneNumber, layoutPassword, layoutVerifyPassword;
    FirebaseAuth mAuth;
    Button buttonReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        textName = findViewById(R.id.name);
        textEmailAddress = findViewById(R.id.email);
        textPhoneNumber = findViewById(R.id.phone);
        textPassword = findViewById(R.id.Password);
        textVerifyPassword = findViewById(R.id.verifyPassword);
        layoutName = findViewById(R.id.layoutName);
        layoutEmail = findViewById(R.id.layoutEmail);
        layoutPhoneNumber = findViewById(R.id.layoutPhone);
        layoutPassword = findViewById(R.id.layoutPassword);
        layoutVerifyPassword = findViewById(R.id.layoutVerifyPassword);
        buttonReg = findViewById(R.id.buttonSignUp);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }

    private void signUp() {
        String name = textName.getText().toString();
        String email = textEmailAddress.getText().toString();
        String phoneNumber = textPhoneNumber.getText().toString();
        String password = textPassword.getText().toString();
        String verifyPassword = textVerifyPassword.getText().toString();

        // Validate fields
        if (!validateName(name) || !validatePhoneNumber(phoneNumber) ||
                !validatePassword(password, verifyPassword)) {
            return;
        }



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registration success
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Update user profile with name and phone number
                            if (user != null) {
                                user.updateProfile(buildUserProfileChangeRequest(name, phoneNumber))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    // Update successful
                                                    sendEmailVerification();
                                                } else {
                                                    Toast.makeText(Register.this,
                                                            "Failed to update user profile.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            // If registration fails, display a message to the user.
                            Toast.makeText(Register.this, "Registration failed. " + task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.sendEmailVerification()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register.this,
                                        "Email verification sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();

                                // You can navigate to another activity or perform other actions here
                            } else {
                                Toast.makeText(Register.this,
                                        "Failed to send email verification.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    private boolean validateName(String name) {
        if (name.isEmpty()) {
            layoutName.setError("Enter your name");
            return false;
        } else {
            layoutName.setError(null);
            return true;
        }
    }

    private boolean validatePhoneNumber(String phoneNumber) {
        // You can add more advanced validation for phone number if needed
        if (phoneNumber.isEmpty()) {
            layoutPhoneNumber.setError("Enter your phone number");
            return false;
        } else {
            layoutPhoneNumber.setError(null);
            return true;
        }
    }

    private boolean validatePassword(String password, String verifyPassword) {
        if (password.isEmpty() || password.length() < 6) {
            layoutPassword.setError("Password must be at least 6 characters");
            return false;
        } else if (!password.equals(verifyPassword)) {
            layoutVerifyPassword.setError("Passwords do not match");
            return false;
        } else {
            layoutPassword.setError(null);
            layoutVerifyPassword.setError(null);
            return true;
        }
    }

    // Helper method to build user profile update request
    private UserProfileChangeRequest buildUserProfileChangeRequest(String name, String phoneNumber) {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();
        builder.setDisplayName(name);

        // Additional user profile information can be added as needed
        // For example: builder.setPhoneNumber(phoneNumber);

        return builder.build();
    }
}
