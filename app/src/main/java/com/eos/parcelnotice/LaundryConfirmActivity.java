package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TimePicker;
import com.eos.parcelnotice.adapter.LaundryFloorAdapter;
import com.eos.parcelnotice.adapter.LaundryView;
import com.eos.parcelnotice.data.LaundryData;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class LaundryConfirmActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    LaundryFloorAdapter floorAdapter;
    static GridView gridView;
    static int currentFloor;
    static ArrayList<LaundryAdapter> laundryAdapters;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //층 수 서버에서 가져오기
        int floorNum=5, laundryNum, dryNum;
        super.onCreate(savedInstanceState);

        laundryAdapters = new ArrayList<>();
        setContentView(R.layout.activity_laundry_confirm);
        gridView= (GridView) findViewById(R.id.laundry_confirm_gridView);
        recyclerView = findViewById(R.id.laundry_confirm_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        floorAdapter = new LaundryFloorAdapter(this);
        LaundryAdapter laundryAdapter;
        for(int i=0; i<floorNum; i++ ){
            floorAdapter.addItem(i);
            //서버에서 세탁기 건조기 개수 가져오기
            laundryNum=3;
            dryNum=4;
            laundryAdapter = new LaundryAdapter(this);
            laundryAdapter.setLaundryNum(laundryNum);
            laundryAdapter.setDryNum(dryNum);
           for(int j=0; j<laundryAdapter.getLaundryNum();j++){
               //상태 서버에서 가져오기
                laundryAdapter.addItem(new LaundryData((i+1)+"층 세탁기"+(j+1),false,null,null));
            }
            for(int j=0;j<laundryAdapter.getDryNum();j++){
                laundryAdapter.addItem(new LaundryData((i+1)+ "층 건조기"+(j+1),false,null,null));
            }
            laundryAdapters.add(laundryAdapter);
        }
        recyclerView.setAdapter(floorAdapter);
        setCurrentFloor(0);
    }

    public static void setCurrentFloor(int currentFloor) {
        LaundryConfirmActivity.currentFloor = currentFloor;
        gridView.setAdapter(laundryAdapters.get(currentFloor));
    }


    public class LaundryAdapter extends BaseAdapter {
        Context context;
        int laundryNum;
        int dryNum;
        public LaundryAdapter(Context context){
            this.context = context;
        }
        ArrayList<LaundryData> items = new ArrayList<>();

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

            changeViewContent(laundryView,item);

            final Timer[] timer = new Timer[2];

            Button btnLaundry = (Button) laundryView.findViewById(R.id.laundry_button);
            btnLaundry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!item.getIsUsed()){
                        item.setUserName("서시언");
                        timer[0] = new Timer();
                        timer[1] = new Timer();
                        startTimer(laundryView,item, timer[0],timer[1]);
                    }
                    else{
                        timer[0].cancel();
                        timer[1].cancel();
                        item.setUserName(null);
                    }
                    item.changeIsUsed();
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

        public void changeViewContent(LaundryView view, LaundryData item){
            view.setLaundryMachine(item.getMachine());
            if(item.getIsUsed()){
                view.setLaundryUse("사용중");
                view.setLaundryButton("세탁 완료");
                view.setLaundryUser("사용인: "+item.getUserName());
                view.setLaundryTime(item.getTime());
            }
            else{
                view.setLaundryUse("비어있음");
                view.setLaundryButton("사용");
                view.setLaundryUser("");
                view.setLaundryTime("");
            }
        }

        public void showNotice(){
            NotificationCompat.Builder builder = null;
            NotificationManager manager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            String channelId = "id";
            String channelName = "name";

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                manager.createNotificationChannel(new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT));
                builder = new NotificationCompat.Builder(context,channelId);
            }
            else{
                builder = new NotificationCompat.Builder(context);
            }
            Intent intent = new Intent(context,LaundryConfirmActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,101,intent,PendingIntent.FLAG_UPDATE_CURRENT);

            builder.setContentTitle("세탁 완료!")
                    .setContentText("세탁이 완료되었습니다. 세탁물을 찾아가세요")
                    .setSmallIcon(R.drawable.app_main_image)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            Notification notification = builder.build();

            manager.notify(1,notification);

        }

        public void startTimer(LaundryView view, LaundryData data, final Timer timer, final Timer timer2) {
            final LaundryView tmpView = view;
            final LaundryData tmpData = data;
            TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(final TimePicker view, int hourOfDay, int minute) {
                    final int[] time = {(hourOfDay * 60 + minute) * 60000};
                    System.out.println("time : " + time[0]);

                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                            tmpData.changeIsUsed();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    changeViewContent(tmpView,tmpData);
                                }
                            });
                            showNotice();
                        }
                    };
                    TimerTask showTime = new TimerTask() {
                        @Override
                        public void run() {
                            time[0] -=1000;
                            tmpData.setTime(getStringTime(time[0]));
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    changeViewContent(tmpView,tmpData);
                                }
                            });
                            if(time[0] ==0) timer2.cancel();
                        }
                    };

                    timer.schedule(timerTask, time[0]);
                    timer2.schedule(showTime,0,1000);

                }
            };
            TimePickerDialog tpd = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listener,0,0, true);
            tpd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            tpd.setTitle("세탁시간을 입력하세요");
            tpd.show();
        }


    }
    public static String getStringTime(int time){
        long hour=0, minute=0, second=0;

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


}