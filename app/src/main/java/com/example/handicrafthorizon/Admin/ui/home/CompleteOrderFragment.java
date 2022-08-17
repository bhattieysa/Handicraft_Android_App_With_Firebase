package com.example.handicrafthorizon.Admin.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handicrafthorizon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;


public class CompleteOrderFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<OrdersModel> list;
    DatabaseReference databaseReference;
    Query applesQuery;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    OrdersAdapter adapter;
    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    String category;

    Button placeorder;
    TextView total;
    Integer TotalPrice;
    Integer Quantity=1;
    Button complete;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_complete_order, container, false);

        recyclerView=v1.findViewById(R.id.recyclerview);
        placeorder=v1.findViewById(R.id.place_order);
        total=v1.findViewById(R.id.total);
        recyclerView=v1.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);


        fragmentManager=getParentFragmentManager();




        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);


        recyclerView.setLayoutManager(layoutManager);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("order");
        applesQuery = databaseReference.orderByChild("status").equalTo("Complete");




        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                TotalPrice=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String status = dataSnapshot.child("status").getValue(String.class);
                    String order_id = dataSnapshot.child("order_id").getValue(String.class);
                    String order_date = dataSnapshot.child("order_date").getValue(String.class);
                    String total_price = dataSnapshot.child("total_price").getValue(String.class);
                    String name = dataSnapshot.child("Customer_name").getValue(String.class);
                    String number = dataSnapshot.child("Customer_number").getValue(String.class);
                    String address = dataSnapshot.child("Customer_address").getValue(String.class);
                    String city = dataSnapshot.child("Customer_city").getValue(String.class);



                    OrdersModel model= new OrdersModel(total_price,status,order_id,order_date,name,number,city,address);
                    list.add(model);
                }
                if(list.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Orders is Empty",Toast.LENGTH_LONG).show();
                }else {
                    Collections.reverse(list);
                    adapter = new OrdersAdapter(getActivity(), list, progressDialog, databaseReference,database,fragmentManager);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });
        return v1;
    }
}