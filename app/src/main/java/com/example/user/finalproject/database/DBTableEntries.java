package com.example.user.finalproject.database;

public class DBTableEntries  {

    public static final String PRODUCT_TABLE_NAME = "Products";
    public static final String PRODUCT_ID = "product_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_DESCRIPTION = "product_description";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String PRODUCT_IMAGE = "product_image";
    public static final String PRODUCT_SERVER_ID = "product_server_id";
    public static final String PRODUCT_IMAGE_LINK = "product_image_link";

    public static final String CATEGORY_TABLE_NAME = "Categories";
    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_SERVER_ID = "category_server_id";

    public static final String MAP_CATEGORY_PRODUCT_TABLE_NAME = "MapCategoryProduct";
    public static final String MAP_ID = "map_category_product_id";
    public static final String MAP_CATEGORY_ID = "map_category_product_categoryID";
    public static final String MAP_PRODUCT_ID = "map_category_product_productID";

    public static final String NEWS_TABLE_NAME = "News";
    public static final String NEWS_ID = "news_id";
    public static final String NEWS_NAME = "news_name";
    public static final String NEWS_DESCRIPTION = "news_description";
    public static final String NEWS_FROM_DATE = "news_from_date";
    public static final String NEWS_TO_DATE = "news_to_date";
    public static final String NEWS_SERVER_ID = "news_server_id";

    public static final String MENU_TABLE_NAME = "Menu";
    public static final String MENU_ID = "menu_id";
    public static final String MENU_NAME = "menu_name";
    public static final String MENU_DESCRIPTION = "menu_description";
    public static final String MENU_PRICE = "menu_price";
    public static final String MENU_IMAGE = "menu_image";
    public static final String MENU_SERVER_ID = "menu_server_id";
    public static final String MENU_IMAGE_LINK = "menu_image_link";

    public static final String MAP_MENU_PRODUCT_TABLE_NAME = "MapMenuProduct";
    public static final String MAP_MENU_TABLE_ID = "menu_id";
    public static final String MAP_MENU_TABLE_MENU_ID = "map_menu_product_menuID";
    public static final String MAP_MENU_TABLE_PRODUCT_ID = "map_menu_product_productID";

    public static final String USER_TABLE_NAME = "user";
    public static final String USER_ENTRY_ID = "user_id";
    public static final String USER_FIELD_NAME_COLUMN = "user_fields";
    public static final String USER_FILED_VALUE_COLUMN = "user_values";

    public static final String BASKET_TABLE_NAME = "Basket";
    public static final String BASKET_ID = "basket_id";
    public static final String BASKET_PRODUCT_ID = "basket_product_id";
    public static final String BASKET_PRODUCT_NUMBER = "basket_product_quantity";

    public static final String AS_USUAL_TABLE_NAME = "AsUsual";
    public static final String AS_USUAL_ID = "as_usual_id";
    public static final String AS_USUAL_NAME = "as_usual_name";
    public static final String AS_USUAL_PRICE = "as_usual_price";

    public static final String MAP_AS_USUAL_PRODUCT_TABLE_NAME = "MapAsUsualProduct";
    public static final String MAP_AS_USUAL_PRODUCT_TABLE_ID = "map_as_usual_product_table_id";
    public static final String MAP_AS_USUAL_PRODUCT_AS_USUAL_ID = "map_as_usual_product_as_usual_id";
    public static final String MAP_AS_USUAL_PRODUCT_PRODUCT_ID = "map_as_usual_product_product_id";

}
