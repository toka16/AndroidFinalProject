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
import com.example.user.finalproject.Intent_Variables.Intent_Variables;
import com.example.user.finalproject.R;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Product;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Category_Products_Activity extends ActionBarActivity {

    private ArrayList<Product> products ;
    private ListView productsListView;
    public static Product_Tab_Adapter adapter;


    private Product selected_Product;
    private int selected_item_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_category_list_view);

        selected_item_index = -1;
        productsListView = (ListView) findViewById(R.id.products_list_in_detailed_category_activity);


        Intent intent = getIntent();


        TextView temp = (TextView) findViewById(R.id.detailed_category_activity_header);
        temp.setText(temp.getText() + " "+intent.getStringExtra(Intent_Variables.category_Name_For_Intent));


        products = DBHelper.getInstance(getApplication()).getCategoryProducts(intent.getLongExtra(Intent_Variables.category_ID_Fof_Intent, 0));
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



        Button add_to_basket = (Button) findViewById(R.id.from_category_add_to_basket);
        add_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //!!!!!!!!!!!!!!!!!!!!!!!!!!!


                // jer sanam shekveta gaketdeba da davstartav activities iqamde basketis table unda gavavso

                // egreve basketis intents davustartav

                if(selected_item_index < 0){
                    Toast toast;
                    toast = Toast.makeText(getApplicationContext(), "You haven`t marked any product yet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                // basketshi chavamatot monishnuli product
                DBHelper.getInstance(Category_Products_Activity.this).insertNewProductIntoBasket(selected_Product.getDb_ID());
                Toast toast;
                toast = Toast.makeText(getApplicationContext(), "ჩავამატე კალათაში :)", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                toast.show();
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
