package com.example.user.finalproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this, "FinalProjectDB", 2);
        testAddSomeCategories(dbHelper); // ragdan unique adevs ertxel unda gavushvat.
//        testProduct(dbHelper);

    }


    private void testProduct(DBHelper dbHelper){
        Product product = new Product("ჰამბურგერი", "ყველით");
        product.setPrice(2.00);
        product.setCategoryName("ბურგერები");
        dbHelper.insertNewProduct(product);
        List<Product> products = new ArrayList<>();
        dbHelper.allProducts(products);

        for(int i = 0; i < products.size(); i++){
            Log.d("TEST", product.toString());
        }
    }

    private void testAddSomeCategories(DBHelper dbHelper){
        List<String> categoryList = new ArrayList<>();
        categoryList.add("ბურგერები");
        categoryList.add("ალკოჰოლური სასმელი");
        categoryList.add("სასმელი");
        categoryList.add("ნაყინი");
        categoryList.add("ნამცხვარი");
        categoryList.add("ფუნთუშეული");
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
