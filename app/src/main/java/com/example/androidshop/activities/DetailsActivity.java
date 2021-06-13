package com.example.androidshop.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.androidshop.R;
import com.example.androidshop.models.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class DetailsActivity extends AppCompatActivity {
    TextView name, description, price, quantity;
    Button addToCart;
    ImageView addItems, removeItems;
    int totalQuantity = 1;
    Double totalPrice = 0.0;

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Object object = getIntent().getSerializableExtra("product");
        Product product = (Product) object;

        Toolbar toolbar = findViewById(R.id.details_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(((Product) object).getName());
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });



        ImageView image = findViewById(R.id.detailed_img);
        quantity = findViewById(R.id.quantity);
        name = findViewById(R.id.detailed_name);
        description = findViewById(R.id.detailed_desc);
        price = findViewById(R.id.detailed_price);
        addToCart = findViewById(R.id.add_to_cart);
        addItems = findViewById(R.id.add_item);
        removeItems = findViewById(R.id.remove_item);

        if (product != null) {
            Glide.with(getApplicationContext()).load(product.getImageUrl()).into(image);
            name.setText(product.getName());
            description.setText(product.getDescription());
            price.setText(String.valueOf(product.getPrice()) + "$");
            totalPrice = Double.parseDouble(product.getPrice()) * totalQuantity;
        }

        addToCart.setOnClickListener(v -> {
            addToCart();
        });

        addItems.setOnClickListener(v -> {
            if (totalQuantity < 10) {
                totalQuantity++;
                quantity.setText(String.valueOf(totalQuantity));
            }
        });

        removeItems.setOnClickListener(v -> {
            if (totalQuantity > 1) {
                totalQuantity--;
                quantity.setText(String.valueOf(totalQuantity));
                if (product != null) {
                    totalPrice = Double.parseDouble(product.getPrice()) * totalQuantity;
                }
            }
        });
    }

    private void addToCart() {
        HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("productName", name.getText().toString());
        cartMap.put("productPrice", price.getText().toString());
        cartMap.put("totalPrice", String.valueOf(totalPrice));
        cartMap.put("totalQuantity", quantity.getText().toString());

        firebaseFirestore.collection("cart").document(auth.getCurrentUser().getUid())
                .collection("user").add(cartMap).addOnCompleteListener(task -> {
            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show();
        });
    }
}