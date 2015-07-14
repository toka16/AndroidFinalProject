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
import com.example.user.finalproject.asynchtasks.MenuImageDownLoadTask;
import com.example.user.finalproject.asynchtasks.ProductImageDownloadTask;
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

    public static final String SHARED_PREFERENCES_COOKIE = "cookie";
    private static final String SHARED_PREFERENCES_VERSION_PRODUCTS = "products_version";
    private static final String SHARED_PREFERENCES_VERSION_NEWS = "news_version";
    private static final String SHARED_PREFERENCES_VERSION_MENUS = "menu_version";
    private static final String SHARED_PREFERENCES_VERSION_CATEGORIES = "categories_version";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("before sync");
        new Thread(new Runnable() {
            @Override
            public void run() {
                SharedPreferences pref = getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                int cur_products_version = pref.getInt(SHARED_PREFERENCES_VERSION_PRODUCTS, -1);
                int cur_news_version = pref.getInt(SHARED_PREFERENCES_VERSION_NEWS, -1);
                int cur_menus_version = pref.getInt(SHARED_PREFERENCES_VERSION_MENUS, -1);
                int cur_categories_version = pref.getInt(SHARED_PREFERENCES_VERSION_CATEGORIES, -1);

                String cookie = pref.getString(SHARED_PREFERENCES_COOKIE, "");
                final ServerHelper helper = ServerHelper.getInstance(cookie);
                if(cookie.equals("")) {
                    Intent intent = new Intent(SpinnerActivity.this, Log_in_Activity.class);
                    startActivity(intent);
                }else {

                    int remote_products_version = helper.getProductsVersion();
                    int remote_news_version = helper.getNewsVersion();
                    int remote_menus_version = helper.getMenusVersion();
                    int remote_categories_version = helper.getCategoriesVersion();

                    DBHelper db = DBHelper.getInstance(SpinnerActivity.this);
                    if (cur_products_version == -1 || cur_products_version != remote_products_version ||
                            cur_categories_version == -1 || cur_categories_version != remote_categories_version ||
                            cur_menus_version == -1 || cur_categories_version != remote_menus_version ||
                            cur_news_version == -1 || cur_news_version != remote_news_version) {

                        db.cleanProducts();
                        db.cleanCategories();
                        db.cleanMenus();
                        db.cleanNews();

                        SharedPreferences.Editor editor = pref.edit();

                        ArrayList<Product> products = helper.getProducts();
                        editor.putInt(SHARED_PREFERENCES_VERSION_PRODUCTS, remote_products_version);
                        for (Product product : products) {
                            System.out.println("product: " + product.getName()+" : "+product.getServer_ID());
                            db.insertNewProduct(product);
                        }

                        ArrayList<Category> categories = helper.getCategories();
                        editor.putInt(SHARED_PREFERENCES_VERSION_CATEGORIES, remote_categories_version);
                        for (Category cat : categories) {
                            System.out.println("category: " + cat.getName());
                            db.insertNewCategory(cat);
                        }

                        ArrayList<News> newses = helper.getNews();
                        editor.putInt(SHARED_PREFERENCES_VERSION_NEWS, remote_news_version);
                        for (News news : newses) {
                            System.out.println("news: " + news.getName());
                            db.insertNews(news);
                        }

                        ArrayList<Menu> menus = helper.getMenus();
                        editor.putInt(SHARED_PREFERENCES_VERSION_MENUS, remote_menus_version);
                        for (Menu menu : menus) {
                            System.out.println("menu: " + menu.getName());
                            for(Product p : menu.getProducts())
                                System.out.println("menu product: "+p.getServer_ID());
                            db.insertNewMenu(menu);
                        }
                        editor.commit();
                    }
                    System.out.println("before images download");
                    ArrayList<Product> products = DBHelper.getInstance(SpinnerActivity.this).allProducts();
                    ProductImageDownloadTask productImageDownloadTask = new ProductImageDownloadTask(products);
                    productImageDownloadTask.execute();
                    System.out.println("product images started");
                    ArrayList<Menu> menus = DBHelper.getInstance(SpinnerActivity.this).allMenus();
                    MenuImageDownLoadTask menuImageDownLoadTask = new MenuImageDownLoadTask(menus);
                    menuImageDownLoadTask.execute();

                    Intent intent = new Intent(SpinnerActivity.this, MainActivity.class);
                    startActivity(intent);

//                    if (cur_categories_version == -1 || cur_categories_version != remote_categories_version) {
//                        ArrayList<Category> categories = helper.getCategories();
//                        editor.putInt(SHARED_PREFERENCES_VERSION_CATEGORIES, remote_categories_version);
//                        for (Category cat : categories) {
//                            System.out.println("category: " + cat.getName());
//                            db.insertNewCategory(cat);
//                        }
//                    }
//                    if (cur_menus_version == -1 || cur_categories_version != remote_menus_version) {
//                        ArrayList<Menu> menus = helper.getMenus();
//                        editor.putInt(SHARED_PREFERENCES_VERSION_MENUS, remote_menus_version);
//                        for (Menu menu : menus) {
//                            System.out.println("menu: " + menu.getName());
//                            for(Product p : menu.getProducts())
//                                System.out.println("menu product: "+p.getServer_ID());
//                            db.insertNewMenu(menu);
//                        }
//                    }
//                    ArrayList<Menu> menus = DBHelper.getInstance(SpinnerActivity.this).allMenus();
//                    MenuImageDownLoadTask menuImageDownLoadTask = new MenuImageDownLoadTask(menus);
//                    menuImageDownLoadTask.execute();
//
//                    if (cur_news_version == -1 || cur_news_version != remote_news_version) {
//                        ArrayList<News> newses = helper.getNews();
//                        editor.putInt(SHARED_PREFERENCES_VERSION_NEWS, remote_news_version);
//                        for (News news : newses) {
//                            System.out.println("news: " + news.getName());
//                            db.insertNews(news);
//                        }
//                    }
//                    editor.commit();
//                    Intent intent = new Intent(SpinnerActivity.this, MainActivity.class);
//                    startActivity(intent);
                }
            }
        }).start();
//        sync();
        System.out.println("after sync");
    }

    private void sync(){
        SharedPreferences pref = getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        int cur_products_version = pref.getInt(SHARED_PREFERENCES_VERSION_PRODUCTS, -1);
        int cur_news_version = pref.getInt(SHARED_PREFERENCES_VERSION_NEWS, -1);
        int cur_menus_version = pref.getInt(SHARED_PREFERENCES_VERSION_MENUS, -1);
        int cur_categories_version = pref.getInt(SHARED_PREFERENCES_VERSION_CATEGORIES, -1);

        String cookie = pref.getString(SHARED_PREFERENCES_COOKIE, "");
        final ServerHelper helper = ServerHelper.getInstance(cookie);
        if(cookie.equals("")) {
            Intent intent = new Intent(this, Log_in_Activity.class);
            startActivity(intent);
        }else {

            int remote_products_version = helper.getProductsVersion();
            int remote_news_version = helper.getNewsVersion();
            int remote_menus_version = helper.getMenusVersion();
            int remote_categories_version = helper.getCategoriesVersion();

            SharedPreferences.Editor editor = pref.edit();
            DBHelper db = DBHelper.getInstance(this);
            if (cur_products_version == -1 || cur_products_version != remote_products_version) {
                ArrayList<Product> products = helper.getProducts();
                editor.putInt(SHARED_PREFERENCES_VERSION_PRODUCTS, remote_products_version);
                for (Product product : products) {
                    System.out.println("product: " + product.getName()+" : "+product.getServer_ID());
                    db.insertNewProduct(product);
                }
            }
            System.out.println("before images download");
            ArrayList<Product> products = DBHelper.getInstance(SpinnerActivity.this).allProducts();
            ProductImageDownloadTask productImageDownloadTask = new ProductImageDownloadTask(products);
            productImageDownloadTask.execute();
            System.out.println("product images started");

            if (cur_categories_version == -1 || cur_categories_version != remote_categories_version) {
                ArrayList<Category> categories = helper.getCategories();
                editor.putInt(SHARED_PREFERENCES_VERSION_CATEGORIES, remote_categories_version);
                for (Category cat : categories) {
                    System.out.println("category: " + cat.getName());
                    db.insertNewCategory(cat);
                }
            }
            if (cur_menus_version == -1 || cur_categories_version != remote_menus_version) {
                ArrayList<Menu> menus = helper.getMenus();
                editor.putInt(SHARED_PREFERENCES_VERSION_MENUS, remote_menus_version);
                for (Menu menu : menus) {
                    System.out.println("menu: " + menu.getName());
                    for(Product p : menu.getProducts())
                        System.out.println("menu product: "+p.getServer_ID());
                    db.insertNewMenu(menu);
                }
            }
            ArrayList<Menu> menus = DBHelper.getInstance(SpinnerActivity.this).allMenus();
            MenuImageDownLoadTask menuImageDownLoadTask = new MenuImageDownLoadTask(menus);
            menuImageDownLoadTask.execute();

            if (cur_news_version == -1 || cur_news_version != remote_news_version) {
                ArrayList<News> newses = helper.getNews();
                editor.putInt(SHARED_PREFERENCES_VERSION_NEWS, remote_news_version);
                for (News news : newses) {
                    System.out.println("news: " + news.getName());
                    db.insertNews(news);
                }
            }
            editor.commit();
            Intent intent = new Intent(SpinnerActivity.this, MainActivity.class);
            startActivity(intent);
        }

    }
}
