package com.example.handicrafthorizon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handicrafthorizon.Admin.AdminDashboard;
import com.example.handicrafthorizon.User.UserDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView signup,logintxt;
    EditText t1, t2,t3;
    Button login;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseUser user;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Loading...");

        if (auth.getCurrentUser() != null) {
            progressDialog.show();
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser user = mAuth.getCurrentUser();

            database = FirebaseDatabase.getInstance();

            myRef = database.getReference().getRoot().child("Users").child(user.getUid());
            myRef.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.

                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                    progressDialog.dismiss();


                    String type = td.get("type").toString();
                    if (type.equals("User")) {
//                        Toast.makeText(Login.this, "Login Successfull",
//                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, UserDashboard.class);
                        startActivity(intent);
                        finish();


                    } else {

//                        Toast.makeText(Login.this, "Login Successfull",
//                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, AdminDashboard.class);
                        startActivity(intent);
                        finish();


                    }


                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    progressDialog.dismiss();
                }
            });


        } else {

            setContentView(R.layout.activity_login);


//        if (user != null) {
//
//            String uid = user.getUid();
//            Intent intent=new Intent(Login.this,MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

            signup = findViewById(R.id.textView2);
            signup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Login.this, Signup.class);
                    startActivity(intent);
                }
            });

            login = findViewById(R.id.login_btn);
            logintxt = findViewById(R.id.login);
            t1 = findViewById(R.id.email);
            t2 = findViewById(R.id.password);
            mAuth = FirebaseAuth.getInstance();
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String email = t1.getText().toString().trim();
                    String pass = t2.getText().toString().trim();

                    if (email.isEmpty()) {
                        t1.setError("Email Cant't Be Blank");
                        t1.requestFocus();
                        return;
                    }

                    if (pass.isEmpty()) {

                        t2.setError("Password Cant't Be Blank");
                        t2.requestFocus();
                        return;
                    }

                    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        t1.setError("Enter Valid Email");
                        t1.requestFocus();

                    }

                    if (pass.length() < 6) {
                        t2.setError("Minimum length of password Is 6");
                        t2.requestFocus();
                    }
                    login(email, pass);

                }
            });


        }
    }
    private void login(final String email, final String password){

        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("", "signInWithEmail:success");
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            mAuth = FirebaseAuth.getInstance();
                            database = FirebaseDatabase.getInstance();

                            myRef = database.getReference().getRoot().child("Users").child(user.getUid());
                            myRef.addValueEventListener(new ValueEventListener() {

                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    // This method is called once with the initial value and again
                                    // whenever data at this location is updated.

                                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                                    progressDialog.dismiss();


                              String type=td.get("type").toString();
                              if(type.equals("User")){
                                  Toast.makeText(Login.this, "Login Successfull",
                                          Toast.LENGTH_SHORT).show();
                                  Intent intent=new Intent(Login.this, UserDashboard.class);
                                  startActivity(intent);
                                  finish();



                              }else{

                                  Toast.makeText(Login.this, "Login Successfull",
                                          Toast.LENGTH_SHORT).show();
                                  Intent intent=new Intent(Login.this, AdminDashboard.class);
                                  startActivity(intent);
                                  finish();


                              }


                                }

                                @Override
                                public void onCancelled(DatabaseError error) {
                                    // Failed to read value
                                    progressDialog.dismiss();
                                }
                            });







                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("", "signInWithEmail:failure", task.getException());
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }
}