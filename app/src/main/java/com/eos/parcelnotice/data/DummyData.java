package com.eos.parcelnotice.data;

import java.util.ArrayList;
import java.util.Date;

public class DummyData {
    public static ArrayList<ParcelData> parcels = new ArrayList<ParcelData>(){
        {
            add(new ParcelData(new Date(2020,9,25), "Sejin", "Hello"));
            add(new ParcelData(new Date(2020,9,25), "Sejin", "Hello"));
            add(new ParcelData(new Date(2020,9,25), "Sejin", "Hello"));
        }
    };

    public static String Notice = "1. 복도에 우산 치우기\n2. 점호 후에 조용히 하기";
}
