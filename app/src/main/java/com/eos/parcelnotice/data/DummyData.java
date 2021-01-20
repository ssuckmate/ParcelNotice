package com.eos.parcelnotice.data;

import java.util.ArrayList;
import java.util.Date;

public class DummyData {

    public static ArrayList<RewardData> plusPoints = new ArrayList<RewardData>(){
        {
            add(new RewardData(new Date(2020, 10, 1), 2, "벌레 잡음"));
            add(new RewardData(new Date(2020, 10, 3), 3, "생활실 상태 양호"));
            add(new RewardData(new Date(2020, 10, 5), 2, "분실물 신고"));

        }
    };

    public static ArrayList<RewardData> minusPoints = new ArrayList<RewardData>(){
        {
            add(new RewardData(new Date(2020, 10, 2), 2, "복장불량"));
            add(new RewardData(new Date(2020, 10, 4), 3, "친구와 싸움"));
        }
    };


}
