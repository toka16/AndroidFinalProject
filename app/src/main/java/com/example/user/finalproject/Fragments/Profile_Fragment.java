package com.example.user.finalproject.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.finalproject.Adapters.Profile_Tab_Adapter;
import com.example.user.finalproject.R;
import com.example.user.finalproject.model.User_Information;

import java.util.ArrayList;


public class Profile_Fragment extends Fragment {
    private View view;
    //    private boolean clicked;
    private ListView informationListView;
    private Profile_Tab_Adapter adapter;
    private LayoutInflater inf;
    private EditText edit;

//    public static ContactListAdapter adapter;

    private ArrayList<User_Information> userInformation;
    private User_Information selected_field_User_Information;
    private String selected_User_Information_Value;
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
        edit = (EditText)view.findViewById(R.id.update_field);
        informationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_field_User_Information = userInformation.get(position);
                selected_User_Information_Value = selected_field_User_Information.getValue();
                edit.setText(selected_User_Information_Value);
            }
        });



        userInformation = fromBase();
        adapter = new Profile_Tab_Adapter(inf.getContext(),userInformation);
        informationListView.setAdapter(adapter);

        Button update_row = (Button) view.findViewById(R.id.update_row);
        update_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected_item_index<0){
                    Toast toast = Toast.makeText(inf.getContext(), "Please select row to update", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                User_Information temp = userInformation.get(selected_item_index);
                temp.setValue(edit.getText().toString());
                adapter.notifyDataSetChanged();

                // aqve unda gaketdes bazashi cvlileba

            }
        });

        ImageView caution = (ImageView)view.findViewById(R.id.caution_img);
        caution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected_item_index == 0){
                    Toast toast = Toast.makeText(inf.getContext(), "This Field is for your First Name ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(selected_item_index == 1){
                    Toast toast = Toast.makeText(inf.getContext(), "This Field is for your Last Name ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(selected_item_index == 2){
                    Toast toast = Toast.makeText(inf.getContext(), "This Field is for your Private Name . It should be valid or " +
                            "you might have some problems while getting your order ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(selected_item_index == 3){
                    Toast toast = Toast.makeText(inf.getContext(), "This Field is for your Mobile Number. " +
                            "Please make sure this is valid or we won`t be able to contact you  ", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(selected_item_index == 4){
                    Toast toast = Toast.makeText(inf.getContext(), "This Field is for your Email " +
                            "It should be valid or You won`t be able to restore your password", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }else if(selected_item_index == 5){
                    Toast toast = Toast.makeText(inf.getContext(), "This Field is for your Card Number " +
                            "It should be valid or you won`t be able to make an order", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(inf.getContext(), "Select row to get Information", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }
            }
        });
        // aq kidev damchirdeba buttonze listeneris damateba

        return view;
    }

    private ArrayList<User_Information> fromBase(){
        ArrayList<User_Information> result = new ArrayList<>();
        // aq realurad bazidan unda momqondes rogorc es rulebis magalitshi maqvs magrad droebit for ciklic kargia
        User_Information temp = new User_Information();
        temp.setRowName("Name: ");
        result.add(temp);
        User_Information temp1 = new User_Information();
        temp1.setRowName("Last Name: ");
        result.add(temp1);
        User_Information temp2 = new User_Information();
        temp2.setRowName("Private number: ");
        result.add(temp2);
        User_Information temp3 = new User_Information();
        temp3.setRowName("Mobile: ");
        result.add(temp3);
        User_Information temp4 = new User_Information();
        temp4.setRowName("Email: ");
        result.add(temp4);
        User_Information temp5 = new User_Information();
        temp5.setRowName("Card Number: ");
        result.add(temp5);

        return result;
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
