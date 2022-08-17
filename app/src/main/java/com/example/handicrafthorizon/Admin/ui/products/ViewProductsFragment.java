package com.example.handicrafthorizon.Admin.ui.products;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
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
import java.util.Arrays;
import java.util.List;


public class ViewProductsFragment extends Fragment {

    Spinner mySpinner;
    String[] Group_List = {"Select Category"};
    private TextView label;
    List<String> list;
    DatabaseReference databaseReference;
    String[] categories_list;
    ProgressDialog progressDialog;
    Button btn;
    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v1= inflater.inflate(R.layout.fragment_view_products, container, false);


        mySpinner =  v1.findViewById(R.id.categories);
        btn =  v1.findViewById(R.id.btn);
        progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("loading");
        progressDialog.show();
        databaseReference= FirebaseDatabase.getInstance().getReference("categories");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list = new ArrayList<String>(Arrays.asList(Group_List));
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){

                    String name = dataSnapshot.child("name").getValue(String.class);






                    list.add(name);
                }

                Group_List= list.toArray(new String[]{});

                mySpinner.setAdapter(new ViewProductsFragment.MyCustomAdapter(getActivity(), R.layout.categories_spinner, Group_List));
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String Categories = label.getText().toString().trim();
                if (Categories.equals("Select Category")) {


                    Toast.makeText(getActivity(), "Select Category", Toast.LENGTH_LONG).show();

                    return;
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putString("category", Categories);
                    fragment = new ViewProductsFilterFragment();
                    fragment.setArguments(bundle);
                    fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();








                }

            }
        });



        return v1;
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