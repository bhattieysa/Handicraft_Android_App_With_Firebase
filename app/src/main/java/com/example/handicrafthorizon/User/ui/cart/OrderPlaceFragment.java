package com.example.handicrafthorizon.User.ui.cart;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.User.UserDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class OrderPlaceFragment extends Fragment {

TextView name,address,number,city;
Button order;
ProgressDialog progressDialog;
    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    Query applesQuery;
    String TotalPrice;
    String Name;
    String Address;
    String Number;
    String City;
    String mGroupId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_order_place, container, false);

        name=v1.findViewById(R.id.name);
        address=v1.findViewById(R.id.address);
        number=v1.findViewById(R.id.number);
        city=v1.findViewById(R.id.city);
        order=v1.findViewById(R.id.order);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        TotalPrice = getArguments().getString("total");
        database= FirebaseDatabase.getInstance();
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 Name=name.getText().toString();
                 Address=address.getText().toString();
                 Number= number.getText().toString();
                 City=city.getText().toString();

                if (Name.isEmpty()) {
                    name.setError("Full Name Cant't Be Blank");
                    name.requestFocus();
                    return;
                }

                if (Address.isEmpty()) {

                    address.setError("Address Cant't Be Blank");
                    address.requestFocus();
                    return;
                }
                if (Number.isEmpty()) {

                    number.setError("Number Cant't Be Blank");
                    number.requestFocus();
                    return;
                }
                if (City.isEmpty()) {

                    city.setError("City Cant't Be Blank");
                    city.requestFocus();
                    return;
                }

                database=FirebaseDatabase.getInstance();
                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = mAuth.getCurrentUser();
                databaseReference= FirebaseDatabase.getInstance().getReference("cart");
                applesQuery = databaseReference.orderByChild("user_id").equalTo(user.getUid());
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);


                 mGroupId = database.getReference().push().getKey();
                database.getReference().child("order").child(mGroupId).child("order_id").setValue(mGroupId);
                database.getReference().child("order").child(mGroupId).child("user_id").setValue(user.getUid());
                database.getReference().child("order").child(mGroupId).child("total_price").setValue(TotalPrice);
                database.getReference().child("order").child(mGroupId).child("Customer_name").setValue(Name);
                database.getReference().child("order").child(mGroupId).child("Customer_address").setValue(Address);
                database.getReference().child("order").child(mGroupId).child("Customer_city").setValue(City);
                database.getReference().child("order").child(mGroupId).child("Customer_number").setValue(Number);
                database.getReference().child("order").child(mGroupId).child("status").setValue("Pending");
                database.getReference().child("order").child(mGroupId).child("order_date").setValue(formattedDate);

                applesQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                            String name = dataSnapshot.child("name").getValue(String.class);
                            String image = dataSnapshot.child("image").getValue(String.class);
                            String id = dataSnapshot.child("id").getValue(String.class);
                            String price = dataSnapshot.child("price").getValue(String.class);
                            String quantity = dataSnapshot.child("quantity").getValue(String.class);
                            String user_id = dataSnapshot.child("user_id").getValue(String.class);
                            String product_id = dataSnapshot.child("product_id").getValue(String.class);



                            String mGroupId1 = database.getReference().push().getKey();
                            database.getReference().child("order").child(mGroupId).child("products").child(mGroupId1).child("id").setValue(product_id);
                            database.getReference().child("order").child(mGroupId).child("products").child(mGroupId1).child("name").setValue(name);
                            database.getReference().child("order").child(mGroupId).child("products").child(mGroupId1).child("price").setValue(price);
                            database.getReference().child("order").child(mGroupId).child("products").child(mGroupId1).child("quantity").setValue(quantity);
                            database.getReference().child("order").child(mGroupId).child("products").child(mGroupId1).child("image").setValue(image);

                            snapshot.getRef().removeValue();
                            Toast.makeText(getActivity(),"Order Place Successful",Toast.LENGTH_LONG).show();

                            Intent intent=new Intent(getActivity(), UserDashboard.class);
                            startActivity(intent);
                            getActivity().finish();
                            progressDialog.dismiss();


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressDialog.dismiss();

                    }
                });

            }
        });





        return v1;
    }

}