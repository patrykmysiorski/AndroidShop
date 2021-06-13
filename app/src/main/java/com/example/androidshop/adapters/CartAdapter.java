package com.example.androidshop.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.androidshop.R;
import com.example.androidshop.models.CartItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    List<CartItem> cartItems;

    public CartAdapter(Context context, List<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);

        Glide.with(context).load(cartItem.getUrl()).into(holder.productImage);
        holder.price.setText(cartItem.getProductPrice());
        holder.name.setText(cartItem.getProductName());
        holder.totalPrice.setText(String.valueOf(cartItem.getTotalPrice()));
        holder.totalQuantity.setText(cartItem.getTotalQuantity());
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView price, name, totalQuantity, totalPrice;
        ImageView productImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.productName);
            price = itemView.findViewById(R.id.productPrice);
            totalQuantity = itemView.findViewById(R.id.totalQuantity);
            totalPrice = itemView.findViewById(R.id.totalPriceNumber);
            productImage = itemView.findViewById(R.id.productImage);
        }
    }
}
