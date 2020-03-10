package edu.sjsu.cmpe133app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPageActivity extends AppCompatActivity {

    private FirebaseAuth mFireBaseAuth;
    private static final String TAG = LoginPageActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mFireBaseAuth = FirebaseAuth.getInstance();
        // Still need to check if the user is already signed in.
    }

    public void signInButtonClick(View view)
    {
        EditText editTextUsername = (EditText) findViewById(R.id.editText);
        EditText editTextPassword = (EditText) findViewById(R.id.editText2);
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        // Do not have to parse anything. Just check database and verify if user exists here

        mFireBaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mFireBaseAuth.getCurrentUser();
                            // Update UI
                            Toast.makeText(LoginPageActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginPageActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //Update UI
                        }

                        // ...
                    }
                });
    }
}
