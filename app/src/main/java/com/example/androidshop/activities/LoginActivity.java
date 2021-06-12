package com.example.androidshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidshop.MainActivity;
import com.example.androidshop.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void signIn(View view) {
        EditText emailField = findViewById(R.id.email);
        EditText passwordField = findViewById(R.id.password);
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

        if (!fieldValidator(email, "email")) {
            return;
        }
        if (!fieldValidator(password, "password")) {
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "Error: " + task.getException().toString() + task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void redirectToSignUp(View view) {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();
    }

    private boolean fieldValidator(String field, String name) {
        if (field.length() < 6) {
            Toast.makeText(this, "Enter " + name + " with minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}