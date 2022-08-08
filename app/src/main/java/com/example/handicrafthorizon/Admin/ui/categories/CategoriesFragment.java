package com.example.handicrafthorizon.Admin.ui.categories;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.handicrafthorizon.Admin.ui.home.HomeFragment;
import com.example.handicrafthorizon.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class CategoriesFragment extends Fragment {

    EditText category;
    ImageView image;
    Button submit;
ActivityResultLauncher<String> launcher;
FirebaseDatabase database;
FirebaseStorage storage;
String name;
ProgressDialog progressDialog;
Uri imageData;
TextView view;
    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();


        View v1 = inflater.inflate(R.layout.fragment_categories, container, false);

            category=v1.findViewById(R.id.category);
            image=v1.findViewById(R.id.image);
            submit=v1.findViewById(R.id.submit);
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("loading");
            view=v1.findViewById(R.id.textView2);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragment = new ViewCategoriesFragment();
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
                    name=category.getText().toString();

                    if (name.isEmpty()) {
                        category.setError("Category Name Cant't Be Blank");
                        category.requestFocus();
                        return;
                    }



                    progressDialog.show();

                    String mGroupId1 = database.getReference().push().getKey();


                    final StorageReference reference= storage.getReference().child("categoriesimage").child(mGroupId1);
                    reference.putFile(imageData).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String mGroupId = database.getReference().push().getKey();
                                    database.getReference().child("categories").child(mGroupId).child("image").setValue(uri.toString());
                                    database.getReference().child("categories").child(mGroupId).child("name").setValue(name);
                                    database.getReference().child("categories").child(mGroupId).child("id").setValue(mGroupId);
                                    Toast.makeText(getActivity(),"Cattegory Added Successful",Toast.LENGTH_LONG).show();
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
}