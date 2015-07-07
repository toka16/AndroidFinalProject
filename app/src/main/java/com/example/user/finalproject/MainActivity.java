package com.example.user.finalproject;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this, "FinalProjectDB", 1);
//        testAddSomeCategories(dbHelper); // ragdan unique adevs ertxel unda gavushvat.
//        testProduct(dbHelper);
        testNews(dbHelper);
    }

    private void testNews(DBHelper dbHelper){
        News news = new News("aqcia burgerze", "ori hamburgeri ertis pasad");
        news.setFromDate("2015-08-01");
        news.setToDate("2015-09-01");
        try {
            dbHelper.insertNews(news);
        } catch (ParseException e) {
            Log.d("TEST", "incorrect date");
            return;
        }

        List<News> newsList = new ArrayList<>();
        dbHelper.allNews(newsList);
        for(int i = 0; i < newsList.size(); i++){
            Log.d("TEST", newsList.get(i).toString());
        }
    }


    private void testProduct(DBHelper dbHelper){
        Product product = new Product("mozapini", "shokoladit");
        product.setPrice(2.00);
        product.setCategoryName("icecream");
        dbHelper.insertNewProduct(product);

        List<Product> products = new ArrayList<>();
        dbHelper.allProducts(products);

        for(int i = 0; i < products.size(); i++){
            Log.d("TEST", product.toString());
        }
    }

    private void testAddSomeCategories(DBHelper dbHelper){
        List<String> categoryList = new ArrayList<>();
        categoryList.add("burgeri");
        categoryList.add("alkoholuri sasmeli");
        categoryList.add("nayini");
        categoryList.add("namcxvari");
        categoryList.add("sasmeli");
        categoryList.add("ფუნთუშეული");

        for(int i = 0; i < categoryList.size(); i++){
            dbHelper.insertNewCategory(categoryList.get(i));
        }

        categoryList.clear();
        dbHelper.allCategories(categoryList);
        for(int i = 0; i < categoryList.size(); i++){
            Log.d("TEST", "category: " + categoryList.get(i));
        }
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
