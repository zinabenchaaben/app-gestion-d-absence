package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

    public class Add extends AppCompatActivity {

        private EditText editTextNom, editTextPrenom, editTextID, editTextClasse;
        private Button btnAddStudent, btnJava, btnWeb, btnMobile, btnPaiement;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);

            editTextNom = findViewById(R.id.editTextNom);
            editTextPrenom = findViewById(R.id.editTextPrenom);
            editTextID = findViewById(R.id.editTextID);
            editTextClasse = findViewById(R.id.editTextClasse);
            btnAddStudent = findViewById(R.id.btnAddStudent);
            btnJava = findViewById(R.id.btnJava);
            btnWeb = findViewById(R.id.btnWeb);
            btnMobile = findViewById(R.id.btnMobile);
            btnPaiement = findViewById(R.id.btnPaiement);

            btnAddStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addStudentToCSV("General.csv");
                }
            });

            btnJava.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addStudentToCSV("Java.csv");
                }
            });

            btnWeb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addStudentToCSV("Web.csv");
                }
            });

            btnMobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addStudentToCSV("Mobile.csv");
                }
            });

            btnPaiement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addStudentToCSV("Paiement.csv");
                }
            });
        }

        private void addStudentToCSV(String fileName) {
            // Get data entered by the user
            String nom = editTextNom.getText().toString();
            String prenom = editTextPrenom.getText().toString();
            String id = editTextID.getText().toString();
            String classe = editTextClasse.getText().toString();

            // Check input controls
            if (isValidName(nom) && isValidName(prenom) && isValidID(id)) {
                // Add logic to process the data (e.g., add to a database)
                // ...

                // Save data to the corresponding CSV file in internal storage
                saveToCSV(fileName, nom, prenom, id, classe);

                // Example: Display a success message
                showToast("Student added successfully!");
            } else {
                showToast("Please enter valid information.");
            }
        }

        private void saveToCSV(String fileName, String nom, String prenom, String id, String classe) {
            try {
                // Use getFilesDir() to get the app's internal storage directory
                File file = new File(getFilesDir(), fileName);

                FileWriter fw = new FileWriter(file, true); // true for append mode
                fw.append(nom).append(",").append(prenom).append(",").append(id).append(",").append(classe).append("\n");
                fw.close();

                // Add Python-like logic here if needed
                // For example, calling a Python function from Java

                showToast("Data added to " + fileName + " in internal storage successfully!");
            } catch (IOException e) {
                e.printStackTrace();
                showToast("Error writing to " + fileName + " in internal storage: " + e.getMessage());
            }
        }

        private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private boolean isValidName(String name) {
        return !TextUtils.isEmpty(name) && !Character.isDigit(name.charAt(0));
    }

    private boolean isValidID(String id) {
        return id.matches("\\d{8}");
    }
}
