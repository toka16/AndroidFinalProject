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
    private long as_usualDBID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deletable_product_list_view);

        selected_item_index = -1;
        selected_item_index_for_all_products = -1;

        productsListView = (ListView) findViewById(R.id.deletable_list_view_for_products);
        allProductsListView = (ListView) findViewById(R.id.all_products);


        Intent intent = getIntent();

        as_usualDBID = intent.getLongExtra(Intent_Variables.as_Usual_ID_Fof_Intent,0);
        products = DBHelper.getInstance(this).getAsUsualProducts(as_usualDBID);

        // aq yvela productis bazidan unda wmaoigo
        allProducts = (ArrayList)DBHelper.getInstance(getApplicationContext()).allProducts();

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

                DBHelper.getInstance(getApplicationContext()).asUsualAddProduct
                        (as_usualDBID,selected_Product_from_all_products.getDb_ID());

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
                DBHelper.getInstance(getApplicationContext()).asUsualRemoveProduct(as_usualDBID,selected_Product.getDb_ID());
                TextView chosen_product_field = (TextView) findViewById(R.id.choosen_text);
                chosen_product_field.setText("მონიშნული პროდუქტი: ");
                selected_item_index = -1;
            }
        });

        Button go_to_basket = (Button) findViewById(R.id.to_basket);
        go_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0 ; i < products.size(); i++){
                    DBHelper.getInstance(getApplicationContext()).insertNewProductIntoBasket(products.get(i).getDb_ID());
                }

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
