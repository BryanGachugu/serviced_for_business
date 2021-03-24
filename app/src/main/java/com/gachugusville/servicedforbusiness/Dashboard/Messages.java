package com.gachugusville.servicedforbusiness.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gachugusville.development.servicedforbusiness.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Messages extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        NavigationView navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.msg_nav);



        return view;
    }
}