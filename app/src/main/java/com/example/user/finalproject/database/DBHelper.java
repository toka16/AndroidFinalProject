package com.example.user.finalproject.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.user.finalproject.model.Category;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;
import com.example.user.finalproject.model.User_Information;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String CREATE_PRODUCT_TABLE = " CREATE TABLE " +
            DBTableEntries.PRODUCT_TABLE_NAME  + " ( " +
            DBTableEntries.PRODUCT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.PRODUCT_NAME + " NVARCHAR(64)," +
            DBTableEntries.PRODUCT_DESCRIPTION + " TEXT," +
            DBTableEntries.PRODUCT_PRICE + " DOUBLE," +
            DBTableEntries.PRODUCT_IMAGE + " BLOB," +
            DBTableEntries.PRODUCT_SERVER_ID + " INT," +
            "CONSTRAINT product_unique_const UNIQUE (" + DBTableEntries.PRODUCT_NAME + "," + DBTableEntries.PRODUCT_DESCRIPTION + ")"
        + " )";

    private static final String CREATE_CATEGORIES_TABLE = " CREATE TABLE " +
            DBTableEntries.CATEGORY_TABLE_NAME + " ( " +
            DBTableEntries.CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.CATEGORY_NAME + " NVARCHAR(64), " +
            DBTableEntries.CATEGORY_SERVER_ID + " INT," +
            "CONSTRAINT category_unique_const UNIQUE (" + DBTableEntries.CATEGORY_NAME + ")"
        + " )";

    private static final String CREATE_MAP_CATEGORY_PRODUCT_TABLE = " CREATE TABLE " +
            DBTableEntries.MAP_CATEGORY_PRODUCT_TABLE_NAME + " ( " +
            DBTableEntries.MAP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.MAP_CATEGORY_ID + " LONG," +
            DBTableEntries.MAP_PRODUCT_ID + " LONG," +
            "CONSTRAINT map_unique_const UNIQUE (" + DBTableEntries.MAP_CATEGORY_ID + "," + DBTableEntries.MAP_PRODUCT_ID + ")"
        + " )";

    private static final String CREATE_NEWS_TABLE = " CREATE TABLE " +
            DBTableEntries.NEWS_TABLE_NAME + " ( " +
            DBTableEntries.NEWS_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.NEWS_NAME + " NVARCHAR(64)," +
            DBTableEntries.NEWS_DESCRIPTION + " TEXT," +
            DBTableEntries.NEWS_FROM_DATE + " TIMESTAMP," +
            DBTableEntries.NEWS_TO_DATE + " TIMESTAMP," +
            DBTableEntries.NEWS_SERVER_ID + " INT"
        + " )";

    private static final String CREATE_MENU_TABLE = " CREATE TABLE " +
            DBTableEntries.MENU_TABLE_NAME + " (" +
            DBTableEntries.MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.MENU_NAME + " NVARCHAR(64)," +
            DBTableEntries.MENU_DESCRIPTION + " TEXT," +
            DBTableEntries.MENU_PRICE + " DOUBLE," +
            DBTableEntries.MENU_IMAGE + " BLOB," +
            DBTableEntries.MENU_SERVER_ID + " INT," +
            "CONSTRAINT menu_unique_const UNIQUE (" + DBTableEntries.MENU_NAME + "," + DBTableEntries.MENU_DESCRIPTION + ")"
        + " )";

    private static final String CREATE_MAP_MENU_CATEGORY = " CREATE TABLE " +
            DBTableEntries.MAP_MENU_PRODUCT_TABLE_NAME + " ( " +
            DBTableEntries.MAP_MENU_TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.MAP_MENU_TABLE_MENU_ID + " LONG," +
            DBTableEntries.MAP_MENU_TABLE_PRODUCT_ID + " LONG," +
            "CONSTRAINT map_menu_unique_const UNIQUE (" + DBTableEntries.MAP_MENU_TABLE_MENU_ID + "," + DBTableEntries.MAP_MENU_TABLE_PRODUCT_ID + ")"
        + " )";

    private static final String CREATE_USER_TABLE = " CREATE TABLE " +
            DBTableEntries.USER_TABLE_NAME + " ( " +
            DBTableEntries.USER_ENTRY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            DBTableEntries.USER_FIELD_NAME_COLUMN + " VARCHAR(32)," +
            DBTableEntries.USER_FILED_VALUE_COLUMN + " NVARCHAR(32)"
        + " )";

    private static final String NAME = "FinalProjectDB";
    private static final int VERSION = 1;
    private SQLiteDatabase db;

    private static final String INSERT_QUERY_INTO_USER =
            "insert into " + DBTableEntries.USER_TABLE_NAME + "(" + DBTableEntries.USER_FIELD_NAME_COLUMN  + ")" +
            " values " + "('name'), ('surname'), ('email'), ('password'), ('phone'), ('card_number'), ('primary_number')";

    private DBHelper(Context context) {
        super(context, NAME, null, VERSION);
        db = getWritableDatabase();
    }

    private static DBHelper instance;

    public static DBHelper getInstance(Context context){
        if(instance == null){
           instance = new DBHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_CATEGORIES_TABLE);
        db.execSQL(CREATE_MAP_CATEGORY_PRODUCT_TABLE);
        db.execSQL(CREATE_NEWS_TABLE);
        db.execSQL(CREATE_MENU_TABLE);
        db.execSQL(CREATE_MAP_MENU_CATEGORY);
        db.execSQL(CREATE_USER_TABLE);


        db.execSQL(INSERT_QUERY_INTO_USER); // inserts into user table first column key variables.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        deleteTable(DBTableEntries.PRODUCT_TABLE_NAME);
        deleteTable(DBTableEntries.CATEGORY_TABLE_NAME);
        deleteTable(DBTableEntries.MAP_CATEGORY_PRODUCT_TABLE_NAME);
        deleteTable(DBTableEntries.NEWS_TABLE_NAME);
        deleteTable(DBTableEntries.MENU_TABLE_NAME);
        deleteTable(DBTableEntries.MAP_MENU_PRODUCT_TABLE_NAME);
        deleteTable(DBTableEntries.USER_TABLE_NAME);
    }

    // The helper method drops table.
    private void deleteTable(String tableName){
        String deleteQuery = "drop table if exists " + tableName;
        db.execSQL(deleteQuery);
    }

    // public methods:

    // The method inserts user fields into database.
    public void insertUserInformation(User_Information information){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.USER_FILED_VALUE_COLUMN, information.getValue());

        long id = db.update(DBTableEntries.USER_TABLE_NAME, values, DBTableEntries.USER_FIELD_NAME_COLUMN + "=" + information.getRowName(), null);
        information.setDb_ID(id);
    }

    // The method inserts new product into database.
    public void insertNewProduct(Product product){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.PRODUCT_NAME, product.getName());
        values.put(DBTableEntries.PRODUCT_DESCRIPTION, product.getDescription());
        values.put(DBTableEntries.PRODUCT_PRICE, product.getPrice());
        values.put(DBTableEntries.PRODUCT_SERVER_ID, product.getServer_ID());
        long productID = db.insert(DBTableEntries.PRODUCT_TABLE_NAME, null, values);
        product.setDb_ID(productID);
    }

    // The method adds into database a new category.
    public void insertNewCategory(Category category){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.CATEGORY_NAME, category.getName());
        values.put(DBTableEntries.CATEGORY_SERVER_ID, category.getServer_ID());

        long categoryID = db.insert(DBTableEntries.CATEGORY_TABLE_NAME, null, values);
        category.setDb_ID(categoryID);

        List<Product> products = category.getProducts();
        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            fillMapCategoryProduct(categoryID, product.getDb_ID());
        }
    }

    // The helper method fills MapCategoryProduct table.
    private void fillMapCategoryProduct(long categoryID, long productID){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.MAP_CATEGORY_ID, categoryID);
        values.put(DBTableEntries.MAP_PRODUCT_ID, productID);

        db.insert(DBTableEntries.MAP_CATEGORY_PRODUCT_TABLE_NAME, null, values);
    }

    public void insertNewMenu(Menu menu){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.MENU_NAME, menu.getName());
        values.put(DBTableEntries.MENU_DESCRIPTION, menu.getDescription());
        values.put(DBTableEntries.MENU_PRICE, menu.getPrice());
        values.put(DBTableEntries.MENU_IMAGE, menu.getMenuImage());
        values.put(DBTableEntries.MENU_SERVER_ID, menu.getServer_ID());

        long menuID = db.insert(DBTableEntries.MENU_TABLE_NAME, null, values);
        menu.setDb_ID(menuID);

        List<Product> products = menu.getProducts();
        for(int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            fillMapMenuProduct(menuID, product.getDb_ID());
        }
    }

    // The helper method fills mapMenuProduct table.
    private void fillMapMenuProduct(long menuID, long productID){
        ContentValues values = new ContentValues();
        values.put(DBTableEntries.MAP_MENU_TABLE_MENU_ID, menuID);
        values.put(DBTableEntries.MAP_MENU_TABLE_PRODUCT_ID, productID);

        db.insert(DBTableEntries.MAP_MENU_PRODUCT_TABLE_NAME, null, values);
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
        values.put(DBTableEntries.NEWS_SERVER_ID, news.getServer_ID());

        long id = db.insert(DBTableEntries.NEWS_TABLE_NAME, null, values);
        news.setDb_ID(id);
    }


    // The method fills a given news list by news.
    public List<News> allNews(){
        List<News> newsList = new ArrayList<>();
        String selectQuery = "select * from " + DBTableEntries.NEWS_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int idIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_ID);
        int nameIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_NAME);
        int descriptionIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_DESCRIPTION);
        int fromDateIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_FROM_DATE);
        int toDateIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_TO_DATE);
        int serverIDIndex = cursor.getColumnIndexOrThrow(DBTableEntries.NEWS_SERVER_ID);
        while(cursor.moveToNext()){
            long id = cursor.getLong(idIndex);
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            String fromDate = cursor.getString(fromDateIndex);
            String toDate = cursor.getString(toDateIndex);
            int serverID = cursor.getInt(serverIDIndex);

            News news = new News(name, description);
            news.setFromDate(fromDate);
            news.setToDate(toDate);
            news.setServer_ID(serverID);
            news.setDb_ID(id);

            newsList.add(news);
        }
        return newsList;
    }


    // The method fills a given list by all categories.
    public ArrayList<Category> allCategories(){
        ArrayList<Category> categoryList = new ArrayList<>();
        String selectQuery = "select * from " + DBTableEntries.CATEGORY_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);

        int localDBIDIndex = cursor.getColumnIndexOrThrow(DBTableEntries.CATEGORY_ID);
        int nameIndex = cursor.getColumnIndexOrThrow(DBTableEntries.CATEGORY_NAME);
        int serverIDIndex = cursor.getColumnIndexOrThrow(DBTableEntries.CATEGORY_SERVER_ID);
        while(cursor.moveToNext()){
            long id = cursor.getLong(localDBIDIndex);
            String name = cursor.getString(nameIndex);
            int serverID = cursor.getInt(serverIDIndex);

            Category category = new Category();
            category.setDb_ID(id);
            category.setName(name);
            category.setServer_ID(serverID);

            ArrayList<Product> products = getCategoryProducts(id);

            category.setProducts(products);
            categoryList.add(category);

        }
        cursor.close();
        return categoryList;
    }

    public ArrayList<Product> getCategoryProducts(long categoryID){
        String selectProducts = "select " + DBTableEntries.PRODUCT_ID + ", " + DBTableEntries.PRODUCT_NAME + ", " +
                DBTableEntries.PRODUCT_DESCRIPTION + ", " + DBTableEntries.PRODUCT_PRICE + ", " +
                DBTableEntries.PRODUCT_IMAGE + ", " + DBTableEntries.PRODUCT_SERVER_ID +
                " from " + DBTableEntries.PRODUCT_TABLE_NAME +
                " inner join " + DBTableEntries.MAP_CATEGORY_PRODUCT_TABLE_NAME +
                " on " + DBTableEntries.PRODUCT_ID + " = " + DBTableEntries.MAP_PRODUCT_ID +
                " where " + DBTableEntries.MAP_CATEGORY_ID + " = " + categoryID;

        Cursor cursorCategoryProducts = db.rawQuery(selectProducts, null);
        ArrayList<Product> products = new ArrayList<>();

        processProductCursor(cursorCategoryProducts, products);
        cursorCategoryProducts.close();
        return products;
    }

    // The method returns all menus from database.
    public ArrayList<Menu> allMenus(){
        ArrayList<Menu> menuList = new ArrayList<>();
        String selectMenuQuery = "select * from " + DBTableEntries.MENU_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectMenuQuery, null);

        int idIndex = cursor.getColumnIndexOrThrow(DBTableEntries.MENU_ID);
        int nameIndex = cursor.getColumnIndexOrThrow(DBTableEntries.MENU_NAME);
        int descriptionIndex = cursor.getColumnIndexOrThrow(DBTableEntries.MENU_DESCRIPTION);
        int priceIndex = cursor.getColumnIndexOrThrow(DBTableEntries.MENU_PRICE);
        int imageIndex = cursor.getColumnIndexOrThrow(DBTableEntries.MENU_IMAGE);
        int serverIDIndex = cursor.getColumnIndexOrThrow(DBTableEntries.MENU_SERVER_ID);

        while(cursor.moveToNext()){
            long id = cursor.getLong(idIndex);
            String name = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            double price = cursor.getDouble(priceIndex);
            byte[] image = cursor.getBlob(imageIndex);
            int serverID = cursor.getInt(serverIDIndex);

            Menu menu = new Menu();
            menu.setDb_ID(id);
            menu.setName(name);
            menu.setDescription(description);
            menu.setPrice(price);
            menu.setMenuImage(image);
            menu.setServer_ID(serverID);

            ArrayList<Product> products = getMenuProducts(id);
            menu.setProducts(products);
            menuList.add(menu);

        }
        cursor.close();

        return menuList;
    }

    // The method returns all products into appropriate menu.
    public ArrayList<Product> getMenuProducts(long menuID){
        String selectMenuProductQuery = "select " + DBTableEntries.PRODUCT_ID + ", " + DBTableEntries.PRODUCT_NAME + ", " +
                DBTableEntries.PRODUCT_DESCRIPTION + ", " + DBTableEntries.PRODUCT_PRICE + ", " +
                DBTableEntries.PRODUCT_IMAGE + ", " + DBTableEntries.PRODUCT_SERVER_ID +
                " from " + DBTableEntries.PRODUCT_TABLE_NAME +
                " inner join " + DBTableEntries.MAP_MENU_PRODUCT_TABLE_NAME +
                " on " + DBTableEntries.PRODUCT_ID + " = " + DBTableEntries.MAP_MENU_TABLE_PRODUCT_ID +
                " where " + DBTableEntries.MAP_MENU_TABLE_MENU_ID + " = " + menuID;

        Cursor cursorMenuProduct = db.rawQuery(selectMenuProductQuery, null);
        ArrayList<Product> products = new ArrayList<>();
        processProductCursor(cursorMenuProduct, products);
        cursorMenuProduct.close();
        return products;
    }

    // The method returns a list with all products.
    public List<Product> allProducts(){
        List<Product> productList = new ArrayList<>();
        String selectQuery = "select * from " + DBTableEntries.PRODUCT_TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        processProductCursor(cursor, productList);
        cursor.close();
        return productList;
    }

    // The helper method fills the given products list by products.
    private void processProductCursor(Cursor cursor, List<Product> productList){
        int localDBIDIndex = cursor.getColumnIndex(DBTableEntries.PRODUCT_ID);
        int nameIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_NAME);
        int descriptionIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_DESCRIPTION);
        int priceIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_PRICE);
        int imageIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_IMAGE);
        int serverDBIDIndex = cursor.getColumnIndexOrThrow(DBTableEntries.PRODUCT_SERVER_ID);

        while(cursor.moveToNext()){
            long id = cursor.getLong(localDBIDIndex);
            String productName = cursor.getString(nameIndex);
            String description = cursor.getString(descriptionIndex);
            double price = cursor.getDouble(priceIndex);
            byte[] image = cursor.getBlob(imageIndex);
            int serverID = cursor.getInt(serverDBIDIndex);

            Product product = new Product(productName, description);
            product.setDb_ID(id);
            product.setPrice(price);
            product.setProductImage(image);
            product.setServer_ID(serverID);

            productList.add(product);
        }
    }
}
