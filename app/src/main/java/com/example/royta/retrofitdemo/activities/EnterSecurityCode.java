package com.example.royta.retrofitdemo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.royta.retrofitdemo.R;

public class EnterSecurityCode extends AppCompatActivity {

    private EditText editTextCode;
    private TextView emailTextView;
    private Intent i;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_security_code);
        editTextCode = findViewById(R.id.code);
        emailTextView = findViewById(R.id.view_email);
        i = getIntent();
        emailTextView.setText(i.getExtras().getString("email"));
    }

    public void updatePasswordIfCodeMatch(View v) {
        String userGivenCode = editTextCode.getText().toString();
        String sentCode = i.getExtras().getString("code");
        if(sentCode.equals(userGivenCode)) {
            Toast.makeText(this, "Update Password", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this, "The number that you've entered doesn't match your code. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    public void goToLoginPage(View v) {
        startActivity(new Intent(this, LoginActivity.class));
    }

}
