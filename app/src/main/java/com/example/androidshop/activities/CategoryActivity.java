package com.example.androidshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidshop.R;
import com.example.androidshop.adapters.CategoryOverviewAdapter;
import com.example.androidshop.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        String type = getIntent().getStringExtra("type");

        Toolbar toolbar = findViewById(R.id.activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(type);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });


        RecyclerView categoryRecycler = findViewById(R.id.categoryOverview);
        categoryRecycler.setLayoutManager(new GridLayoutManager(this, 1));
        List<Product> products = new ArrayList<>();
        CategoryOverviewAdapter categoryOverviewAdapter = new CategoryOverviewAdapter(this, products);
        categoryRecycler.setAdapter(categoryOverviewAdapter);


        if (type != null && !type.isEmpty()) {
            firestore.collection("products").whereEqualTo("type", type).get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Product product = document.toObject(Product.class);
                        products.add(product);
                        products.add(product);
                        categoryOverviewAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(this, "Error: " + task.getException().toString() + task.getException().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
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