package com.eos.parcelnotice.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eos.parcelnotice.LaundryConfirmActivity;
import com.eos.parcelnotice.R;
import com.eos.parcelnotice.data.LaundryData;
import com.eos.parcelnotice.data.ResponseData;
import com.eos.parcelnotice.retrofit.LaundryApi;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.eos.parcelnotice.LaundryConfirmActivity.getToken;
import static com.eos.parcelnotice.LaundryConfirmActivity.setLaundryAdapter;

public class LaundryFloorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Integer> floors;
    private static Context context;
    private LaundryApi laundryApi;
    ArrayList<LaundryAdapter> laundryAdapters;
    ArrayList<ViewHolder> viewHolders;
    private int currentFloor;

    public LaundryFloorAdapter(Context context,ArrayList<Integer> floors){
        this.context = context;
        this.floors = floors;
        laundryAdapters = new ArrayList<>();
        viewHolders = new ArrayList<>();
        getMachines(0);
        currentFloor=1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_laundry_floor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int floor = floors.get(position);
        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolders.add(viewHolder);

        viewHolder.tv_laundry_floor.setText(floor+"");
        if(floor==currentFloor) viewHolder.tv_laundry_floor.setBackgroundColor(Color.parseColor("#14A534"));
        else viewHolder.tv_laundry_floor.setBackgroundColor(Color.parseColor("#A8E4B5"));
        viewHolder.tv_laundry_floor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolders.get(currentFloor-1).tv_laundry_floor.setBackgroundColor(Color.parseColor("#A8E4B5"));
                viewHolder.tv_laundry_floor.setBackgroundColor(Color.parseColor("#14A534"));
                currentFloor=floor;
                setLaundryAdapter(laundryAdapters.get(floor-1));
            }
        });
    }

    private void getMachines(final int position) {
        if(position == floors.size()) return;
        final int floor = floors.get(position);
        laundryApi = new Retrofit.Builder()
                .baseUrl(LaundryConfirmActivity.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LaundryApi.class);

        Call<ArrayList<LaundryData>> callLaundryList = laundryApi.get_laundry_list(getToken(),floor);

        Callback<ArrayList<LaundryData>> callback = new Callback<ArrayList<LaundryData>>() {
            @Override
            public void onResponse(Call<ArrayList<LaundryData>> call, Response<ArrayList<LaundryData>> response) {
                LaundryAdapter laundryAdapter = new LaundryAdapter(context,response.body(),laundryApi);
                laundryAdapters.add(laundryAdapter);
                if(floor==1) setLaundryAdapter(laundryAdapter);
                getMachines(position+1);
            }

            @Override
            public void onFailure(Call<ArrayList<LaundryData>> call, Throwable t) {
                Toast.makeText(context,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        callLaundryList.enqueue(callback);
    }

    @Override
    public int getItemCount() {
        return floors.size();
    }

    public void addItem(int i){
        floors.add(i);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_laundry_floor;
        GridView gv_laundry_machine;

        ViewHolder(View itemView){
            super(itemView);
            tv_laundry_floor = itemView.findViewById(R.id.textView_laundry_floor);
            gv_laundry_machine = itemView.findViewById(R.id.laundry_confirm_gridView);
        }
    }


}
