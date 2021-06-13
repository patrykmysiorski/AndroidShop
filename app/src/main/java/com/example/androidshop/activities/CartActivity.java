package com.example.androidshop.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidshop.R;
import com.example.androidshop.adapters.CartAdapter;
import com.example.androidshop.models.CartItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        Toolbar toolbar = findViewById(R.id.cart_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {
            finish();
        });


        RecyclerView cartRecycler = findViewById(R.id.cartRecycler);
        cartRecycler.setLayoutManager(new GridLayoutManager(this, 1));
        List<CartItem> cartItemList = new ArrayList<>();
        CartAdapter cartAdapter = new CartAdapter(this, cartItemList);
        cartRecycler.setAdapter(cartAdapter);

        Button buyNowButton = findViewById(R.id.buyNow);

        firebaseFirestore.collection("cart").document(firebaseAuth.getCurrentUser().getUid())
                .collection("user").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int cartAmount = 0;
                for (DocumentSnapshot doc : task.getResult()) {
                    CartItem cartItem = doc.toObject(CartItem.class);
                    cartAmount += Integer.parseInt(cartItem.getTotalPrice());
                    cartItemList.add(cartItem);
                    cartAdapter.notifyDataSetChanged();
                }
                buyNowButton.setText("Buy now (" + cartAmount + "$)!");
            } else {
                Toast.makeText(CartActivity.this, "Error: " + task.getException().toString() + task.getException().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}