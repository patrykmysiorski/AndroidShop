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

public class RegisterActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }
    }

    public void signUp(View view) {
        EditText nameField = findViewById(R.id.productName);
        EditText emailField = findViewById(R.id.email);
        EditText passwordField = findViewById(R.id.password);
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        String password = passwordField.getText().toString();

        if (!fieldValidator(name, "name")) {
            return;
        }
        if (!fieldValidator(email, "email")) {
            return;
        }
        if (!fieldValidator(password, "password")) {
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, task -> {
            if (task.isSuccessful()) {
                Toast.makeText(RegisterActivity.this, "Successfully register", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration failed" + task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void redirectToSignIn(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
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