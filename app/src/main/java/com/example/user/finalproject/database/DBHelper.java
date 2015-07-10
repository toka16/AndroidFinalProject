package com.example.user.finalproject.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String CREATE_PRODUCT_TABLE = " CREATE TABLE " +
            DBTableEntries.PRODUCT_TABLE_NAME  + " ( " +
            DBTableEntries.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.PRODUCT_NAME + " NVARCHAR(64)," +
            DBTableEntries.PRODUCT_DESCRIPTION + " TEXT," +
            DBTableEntries.PRODUCT_PRICE + " DOUBLE," +
            "CONSTRAINT product_unique_const UNIQUE (" + DBTableEntries.PRODUCT_NAME + "," + DBTableEntries.PRODUCT_DESCRIPTION + ")"
        + " )";

    private static final String CREATE_CATEGORIES_TABLE = " CREATE TABLE " +
            DBTableEntries.CATEGORY_TABLE_NAME + " ( " +
            DBTableEntries.CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.CATEGORY_NAME + " NVARCHAR(64), " +
            "CONSTRAINT category_unique_const UNIQUE (" + DBTableEntries.CATEGORY_NAME + ")"
        + " )";

    private static final String CREATE_MAP_CATEGORY_PRODUCT_TABLE = " CREATE TABLE " +
            DBTableEntries.MAP_CATEGORY_PRODUCT_TABLE_NAME + " ( " +
            DBTableEntries.MAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.MAP_CATEGORY_ID + " INTEGER," +
            DBTableEntries.MAP_PRODUCT_ID + " INTEGER," +
            "CONSTRAINT map_unique_const UNIQUE (" + DBTableEntries.MAP_CATEGORY_ID + "," + DBTableEntries.MAP_PRODUCT_ID + ")"
        + " )";

    private static final String CREATE_NEWS_TABLE = " CREATE TABLE " +
            DBTableEntries.NEWS_TABLE_NAME + " ( " +
            DBTableEntries.NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.NEWS_NAME + " NVARCHAR(64)," +
            DBTableEntries.NEWS_DESCRIPTION + " TEXT," +
            DBTableEntries.NEWS_FROM_DATE + " TIMESTAMP," +
            DBTableEntries.NEWS_TO_DATE + " TIMESTAMP "
        + " )";

    private static final String CREATE_MENU_TABLE = " CREATE TABLE " +
            DBTableEntries.MENU_TABLE_NAME + " (" +
            DBTableEntries.MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.MENU_NAME + " NVARCHAR(64)," +
            DBTableEntries.MENU_DESCRIPTION + " TEXT," +
            DBTableEntries.MENU_PRICE + " DOUBLE," +
            DBTableEntries.MENU_IMAGE_LINK + " TEXT, " +
            "CONSTRAINT menu_unique_const UNIQUE (" + DBTableEntries.MENU_NAME + "," + DBTableEntries.MENU_DESCRIPTION + ")"
        + " )";

    private static final String CREATE_MAP_MENU_CATEGORY = " CREATE TABLE " +
            DBTableEntries.MAP_MENU_PRODUCT_TABLE_NAME + " ( " +
            DBTableEntries.MAP_MENU_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.MAP_MENU_TABLE_MENU_ID + " INTEGER," +
            DBTableEntries.MAP_MENU_TABLE_PRODUCT_ID + " INTEGER," +
            "CONSTRAINT map_menu_unique_const UNIQUE (" + DBTableEntries.MAP_MENU_TABLE_MENU_ID + "," + DBTableEntries.MAP_MENU_TABLE_PRODUCT_ID + ")"
        + " )";

    private static final String NAME = "FinalProjectDB";
    private static final int VERSION = 3;
    private SQLiteDatabase db = getWritableDatabase();

    public DBHelper(Context context) {
        super(context, NAME, null, VERSION);
//        db = getWritableDatabase();
        Log.d("TEST", "constructor-shi shevida");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_MAP_CATEGORY_PRODUCT_TABLE);
        db.execSQL(CREATE_NEWS_TABLE);
        db.execSQL(CREATE_MENU_TABLE);
        db.execSQL(CREATE_MAP_MENU_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTable(DBTableEntries.PRODUCT_TABLE_NAME);
        deleteTable(DBTableEntries.CATEGORY_TABLE_NAME);
        deleteTable(DBTableEntries.MAP_CATEGORY_PRODUCT_TABLE_NAME);
        deleteTable(DBTableEntries.NEWS_TABLE_NAME);
        deleteTable(DBTableEntries.MENU_TABLE_NAME);
        deleteTable(DBTableEntries.MAP_MENU_PRODUCT_TABLE_NAME);
    }

    // The helper method drops table.
    private void deleteTable(String tableName){
        String deleteQuery = "drop table if exists " + tableName;
        db.execSQL(deleteQuery);
    }

    // public methods:

    // The method inserts new product into database.
    public void insertNewProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.PRODUCT_NAME, product.getName());
        values.put(DBTableEntries.PRODUCT_DESCRIPTION, product.getDescription());
        values.put(DBTableEntries.PRODUCT_PRICE, product.getPrice());
        long productID = db.insert(DBTableEntries.PRODUCT_TABLE_NAME, null, values);

//        int categoryID = getAppropriateCategoryID(product.getCategoryName());
        values.clear();
//     values.put(DBTableEntries.MAP_CATEGORY_ID, categoryID);
        values.put(DBTableEntries.MAP_PRODUCT_ID, (int)productID);
        db.insert(DBTableEntries.MAP_CATEGORY_PRODUCT_TABLE_NAME, null, values);
    }

    // The helper method returns category id by given name.
    private int getAppropriateCategoryID(String categoryName){
        String selectQuery = "select " + DBTableEntries.CATEGORY_ID + " from " + DBTableEntries.CATEGORY_TABLE_NAME +
                            " where " + DBTableEntries.CATEGORY_NAME + "='" + categoryName + "'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndexOrThrow(DBTableEntries.CATEGORY_ID);
        int categoryID = cursor.getInt(idIndex);
        return categoryID;
    }

    // The method adds into database a new category.
    public void insertNewCategory(String categoryName){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.CATEGORY_NAME, categoryName);

        db.insert(DBTableEntries.CATEGORY_TABLE_NAME, null, values);
    }

    // The method adds news into database.
    public void insertNews(News news) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = dateFormat.parse(news.getFromDate());
        Date toDate = dateFormat.parse(news.getToDate());

        ContentValues values = new ContentValues();
        values.put(DBTableEntries.NEWS_NAME, news.getName());
        values.put(DBTableEntries.NEWS_DESCRIPTION, news.getDescription());
        values.put(DBTableEntries.NEWS_FROM_DATE, dateFormat.format(fromDate));
        values.put(DBTableEntries.NEWS_TO_DATE, dateFormat.format(toDate));

        db.insert(DBTableEntries.NEWS_TABLE_NAME, null, values);
    }


    // The method fills a given news list by news.
    public void allNews(List<News> newses){
        String selectQuery = "select * from " + DBTableEntries.NEWS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int nameIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_NAME);
        int descriptionIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_DESCRIPTION);
        int fromDateIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_FROM_DATE);
        int toDateIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_TO_DATE);
        while(cursor.moveToNext()){
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            String fromDate = cursor.getString(fromDateIndex);
            String toDate = cursor.getString(toDateIndex);

            News news = new News(name, description);
            news.setFromDate(fromDate);
            news.setToDate(toDate);

            newses.add(news);
        }
    }


    // The method fills a given list by all categories.
    public void allCategories(List<String> categoryList){
        String selectQuery = "select " + DBTableEntries.CATEGORY_NAME + " from " + DBTableEntries.CATEGORY_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int nameIndex = cursor.getColumnIndexOrThrow(DBTableEntries.CATEGORY_NAME);
        while(cursor.moveToNext()){
            String name = cursor.getString(nameIndex);
            categoryList.add(name);
        }
    }

    // The method fills a given list by all products.
    public void allProducts(List<Product> productList){
        String selectQuery = "select * from " + DBTableEntries.PRODUCT_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int nameIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_NAME);
        int descriptionIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_DESCRIPTION);
        int priceIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_PRICE);

        while(cursor.moveToNext()){
            String productName = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            double price = cursor.getDouble(priceIndex);

            Product product = new Product(productName, description);
            product.setPrice(price);
            productList.add(product);
        }
        cursor.close();
    }
}
