package com.example.user.finalproject.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.finalproject.Activities.Deletable_Product_List_Activity;
import com.example.user.finalproject.Adapters.As_Usual_Tab_Adapter;
import com.example.user.finalproject.Intent_Variables.Bundle_Variables;
import com.example.user.finalproject.Intent_Variables.Intent_Variables;
import com.example.user.finalproject.R;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.As_Usual;

import java.util.ArrayList;

public class As_Usual_Fragment extends Fragment {
    private View view;
    //    private boolean clicked;
    private ListView contactListView;
    private As_Usual_Tab_Adapter adapter;
    private LayoutInflater inf;
    private EditText edit;

    private ArrayList<As_Usual> as_Usuals;
//    public static ContactListAdapter adapter;

    private As_Usual selected_As_Usual;
    private String selected_as_usual_name;
    private int selected_item_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        clicked = false;
        view = inflater.inflate(R.layout.as_usual_list_view, container, false);
        selected_item_index = -1;
        contactListView = (ListView)view.findViewById(R.id.list_view_for_as_usuals);
        inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        adapter = new ContactListAdapter(inf);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_As_Usual = as_Usuals.get(position);
                selected_as_usual_name = selected_As_Usual.getName();
                edit.setText(selected_as_usual_name);
            }
        });


        // aq unda wamoigo bazidan

        as_Usuals = DBHelper.getInstance(inf.getContext()).allAsUsual();

        // aq sheidzleba shecdoma iyos miubrundi da naxe
        adapter = new As_Usual_Tab_Adapter(inf.getContext(),as_Usuals);
        contactListView.setAdapter(adapter);


        edit = (EditText) view.findViewById(R.id.as_usual_field);
        //    edit.setText(" ");
//        contactList.setAdapter(adapter);
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = s.toString();
//                adapter.getFilter().filter(s.toString());

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        Button addButton = (Button) view.findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String as_usual_Name = edit.getText().toString();


                for(As_Usual a  :  as_Usuals){

                    String name = a.getName();
                    if(as_usual_Name.equals(name)){
                        Toast toast = Toast.makeText(inf.getContext(), "Such as_usual already exists", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                        toast.show();
                        return;
                    }
                }


                if(as_usual_Name.length() == 0){
                    Toast toast = Toast.makeText(inf.getContext(), "Please enter Something for  as_usual name", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }else{

                    As_Usual new_as_usual = new As_Usual(as_usual_Name);
                    as_Usuals.add(new_as_usual);
                    adapter.notifyDataSetChanged();

                    DBHelper.getInstance(inf.getContext()).insertNewAsUsual(new_as_usual);


                }
            }
        });


        Button deleteButton = (Button) view.findViewById(R.id.deleteBtn);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( selected_item_index >= 0){

                    as_Usuals.remove(selected_item_index);
                    adapter.notifyDataSetChanged();
                    DBHelper.getInstance(inf.getContext()).removeAsUsual(selected_As_Usual.getDb_ID());
                    selected_item_index = -1;
                    edit.setText("");
                }else{
                    Toast toast;
                    toast = Toast.makeText(inf.getContext(), "Select something to delete", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }

            }
        });



        Button updateButton = (Button) view.findViewById(R.id.updateBtn);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( selected_item_index >= 0 ){

                    as_Usuals.get(selected_item_index).setName(edit.getText().toString());
                    adapter.notifyDataSetChanged();
                    DBHelper.getInstance(inf.getContext()).asUsualUpdate
                            (as_Usuals.get(selected_item_index).getDb_ID(),(as_Usuals.get(selected_item_index).getName()));

                    selected_item_index = -1;
                    edit.setText("");
                }else{
                    Toast toast;
                    toast = Toast.makeText(inf.getContext(), "Select something to update", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                }



            }
        });

        Button goToAsUsualButton = (Button) view.findViewById(R.id.go_to_as_usual);
        goToAsUsualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( selected_item_index < 0){
                    Toast toast = Toast.makeText(inf.getContext(), "Please select category to get  more details", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                Long concrete_as_usual_db_id = as_Usuals.get(selected_item_index).getDb_ID();
                Intent intent = new Intent(getActivity(),Deletable_Product_List_Activity.class);
                intent.putExtra(Intent_Variables.as_Usual_ID_Fof_Intent,concrete_as_usual_db_id);
                startActivity(intent);

            }
        });

        if(savedInstanceState != null){
            selected_item_index = savedInstanceState.getInt(Bundle_Variables.AS_USUAL_FRAGMENT_SELECT_PRODUCT_INDEX);
            if(selected_item_index != -1){
                selected_As_Usual = as_Usuals.get(selected_item_index);
                selected_as_usual_name = selected_As_Usual.getName();
                edit.setText(selected_as_usual_name);
            }
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Bundle_Variables.AS_USUAL_FRAGMENT_SELECT_PRODUCT_INDEX, selected_item_index);
    }
}
