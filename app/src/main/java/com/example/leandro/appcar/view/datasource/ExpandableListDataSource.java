package com.example.leandro.appcar.view.datasource;

import android.content.Context;

import com.example.leandro.appcar.R;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class ExpandableListDataSource {


    public static Map<String, List<String>> getData(Context context) {
        Map<String, List<String>> expandableListData = new TreeMap<>();

        List<String> menu = Arrays.asList(context.getResources().getStringArray(R.array.menu));

        List<String> os = Arrays.asList(context.getResources().getStringArray(R.array.os));
        List<String> servicos = Arrays.asList(context.getResources().getStringArray(R.array.servicos));

        expandableListData.put(menu.get(0), os);
        expandableListData.put(menu.get(1), servicos);

        return expandableListData;
    }
}
