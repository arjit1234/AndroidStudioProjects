package com.example.somya.savoir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * Created by somya on 8/3/2017.
 */

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText userName;
    private EditText studentID;
    private EditText userEmail;
    private EditText userPassword;
    private Button submitButton;
    private Toolbar mToolbar;

    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        mToolbar = (Toolbar) findViewById(R.id.reg_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        userName = (EditText)findViewById(R.id.userName);
        studentID = (EditText)findViewById(R.id.studentID);
        userEmail = (EditText)findViewById(R.id.userEmail);
        userPassword = (EditText)findViewById(R.id.userPassword);
        submitButton = (Button)findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);


    }
    public void saveUserInformation(){
        String fullname = userName.getText().toString().trim();
        String emailaddress = userEmail.getText().toString().trim();
        String studentid = studentID.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser();
    }

    private void registerUser(){
        final String fullname = userName.getText().toString().trim();
        final String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        final String studentid = studentID.getText().toString().trim();
        if (TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this, "Please Enter Email",Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //password is empty
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setTitle("Registering user");
        progressDialog.setMessage("Please wait while we are processing");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //user registered successfully
                            mUser = FirebaseAuth.getInstance().getCurrentUser();
                            String uid = mUser.getUid();

                            databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            Toast.makeText(SignUp.this,"Registration Complete",Toast.LENGTH_SHORT).show();
                            saveUserInformation();
                            HashMap<String, String> userMap = new HashMap<>();
                            userMap.put("fullname", fullname);
                            userMap.put("email", email);
                            userMap.put("studentid", studentid);
                            userMap.put("status", "New User");
                            userMap.put("profilepic", "default");
                            userMap.put("thumbpic","default");
                            databaseReference.setValue(userMap);
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainPage.class));
                        }
                        else {
                            Toast.makeText(SignUp.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    @Override
    public void onClick(View view) {
        if(view == submitButton){
            registerUser();
        }
    }
}
