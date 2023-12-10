package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Ajout extends AppCompatActivity {

    private static final int FILE_PICK_REQUEST_CODE = 123;
    private Button btnAddCSV;
    private WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout);

        btnAddCSV = findViewById(R.id.btnAddCSV);

        btnAddCSV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickCSVFile();
            }
        });

        Button onAddStudentClick = findViewById(R.id.btnAddStudent);
        onAddStudentClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ajout.this, Add.class);
                startActivity(intent);
            }
        });
        myWebView = findViewById(R.id.webview); // Replace with your WebView ID
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true); // Enable JavaScript

        // Load the Google Drive link
        myWebView.loadUrl("https://drive.google.com/file/d/1KmDREx94LkRZxKOjMibyzHivsB1zYSb-/view?usp=drive_link");

        // Set a WebViewClient to open the link in the WebView
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    private void pickCSVFile() {
        // You can keep this method if you need it for other purposes
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/csv");

        startActivityForResult(intent, FILE_PICK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICK_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedFileUri = data.getData();
            // Vous pouvez maintenant utiliser cette URI pour lire le fichier CSV
            // importCSV(selectedFileUri);
            showToast("CSV file selected: " + selectedFileUri.toString());
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
