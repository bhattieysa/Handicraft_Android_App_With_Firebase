package com.example.handicrafthorizon.Admin.ui.products;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.handicrafthorizon.Admin.AdminDashboard;
import com.example.handicrafthorizon.Admin.ui.categories.CategoryAdapter;
import com.example.handicrafthorizon.Admin.ui.categories.CategoryModel;
import com.example.handicrafthorizon.Admin.ui.home.HomeFragment;
import com.example.handicrafthorizon.R;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ProductsFragment extends Fragment {

    EditText name,price;
    ImageView image;
    Button submit;
    ActivityResultLauncher<String> launcher;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String Name,Price;
    ProgressDialog progressDialog;
    Uri imageData;
    TextView view;
    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    Spinner mySpinner;
    String[] Group_List = {"Select Category"};
    private TextView label;
    List<String> list = new ArrayList<String>(Arrays.asList(Group_List));
    DatabaseReference databaseReference;
    String[] categories_list;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();


        View v1 = inflater.inflate(R.layout.fragment_products, container, false);

        name=v1.findViewById(R.id.name);
        price=v1.findViewById(R.id.price);
        image=v1.findViewById(R.id.image);
        submit=v1.findViewById(R.id.submit);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("loading");
        progressDialog.show();
        view=v1.findViewById(R.id.textView2);
        mySpinner =  v1.findViewById(R.id.categories);


        databaseReference= FirebaseDatabase.getInstance().getReference("categories");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String name = dataSnapshot.child("name").getValue(String.class);






                    list.add(name);
                }

                Group_List= list.toArray(new String[]{});

                mySpinner.setAdapter(new ProductsFragment.MyCustomAdapter(getActivity(), R.layout.categories_spinner, Group_List));
progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {



                    String name = dataSnapshot.child("name").getValue(String.class);






                    list.add(name);
                }

                Group_List= list.toArray(new String[]{});

                mySpinner.setAdapter(new ProductsFragment.MyCustomAdapter(getActivity(), R.layout.categories_spinner, Group_List));
                progressDialog.dismiss();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("error", "onCancelled", databaseError.toException());
                progressDialog.dismiss();
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new ViewProductsFragment();
                fragmentManager = getActivity().getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();


        launcher=registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {

                image.setImageURI(result);
                imageData=result;
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                launcher.launch("image/*");

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Name=name.getText().toString();
                Price=price.getText().toString();

                final String Categories = label.getText().toString().trim();
                if (Categories.equals("Select Category")) {


                    Toast.makeText(getActivity(), "Select Category", Toast.LENGTH_LONG).show();

                    return;
                }


                if (Name.isEmpty()) {
                    name.setError("Product Name Cant't Be Blank");
                    name.requestFocus();
                    return;
                }



                if (Price.isEmpty()) {
                    price.setError("Price Cant't Be Blank");
                    price.requestFocus();
                    return;
                }



                progressDialog.show();

                String mGroupId1 = database.getReference().push().getKey();


                final StorageReference reference= storage.getReference().child("productsimage").child(mGroupId1);
                reference.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String mGroupId = database.getReference().push().getKey();
                                database.getReference().child("products").child(mGroupId).child("image").setValue(uri.toString());
                                database.getReference().child("products").child(mGroupId).child("name").setValue(Name);
                                database.getReference().child("products").child(mGroupId).child("id").setValue(mGroupId);
                                database.getReference().child("products").child(mGroupId).child("price").setValue(Price);
                                database.getReference().child("products").child(mGroupId).child("category").setValue(Categories);
                                Toast.makeText(getActivity(),"Product Added Successful",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            }
                        });

                    }
                });



            }
        });


        return v1;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
    //Spinner data......
    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.categories_spinner, parent, false);
            label = (TextView) row.findViewById(R.id.categories_layout);
            label.setText(Group_List[position]);


            if (position == 0) {
                label.setTextColor(Color.parseColor("#CA9D58"));
            }


            return row;
        }
    }
}