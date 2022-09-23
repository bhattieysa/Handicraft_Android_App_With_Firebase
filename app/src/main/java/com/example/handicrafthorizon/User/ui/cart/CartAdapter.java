package com.example.handicrafthorizon.User.ui.cart;





import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.handicrafthorizon.Admin.ui.products.ProductsModel;
import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.User.ui.home.UserProductsModel;
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


public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    private Context mCtx;
    private static final int REQUEST_CALL = 1;
    private ArrayList<CartModel> categoryList;

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

    public CartAdapter(Activity activity , ArrayList <CartModel> categoryList,ProgressDialog progressDialog,    DatabaseReference databaseReference, FirebaseDatabase database) {
        this.mCtx = activity;
        this.categoryList = categoryList;
        this.progressDialog=progressDialog;
        this.databaseReference=databaseReference;
        this.database=database;

    }


    @NonNull
    @Override
    public CartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from ( mCtx );
        View view = inflater.inflate ( R.layout.cart_list , null );
        return new CartAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.MyViewHolder holder, int position) {

        progressDialog.dismiss();


        final CartModel category = categoryList.get(position);



        holder.name.setText(category.getName());
        holder.price.setText(category.getPrice());
        holder.Name=category.getName();
        holder.Price=category.getPrice();
        holder.id=category.getId();
        holder.Image=category.getImage();
        holder.quantity.setText(category.getQuantity());
        holder.Quantity=category.getQuantity();


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();



                Query applesQuery = databaseReference.orderByChild("id").equalTo(holder.id);

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }

                        progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });




            }
        });


holder.plus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        quantity=Integer.parseInt(holder.Quantity)+1;
        databaseReference.child(holder.id).child("quantity").setValue(String.valueOf(quantity));

    }
});
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(Integer.parseInt(holder.Quantity)>1) {
                    quantity = Integer.parseInt(holder.Quantity) - 1;
                    databaseReference.child(holder.id).child("quantity").setValue(String.valueOf(quantity));
                }
            }
        });




        Glide.with(mCtx)
                .load(category.getImage())
                .override(100, Target.SIZE_ORIGINAL)
                .fitCenter()
                .into(holder.image);







    }

    @Override
    public int getItemCount() {
        return categoryList.size() ;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name,price,quantity;
        ImageView image;
        String id,Name,Price;
        String Image;
        Button plus;
        Button minus,delete;
        String Quantity;




        public MyViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            delete = itemView.findViewById(R.id.delete);

            image = itemView.findViewById(R.id.image);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
            quantity = itemView.findViewById(R.id.quantity);



        }
    }


}