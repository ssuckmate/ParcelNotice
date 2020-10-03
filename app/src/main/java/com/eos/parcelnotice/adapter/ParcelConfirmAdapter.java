package com.eos.parcelnotice.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnotice.R;
import com.eos.parcelnotice.data.DummyData;

public class ParcelConfirmAdapter extends RecyclerView.Adapter<ParcelConfirmAdapter.ParcelConfirmViewHolder> {

    //layout manager가 호출하는 함수.
    @NonNull
    @Override
    public ParcelConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parcel_confirm_recycler, parent, false);
        return new ParcelConfirmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParcelConfirmViewHolder holder, int position) {
//        ParcelData parcel =DummyData.parcels.get(position);
//        Date date = parcel.getArrival();
//        holder.tvArrival.setText(date.getYear()+"년 "+ date.getMonth()+"월"+date.getDay()+"일 도착 예정");
//        holder.tvRecipient.setText(parcel.getRecipient());
//        holder.tvStatus.setText(parcel.getStatus());
    }

    @Override
    public int getItemCount() {
        int size = DummyData.parcels.size();
        return size;
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
