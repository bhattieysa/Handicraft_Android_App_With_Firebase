package com.example.handicrafthorizon.User.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handicrafthorizon.Admin.ui.products.ProductsAdapter;
import com.example.handicrafthorizon.Admin.ui.products.ProductsModel;
import com.example.handicrafthorizon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserViewProductsFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<UserProductsModel> list;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    UserProductAdapter adapter;
    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    String category;
    Query applesQuery;
    TextView categoryname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View v1= inflater.inflate(R.layout.fragment_user_view_products, container, false);

        recyclerView=v1.findViewById(R.id.recyclerview);
        categoryname=v1.findViewById(R.id.category);

        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
        database=FirebaseDatabase.getInstance();
        category = getArguments().getString("category");
        categoryname.setText(category);
        databaseReference= FirebaseDatabase.getInstance().getReference("products");
        applesQuery = databaseReference.orderByChild("category").equalTo(category);

        fragmentManager=getParentFragmentManager();

        list = new ArrayList<>();
        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String image = dataSnapshot.child("image").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);
                    String category = dataSnapshot.child("category").getValue(String.class);


                    UserProductsModel model= new UserProductsModel(image,name,id,price,category);


                    list.add(model);
                }
                if(list.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Products Are Empty",Toast.LENGTH_LONG).show();
                }else {
                    adapter = new UserProductAdapter(getActivity(), list, progressDialog, databaseReference,database);
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