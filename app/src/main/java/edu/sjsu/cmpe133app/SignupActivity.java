package edu.sjsu.cmpe133app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignupActivity extends AppCompatActivity {

    private EditText userName, userEmail, userPassword;
    private Button signUpButton;
    private TextView userLogin;
    private FirebaseAuth firebaseAuth;


    private void signUp(){
        userName = (EditText)findViewById(R.id.etuserName);
        userEmail= (EditText)findViewById(R.id.etuserEmail);
        userPassword = (EditText)findViewById(R.id.etuserPassword);
        signUpButton = (Button)findViewById(R.id.btnsignUpButton);
        userLogin = (TextView)findViewById(R.id.tvuserLogin);


    }

    private boolean validateUsername() {
        String usernameInput = userName.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            userName.setError("Field can't be empty");
            return false;
        } else if (usernameInput.length() > 15) {
            userName.setError("Username too long");
            return false;
        } else {
            userName.setError(null);
            return true;
        }
    }

    private boolean validateEmail(){

        String emailInput = userEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@sjsu+\\.+edu+";
        if (emailInput.isEmpty()){
            userEmail.setError("Field can't be empty");
            return false;
        } else if (!emailInput.matches(emailPattern)) {
            userEmail.setError("Please enter a valid email address");
            return false;

        } else {
            userEmail.setError(null);
            return true;
        }


    }

    private boolean validatePassword (){

        String passwordInput = userPassword.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            userPassword.setError("Field can't be empty");
            return false;
        } else if (passwordInput.length() < 6) {
            userPassword.setError("Password too short");
            return false;
        } else {
            userPassword.setError(null);
            return true;
        }
    }



    private void sendEmailVerification(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser !=null)
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(SignupActivity.this,"Succesfully Registerd",Toast.LENGTH_SHORT).show();
                        firebaseAuth.signOut();
                        finish();
                        startActivity(new Intent(SignupActivity.this, LoginPageActivity.class));
                    }
                }
            });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signUp();
        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() && validatePassword() && validateUsername()) {
                    String user_email = userEmail.getText().toString().trim();
                    String user_password = userPassword.getText().toString().trim();
                    firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendEmailVerification();
                                //Toast.makeText(SignupActivity.this, "registration successful", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(SignupActivity.this, LoginPageActivity.class));
                            }else{
                                Toast.makeText(SignupActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(SignupActivity.this, LoginPageActivity.class));
                            }


                        }
                    });
                }
            }
        });

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginPageActivity.class));
            }
        });


    }
}
