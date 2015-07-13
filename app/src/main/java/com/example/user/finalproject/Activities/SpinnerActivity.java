package com.example.user.finalproject.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.user.finalproject.App;
import com.example.user.finalproject.MainActivity;
import com.example.user.finalproject.R;
import com.example.user.finalproject.Server.ServerHelper;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Category;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;

/**
 * Created by toka on 7/13/2015.
 */
public class SpinnerActivity extends Activity {

    private static final String SHARED_PREFERENCES_VERSION_PRODUCTS = "products_version";
    private static final String SHARED_PREFERENCES_VERSION_NEWS = "news_version";
    private static final String SHARED_PREFERENCES_VERSION_MENUS = "menu_version";
    private static final String SHARED_PREFERENCES_VERSION_CATEGORIES = "categories_version";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sync();
        Intent intent = new Intent(SpinnerActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void sync(){
        SharedPreferences pref = getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        int cur_products_version = pref.getInt(SHARED_PREFERENCES_VERSION_PRODUCTS, -1);
        int cur_news_version = pref.getInt(SHARED_PREFERENCES_VERSION_NEWS, -1);
        int cur_menus_version = pref.getInt(SHARED_PREFERENCES_VERSION_MENUS, -1);
        int cur_categories_version = pref.getInt(SHARED_PREFERENCES_VERSION_CATEGORIES, -1);

        ServerHelper helper = ServerHelper.getInstance("");

        int remote_products_version = helper.getProductsVersion();
        int remote_news_version = helper.getNewsVersion();
        int remote_menus_version = helper.getMenusVersion();
        int remote_categories_version = helper.getCategoriesVersion();

        SharedPreferences.Editor editor = pref.edit();
        DBHelper db = DBHelper.getInstance(this);
        if(cur_products_version == -1 || cur_products_version != remote_products_version) {
            ArrayList<Product> products = helper.getProducts();
            editor.putInt(SHARED_PREFERENCES_VERSION_PRODUCTS, remote_products_version);
            for(Product product : products){
                System.out.println("product: "+product.getName());
                db.insertNewProduct(product);
            }
        }
        if(cur_categories_version == -1 || cur_categories_version != remote_categories_version){
            ArrayList<Category> categories = helper.getCategories();
            editor.putInt(SHARED_PREFERENCES_VERSION_CATEGORIES, remote_categories_version);
            for(Category cat : categories){
                System.out.println("category: "+cat.getName());
            }
        }
        if(cur_menus_version == -1 || cur_categories_version != remote_menus_version){
            ArrayList<Menu> menus = helper.getMenus();
            editor.putInt(SHARED_PREFERENCES_VERSION_MENUS, remote_menus_version);
            for(Menu menu : menus){
                System.out.println("menu: "+menu.getName());
            }
        }
        if(cur_news_version == -1 || cur_news_version != remote_news_version){
            ArrayList<News> newses = helper.getNews();
            editor.putInt(SHARED_PREFERENCES_VERSION_NEWS, remote_news_version);
            for(News news : newses){
                System.out.println("news: "+news.getName());
                db.insertNews(news);
            }
        }
        editor.commit();

    }
}
