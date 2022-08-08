package com.example.handicrafthorizon.User.ui.profile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.handicrafthorizon.Login;
import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.databinding.FragmentProductsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class UserProfileFragment extends Fragment {

    TextView name,email,password;
    Button logout;
    FirebaseDatabase database;
    DatabaseReference myRef;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();
        View v1 = inflater.inflate(R.layout.fragment_user_profile, container, false);

        name=v1.findViewById(R.id.name);
        email=v1.findViewById(R.id.textView3);
        password=v1.findViewById(R.id.textView4);
        logout=v1.findViewById(R.id.logout);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading");
        progressDialog.show();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(getActivity(),"test",Toast.LENGTH_SHORT).show();
                mAuth.signOut();


                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();

            }
        });


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        myRef = database.getReference().getRoot().child("Users").child(user.getUid());
        myRef.addValueEventListener(new ValueEventListener() {





            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                progressDialog.dismiss();

                email.setText(td.get("email").toString());
                name.setText(td.get("name").toString());
                password.setText(td.get("password").toString());


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                progressDialog.dismiss();
            }
        });


        return v1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}