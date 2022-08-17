package com.example.handicrafthorizon.Admin.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.User.ui.orders.OrderAdapter;
import com.example.handicrafthorizon.User.ui.orders.OrderDetailsFragment;
import com.example.handicrafthorizon.User.ui.orders.OrderModel;
import com.example.handicrafthorizon.databinding.FragmentHomeBinding;
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

public class HomeFragment extends Fragment {


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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();

        View v1 = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=v1.findViewById(R.id.recyclerview);
        placeorder=v1.findViewById(R.id.place_order);
        total=v1.findViewById(R.id.total);
        recyclerView=v1.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        complete=v1.findViewById(R.id.appCompatButton);

        fragmentManager=getParentFragmentManager();
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();




                CompleteOrderFragment userProfileFragment = new CompleteOrderFragment();

                fragmentTransaction.addToBackStack("returnFragment");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, userProfileFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);


        recyclerView.setLayoutManager(layoutManager);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("order");
        applesQuery = databaseReference.orderByChild("status").equalTo("Pending");




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

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}