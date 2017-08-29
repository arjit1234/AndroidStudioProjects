package com.example.somya.savoir;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainPage extends AppCompatActivity{


    private Button logoutbutton;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private String userID;
    private FirebaseUser mUser;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Demo Project");

        firebaseAuth = FirebaseAuth.getInstance();

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        String current_uid = mUser.getUid();
        if (firebaseAuth.getCurrentUser() == null){
            //User not logged in
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }
        FirebaseUser user = firebaseAuth.getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        super.onOptionsItemSelected(item);
        if (item.getItemId()==R.id.logoutbutton){
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        if (item.getItemId()==R.id.setting){
            Intent settingIntent = new Intent(MainPage.this, AccountSetting.class);
            startActivity(settingIntent);
        }
        if (item.getItemId()==R.id.allUsers){
            Intent allUsers = new Intent(MainPage.this, AllUsers.class);
            startActivity(allUsers);
        }
        return true;
    }
}
