package com.example.androidshop.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidshop.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FormAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_address);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });


        EditText name, address, city, postalCode, phoneNumber;
        Button addAddress;

        name = findViewById(R.id.name);
        address = findViewById(R.id.address);
        city = findViewById(R.id.city);
        postalCode = findViewById(R.id.postal);
        phoneNumber = findViewById(R.id.phoneNumber);
        addAddress = findViewById(R.id.addAddressButton);

        addAddress.setOnClickListener(v -> {
            String userName = name.getText().toString();
            String userCity = city.getText().toString();
            String userAddress = address.getText().toString();
            String userCode = postalCode.getText().toString();
            String userNumber = phoneNumber.getText().toString();

            StringBuilder stringBuilder = new StringBuilder();

            if(userName.isEmpty() || userAddress.isEmpty() || userCity.isEmpty() || userCode.isEmpty() || userNumber.isEmpty()) {
                Toast.makeText(this, "Please fill all fields!", Toast.LENGTH_SHORT).show();
            } else {
                Map<String, String> addressMap = new HashMap<>();
                addressMap.put("name", userName);
                addressMap.put("city", userCity);
                addressMap.put("address", userAddress);
                addressMap.put("postalCode", userCode);
                addressMap.put("phoneNumber", userNumber);

                firebaseFirestore.collection("user").document(firebaseAuth.getCurrentUser().getUid())
                        .collection("address").add(addressMap).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Address added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, AddressActivity.class));
                        finish();
                    } else {
                        Toast.makeText(this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_logout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, RegisterActivity.class));
            finish();

        } else if (id == R.id.menu_my_cart) {
            startActivity(new Intent(this, CartActivity.class));
        }

        return true;
    }
}