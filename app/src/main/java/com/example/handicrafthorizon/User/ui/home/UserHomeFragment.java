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
import android.widget.Toast;

import com.example.handicrafthorizon.Admin.ui.categories.CategoryAdapter;
import com.example.handicrafthorizon.Admin.ui.categories.CategoryModel;
import com.example.handicrafthorizon.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class UserHomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<UserCategoryModel> list;
    DatabaseReference databaseReference;
    UserCategoryAdapter adapter;
    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        container.removeAllViews();
        // Inflate the layout for this fragment
        View  v1= inflater.inflate(R.layout.fragment_user_home, container, false);

        recyclerView=v1.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        progressDialog.show();

        databaseReference= FirebaseDatabase.getInstance().getReference("categories");

        fragmentManager = getActivity().getSupportFragmentManager();

        list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String image = dataSnapshot.child("image").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);


                    UserCategoryModel model= new UserCategoryModel(image,name,id);


                    list.add(model);
                }
                if(list.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Categories Are Empty",Toast.LENGTH_LONG).show();
                }else {
                    adapter = new UserCategoryAdapter(getActivity(), list, progressDialog, databaseReference,fragmentManager);
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