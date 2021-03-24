package com.gachugusville.servicedforbusiness.Dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.gachugusville.development.servicedforbusiness.R;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class JobsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jobs, container, false);
        NavigationView navigationView = Objects.requireNonNull(getActivity()).findViewById(R.id.navigation_view);
        navigationView.setCheckedItem(R.id.work_nav);



        return view;
    }
}