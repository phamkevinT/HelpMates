package edu.sjsu.cmpe133app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//Password to sharan's user is sjsu1234

public class LoginPageActivity extends AppCompatActivity {

    private FirebaseAuth mFireBaseAuth;
    private static final String TAG = LoginPageActivity.class.getName();
    private TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
//
//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApplicationId("1:52676221130:android:8b71926f128e9d97d57c33") // Required for Analytics.
//                .setProjectId("cmpe133app") // Required for Firebase Installations.
//                .setApiKey("AIzaSyAttnFLaA4ntHvphzvYNJ4MrXjOYRlBMcM") // Required for Auth.
//                .build();
//        FirebaseApp.initializeApp(this, options, "cmpe133app");

        mFireBaseAuth = FirebaseAuth.getInstance();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        //Reset Password
        forgotPassword = (TextView)findViewById(R.id.tvForgotPassword);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPageActivity.this, ResetPassword.class));
            }
        });

        // Still need to check if the user is already signed in.
    }

    private boolean checkEmailVerification(){
        FirebaseUser firebaseUser = mFireBaseAuth.getInstance().getCurrentUser();
        Boolean emailFlag = firebaseUser.isEmailVerified();
        boolean result;

        if (emailFlag){
            startActivity(new Intent(LoginPageActivity.this, HomePageActivity.class));
            result = true;
        }else{
            Toast.makeText(this,"Verify your email",Toast.LENGTH_SHORT).show();
            result= false;
        }

        return result;

    }

    public void signInButtonClick(View view) {
        EditText editTextUsername = (EditText) findViewById(R.id.editText);
        EditText editTextPassword = (EditText) findViewById(R.id.editText2);
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();

        final Intent homePage = new Intent(this, HomePageActivity.class);
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
                            checkEmailVerification();
                            Toast.makeText(LoginPageActivity.this, "Authentication success.",
                                    Toast.LENGTH_SHORT).show();
                           // startActivity(homePage);
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
