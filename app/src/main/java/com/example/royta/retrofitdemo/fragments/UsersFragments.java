package com.example.royta.retrofitdemo.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.royta.retrofitdemo.APIs.RetrofitClient;
import com.example.royta.retrofitdemo.ModelClass.User;
import com.example.royta.retrofitdemo.ModelClass.UsersResponse;
import com.example.royta.retrofitdemo.R;
import com.example.royta.retrofitdemo.adapters.UsersAdapter;

import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;

public class UsersFragments extends Fragment {

    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private List<User> userList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.users_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        retrofit2.Call<UsersResponse> call = RetrofitClient.getInstance().getApi().getUsers();

        call.enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(retrofit2.Call<UsersResponse> call, Response<UsersResponse> response) {
                userList = response.body().getUsers();
                adapter = new UsersAdapter(getActivity(), userList);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(retrofit2.Call<UsersResponse> call, Throwable t) {

            }
        });

    }
}
