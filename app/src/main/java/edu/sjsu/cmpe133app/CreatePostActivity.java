package edu.sjsu.cmpe133app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class CreatePostActivity extends AppCompatActivity {

    public static final String CREATE_POST_MESSAGE = "CreatePostActivity";
    public static final String IS_REQUEST_POST = "CreatePostActivityRequestPost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
    }

    public void makePost(View view)
    {
        RadioButton radioYes = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radioNo = (RadioButton) findViewById(R.id.radioButton5);
        EditText postContent = (EditText) findViewById(R.id.editText3);
        String postString =  postContent.getText().toString();
        if (radioYes.isChecked())
        {
            Intent openHomePage = new Intent();
            openHomePage.putExtra(CREATE_POST_MESSAGE, postString);
            openHomePage.putExtra(IS_REQUEST_POST, "true");
            setResult(1, openHomePage);
            finish();
        }
        else if (radioNo.isChecked())
        {

        }
    }
}
