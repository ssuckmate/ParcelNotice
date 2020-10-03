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
}
