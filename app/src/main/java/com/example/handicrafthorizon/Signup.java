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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {
    EditText t1, t2,t3;
    Button signup;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = findViewById(R.id.login_btn);
        t1 = findViewById(R.id.email);
        t2 = findViewById(R.id.password);
        t3 = findViewById(R.id.name);
        mAuth = FirebaseAuth.getInstance();
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference().getRoot();
        progressDialog = new ProgressDialog(Signup.this);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = t1.getText().toString().trim();
                String pass = t2.getText().toString().trim();
                String name = t3.getText().toString().trim();
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
                if (name.isEmpty()) {

                    t3.setError("name Cant't Be Blank");
                    t3.requestFocus();
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
                signup(email,pass,name);

            }
        });
    }
    private void signup(final String email, final String password, final String name){
        progressDialog.setMessage("Loading...");
        progressDialog.show();
  mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("email", "createUserWithEmail:success");
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();

                            myRef.child("Users").child(user.getUid()).child("email").setValue(email);
                            myRef.child("Users").child(user.getUid()).child("password").setValue(password);
                            myRef.child("Users").child(user.getUid()).child("name").setValue(name);
                            myRef.child("Users").child(user.getUid()).child("type").setValue("User");
                            Toast.makeText(Signup.this, "Signup Successfull",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Signup.this,Login.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("fail", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }


                    }
                });


    }
}