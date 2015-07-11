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

public class Deletable_Product_List_Activity extends ActionBarActivity {

    private ArrayList<Product> products ;
    private ListView productsListView;
    public static Product_Tab_Adapter adapter;


    private Product selected_Product;
    private int selected_item_index;

    private ArrayList<Product> allProducts ;
    private ListView allProductsListView;
    public static Product_Tab_Adapter allProductsAdapter;

    private Product selected_Product_from_all_products;
    private int selected_item_index_for_all_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletable_product_list_view);

        selected_item_index = -1;
        selected_item_index_for_all_products = -1;

        productsListView = (ListView) findViewById(R.id.deletable_list_view_for_products);
        allProductsListView = (ListView) findViewById(R.id.all_products);

        // itogshi aq bazidan unda davitrio es arraylist
        // gadmocemul intentshi aris asusual_ID da imis mixedvit udna modzebno tableshi
        products = fromBase();

        // aq yvela productis bazidan unda wmaoigo
        allProducts = fromBase();

        allProductsAdapter = new Product_Tab_Adapter(this,allProducts);
        allProductsAdapter.notifyDataSetChanged();
        allProductsListView.setAdapter(allProductsAdapter);

        adapter = new Product_Tab_Adapter(this,products);
        adapter.notifyDataSetChanged();
        productsListView.setAdapter(adapter);



        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_Product = products.get(position);
                TextView choosen_product_field = (TextView) findViewById(R.id.choosen_text);
                choosen_product_field.setText("მონიშნული პროდუქტი: " + products.get(position).getName());
            }
        });

        allProductsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index_for_all_products = position;
                selected_Product_from_all_products = allProducts.get(position);
                TextView chosen_product_field = (TextView) findViewById(R.id.products_for_as_usual);
                chosen_product_field.setText("პროდუქტი ყველა პროდუქტიდან: " + selected_Product_from_all_products.getName());
            }
        });

        Button addButton = (Button) findViewById(R.id.add_product_in_as_usual);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_item_index_for_all_products < 0) {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "You haven`t marked Product from allProductsList", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                products.add(selected_Product_from_all_products);
                adapter.notifyDataSetChanged();

                // da aqve bazashic unda chaagdo umartives

                TextView chosen_product_field = (TextView) findViewById(R.id.products_for_as_usual);
                chosen_product_field.setText("პროდუქტი ყველა პროდუქტიდან: ");
                selected_item_index_for_all_products = -1;
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_product_from_as_usual);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (selected_item_index < 0) {
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "You haven`t marked Product yet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                products.remove(selected_item_index);
                adapter.notifyDataSetChanged();
                TextView chosen_product_field = (TextView) findViewById(R.id.choosen_text);
                chosen_product_field.setText("მონიშნული პროდუქტი: ");
                selected_item_index = -1;
            }
        });

        Button go_to_basket = (Button) findViewById(R.id.to_basket);
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


    private ArrayList<Product> fromBase(){

        ArrayList<Product> result = new ArrayList<>();

        // aq realurad basidan unda wamovigot es productebi


        Product temp = new Product("burger","gemrielia simon");
        result.add(temp);

        Product temp2 = new Product("nayini","esec gemrielia simon");
        result.add(temp2);

        Product temp3 = new Product("free","ramdens cham ra ubedurebaa");
        result.add(temp3);
        return result;
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
