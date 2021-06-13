package com.example.androidshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidshop.R;
import com.example.androidshop.models.Address;
import com.example.androidshop.models.CartItem;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {

    Context context;
    List<Address> addressList;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.single_address, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Address address = addressList.get(position);

        holder.printedAddress.setText(address.toString());
        holder.radioButton.setOnClickListener(v -> {
            for(Address addressIterator: addressList) {
                address.setSelected(false);
            }
            address.setSelected(true);

            if(holder.radioButton != null) {
                holder.radioButton.setChecked(false);
            }
            holder.radioButton = (RadioButton) v;
            holder.radioButton.setChecked(true);
//            holder.printedAddress.s(item.getUserAddress());
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView printedAddress;
        RadioButton radioButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.address_radio);
            printedAddress = itemView.findViewById(R.id.printedAddress);
        }
    }
}
