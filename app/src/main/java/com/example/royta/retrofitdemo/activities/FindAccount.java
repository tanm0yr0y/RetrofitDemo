package com.example.royta.retrofitdemo.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.royta.retrofitdemo.APIs.RetrofitClient;
import com.example.royta.retrofitdemo.ModelClass.UserExist;
import com.example.royta.retrofitdemo.R;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindAccount extends AppCompatActivity {

    private EditText editTextEmail;
    private Button search, cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_account);

        editTextEmail = findViewById(R.id.textViewEmail);

    }

    public void searchEmail(View v) {
        final String email = editTextEmail.getText().toString().trim();
        if(email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email address!!");
            editTextEmail.requestFocus();
            return;
        }

        //ProgressBar
        final ProgressBar progressBar = findViewById(R.id.progressbarView);
        progressBar.setVisibility(View.VISIBLE);

        Call <UserExist> call = RetrofitClient.getInstance().getApi().isUserExist(email);


        call.enqueue(new Callback<UserExist>() {
            @Override
            public void onResponse(Call<UserExist> call, Response<UserExist> response) {

                progressBar.setVisibility(View.GONE);

                if(response.body().isEmailExist()) {
                    //String randeomnum = String.valueOf(new Random().nextInt(900000) + 100000);
                    String resetCode = String.valueOf(response.body().getCode());
                    Log.d("ROYs", "Reset Code: "+resetCode);
                    Intent i = new Intent(FindAccount.this, EnterSecurityCode.class);
                    i.putExtra("email", email);
                    i.putExtra("code", resetCode);
                    startActivity(i);
                }
                else {

                    Toast.makeText(FindAccount.this, "You are not registered", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserExist> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                String stacktrace = Log.getStackTraceString(t);
                Log.d("ROYs", "onFailure Method: "+stacktrace);
            }
        });
    }
    public void cancelSearch(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
