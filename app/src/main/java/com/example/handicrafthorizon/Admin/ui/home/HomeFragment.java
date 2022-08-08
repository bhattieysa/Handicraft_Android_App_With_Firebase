package com.example.handicrafthorizon.Admin.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


import com.example.handicrafthorizon.R;
import com.example.handicrafthorizon.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        container.removeAllViews();

        View v1 = inflater.inflate(R.layout.fragment_home, container, false);



        return v1;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}