package com.example.handicrafthorizon.Admin.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.User.ui.orders.OrderDetailsAdapter;
import com.example.handicrafthorizon.User.ui.orders.OrderDetailsModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrdersProductFragment extends Fragment {


    RecyclerView recyclerView;
    ArrayList<OrderDetailsModel> list;
    DatabaseReference databaseReference,databaseReference1;
    Query applesQuery;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    OrderDetailsAdapter adapter;
    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    String category;

    Button placeorder;
    TextView total;
    Integer TotalPrice;
    Integer Quantity=1;
    String Order_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_orders_product, container, false);

        recyclerView=v1.findViewById(R.id.recyclerview);
        placeorder=v1.findViewById(R.id.place_order);
        total=v1.findViewById(R.id.total);
        recyclerView=v1.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);

        Order_id = getArguments().getString("order_id");
        recyclerView.setLayoutManager(layoutManager);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("order");
        applesQuery = databaseReference.orderByChild("order_id").equalTo(Order_id);
        fragmentManager=getParentFragmentManager();



        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                TotalPrice=0;


                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){





                    for(DataSnapshot dataSnapshot:dataSnapshot1.child("products").getChildren()) {




                        String id = dataSnapshot.child("id").getValue(String.class);
                        String image = dataSnapshot.child("image").getValue(String.class);
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String price = dataSnapshot.child("price").getValue(String.class);
                        String quantity = dataSnapshot.child("quantity").getValue(String.class);


                        OrderDetailsModel model= new OrderDetailsModel(id,image,name,price,quantity);
                        list.add(model);

                    }


                    adapter = new OrderDetailsAdapter(getActivity(), list, progressDialog, databaseReference,database,fragmentManager);
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