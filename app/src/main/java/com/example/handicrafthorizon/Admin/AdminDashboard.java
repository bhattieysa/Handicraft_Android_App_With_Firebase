package com.example.handicrafthorizon.Admin;

import static androidx.navigation.Navigation.findNavController;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.handicrafthorizon.Admin.ui.categories.CategoriesFragment;
import com.example.handicrafthorizon.Admin.ui.home.HomeFragment;
import com.example.handicrafthorizon.Admin.ui.products.ProductsFragment;

import com.example.handicrafthorizon.Admin.ui.profile.ProfileFragment;
import com.example.handicrafthorizon.R;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;



public class AdminDashboard extends AppCompatActivity {

    FragmentManager fragmentManager;
    Fragment fragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



        BottomNavigationView bottomNavigationView =  findViewById(R.id.nav_view);

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.navigation_home:

                         fragment = new HomeFragment();
                         fragmentManager = AdminDashboard.this.getSupportFragmentManager();
                         fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();



                        break;
                    case R.id.navigation_categories:

                        fragment = new CategoriesFragment();
                        fragmentManager = AdminDashboard.this.getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();




                        break;
                    case R.id.navigation_products:
                        fragment = new ProductsFragment();
                        fragmentManager = AdminDashboard.this.getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        fragmentManager = AdminDashboard.this.getSupportFragmentManager();
                        fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment_activity_admin_dashboard, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        break;


            }

                return true;
        }




        });
    }


}