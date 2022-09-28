package com.example.handicrafthorizon.Admin.ui.home;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.handicrafthorizon.Admin.ui.products.ProductsModel;
import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.User.ui.home.UserProductsModel;
import com.example.handicrafthorizon.User.ui.home.UserViewProductsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder>{

    private Context mCtx;
    private static final int REQUEST_CALL = 1;
    private ArrayList<OrdersModel> categoryList;
    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    StorageReference photoRef;
    DatabaseReference ref;
    Query applesQuery;
    DatabaseReference databaseReference;

    FirebaseDatabase database;
    private FirebaseAuth mAuth;
    Integer quantity;

    public OrdersAdapter(Activity activity , ArrayList <OrdersModel> categoryList,ProgressDialog progressDialog, DatabaseReference databaseReference, FirebaseDatabase database,FragmentManager fragmentManager) {
        this.mCtx = activity;
        this.categoryList = categoryList;
        this.progressDialog=progressDialog;
        this.databaseReference=databaseReference;
        this.database=database;
        this.fragmentManager=fragmentManager;

    }


    @NonNull
    @Override
    public OrdersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( mCtx );
        View view = inflater.inflate ( R.layout.orders_list , null );
        return new OrdersAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.MyViewHolder holder, int position) {

        progressDialog.dismiss();


        final OrdersModel category = categoryList.get(position);

        holder.date.setText(category.getOrder_date());
        holder.price.setText(category.getTota_price());
        holder.status.setText(category.getStatus());
        holder.name.setText(category.getC_name());
        holder.address.setText(category.getC_address());
        holder.city.setText(category.getC_city());
        holder.number.setText(category.getC_number());

        if(category.getStatus().equals("Complete")) {
            holder.status.setTextColor(Color.parseColor("#42ba96"));
            holder.complete.setVisibility(View.GONE);

        }

        holder.orderid=category.getOrder_id();
//        holder.card.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Bundle bundle = new Bundle();
//                bundle.putString("order_id", holder.orderid);
//                OrdersProductFragment userProfileFragment = new OrdersProductFragment();
//                userProfileFragment.setArguments(bundle);
//                fragmentTransaction.addToBackStack("returnFragment");
//                fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, userProfileFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//
//            }
//        });

        holder.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference.child(holder.orderid).child("status").setValue("Complete");


            }
        });










    }

    @Override
    public int getItemCount() {
        return categoryList.size() ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView date,price,status,name,number,city,address;
        String orderid;
        CardView card;
        Button complete;




        public MyViewHolder(View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
            name=itemView.findViewById(R.id.name);
            number=itemView.findViewById(R.id.number);
            city=itemView.findViewById(R.id.city);
            address=itemView.findViewById(R.id.address);
            price = itemView.findViewById(R.id.price);
            complete = itemView.findViewById(R.id.complete);

            date = itemView.findViewById(R.id.order_date);
            status = itemView.findViewById(R.id.status);



        }
    }


}