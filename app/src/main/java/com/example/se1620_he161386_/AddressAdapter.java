package com.example.se1620_he161386_;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder>{

    private List<Address> addressList;

    public AddressAdapter(List<Address> addresses) {
        addressList = addresses;
    }


    public class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tv_id, tv_street, tv_city, tv_zipcode;
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_id= itemView.findViewById(R.id.tv_id);
            tv_street= itemView.findViewById(R.id.tv_street);
            tv_city= itemView.findViewById(R.id.tv_city);
            tv_zipcode= itemView.findViewById(R.id.tv_zipcode);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                // Show a Toast message with the position
                Toast.makeText(itemView.getContext(), "Clicked position: " + position, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_items, parent, false);
        return new AddressViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addressList.get(position);
        holder.tv_id.setText("Id: " + address.getId());
        holder.tv_street.setText("Street: " + address.getStreet());
        holder.tv_city.setText("City: " + address.getCity());
        holder.tv_zipcode.setText("Zipcode: " + address.getZipcode());
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

}
