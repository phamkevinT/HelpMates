package edu.sjsu.cmpe133app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    public final int maxNumOfPosts = 10;
    public final TextView[] postTextViews = new TextView[maxNumOfPosts];
    private String[] postDbReference = new String[maxNumOfPosts];
    private boolean[] isRequestPost = new boolean[maxNumOfPosts];
    private int numOfPosts;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mFirebaseAuth.getCurrentUser();
    private Button AcceptButtons[] = new Button[maxNumOfPosts];
    private Button RejectButtons[] = new Button[maxNumOfPosts];

    /*
        Returns the number of posts currently posted on HomePage.
        @return the current number of posts.
     */
    public int getNumOfPosts()
    {
        return this.numOfPosts;
    }

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


    /**
     * Set the layout of the HomePageActivity to have the homepage layout
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // This is the navigation toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // This is the hamburger menu
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //get instance of db
        FirebaseDatabase database = FirebaseDatabase.getInstance();


        // Read from the database
        for (int i = 0; i < maxNumOfPosts; i++)
        {
                DatabaseReference myRef = database.getReference("postNum" + Integer.toString(i));
                ValueEventListener postListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // Get Post object and use the values to update the UI
                        String post = dataSnapshot.getValue(String.class);
                        if (post != null)
                            addPost(post, true);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        // Getting Post failed, log a message
                        // ...
                    }
                };
                myRef.addListenerForSingleValueEvent(postListener);
        }


    }

    private void onAcceptRequestClick(View view)
    {
        Button btn = (Button) view;
        btn.setEnabled(false);
        Intent moveToChat = new Intent(this, ChatActivity.class);
        startActivity(moveToChat);

    }

    private void onRejectRequestClick(View view)
    {
        Button btn = (Button) view;
        btn.setEnabled(false);
    }

    /**
     * For Navigation bar Fragments.
     * Opens when pressed
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                Intent openHomepageAgain = new Intent(this, HomePageActivity.class);
                startActivity(openHomepageAgain);
                break;
            case R.id.nav_chat:
                Intent openChat = new Intent(this, ChatActivity.class);
                startActivity(openChat);
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;

            case R.id.nav_lock:
                Intent logout = new Intent(this, SignupActivity.class);
                startActivity(logout);
               break;

            // Displays as a toast for now
            case R.id.nav_share:
                Toast.makeText(this, "Shared!", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void createPostMethod(View view)
    {
        Intent createPostAct = new Intent(this, CreatePostActivity.class);
        startActivityForResult(createPostAct, 1);
    }

    private void addPost(String postString, boolean isRequestPost)
    {
        LinearLayout mainFrameLayout = (LinearLayout) findViewById(R.id.fragment_container);

        //Creating a new textview for new post
        TextView newPost = new TextView(this);

        //Setting the text to textview from user input
        newPost.setText(getUserDisplayName() + ": " + postString);

        //Creating a linearlayout for new post
        LinearLayout homePageFrameLayout = new LinearLayout(this);
        homePageFrameLayout.setOrientation(LinearLayout.VERTICAL);

        //Adding a reference to save post's text to db
        postDbReference[numOfPosts] = "postNum" + Integer.toString(numOfPosts);
        DatabaseReference myRef = database.getReference(postDbReference[numOfPosts]);
        myRef.setValue(postString);

        //Adding the new textview to the new linearlayout
        homePageFrameLayout.addView(newPost);

        if (isRequestPost)
        {
            Button acceptBtn = new Button(this);
            acceptBtn.setText("Accept");
            acceptBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onAcceptRequestClick(view);
                }
            });
            homePageFrameLayout.addView(acceptBtn);
            AcceptButtons[numOfPosts] = acceptBtn;

            Button rejectBtn = new Button(this);
            rejectBtn.setText("Reject");
            rejectBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    onRejectRequestClick(view);
                }
            });
            homePageFrameLayout.addView(rejectBtn);
            RejectButtons[numOfPosts] = rejectBtn;
        }

        mainFrameLayout.addView(homePageFrameLayout, 0);
        postTextViews[numOfPosts] = newPost;
        numOfPosts++;
    }

    protected void onActivityResult (int requestCode,
                                     int resultCode,
                                     Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        LinearLayout mainFrameLayout = (LinearLayout) findViewById(R.id.fragment_container);

        //Creating a new textview for new post
        TextView newPost = new TextView(this);

        //Setting the text to textview from user input
        newPost.setText(data.getStringExtra(CreatePostActivity.CREATE_POST_MESSAGE));

        //Creating a linearlayout for new post
        LinearLayout homePageFrameLayout = new LinearLayout(this);
        homePageFrameLayout.setOrientation(LinearLayout.VERTICAL);

        //Adding a reference to save post's text to db
        postDbReference[numOfPosts] = "postNum" + Integer.toString(numOfPosts);
        DatabaseReference myRef = database.getReference(postDbReference[numOfPosts]);
        myRef.setValue(data.getStringExtra(CreatePostActivity.CREATE_POST_MESSAGE));

        //Adding the new textview to the new linearlayout
        homePageFrameLayout.addView(newPost);

        if (data.getStringExtra(CreatePostActivity.IS_REQUEST_POST).equals("true"))
        {
            Button acceptBtn = new Button(this);
            acceptBtn.setText("Accept");
            homePageFrameLayout.addView(acceptBtn);

            Button rejectBtn = new Button(this);
            rejectBtn.setText("Reject");
            homePageFrameLayout.addView(rejectBtn);
        }

        mainFrameLayout.addView(homePageFrameLayout, 0);
        postTextViews[numOfPosts] = newPost;
        numOfPosts++;
    }

    /**
     * If our navigation bar is opened, pressing the back button on the phone will
     * close the navigation bar rather than going a page back
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}
