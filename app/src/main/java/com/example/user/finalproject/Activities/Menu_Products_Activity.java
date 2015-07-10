package com.example.user.finalproject.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.finalproject.Adapters.Product_Tab_Adapter;
import com.example.user.finalproject.R;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;

public class Menu_Products_Activity extends ActionBarActivity {


    private ArrayList<Product> products ;
    private ListView productsListView;
    public static Product_Tab_Adapter adapter;


    private Product selected_Product;
    private int selected_item_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity_list_view);

        selected_item_index = -1;
        productsListView = (ListView) findViewById(R.id.products_list_in_menu_activity);

        // itogshi aq bazidan unda davitrio es arraylist
        // gadmocemul intentshi aris asusual_ID da imis mixedvit udna modzebno tableshi
        products = new ArrayList<>();
        adapter = new Product_Tab_Adapter(this,products);
        adapter.notifyDataSetChanged();
        productsListView.setAdapter(adapter);



        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_Product = products.get(position);
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), selected_Product.getDescription(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();
            }
        });



        Button go_to_basket = (Button) findViewById(R.id.from_menu_add_to_basket);
        go_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //!!!!!!!!!!!!!!!!!!!!!!!!!!!


                // jer sanam shekveta gaketdeba da davstartav activities iqamde basketis table unda gavavso

                // egreve basketis intents davustartav

                Intent intent = new Intent(getApplicationContext(),Basket_Activity.class);
                startActivity(intent);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
