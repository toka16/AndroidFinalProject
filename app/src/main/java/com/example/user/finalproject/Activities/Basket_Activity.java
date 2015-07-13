package com.example.user.finalproject.Activities;


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
import com.example.user.finalproject.Intent_Variables.Bundle_Variables;
import com.example.user.finalproject.R;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;

public class Basket_Activity extends ActionBarActivity {

    private ArrayList<Product> products ;
    private ListView productsListView;
    public static Product_Tab_Adapter adapter;


    private Product selected_Product;
    private int selected_item_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket_list_view);

        selected_item_index = -1;
        productsListView = (ListView) findViewById(R.id.deletable_list_view_for_basket);



        products = DBHelper.getInstance(getApplicationContext()).getBasketProducts();

        adapter = new Product_Tab_Adapter(this,products);
        adapter.notifyDataSetChanged();
        productsListView.setAdapter(adapter);



        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_Product = products.get(position);
                TextView choosen_product_field = (TextView) findViewById(R.id.chosen_text_basket);
                choosen_product_field.setText("მონიშნული პროდუქტი: " + products.get(position).getName());
            }
        });

        Button deleteButton = (Button) findViewById(R.id.delete_product_from_basket);
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
                DBHelper.getInstance(getApplicationContext()).basketRemoveProduct(selected_Product.getDb_ID());

                TextView chosen_product_field = (TextView) findViewById(R.id.chosen_product_text_first_tab);
                chosen_product_field.setText("მონიშნული პროდუქტი: ");
                selected_item_index = -1;
            }
        });



        Button makeOrderButton = (Button) findViewById(R.id.order_products);
        makeOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // tokas es aris shekvetis button

                // shekvetas ro gaaketeb basketis tabledan yvelaferi washale
            }
        });

        if(savedInstanceState != null){
            selected_item_index = savedInstanceState.getInt(Bundle_Variables.BASKET_SELECT_ITEM_INDEX);
            if(selected_item_index != -1){
                selected_Product = products.get(selected_item_index);
                TextView choosen_product_field = (TextView) findViewById(R.id.chosen_text_basket);
                choosen_product_field.setText("მონიშნული პროდუქტი: " + products.get(selected_item_index).getName());
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt(Bundle_Variables.BASKET_SELECT_ITEM_INDEX, selected_item_index);
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
