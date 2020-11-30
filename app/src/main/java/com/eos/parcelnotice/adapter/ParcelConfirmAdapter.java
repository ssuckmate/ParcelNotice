package com.eos.parcelnotice.adapter;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnotice.ParcelConfirmActivity;
import com.eos.parcelnotice.R;
import com.eos.parcelnotice.data.DummyData;
import com.eos.parcelnotice.data.ParcelData;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;

public class ParcelConfirmAdapter extends RecyclerView.Adapter<ParcelConfirmAdapter.ParcelConfirmViewHolder> {
    List<ParcelData> parcels;

    //layout_join manager가 호출하는 함수.
    public ParcelConfirmAdapter(List<ParcelData> parcels){
        this.parcels = parcels;
    }
    public ParcelConfirmAdapter(){
        parcels= new ArrayList<>();
        parcels.add(new ParcelData("장보경","11/28(토) 도착","??", 1,"서시언","보관중",null));
        parcels.add(new ParcelData("주영환","11/29(일) 도착","??", 2,"장보경","찾아감","장보경"));
        parcels.add(new ParcelData("서시언","11/28(토) 도착","??", 3,"김현수","보관중",null));
        parcels.add(new ParcelData("장보경","11/28(토) 도착","??", 4,"주영환","보관중",null));
        parcels.add(new ParcelData("고세진","11/28(토) 도착","??", 5,"김현수","찾아감","고세진"));
        parcels.add(new ParcelData("김현수","11/28(토) 도착","??", 6,"고세진","보관중",null));
        parcels.add(new ParcelData("서시언","11/28(토) 도착","??", 7,"장보경","보관중",null));
        parcels.add(new ParcelData("고세진","11/28(토) 도착","??", 8,"서시언","보관중",null));
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
        holder.tvRecipient.setText(parcel.getRecipient());

        if(parcel.getStatus().equals("보관중") || parcel.getStatus().equals("분실")){
            holder.tvStatus.setText(parcel.getStatus());
            holder.btnDelete.setVisibility(View.INVISIBLE);
            holder.btnCancel.setVisibility(View.INVISIBLE);
            holder.btnReceive.setVisibility(View.VISIBLE);
            holder.btnReceive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // parcel.setTaker(ParcelConfirmActivity.getUserName());
                    parcel.setTaker("서시언");
                    parcel.setStatus("찾아감");
                    JsonObject json=new JsonObject();
                    //json.addProperty("token", ParcelConfirmActivity.getToken());
                    json.addProperty("id",parcel.getId());
                    json.addProperty("status","찾아감");
                    //ParcelConfirmActivity.changeParcelStatus(json);
                    ParcelConfirmActivity.changedView();
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
                    //json.addProperty("token", ParcelConfirmActivity.getToken());
                    json.addProperty("id",parcel.getId());
                    json.addProperty("status","보관중");
                    //ParcelConfirmActivity.changeParcelStatus(json);
                    ParcelConfirmActivity.changedView();
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    JsonObject json=new JsonObject();
                   // json.addProperty("token",ParcelConfirmActivity.getToken());
                    json.addProperty("id",parcel.getId());
                   // ParcelConfirmActivity.deleteParcel(json);
                    parcels.remove(parcel);
                    ParcelConfirmActivity.changedView();

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
