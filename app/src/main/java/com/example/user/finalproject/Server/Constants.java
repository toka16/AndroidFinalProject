package com.example.user.finalproject.Server;

/**
 * Created by toka on 7/10/2015.
 */
public class Constants {
    private static final String HOST = "http://localhost:8080/AndroidFinalProjectServer/";



    public static final String URL_LOGIN = HOST + "webapi/user/login";
    public static final String URL_REGISTER = HOST + "webapi/user/register";

    public static final String URL_PRODUCTS_ALL = HOST + "webapi/product";
    public static final String URL_PRODUCTS_VERSION = HOST + "webapi/product/version";

    public static final String URL_NEWS_ALL = HOST + "webapi/news";
    public static final String URL_NEWS_VERSION = HOST + "webapi/news/version";

    public static final String URL_MENUS_ALL = HOST + "webapi/menu";
    public static final String URL_MENUS_VERSION = HOST + "webapi/menu/version";

    public static final String URL_CATEGORY_ALL = HOST + "webapi/category";
    public static final String URL_CATEGORY_VERSION = HOST + "webapi/category/version";

    public static final String URL_SHOP = HOST + "webapi/shop";
}
