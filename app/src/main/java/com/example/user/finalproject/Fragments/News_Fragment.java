package com.example.user.finalproject.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.finalproject.Activities.Basket_Activity;
import com.example.user.finalproject.Activities.Detailed_News;
import com.example.user.finalproject.Adapters.News_Tab_Adapter;
import com.example.user.finalproject.Intent_Variables.Intent_Variables;
import com.example.user.finalproject.R;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.News;

import java.util.ArrayList;


public class News_Fragment extends Fragment {
    private LayoutInflater inf;
    private View view;

    private ArrayList<News> news;
    private News selected_news;
    private int selected_item_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        clicked = false;
        view = inflater.inflate(R.layout.news_list_view, container, false);
        selected_item_index = -1;
        ListView menusListView = (ListView)view.findViewById(R.id.list_view_for_news);
        inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        menusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_news = news.get(position);

                TextView temp = (TextView)view.findViewById(R.id.chosen_news_name);
                temp.setText("მონიშნული მენიუ: " + selected_news.getName());

            }
        });

        // aqac bazidan unda sheavso

        news = (ArrayList)DBHelper.getInstance(inf.getContext()).allNews();

        News_Tab_Adapter adapter = new News_Tab_Adapter(inf.getContext(),news);
        menusListView.setAdapter(adapter);


        Button go_to_basket = (Button) view.findViewById(R.id.go_for_news_details);
        go_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_item_index < 0) {
                    Toast toast;
                    toast = Toast.makeText(inf.getContext(), "You haven`t marked News yet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                // egreve basketis intents davustartav

                selected_item_index = -1;

                // aq gavatanot news int rata mere martivvad modzebnos iqet intentshi

                Intent intent = new Intent(getActivity(),Detailed_News.class);
                intent.putExtra(Intent_Variables.news_Name_For_Intent,selected_news.getName());
                intent.putExtra(Intent_Variables.news_ID_Fof_Intent,selected_news.getDb_ID());
                startActivity(intent);

            }
        });


        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
