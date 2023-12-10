package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.StreamDownloadTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Presence extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Spinner courseSpinner;
    private Button showAttendanceButton;
    private Button showStatsButton;
    private List<Etudiant> etudiantList;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presence);

        recyclerView = findViewById(R.id.recyclerView);
        courseSpinner = findViewById(R.id.courseSpinner);
        showAttendanceButton = findViewById(R.id.showAttendanceButton);
        showStatsButton = findViewById(R.id.showStatsButton);

        storage = FirebaseStorage.getInstance();

        // Set up RecyclerView with a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Fetch CSV file names from Firebase Storage and populate the Spinner
        fetchCSVFileNames();

        // Set click listener for showAttendanceButton
        showAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logic to show attendance based on selected course
                showAttendance();
            }
        });

        // Set click listener for showStatsButton
        showStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle logic to show statistics
                showStatistics();
            }
        });
    }

    private void fetchCSVFileNames() {
        StorageReference storageRef = storage.getReference().child("your_csv_folder"); // Change to your folder
        storageRef.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                    @Override
                    public void onSuccess(ListResult listResult) {
                        List<String> fileNames = new ArrayList<>();
                        for (StorageReference item : listResult.getItems()) {
                            fileNames.add(item.getName());
                        }

                        // Populate the Spinner with file names
                        populateSpinner(fileNames);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Presence.this, "Error fetching CSV file names", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateSpinner(List<String> fileNames) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, fileNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        courseSpinner.setAdapter(adapter);
        courseSpinner.setEnabled(true);

        // Set up item selected listener for Spinner
        courseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                // Handle logic when a course is selected
                String selectedCourse = fileNames.get(position);
                Toast.makeText(Presence.this, "Selected Course: " + selectedCourse, Toast.LENGTH_SHORT).show();
                // Now you can load the corresponding CSV file using the selectedCourse information
                loadCSVFile(selectedCourse);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Handle logic when nothing is selected
            }
        });
    }

    private void updateRecyclerView(List<Etudiant> etudiants) {
        // Create and set the adapter for the RecyclerView

        com.example.myapplication.adapter.EtudiantAdapter adapter = new com.example.myapplication.adapter.EtudiantAdapter(etudiants);
        recyclerView.setAdapter(adapter);
    }

    private List<Etudiant> readCSV(InputStream inputStream) {
        List<Etudiant> etudiants = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");

                if (tokens.length >= 5) { // Ensure the line has at least 5 columns
                    String id = tokens[0].trim();
                    String nom = tokens[1].trim();
                    String prenom = tokens[2].trim();
                    String classe = tokens[3].trim();
                    boolean presence = Boolean.parseBoolean(tokens[4].trim());

                    Etudiant etudiant = new Etudiant(id, nom, prenom, classe, presence);
                    etudiants.add(etudiant);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error reading CSV file", Toast.LENGTH_SHORT).show();
        }

        return etudiants;
    }

    private void loadCSVFile(String fileName) {
        // Get the reference to the CSV file in Firebase Storage
        StorageReference csvRef = storage.getReference().child("Mobile").child(fileName);

        // Download the CSV file and read its content
        StorageTask<StreamDownloadTask.TaskSnapshot> errorDownloadingCsvFile = csvRef.getStream().addOnSuccessListener(new OnSuccessListener<InputStream>() {
            @Override
            public void onSuccess(InputStream inputStream) {

            }

            @Override
            public void OnSuccessListene(InputStream inputStream) {
                etudiantList = readCSV(inputStream);
                updateRecyclerView(Collections.unmodifiableList(etudiantList));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                Toast.makeText(Presence.this, "Error downloading CSV file", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAttendance() {
        // Implement logic to show attendance based on the selected course
        // You can access the selected course using courseSpinner.getSelectedItem().toString()
        Toast.makeText(this, "Show attendance for " + courseSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    private void showStatistics() {
        // Implement logic to show statistics
        // You can access the selected course using courseSpinner.getSelectedItem().toString()
        Toast.makeText(this, "Show statistics for " + courseSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }
}
