package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eos.parcelnotice.adapter.LaundryFloorAdapter;
import com.eos.parcelnotice.adapter.LaundryView;
import com.eos.parcelnotice.data.LaundryData;
import com.eos.parcelnotice.databinding.ActivityLaundryConfirmBinding;
import com.eos.parcelnotice.retrofit.LaundryApi;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LaundryConfirmActivity extends AppCompatActivity {
    LaundryFloorAdapter floorAdapter;
    static int currentFloor, totalFloor;
    static ArrayList<LaundryAdapter> laundryAdapters;
    private LaundryApi laundryApi;
    static ActivityLaundryConfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_laundry_confirm);
        binding.setActivity(this);

        laundryAdapters = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        binding.laundryConfirmRecyclerView.setLayoutManager(layoutManager);
        floorAdapter = new LaundryFloorAdapter(this);

        //init();

        totalFloor=5;
        int [] laundryNum = {2,3,1,2,4};
        int [] dryNum = {3,1,2,3,1};
        LaundryAdapter laundryAdapter;
        for(int i=0; i<totalFloor; i++ ){
            floorAdapter.addItem(i);
            //서버에서 세탁기 건조기 개수 가져오기
            laundryAdapter = new LaundryAdapter();
            laundryAdapter.setLaundryNum(laundryNum[i]);
            laundryAdapter.setDryNum(dryNum[i]);
           for(int j=0; j<laundryAdapter.getLaundryNum();j++){
               //상태 서버에서 가져오기
                laundryAdapter.addItem(new LaundryData((i+1)+"층 세탁기"+(j+1),null,"비었음"));
            }
            for(int j=0;j<laundryAdapter.getDryNum();j++){
                laundryAdapter.addItem(new LaundryData((i+1)+ "층 건조기"+(j+1),null,"비었음"));
            }
            laundryAdapters.add(laundryAdapter);
        }
        setCurrentFloor(0);
        binding.laundryConfirmRecyclerView.setAdapter(floorAdapter);
        binding.laundryConfirmGridView.setAdapter(laundryAdapters.get(currentFloor));

    }

    public static void setCurrentFloor(int currentFloor) {
        LaundryConfirmActivity.currentFloor = currentFloor;
        binding.laundryConfirmGridView.setAdapter(laundryAdapters.get(currentFloor));
    }


    public class LaundryAdapter extends BaseAdapter {
        int laundryNum;
        int dryNum;
        ArrayList<LaundryData> items;
        public LaundryAdapter(ArrayList<LaundryData> items){
            this.items = items;
        }

        public LaundryAdapter() {
            items = new ArrayList<>();

        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent) {
            final LaundryView laundryView;

            if(convertView == null){
                laundryView = new LaundryView(getApplicationContext());
            }
            else{
                laundryView = (LaundryView) convertView;
            }

            final LaundryData item = items.get(position);

            changeViewContent(laundryView,item);

            Button btnLaundry = (Button) laundryView.findViewById(R.id.laundry_button);
            btnLaundry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(item.getStatus().equals("비었음")){
                        item.setStatus("사용중");
                        item.setUserName("서시언");
                        startTimer(laundryView,item);
                    }
                    else{
                        item.setStatus("비었음");
                        item.setUserName(null);
                    }
                    changeViewContent(laundryView,item);

                }
            });

            return laundryView;
        }

        public void addItem(LaundryData item){
            items.add(item);
        }

        public int getLaundryNum() {
            return laundryNum;
        }

        public void setLaundryNum(int laundryNum) {
            this.laundryNum = laundryNum;
        }

        public int getDryNum() {
            return dryNum;
        }

        public void setDryNum(int dryNum) {
            this.dryNum = dryNum;
        }

        public void changeViewContent(final LaundryView view, final LaundryData item){
            view.setLaundryMachine(item.getMachine());
            //changeLaundryStatusInServer(item.getStatus());
            view.setLaundryUse(item.getStatus());
            if(item.getStatus().equals("사용중")){
                view.setLaundryButton("세탁 완료");
                view.setLaundryUser("사용인: "+item.getUserName());
                view.setLaundryTime(getStringTime(item.getTime()));
                if(item.getTime()>0) {
                    if(view.getTimer()==null) {
                        System.out.println("널이다~~");
                        CountDownTimer countDownTimer = new CountDownTimer(item.getTime(), 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                item.setTime((int) millisUntilFinished);
                                view.setLaundryTime(getStringTime(item.getTime()));
                            }

                            @Override
                            public void onFinish() {
                                System.out.println("time: " + item.getTime());
                                item.setTime(0);
                                item.setStatus("비었음");
                               // showNotice();
                              //  System.out.println("공지띄움 vewcontent에서");
                                view.setTimer(null);
                                changeViewContent(view, item);
                            }
                        };
                        view.setTimer(countDownTimer);
                        countDownTimer.start();
                    }
                }
            }
            else{
                view.setLaundryButton("사용");
                view.setLaundryUser("");
                view.setLaundryTime("");
            }
        }

        public void showNotice(){
            NotificationCompat.Builder builder = null;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String channelId = "id";
            String channelName = "name";

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                manager.createNotificationChannel(new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT));
                builder = new NotificationCompat.Builder(getApplicationContext(),channelId);
            }
            else{
                builder = new NotificationCompat.Builder(getApplicationContext());
            }
            Intent intent = new Intent(getApplicationContext(),LaundryConfirmActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle("세탁 완료!")
                    .setContentText("세탁이 완료되었습니다. 세탁물을 찾아가세요")
                    .setSmallIcon(R.drawable.app_main_image)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            Notification notification = builder.build();

            manager.notify(1,notification);

        }

        public void startTimer(LaundryView view, LaundryData data) {
            final LaundryView tmpView = view;
            final LaundryData tmpData = data;
            TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(final TimePicker view, int hourOfDay, int minute) {
                    final int time = (hourOfDay * 60 + minute) * 60000;

                    tmpData.setTime(time);
                    CountDownTimer countDownTimer = new CountDownTimer(tmpData.getTime(),1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            tmpData.setTime((int)millisUntilFinished);
                            tmpView.setLaundryTime(getStringTime(tmpData.getTime()));
                        }

                        @Override
                        public void onFinish() {
                            System.out.println("time: "+tmpData.getTime());
                            tmpData.setTime(0);
                            tmpData.setStatus("비었음");
                            showNotice();
                            System.out.println("공지띄움 타이머에서");
                            tmpView.setTimer(null);
                            changeViewContent(tmpView, tmpData);
                        }
                    };
                    tmpView.setTimer(countDownTimer);
                    countDownTimer.start();

                }
            };
            TimePickerDialog tpd = new TimePickerDialog(LaundryConfirmActivity.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listener,0,0, true);
            tpd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            tpd.setTitle("세탁시간을 입력하세요");
            tpd.show();
        }


    }
    public static String getStringTime(int time){
        long hour=0, minute=0, second=0;
        if(time<0) return "";

        hour = TimeUnit.MILLISECONDS.toHours(time);
        time-=hour*1000*60*60;
        minute = TimeUnit.MILLISECONDS.toMinutes(time);
        time-=minute*1000*60;
        second = TimeUnit.MILLISECONDS.toSeconds(time);

        if(hour >0){
            return hour +":" + minute + ":" + second;
        }
        if(minute>0){
            return minute + ":" + second;
        }
        return ""+second;
    }

    private void init(){
        totalFloor = 5;
        laundryApi = new Retrofit.Builder()
                .baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LaundryApi.class);
        for(int i=0; i<totalFloor; i++){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("token",ParcelConfirmActivity.getToken());
            jsonObject.addProperty("floor",i);
            Call<ArrayList<LaundryData>> callLaundryList = laundryApi.get_laundry_list(jsonObject);
            Callback<ArrayList<LaundryData>> callback = new Callback<ArrayList<LaundryData>>() {
                @Override
                public void onResponse(Call<ArrayList<LaundryData>> call, Response<ArrayList<LaundryData>> response) {
                    laundryAdapters.add(new LaundryAdapter(response.body()));
                }

                @Override
                public void onFailure(Call<ArrayList<LaundryData>> call, Throwable t) {
                    Toast.makeText(LaundryConfirmActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();

                }
            };
            callLaundryList.enqueue(callback);
        }
    }
    private void changeLaundryStatusInServer(String status){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", ParcelConfirmActivity.getToken());
        jsonObject.addProperty("status",status);
        laundryApi.change_laundry_status(jsonObject);
    }

}