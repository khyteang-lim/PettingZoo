package com.example.limsanity.firstapp.Activities;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.limsanity.firstapp.R;

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
        ConstraintLayout errorUsernameConstraint = findViewById(R.id.enterUsernameError);
        ConstraintLayout errorPasswordConstraint = findViewById(R.id.enterPasswordError);
        errorUsernameConstraint.setVisibility(View.INVISIBLE);
        errorPasswordConstraint.setVisibility(View.INVISIBLE);
        EditText username = findViewById(R.id.usernameTV);
        EditText password = findViewById(R.id.passwordTV);
        if ((username.getText() != null && username.getText().length() > 0)
                && (password.getText() != null && password.getText().length() > 0)){
            Intent intent = new Intent(this, MainActivity.class);
            String message = username.getText().toString();
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(intent);
        }
        else {
            if (!(username.getText() != null && username.getText().length() > 0))
            {
                errorUsernameConstraint.setVisibility(View.VISIBLE);
            }
            if (!(password.getText() != null && password.getText().length() > 0))
            {
                errorPasswordConstraint.setVisibility(View.VISIBLE);
            }
        }

    }
}
