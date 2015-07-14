package com.example.user.finalproject.Server;

import android.os.AsyncTask;

import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Category;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/**
 * Created by toka on 7/10/2015.
 */
public class ServerHelper {
    private String COOKIE;

    private static ServerHelper helper;
    private ServerHelper(String cookie){
        COOKIE = cookie;
    }
    private ServerHelper(){}

    public static ServerHelper getInstance(String cookie){
        if(helper == null)
            helper = new ServerHelper(cookie);

        return helper;
    }

    public static ServerHelper getInstance(){
        if(helper == null)
            helper = new ServerHelper();

        return helper;
    }

    public String getCookie(){
        return COOKIE;
    }

    public ServerResponse login(String username, String password) {
        final Map<String, String> data = new HashMap<>();
        data.put("email", username);
        data.put("password", sha256(password));
        AsyncTask<Object, Object, Integer> task = new AsyncTask<Object, Object, Integer>() {
            @Override
            protected Integer doInBackground(Object... params) {
                int res = -1;
                try {
                    res = doSubmit(Constants.URL_LOGIN, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res;
            }
        }.execute();
        try {
            int res = task.get();
            if (res == 200) {
                return ServerResponse.OK;
            } else if (res == 406) {
                return ServerResponse.INVALID_USERNAME_OR_PASSWORD;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ServerResponse.UNKNOWN;
    }

    public ServerResponse register(String email,
                                   String password,
                                   String firstName,
                                   String lastName,
                                   String primaryNumber,
                                   String mobileNumber,
                                   String cardNumber) {

        final Map<String, String> data = new HashMap<>();
        data.put("email", email);
        data.put("password", sha256(password));
        data.put("first_name", firstName);
        data.put("last_name", lastName);
        data.put("primary_number", primaryNumber);
        data.put("mobile_number", mobileNumber);
        data.put("card_number", cardNumber);

        AsyncTask<Object, Object, Integer> task = new AsyncTask<Object, Object, Integer>() {
            @Override
            protected Integer doInBackground(Object... params) {
                int res = -1;
                try {
                    res = doSubmit(Constants.URL_REGISTER, data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return res;
            }
        }.execute();
        try {
            int res = task.get();
            if (res == 200) {
                return ServerResponse.OK;
            } else if (res == 409) {
                return ServerResponse.BAD_REQUEST;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return ServerResponse.UNKNOWN;
    }

    public int getProductsVersion(){
        Response res = makeRequest(Constants.URL_PRODUCTS_VERSION, "GET", null);
        if(res == null || res.status != 200)
            return -1;

        return Integer.parseInt(res.text);
    }

    public ArrayList<Product> getProducts(){
        Response res = makeRequest(Constants.URL_PRODUCTS_ALL, "GET", null);
        if(res != null && res.status == 200) {
            try {
                return parseProductArray(new JSONArray(res.text));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getNewsVersion(){
        Response res = makeRequest(Constants.URL_NEWS_VERSION, "GET", null);
        if(res == null || res.status != 200)
            return -1;

        return Integer.parseInt(res.text);
    }

    public ArrayList<News> getNews(){
        Response res = makeRequest(Constants.URL_NEWS_ALL, "GET", null);
        if(res != null && res.status == 200){
            try {
                return parseNewsArray(new JSONArray(res.text));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getMenusVersion(){
        Response res = makeRequest(Constants.URL_MENUS_VERSION, "GET", null);
        if(res == null || res.status != 200)
            return -1;

        return Integer.parseInt(res.text);
    }

    public ArrayList<Menu> getMenus(){
        Response res = makeRequest(Constants.URL_MENUS_ALL, "GET", null);
        if(res != null && res.status == 200){
            try {
                return parseMenuArray(new JSONArray(res.text));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public int getCategoriesVersion(){
        Response res = makeRequest(Constants.URL_CATEGORY_VERSION, "GET", null);
        if(res == null || res.status != 200)
            return -1;

        return Integer.parseInt(res.text);
    }

    public ArrayList<Category> getCategories(){
        Response res = makeRequest(Constants.URL_CATEGORY_ALL, "GET", null);
        if(res != null && res.status == 200){
            try {
                return parseCategoryArray(new JSONArray(res.text));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public ServerResponse makeOrder(List<Product> products){
        JSONArray arr = new JSONArray();
        for(Product p : products)
            arr.put(p.getServer_ID());
        JSONObject obj = new JSONObject();

        try {
            obj.put("product_ids", arr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String request = obj.toString();
        Response res =  makeRequest(Constants.URL_SHOP, "POST", request);
        if(res == null)
            return ServerResponse.UNKNOWN;
        if(res.status == 401)
            return ServerResponse.UNAUTHORIZED;
        if(res.status == 200)
            return ServerResponse.OK;

        return ServerResponse.UNKNOWN;
    }

    public static ArrayList<Category> parseCategoryArray(JSONArray arr){
        if(arr == null)
            return null;
        ArrayList<Category> categories = new ArrayList<>();
        for(int i=0; i<arr.length(); i++){
            categories.add(parseCategory(arr.optJSONObject(i)));
        }
        return categories;
    }

    public static Category parseCategory(JSONObject obj){
        Category category = new Category();
        category.setServer_ID(obj.optInt("id"));
        category.setName(obj.optString("name"));
        int[] ids = parseIntArray(obj.optJSONArray("products"));
        category.setProducts(DBHelper.getInstance(null).getProductsByServerIDs(ids));

        return category;
    }

    public static ArrayList<News> parseNewsArray(JSONArray arr){
        if(arr == null)
            return null;
        ArrayList<News> newses = new ArrayList<>();
        for(int i=0; i<arr.length(); i++){
            newses.add(parseNews(arr.optJSONObject(i)));
        }
        return newses;
    }

    public static News parseNews(JSONObject obj){
        News news = new News();
        news.setServer_ID(obj.optInt("id"));
        news.setName(obj.optString("name"));
        news.setDescription(obj.optString("description"));
        news.setFromDate(new Date(obj.optLong("from_date")).toString());
        news.setToDate(new Date(obj.optLong("to_date")).toString());
        return news;
    }

    public static ArrayList<Menu> parseMenuArray(JSONArray arr){
        if(arr == null)
            return null;
        ArrayList<Menu> menus = new ArrayList<>();
        for(int i=0; i<arr.length(); i++){
            menus.add(parseMenu(arr.optJSONObject(i)));
        }
        return menus;
}

    public static Menu parseMenu(JSONObject obj){
        Menu menu = new Menu();
        menu.setServer_ID(obj.optInt("id"));
        menu.setName(obj.optString("name"));
        menu.setDescription(obj.optString("description"));
        menu.setPrice(obj.optDouble("price"));
        menu.setImage_link(obj.optString("image_link"));
        int[] ids = parseIntArray(obj.optJSONArray("products"));
        menu.setProducts(DBHelper.getInstance(null).getProductsByServerIDs(ids));
        System.out.println("new menu: "+menu.getName());
        for(int i : ids){
            System.out.println("product id: "+i);
        }
        return menu;
    }

    public static ArrayList<Product> parseProductArray(JSONArray arr){
        if(arr == null)
            return null;
        ArrayList<Product> products = new ArrayList<>();
        for(int i=0; i<arr.length(); i++){
            products.add(parseProduct(arr.optJSONObject(i)));
        }
        return products;
    }

    public static Product parseProduct(JSONObject obj){
        Product product = new Product();
        product.setServer_ID(obj.optInt("id"));
        product.setName(obj.optString("name"));
        product.setDescription(obj.optString("description"));
        product.setPrice(obj.optDouble("price"));
        product.setImage_link(obj.optString("image_link"));
        System.out.println("downloaded product image link: "+product.getImage_link());
        return product;
    }

    public static int[] parseIntArray(JSONArray arr){
        if(arr == null)
            return null;
        int[] res = new int[arr.length()];
        for(int i=0; i<arr.length(); i++){
            res[i] = arr.optInt(i);
        }
        return res;
    }

    private Response makeRequest(final String link, final String type, final String request){
        AsyncTask<Void, Void, Response> task = new AsyncTask<Void, Void, Response>() {
            @Override
            protected Response doInBackground(Void... params) {
                try {
                    URL url = new URL(link);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestProperty("Cookie", COOKIE);
                    conn.setUseCaches(true);
                    conn.setDoInput(true);
                    if(request != null){
                        conn.setDoOutput(true);
                        OutputStream out = conn.getOutputStream();
                        out.write(request.getBytes());
                        out.flush();
                        out.close();
                    }

                    Response res = new Response();
                    res.status = conn.getResponseCode();
                    StringBuilder builder = new StringBuilder();
                    if(conn.getResponseCode() == 200){
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        String line;
                        while((line = in.readLine()) != null)
                            builder.append(line);

                        in.close();
                        res.text = builder.toString();
                        return res;
                    }
                } catch (MalformedURLException ex) {
                    ex.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        task.execute();
        Response res = null;
        try {
            res = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return res;
    }

    private static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private int doSubmit(String url, Map<String, String> data) throws IOException {
        URL siteUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) siteUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        conn.setUseCaches(true);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        Set keys = data.keySet();
        Iterator keyIter = keys.iterator();
        String content = "";
        for (int i = 0; keyIter.hasNext(); i++) {
            Object key = keyIter.next();
            if (i != 0) {
                content += "&";
            }
            content += key + "=" + URLEncoder.encode(data.get(key), "UTF-8");
        }
        out.writeBytes(content);
        out.flush();
        out.close();
        COOKIE = conn.getHeaderField("Set-Cookie");
        return conn.getResponseCode();
    }

    private static class Response{
        int status;
        String text;
    }

    public static enum ServerResponse{
        OK,
        UNAUTHORIZED,
        UNKNOWN,
        INVALID_USERNAME_OR_PASSWORD,
        BAD_REQUEST
    }

}
