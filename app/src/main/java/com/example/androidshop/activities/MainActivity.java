package com.example.androidshop.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.androidshop.R;
import com.example.androidshop.adapters.CategoryAdapter;
import com.example.androidshop.adapters.ProductAdapter;
import com.example.androidshop.models.Category;
import com.example.androidshop.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.menu_icon);

        prepareImageSlider();
        prepareCategories();
        getProducts();
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
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();

        } else if (id == R.id.menu_my_cart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        }

        return true;
    }

    private void getProducts() {
        RecyclerView productRecycler = findViewById(R.id.productsRecycler);
        productRecycler.setLayoutManager(new GridLayoutManager(this, 2));
        List<Product> products = new ArrayList<>();
        ProductAdapter productAdapter = new ProductAdapter(this, products);
        productRecycler.setAdapter(productAdapter);

        firestore.collection("products")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Product product = document.toObject(Product.class);
                            products.add(product);
                            productAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void prepareCategories() {
        RecyclerView categoryRecycler = findViewById(R.id.categoryRecycler);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        List<Category> categories = new ArrayList<>();
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories);
        categoryRecycler.setAdapter(categoryAdapter);

        firestore.collection("categories")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Category category = document.toObject(Category.class);
                            categories.add(category);
                            categoryAdapter.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(this, "" + task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void prepareImageSlider() {
        ImageSlider imageSlider = findViewById(R.id.image_slider);
        List<SlideModel> slideModelList = new ArrayList<>();
        slideModelList.add(new SlideModel(R.drawable.banner1, getString(R.string.shoesDiscount), ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner2, getString(R.string.womenDiscount), ScaleTypes.CENTER_CROP));
        slideModelList.add(new SlideModel(R.drawable.banner3, getString(R.string.christmasDiscount), ScaleTypes.CENTER_CROP));
        imageSlider.setImageList(slideModelList);
    }
}