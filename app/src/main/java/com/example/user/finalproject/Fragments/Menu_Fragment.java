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
import com.example.user.finalproject.Activities.Menu_Products_Activity;
import com.example.user.finalproject.Adapters.Menu_Tab_Adapter;
import com.example.user.finalproject.Intent_Variables.Bundle_Variables;
import com.example.user.finalproject.Intent_Variables.Intent_Variables;
import com.example.user.finalproject.R;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Menu;

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



        menus = DBHelper.getInstance(inf.getContext()).allMenus();
        Menu_Tab_Adapter adapter = new Menu_Tab_Adapter(inf.getContext(),menus);
        menusListView.setAdapter(adapter);

        Button details_about_menu = (Button) view.findViewById(R.id.details_about_menu);
        details_about_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selected_item_index < 0) {
                    Toast toast;
                    toast = Toast.makeText(inf.getContext(), "You haven`t marked Product yet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }


                selected_item_index = -1;
                TextView temp = (TextView)view.findViewById(R.id.chosen_menu);
                temp.setText("მონიშნული მენიუ: ");

                Intent intent = new Intent(getActivity(),Menu_Products_Activity.class);
                intent.putExtra(Intent_Variables.menu_ID_Fof_Intent,selected_menu.getDb_ID());
                intent.putExtra(Intent_Variables.menu_Name_For_Intent,selected_menu.getName());
                startActivity(intent);
            }
        });

        Button go_to_basket_from_menu_tab = (Button) view.findViewById(R.id.go_to_basket_from_menu_tab);
        go_to_basket_from_menu_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // egreve basketis intents davustartav

                selected_item_index = -1;

                Intent intent = new Intent(getActivity(),Basket_Activity.class);
                startActivity(intent);

            }
        });

        if(savedInstanceState != null){
            selected_item_index = savedInstanceState.getInt(Bundle_Variables.MENU_FRAGMENT_SELECT_PRODUCT_INDEX);
            if(selected_item_index != -1) {
                selected_menu = menus.get(selected_item_index);

                TextView temp = (TextView) view.findViewById(R.id.chosen_menu);
                temp.setText("მონიშნული პროდუქტი: " + selected_menu.getName());
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
        outState.putInt(Bundle_Variables.MENU_FRAGMENT_SELECT_PRODUCT_INDEX, selected_item_index);
    }
}
