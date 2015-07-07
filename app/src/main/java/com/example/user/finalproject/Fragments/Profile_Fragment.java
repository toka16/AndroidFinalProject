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


public class Profile_Fragment extends Fragment {
    private View view;
    //    private boolean clicked;
    private ListView informationListView;
    private Profile_Tab_Adapter adapter;
    private LayoutInflater inf;
    private EditText edit;

//    public static ContactListAdapter adapter;

    private ArrayList<String> userInformation;
    private String selected_field_text;
    private int selected_item_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        clicked = false;
        view = inflater.inflate(R.layout.profile_list_view, container, false);
        selected_item_index = -1;
        informationListView = (ListView)view.findViewById(R.id.list_view_for_profile);
        inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        adapter = new ContactListAdapter(inf);
        informationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                //selected_Category = App.getCategories().get(position);
                //selected_category_name = selected_Category.getName().substring(3);
                //edit.setText(selected_category_name);
            }
        });



        userInformation = fromBase();
        adapter = new Profile_Tab_Adapter(inf.getContext(),userInformation);
        informationListView.setAdapter(adapter);

        // aq kidev damchirdeba buttonze listeneris damateba

        return view;
    }

    private ArrayList<String> fromBase(){
        ArrayList<String> result = new ArrayList<>();
        // aq realurad bazidan unda momqondes rogorc es rulebis magalitshi maqvs magrad droebit for ciklic kargia
        result.add("Name: ");
        result.add("Last Name: ");
        result.add("Private number: ");
        result.add("Mobile:");
        result.add("Email: ");
        result.add("Card Number: ");
        return result;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
