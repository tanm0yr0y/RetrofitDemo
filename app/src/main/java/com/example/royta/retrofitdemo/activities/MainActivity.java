package com.example.royta.retrofitdemo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.royta.retrofitdemo.ModelClass.DefaultResponse;
import com.example.royta.retrofitdemo.R;
import com.example.royta.retrofitdemo.APIs.RetrofitClient;
import com.example.royta.retrofitdemo.Storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText userEmail, userPassword, userName, userSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getSharedPrefManager(this).isLoggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void goToLoginPage(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void signUpAction(View v) {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString();
        String name = userName.getText().toString().trim();
        String school = userSchool.getText().toString().trim();

        if(email.isEmpty()) {
            userEmail.setError("Email is required");
            userEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError("Enter a Valid Email");
            userEmail.requestFocus();
            return;
        }

        if(password.isEmpty()) {
            userPassword.setError("Password is required");
            userPassword.requestFocus();
            return;
        }

        if(password.length() < 6) {
            userPassword.setError("Password should be 6 character long");
            userPassword.requestFocus();
            return;
        }

        if(name.isEmpty()) {
            userName.setError("Name is required");
            userName.requestFocus();
            return;
        }

        if(school.isEmpty()) {
            userSchool.setError("School is required");
            userSchool.requestFocus();
            return;
        }

        //added GSON library from http://square.github.io/retrofit/ (implementation 'com.squareup.retrofit2:retrofit:2.5.0')
        //added GSON Converter from https://github.com/square/retrofit/tree/master/retrofit-converters/gson (implementation 'com.squareup.retrofit2:converter-gson:2.5.0')
        //check app level build.gradle

        //Doing Registration using the API call

        /*
        Call<ResponseBody> call = RetrofitClient.getInstance().getApi().createUser(email, password, name, school);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //Log.d("ROYs", "onResponse : "+response.toString());
                String s = null;
                try {
                    if(response.code() == 201) {
                        s = response.body().string();
                    }
                    else {
                        s = response.errorBody().string();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(s != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("ROYs", "onFailure: "+t.toString());
            }
        });  */

        Call <DefaultResponse> call = RetrofitClient.getInstance().getApi().createUser(email, password, name, school);
        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if(response.code() == 201) {
                    DefaultResponse defaultResponse = response.body();
                    Toast.makeText(MainActivity.this, defaultResponse.getMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                else {
                    Toast.makeText(MainActivity.this, "User Already Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void init() {
        userEmail = findViewById(R.id.email);
        userPassword = findViewById(R.id.password);
        userName = findViewById(R.id.name);
        userSchool = findViewById(R.id.school);
    }
}
