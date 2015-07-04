package com.example.user.finalproject.database;

public class DBTableEntries  {

    public static final String PRODUCT_TABLE_NAME = "Products";
    public static final String PRODUCT_ID = "ID";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_DESCRIPTION = "description";
    public static final String PRODUCT_PRICE = "price";

    public static final String CATEGORY_TABLE_NAME = "Categories";
    public static final String CATEGORY_ID = "ID";
    public static final String CATEGORY_NAME = "name";

    public static final String MAP_CATEGORY_PRODUCT_TABLE_NAME = "MapCategoryProduct";
    public static final String MAP_ID = "ID";
    public static final String MAP_CATEGORY_ID = "categoryID";
    public static final String MAP_PRODUCT_ID = "productID";

    public static final String NEWS_TABLE_NAME = "News";
    public static final String NEWS_ID = "ID";
    public static final String NEWS_NAME = "name";
    public static final String NEWS_DESCRIPTION = "description";
    public static final String NEWS_FROM_DATE = "from_date";
    public static final String NEWS_TO_DATE = "to_date";

    public static final String MENU_TABLE_NAME = "Menu";
    public static final String MENU_ID = "ID";
    public static final String MENU_NAME = "name";
    public static final String MENU_DESCRIPTION = "description";
    public static final String MENU_PRICE = "price";
    public static final String MENU_IMAGE_LINK = "image_link";

    public static final String MAP_MENU_PRODUCT_TABLE_NAME = "MapMenuProduct";
    public static final String MAP_MENU_TABLE_ID = "ID";
    public static final String MAP_MENU_TABLE_MENU_ID = "menuID";
    public static final String MAP_MENU_TABLE_PRODUCT_ID = "productID";

}
