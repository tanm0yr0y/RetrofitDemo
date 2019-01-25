package com.example.royta.retrofitdemo.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.royta.retrofitdemo.APIs.RetrofitClient;
import com.example.royta.retrofitdemo.Activities.LoginActivity;
import com.example.royta.retrofitdemo.Activities.MainActivity;
import com.example.royta.retrofitdemo.ModelClass.DefaultResponse;
import com.example.royta.retrofitdemo.ModelClass.LoginResponse;
import com.example.royta.retrofitdemo.ModelClass.User;
import com.example.royta.retrofitdemo.R;
import com.example.royta.retrofitdemo.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingFragments extends Fragment implements View.OnClickListener {

    EditText editTextEmail, editTextName, editTextSchool;
    EditText editTextCurrentPassword, editTextNewPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settings_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextEmail = view.findViewById(R.id.textViewEmail);
        editTextName = view.findViewById(R.id.textViewName);
        editTextSchool = view.findViewById(R.id.textViewSchool);

        editTextCurrentPassword = view.findViewById(R.id.textViewCurrentPassword);
        editTextNewPassword = view.findViewById(R.id.textViewNewPassword);

        view.findViewById(R.id.save_button).setOnClickListener(this);
        view.findViewById(R.id.change_pass_button).setOnClickListener(this);
        view.findViewById(R.id.logout_button).setOnClickListener(this);
        view.findViewById(R.id.delete_button).setOnClickListener(this);

    }

    private void updateProfile() {

        String email = editTextEmail.getText().toString();
        String name = editTextName.getText().toString();
        String school = editTextSchool.getText().toString();

        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if(name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }
        if(school.isEmpty()) {
            editTextSchool.setError("School is required");
            editTextSchool.requestFocus();
            return;
        }




        User user = SharedPrefManager.getSharedPrefManager(getActivity()).getUser();
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().updateUser(
                user.getId(), email, name, school
        );


        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                Log.d("ROYs", "onResponse called");
                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();

                if(!response.body().isError()) {
                    SharedPrefManager.getSharedPrefManager(getActivity()).saveUsers(response.body().getUser());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("ROYs", "onFailure called");
            }
        });
    }

    private void updatePassword() {

        String currentPassword = editTextCurrentPassword.getText().toString();
        String newPassword = editTextNewPassword.getText().toString();

        if(currentPassword.isEmpty()) {
            editTextCurrentPassword.setError("Password Required");
            editTextCurrentPassword.requestFocus();
            return;
        }
        if(newPassword.isEmpty()) {
            editTextNewPassword.setError("Enter new password");
            editTextNewPassword.requestFocus();
            return;
        }


        User user = SharedPrefManager.getSharedPrefManager(getActivity()).getUser();
        Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().updatePassword(
                currentPassword, newPassword, user.getEmail()
        );

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {

            }
        });
    }

    private void logout() {
        SharedPrefManager.getSharedPrefManager(getActivity()).clear();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void deleteUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Are You Sure?");
        builder.setMessage("This action is irreversible");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                User user = SharedPrefManager.getSharedPrefManager(getActivity()).getUser();

                Call<DefaultResponse> call = RetrofitClient.getInstance().getApi().deleteUser(user.getId());

                call.enqueue(new Callback<DefaultResponse>() {
                    @Override
                    public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {



                        if(!response.body().isErr()) {

                            SharedPrefManager.getSharedPrefManager(getActivity()).clear();
                            Intent intent = new Intent(getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            startActivity(intent);
                        }

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<DefaultResponse> call, Throwable t) {

                    }
                });
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.save_button) {
            updateProfile();
        }
        else if(v.getId() == R.id.change_pass_button) {
            updatePassword();
        }
        else if(v.getId() == R.id.logout_button) {
            logout();
        }
        else if(v.getId() == R.id.delete_button) {
            deleteUser();
        }
    }
}
