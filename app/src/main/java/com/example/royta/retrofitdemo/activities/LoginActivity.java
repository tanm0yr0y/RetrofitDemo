package com.example.royta.retrofitdemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.royta.retrofitdemo.APIs.RetrofitClient;
import com.example.royta.retrofitdemo.ModelClass.LoginResponse;
import com.example.royta.retrofitdemo.R;
import com.example.royta.retrofitdemo.Storage.SharedPrefManager;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText userEmail, userPass;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getSharedPrefManager(this).isLoggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void init() {
        userEmail = findViewById(R.id.email);
        userPass = findViewById(R.id.password);
    }

    public void goToRegisterPage(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void findAccount(View v) {
        startActivity(new Intent(this, FindAccount.class));
    }

    public void loginAction(View view) {
        String email = userEmail.getText().toString().trim();
        String password = userPass.getText().toString().trim();

        if(email.isEmpty()) {
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Enter a valid email");
            userEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            userPass.setError("Password is required");
            userPass.requestFocus();
            return;
        }
        if(password.length() < 6) {
            userPass.setError("Password should be 6 char long");
            userPass.requestFocus();
            return;
        }
        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().userLogin(email, password);
        final ProgressBar progressBar = findViewById(R.id.progressBarView);
        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                progressBar.setVisibility(View.GONE);
                if(response.code() == 200) {

                    LoginResponse loginResponse = response.body();
                    if(loginResponse.isError()) {
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //save user and open profile

                        SharedPrefManager.getSharedPrefManager(LoginActivity.this).saveUsers(loginResponse.getUser());
                        Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(LoginActivity.this, "You are not registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("ROYs", "onFailure called");
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
