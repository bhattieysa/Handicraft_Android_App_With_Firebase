package com.example.handicrafthorizon.User.ui.home;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.handicrafthorizon.Admin.ui.categories.CategoryModel;
import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.User.ui.profile.UserProfileFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class UserCategoryAdapter extends RecyclerView.Adapter<UserCategoryAdapter.MyViewHolder>{

    private Context mCtx;
    private static final int REQUEST_CALL = 1;
    private ArrayList<UserCategoryModel> categoryList;

    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    StorageReference photoRef;
    DatabaseReference ref;
    Query applesQuery;
    DatabaseReference databaseReference;

    public UserCategoryAdapter(Activity activity , ArrayList <UserCategoryModel> categoryList, ProgressDialog progressDialog, DatabaseReference databaseReference,FragmentManager fragmentManager) {
        this.mCtx = activity;
        this.categoryList = categoryList;
        this.progressDialog=progressDialog;
        this.databaseReference=databaseReference;
        this.fragmentManager=fragmentManager;


    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( mCtx );
        View view = inflater.inflate ( R.layout.view_categories_list , null );
        return new MyViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        progressDialog.dismiss();


        final UserCategoryModel category = categoryList.get(position);
        holder.name.setText(category.getName());
        holder.id=category.getId();
        holder.Image=category.getImage();
        holder.Name=category.getName();





        Glide.with(mCtx)
                .load(category.getImage())
                .override(100, Target.SIZE_ORIGINAL)
                .fitCenter()
                .into(holder.image);





        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("category", holder.Name);



                UserViewProductsFragment userProfileFragment = new UserViewProductsFragment();
                userProfileFragment.setArguments(bundle);
                fragmentTransaction.addToBackStack("returnFragment");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard1, userProfileFragment);
               // fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size() ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;
        String id;
        String Image,Name;




        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);



        }
    }


}