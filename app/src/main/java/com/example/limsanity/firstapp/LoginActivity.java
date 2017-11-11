package com.example.limsanity.firstapp;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.submitBtn) void submitLogin(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        EditText username = (EditText) findViewById(R.id.usernameTV);
        EditText password = (EditText) findViewById(R.id.passwordTV);
        String message = username.getText().toString();
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(intent);
    }
}
