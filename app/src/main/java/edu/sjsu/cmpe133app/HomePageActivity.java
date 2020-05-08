package edu.sjsu.cmpe133app;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class HomePageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    public final int maxNumOfPosts = 10;
    public final TextView[] postTextViews = new TextView[maxNumOfPosts];
    private String[] postDbReference = new String[maxNumOfPosts];
    private boolean[] isRequestPost = new boolean[maxNumOfPosts];
    private int numOfPosts;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    /*
        Returns the number of posts currently posted on HomePage.
        @return the current number of posts.
     */
    public int getNumOfPosts()
    {
        return this.numOfPosts;
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

    }

    /**
     * For Navigation bar Fragments.
     * Opens when pressed
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MessageFragment()).commit();
                break;
            case R.id.nav_chat:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ChatFragment()).commit();
                Intent openChat = new Intent(this, ChatActivity.class);
                startActivity(openChat);
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
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
