package edu.sjsu.cmpe133app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class openProfile extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mFirebaseAuth.getCurrentUser();

    private String getUserDisplayName()
    {
        String name = user.getEmail();
        name = name.substring(0, name.indexOf('@'));
        String firstName = name.substring(0, name.indexOf('.'));
        firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1, firstName.length());
        String lastName = name.substring(name.indexOf('.') + 1, name.length());
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1, lastName.length());
        return firstName + " " + lastName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Creates the chat activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        TextView studentName = (TextView) findViewById(R.id.name);
        studentName.setText(getUserDisplayName());

        TextView studentEmail = (TextView) findViewById(R.id.designation);
        studentEmail.setText(user.getEmail());
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
