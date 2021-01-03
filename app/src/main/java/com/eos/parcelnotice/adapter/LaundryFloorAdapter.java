package com.eos.parcelnotice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnotice.LaundryConfirmActivity;
import com.eos.parcelnotice.R;
import com.eos.parcelnotice.data.LaundryData;

import java.util.ArrayList;

public class LaundryFloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Integer> items;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_laundry_floor;

        ViewHolder(View itemView){
            super(itemView);
            tv_laundry_floor = itemView.findViewById(R.id.textView_laundry_floor);
        }
    }

    public LaundryFloorAdapter(Context context){
        this.context = context;
        items = new ArrayList<Integer>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laundry_floor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int item = items.get(position);
        ViewHolder viewHolder = (ViewHolder)holder;

        viewHolder.tv_laundry_floor.setText(""+(item+1));
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LaundryConfirmActivity.setCurrentFloor(item);
            }
        };
        viewHolder.tv_laundry_floor.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(int i){
        items.add(i);

    }
}
