package com.example.handicrafthorizon.User.ui.cart;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.handicrafthorizon.R;


public class UserCartFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View v1= inflater.inflate(R.layout.fragment_user_cart, container, false);


        return v1;
    }
}