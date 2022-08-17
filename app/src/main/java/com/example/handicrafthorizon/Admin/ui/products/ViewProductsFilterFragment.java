package com.example.handicrafthorizon.Admin.ui.products;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.handicrafthorizon.Admin.ui.categories.CategoryAdapter;
import com.example.handicrafthorizon.Admin.ui.categories.CategoryModel;
import com.example.handicrafthorizon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ViewProductsFilterFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<ProductsModel> list;
    DatabaseReference databaseReference;
    ProductsAdapter adapter;
    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    String category;
    Query applesQuery;
    TextView categoryname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_view_products_filter, container, false);
        recyclerView=v1.findViewById(R.id.recyclerview);
        categoryname=v1.findViewById(R.id.category);

        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        progressDialog.show();

        category = getArguments().getString("category");
        categoryname.setText(category);
        databaseReference= FirebaseDatabase.getInstance().getReference("products");
        applesQuery = databaseReference.orderByChild("category").equalTo(category);

        fragmentManager=getParentFragmentManager();


        applesQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String image = dataSnapshot.child("image").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);
                    String category = dataSnapshot.child("category").getValue(String.class);


                    ProductsModel model= new ProductsModel(image,name,id,price,category);


                    list.add(model);
                }
                if(list.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Products Are Empty",Toast.LENGTH_LONG).show();
                }else {
                    adapter = new ProductsAdapter(getActivity(), list, progressDialog, databaseReference);
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