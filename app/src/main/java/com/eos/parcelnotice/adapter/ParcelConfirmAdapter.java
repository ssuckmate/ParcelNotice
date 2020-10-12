package com.eos.parcelnotice.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnotice.R;
import com.eos.parcelnotice.data.DummyData;
import com.eos.parcelnotice.data.ParcelData;

import java.util.List;

public class ParcelConfirmAdapter extends RecyclerView.Adapter<ParcelConfirmAdapter.ParcelConfirmViewHolder> {
    List<ParcelData> parcels;
    //layout manager가 호출하는 함수.
    public ParcelConfirmAdapter(List<ParcelData> parcels){
        this.parcels = parcels;
    }
    public ParcelConfirmAdapter(){

    }

    @NonNull
    @Override
    public ParcelConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parcel_confirm_recycler, parent, false);
        return new ParcelConfirmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParcelConfirmViewHolder holder, int position) {
        ParcelData parcel =parcels.get(position);

        holder.tvArrival.setText(parcel.getCreatedAt());
        holder.tvRecipient.setText(parcel.getRecipeient());
        holder.tvStatus.setText(parcel.getStatus());
    }

    @Override
    public int getItemCount() {

        return parcels.size();
    }

    protected static class ParcelConfirmViewHolder extends RecyclerView.ViewHolder{
        private TextView tvArrival, tvRecipient, tvStatus;
        public ParcelConfirmViewHolder(@NonNull View itemView) {
            super(itemView);
            tvArrival = itemView.findViewById(R.id.text_dummy_delivery_arrival_time);
            tvRecipient = itemView.findViewById(R.id.text_dummy_recipient);
            tvStatus = itemView.findViewById(R.id.text_dummy_status);
        }
    }
}
