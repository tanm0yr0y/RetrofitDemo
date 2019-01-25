package com.example.royta.retrofitdemo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.royta.retrofitdemo.R;
import com.example.royta.retrofitdemo.Storage.SharedPrefManager;

public class HomeFragments extends Fragment {

    private TextView textviewEmail, textviewName, textviewSchool;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        textviewEmail = view.findViewById(R.id.email);
        textviewName = view.findViewById(R.id.name);
        textviewSchool = view.findViewById(R.id.school);

        textviewEmail.setText(SharedPrefManager.getSharedPrefManager(getActivity()).getUser().getEmail());
        textviewName.setText(SharedPrefManager.getSharedPrefManager(getActivity()).getUser().getName());
        textviewSchool.setText(SharedPrefManager.getSharedPrefManager(getActivity()).getUser().getSchool());
    }
}
