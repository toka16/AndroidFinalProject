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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.finalproject.Activities.Basket_Activity;
import com.example.user.finalproject.Activities.Menu_Products_Activity;
import com.example.user.finalproject.Adapters.Menu_Tab_Adapter;
import com.example.user.finalproject.Adapters.Product_Tab_Adapter;
import com.example.user.finalproject.Adapters.Profile_Tab_Adapter;
import com.example.user.finalproject.R;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;

public class Menu_Fragment extends Fragment {
    // buttonis damateba momiwevs rom es ragc chavagdo basketshi danarcheni jigruladaa iyoss informaciis gmaotana ise rogor gmaogaqvs
    // anu selected rom basketshi anu selected indexis sheotana isev mogiwevs ganakomentareb da
    // romelic aris selectied gaushveb imas basketshi easy as hell

    //    private boolean clicked;
    private LayoutInflater inf;
    private View view;

    private ArrayList<Menu> menus;
    private Menu selected_menu;
    private int selected_item_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        clicked = false;
        view = inflater.inflate(R.layout.menu_list_view, container, false);
        selected_item_index = -1;
        ListView menusListView = (ListView)view.findViewById(R.id.menu_list_view);
        inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        menusListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_menu = menus.get(position);
                Toast toast = Toast.makeText(inf.getContext(), selected_menu.getDescription(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();

                TextView temp = (TextView)view.findViewById(R.id.chosen_menu);
                temp.setText("მონიშნული მენიუ: " + selected_menu.getName());

            }
        });

        // aqac bazidan unda sheavso

        menus = new ArrayList<>();
        Menu_Tab_Adapter adapter = new Menu_Tab_Adapter(inf.getContext(),menus);
        menusListView.setAdapter(adapter);

        Button add_to_basket = (Button) view.findViewById(R.id.details_about_menu);
        add_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selected_item_index < 0) {
                    Toast toast;
                    toast = Toast.makeText(inf.getContext(), "You haven`t marked Product yet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }



                // !!!!!!!!! tableshic udna chaagdo basketis

                selected_item_index = -1;
                TextView temp = (TextView)view.findViewById(R.id.chosen_product_text);
                temp.setText("მონიშნული მენიუ: ");

                // !!!!!!!!!!
                // itogshi aq gaatan menus ID-s da dastartav axal activitiees
                Intent intent = new Intent(getActivity(),Menu_Products_Activity.class);
                startActivity(intent);
            }
        });

        Button go_to_basket = (Button) view.findViewById(R.id.go_to_basket_from_menu_tab);
        go_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // egreve basketis intents davustartav

                selected_item_index = -1;

                Intent intent = new Intent(getActivity(),Basket_Activity.class);
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
