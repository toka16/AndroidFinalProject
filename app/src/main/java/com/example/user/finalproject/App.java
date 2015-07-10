package com.example.user.finalproject;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import com.example.user.finalproject.Server.ServerHelper;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Category;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Created by toka on 7/10/2015.
 */
public class App extends Application{
    private static final String SHARED_PREFERENCES_NAME = "final_project_shared_preferences";
    private static final String SHARED_PREFERENCES_COOKIE = "cookie";
    private static final String SHARED_PREFERENCES_VERSION_PRODUCTS = "products_version";
    private static final String SHARED_PREFERENCES_VERSION_NEWS = "news_version";
    private static final String SHARED_PREFERENCES_VERSION_MENUS = "menuw_version";
    private static final String SHARED_PREFERENCES_VERSION_CATEGORIES = "categories_version";

    @Override
    public void onCreate(){
        super.onCreate();

        SharedPreferences pref = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        String cookie = pref.getString(SHARED_PREFERENCES_COOKIE, "");
        final ServerHelper helper = ServerHelper.getInstance(cookie);
        if(cookie.equals(""))
            helper.login("bla@ble.blu", "asdf");

        int cur_products_version = pref.getInt(SHARED_PREFERENCES_VERSION_PRODUCTS, -1);
        int cur_news_version = pref.getInt(SHARED_PREFERENCES_VERSION_NEWS, -1);
        int cur_menus_version = pref.getInt(SHARED_PREFERENCES_VERSION_MENUS, -1);
        int cur_categories_version = pref.getInt(SHARED_PREFERENCES_VERSION_CATEGORIES, -1);

        int remote_products_version = helper.getProductsVersion();
        int remote_news_version = helper.getNewsVersion();
        int remote_menus_version = helper.getMenusVersion();
        int remote_categories_version = helper.getCategoriesVersion();

        SharedPreferences.Editor editor = pref.edit();
        DBHelper db = new DBHelper(this);
        if(cur_products_version == -1 || cur_products_version != remote_products_version) {
            ArrayList<Product> products = helper.getProducts();
            editor.putInt(SHARED_PREFERENCES_VERSION_PRODUCTS, remote_products_version);
            for(Product product : products){
                System.out.println("product: "+product.getName());
//                db.insertNewProduct(product);
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
//                try {
//                    db.insertNews(news);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            }
        }
        editor.commit();

    }
}
