package com.example.user.finalproject.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.user.finalproject.Adapters.Profile_Tab_Adapter;
import com.example.user.finalproject.R;

import java.util.ArrayList;


public class News_Fragment extends Fragment {
    private View view;
//    private boolean clicked;

    private LayoutInflater inf;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        clicked = false;
        view = inflater.inflate(R.layout.activity_main, container, false);

        inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        return view;
    }



    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
