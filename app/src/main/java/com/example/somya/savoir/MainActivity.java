package com.example.somya.savoir;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText loginid;
    private EditText lpassword;
    private Button loginButton;
    private Button signupButton;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toast.makeText(this, "WELCOME TO THE NEW WAY OF EDUCATING", Toast.LENGTH_SHORT).show();

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            // start profile activity
            finish();
            startActivity(new Intent(getApplicationContext(), MainPage.class));
        }

        loginid = (EditText)findViewById(R.id.loginId);
        lpassword = (EditText)findViewById(R.id.lpassword);
        loginButton = (Button)findViewById(R.id.loginButton);
        signupButton = (Button)findViewById(R.id.signupButton);
        progressDialog = new ProgressDialog(this);

        loginButton.setOnClickListener(this);
        signupButton.setOnClickListener(this);
    }

    /*public void signUp(View view){
        //Signup window Button
        Intent i = new Intent(MainActivity.this, SignUp.class);
        startActivity(i);

    }*/
    private void userlogin(){
        String email = loginid.getText().toString().trim();
        String password = lpassword.getText().toString().trim();

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
        progressDialog.setTitle("Logging in");
        progressDialog.setMessage("Please wait!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            //start home activity
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainPage.class));
                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        if (view == loginButton){
            userlogin();
        }
        if (view == signupButton){
            startActivity(new Intent(getApplicationContext(), SignUp.class));
        }

    }
}
