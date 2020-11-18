package com.eos.parcelnotice;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eos.parcelnotice.data.LaundryData;

import java.util.ArrayList;

public class LaundryConfirmActivity extends AppCompatActivity {
    LaundryAdapter adapter;
    GridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laundry_confirm);

        gridView= (GridView) findViewById(R.id.laundry_confirm_gridView);

        adapter = new LaundryAdapter();

        adapter.addItem(new LaundryData("세탁기1"));
        adapter.addItem(new LaundryData("세탁기2"));
        adapter.addItem(new LaundryData("건조기1"));
        adapter.addItem(new LaundryData("건조기2"));
        adapter.addItem(new LaundryData("건조기3"));
        adapter.addItem(new LaundryData("건조기4"));

        gridView.setAdapter(adapter);
    }

    class LaundryAdapter extends BaseAdapter{
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
            LaundryView view = null;

            if(convertView == null){
                view = new LaundryView(getApplicationContext());
            }
            else{
                view = (LaundryView) convertView;
            }

            final LaundryData item = items.get(position);

            changeViewContent(view,item);

            Button btnLaundry = (Button) view.findViewById(R.id.laundry_button);
            final LaundryView finalView = view;
            btnLaundry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.changeIsUsed();
                    changeViewContent(finalView,item);
                }
            });

            return view;
        }

        public void addItem(LaundryData item){
            items.add(item);
        }

        public void changeViewContent(LaundryView view, LaundryData item){
            view.setLaundryMachine(item.getMachine());
            if(item.getIsUsed()){
                view.setLaundryUse("사용중");
                view.setLaundryButton("세탁 완료");
                view.setLaundryUser("사용인: "+item.getUserId());
            }
            else{
                view.setLaundryUse("비어있음");
                view.setLaundryButton("사용");
                view.setLaundryUser("");
            }
        }
    }


    class LaundryView extends LinearLayout{
        TextView tvLaundryMachine,tvLaundryUse,tvLaundryUser;
        Button btnLaundry;


        public LaundryView(Context context) {
            super(context);
            init(context);
        }

        private void init(Context context){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_laundry_confirm_view,this,true);

            tvLaundryMachine = (TextView) findViewById(R.id.textView_laundry_machine);
            tvLaundryUse = (TextView) findViewById(R.id.textView_laundry_use);
            tvLaundryUser = (TextView) findViewById(R.id.textView_laundry_user);
            btnLaundry = (Button) findViewById(R.id.laundry_button);
        }

        public void setLaundryMachine(String machine){
            tvLaundryMachine.setText(machine);
        }

        public void setLaundryUse(String use){
            tvLaundryUse.setText(use);
        }

        public void setLaundryUser(String user){
            tvLaundryUser.setText(user);
        }

        public void setLaundryButton(String use){
            btnLaundry.setText(use);
        }
    }
}