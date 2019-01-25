package com.example.royta.retrofitdemo.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.royta.retrofitdemo.ModelClass.User;
import com.example.royta.retrofitdemo.R;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {

    private Context mctx;
    private List<User> userList;

    public UsersAdapter(Context mctx, List<User> userList) {
        this.mctx = mctx;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mctx).inflate(R.layout.recyclerview_users, viewGroup, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder usersViewHolder, int i) {
        User user = userList.get(i);

        usersViewHolder.textViewName.setText(user.getName());
        usersViewHolder.textViewEmail.setText(user.getEmail());
        usersViewHolder.textViewSchool.setText(user.getSchool());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewEmail, textViewSchool;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);


            textViewName = itemView.findViewById(R.id.textViewName);
            textViewEmail = itemView.findViewById(R.id.textViewEmail);
            textViewSchool = itemView.findViewById(R.id.textViewSchool);
        }
    }

}
