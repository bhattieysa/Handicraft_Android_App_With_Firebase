package com.example.handicrafthorizon.User.ui.cart;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.User.ui.home.UserProductAdapter;
import com.example.handicrafthorizon.User.ui.home.UserProductsModel;
import com.example.handicrafthorizon.User.ui.home.UserViewProductsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;


public class UserCartFragment extends Fragment {



    RecyclerView recyclerView;
    ArrayList<CartModel> list;
    DatabaseReference databaseReference;
    Query applesQuery;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    CartAdapter adapter;
    private FragmentManager fragmentManager;
    ProgressDialog progressDialog;
    String category;
    String jsonStr;
    Button placeorder;
    TextView total;
    int TotalPrice;
    int Quantity=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View v1= inflater.inflate(R.layout.fragment_user_cart, container, false);
        recyclerView=v1.findViewById(R.id.recyclerview);
        placeorder=v1.findViewById(R.id.place_order);
        total=v1.findViewById(R.id.total);
        recyclerView=v1.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...!");
        progressDialog.show();
        database=FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference("cart").child(user.getUid());
        fragmentManager=getParentFragmentManager();



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<>();
                TotalPrice=0;
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String name = dataSnapshot.child("name").getValue(String.class);
                    String image = dataSnapshot.child("image").getValue(String.class);
                    String id = dataSnapshot.child("id").getValue(String.class);
                    String price = dataSnapshot.child("price").getValue(String.class);
                    String quantity = dataSnapshot.child("quantity").getValue(String.class);
                    String user_id = dataSnapshot.child("user_id").getValue(String.class);
                    String product_id = dataSnapshot.child("product_id").getValue(String.class);


                    Integer intQuantity= 0;
                    if (quantity != null) {
                        intQuantity = Integer.parseInt(quantity);
                    }
                    Integer total1= 0;
                    if (price != null) {
                        total1 = Integer.parseInt(price)*intQuantity;
                    }

                    Log.d("eysa", String.valueOf(total1));


                        TotalPrice = TotalPrice + total1;
                        total.setText(String.valueOf(TotalPrice));




                    CartModel model= new CartModel(image,name,id,user_id,product_id,price,String.valueOf(quantity));
                    list.add(model);
                }


                if(list.isEmpty()){
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Cart is Empty",Toast.LENGTH_LONG).show();
                }else {
                    adapter = new CartAdapter(getActivity(), list, progressDialog, databaseReference,database);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });

        placeorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                Bundle bundle = new Bundle();
                bundle.putString("total", String.valueOf(TotalPrice));








                OrderPlaceFragment orderPlaceFragment = new OrderPlaceFragment();
                orderPlaceFragment.setArguments(bundle);
//                fragmentTransaction.addToBackStack("returnFragment");
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard1, orderPlaceFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();


            }
        });


        return v1;
    }

   
}