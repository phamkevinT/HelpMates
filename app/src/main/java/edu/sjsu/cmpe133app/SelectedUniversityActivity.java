package edu.sjsu.cmpe133app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SelectedUniversityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_university);

         Intent intent = getIntent();
         String message = intent.getStringExtra(MainActivity.CHOSEN_UNI_MESSAGE);
         TextView textView = (TextView) findViewById(R.id.textView2);

         textView.setText(message);
    }
}
