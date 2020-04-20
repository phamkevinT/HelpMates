package edu.sjsu.cmpe133app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final String CHOSEN_UNI_MESSAGE = "edu.sjsu.cmpe133app.chosenUniMessage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void selectDone(View view) {

        RadioGroup uniChooser = (RadioGroup) findViewById(R.id.radioGroup);
        Intent intent = new Intent(this, SelectedUniversityActivity.class);
        Intent openHomepage = new Intent(this, HomePageActivity.class);
        RadioButton sjsu = (RadioButton) findViewById(R.id.radioButton3);
        RadioButton other = (RadioButton) findViewById(R.id.radioButton4);
        String message = "Test Message";

        if (sjsu.isChecked()) {
            //  message = "Swipe left to right to view navigation menu!";
            // openHomepage.putExtra(CHOSEN_UNI_MESSAGE, message);
            startActivity(openHomepage);
        } else {
            message = "Sorry! We are working on other universities.";
            intent.putExtra(CHOSEN_UNI_MESSAGE, message);
            startActivity(intent);
        }

    }

    public void selectAlreadyUser(View view) {
        Intent intent = new Intent(this, LoginPageActivity.class);
        startActivity(intent);
    }
}
