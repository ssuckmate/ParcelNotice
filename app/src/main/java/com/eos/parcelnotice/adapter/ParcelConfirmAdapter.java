package com.eos.parcelnotice.adapter;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnotice.ParcelConfirmActivity;
import com.eos.parcelnotice.R;
import com.eos.parcelnotice.data.DummyData;
import com.eos.parcelnotice.data.ParcelData;
import com.eos.parcelnotice.databinding.ItemParcelConfirmRecyclerBinding;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;

import static com.eos.parcelnotice.MainActivity.getMyInfo;
import static com.eos.parcelnotice.ParcelConfirmActivity.changeParcelStatus;
import static com.eos.parcelnotice.ParcelConfirmActivity.deleteParcel;

public class ParcelConfirmAdapter extends RecyclerView.Adapter<ParcelConfirmAdapter.ParcelConfirmViewHolder> {
    List<ParcelData> parcels;

    //layout_join manager가 호출하는 함수.
    public ParcelConfirmAdapter(List<ParcelData> parcels){
        this.parcels = parcels;
    }


    @NonNull
    @Override
    public ParcelConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parcel_confirm_recycler, parent, false);
        return new ParcelConfirmViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ParcelConfirmViewHolder holder, int position) {
        final ParcelData parcel =parcels.get(position);

        holder.tvArrival.setText(parcel.getCreatedAt());
        holder.tvRecipient.setText(parcel.getRecipient());
        holder.tvStatus.setText(parcel.getStatus());

        if(parcel.getStatus().equals("보관중") || parcel.getStatus().equals("분실")){
            holder.tvStatus.setText(parcel.getStatus());
            holder.btnDelete.setVisibility(View.INVISIBLE);
            holder.btnCancel.setVisibility(View.INVISIBLE);
            holder.btnReceive.setVisibility(View.VISIBLE);
            holder.btnReceive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parcel.setTaker(getMyInfo().getName());
                    parcel.setStatus("찾아감");
                    JsonObject json=new JsonObject();
                    json.addProperty("id",parcel.getId());
                    json.addProperty("status","찾아감");
                    changeParcelStatus(json);
                }
            });
        }
        else if(parcel.getStatus().equals("찾아감")){
            holder.tvStatus.setText(parcel.getTaker()+"이(가) "+parcel.getStatus());
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnCancel.setVisibility(View.VISIBLE);
            holder.btnReceive.setVisibility(View.INVISIBLE);
            holder.btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parcel.setTaker(null);
                    parcel.setStatus("보관중");
                    JsonObject json=new JsonObject();
                    json.addProperty("id",parcel.getId());
                    json.addProperty("status","보관중");
                    changeParcelStatus(json);
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject json=new JsonObject();
                    json.addProperty("id",parcel.getId());
                    deleteParcel(json);
                    parcels.remove(parcel);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return parcels.size();
    }

    protected static class ParcelConfirmViewHolder extends RecyclerView.ViewHolder{
        private TextView tvArrival, tvRecipient, tvStatus;
        private Button btnReceive, btnCancel,btnDelete;


        public ParcelConfirmViewHolder(@NonNull View itemView) {
            super(itemView);

            tvArrival = itemView.findViewById(R.id.text_dummy_delivery_arrival_time);
            tvRecipient = itemView.findViewById(R.id.text_dummy_recipient);
            tvStatus = itemView.findViewById(R.id.text_dummy_status);
            btnReceive = itemView.findViewById(R.id.button_parcel_receive);
            btnCancel = itemView.findViewById(R.id.button_parcel_cancel);
            btnDelete = itemView.findViewById(R.id.button_parcel_delete);
        }
    }
}
