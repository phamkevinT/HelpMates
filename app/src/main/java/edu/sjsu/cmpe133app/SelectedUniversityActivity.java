package edu.sjsu.cmpe133app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectedUniversityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_university);

         Intent intent = getIntent();
         String message = intent.getStringExtra(MainActivity.CHOSEN_UNI_MESSAGE);
         TextView textView = findViewById(R.id.textView2);

         textView.setText(message);
    }
}
