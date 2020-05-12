package edu.sjsu.cmpe133app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class openProfile extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mFirebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creates the chat activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
    }

    /** Called when the user touches the button */
    public void restPass(View view) {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseAuth.signOut();
        finish();
        Intent logout = new Intent(this, MainActivity.class);
        startActivity(logout);
    }
}
