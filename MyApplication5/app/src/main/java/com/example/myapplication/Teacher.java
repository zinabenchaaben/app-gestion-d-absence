package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Teacher extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);


        // Find the buttons by their IDs
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegister = findViewById(R.id.btnRegister);

        // Set click listeners for the buttons
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle login button click, for example, navigate to the login activity
                Intent loginIntent = new Intent(Teacher.this, Login.class);
                startActivity(loginIntent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle register button click, for example, navigate to the register activity
                Intent registerIntent = new Intent(Teacher.this, Register.class);
                startActivity(registerIntent);
            }
        });
    }
}
