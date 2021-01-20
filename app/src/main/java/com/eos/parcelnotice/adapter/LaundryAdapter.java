package com.eos.parcelnotice.adapter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.core.app.NotificationCompat;

import com.eos.parcelnotice.LaundryConfirmActivity;
import com.eos.parcelnotice.R;
import com.eos.parcelnotice.data.LaundryData;
import com.eos.parcelnotice.retrofit.LaundryApi;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.eos.parcelnotice.LaundryConfirmActivity.getToken;
import static com.eos.parcelnotice.LaundryConfirmActivity.showTimePicker;
import static com.eos.parcelnotice.MainActivity.getMyInfo;

public class LaundryAdapter extends BaseAdapter {
    Context context;
    ArrayList<LaundryData> items;
    LaundryApi laundryApi;
    NotificationManager manager;
    Notification notification;

    public LaundryAdapter(Context context, ArrayList<LaundryData> items, LaundryApi laundryApi){
        this.context = context;
        this.items = items;
        this.laundryApi = laundryApi;
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
            laundryView = new LaundryView(context);
        }
        else{
            laundryView = (LaundryView) convertView;
        }

        final LaundryData item = items.get(position);
        if(item.getEndTime()!=null && item.getEndTime().getTime()- (new Date()).getTime()<=0){
            item.setStatus("비었음");
            changeLaundryStatus("비었음",null,item.getId());
        }

        laundryView.setLaundryMachine("세탁기 "+(position+1));

        changeViewContent(laundryView,item);

        Button btnLaundry = (Button) laundryView.findViewById(R.id.laundry_button);
        btnLaundry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getStatus().equals("비었음")){
                    item.setStatus("사용중");
                    item.setOccupant(getMyInfo().getId());
                    TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(final TimePicker view, int hourOfDay, int minute) {
                            final int time = (hourOfDay * 60 + minute)*60;

                            Calendar cal = Calendar.getInstance();
                            cal.setTime(new Date());
                            cal.add(Calendar.SECOND, time);
                            item.setEndTime(cal.getTime());
                            Log.d("endTime", "endTime: "+item.getEndTime());
                            changeLaundryStatus("사용중", item.getEndTime(), item.getId());
                            changeViewContent(laundryView,item);
                        }
                    };
                    showTimePicker(listener);
                }
                else{
                    changeLaundryStatus("비었음",null,item.getId());
                    item.setStatus("비었음");
                    changeViewContent(laundryView,item);
                }

            }
        });

        return laundryView;
    }

    public void changeViewContent(final LaundryView view, final LaundryData item){
        view.setLaundryUse(item.getStatus());
        if(item.getStatus().equals("사용중")){
            view.setLaundryButton("세탁 완료");
            view.setLaundryUser(item.getOccupant());
            Date currentTime = new Date();
            long time = item.getEndTime().getTime()- currentTime.getTime();
            view.setLaundryTime(getTime(time));
            if(time>0) {
                initNotice();
                CountDownTimer countDownTimer = new CountDownTimer(time,1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        if(item.getEndTime()== null) cancel();
                        else {
                            view.setLaundryTime(getTime(millisUntilFinished));
                        }
                    }

                    @Override
                    public void onFinish() {
                        if(item.getStatus().equals("사용중")) {
                            manager.notify(1, notification);
                        }
                        changeLaundryStatus("비었음", null, item.getId());
                        item.setStatus("비었음");
                        changeViewContent(view, item);

                    }
                };
                countDownTimer.start();
            }

        }
        else{
            view.setLaundryButton("사용");
            view.setLaundryUser(-1);
            view.setLaundryTime("");
        }
    }


    public String getTime(long time){
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

    public void changeLaundryStatus(String status, Date endTime, int washerId){

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("washerId",washerId);
        jsonObject.addProperty("status",status);
        if(endTime!=null) {
            jsonObject.addProperty("endtime", format.format(endTime));
        }
        Call<LaundryData> call = laundryApi.change_laundry_status(getToken(),jsonObject);
        call.enqueue(new Callback<LaundryData>() {
            @Override
            public void onResponse(Call<LaundryData> call, Response<LaundryData> response) { }
            @Override
            public void onFailure(Call<LaundryData> call, Throwable t) { }
        });
    }

    public void initNotice(){
        NotificationCompat.Builder builder = null;
        manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        String channelId = "id";
        String channelName = "name";

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            manager.createNotificationChannel(new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT));
            builder = new NotificationCompat.Builder(context,channelId);
        }
        else{
            builder = new NotificationCompat.Builder(context);
        }
        Intent intent = new Intent(context, LaundryConfirmActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        builder.setContentTitle("세탁 완료!")
                .setContentText("세탁이 완료되었습니다. 세탁물을 찾아가세요")
                .setSmallIcon(R.drawable.app_main_image)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        notification = builder.build();

    }
}
