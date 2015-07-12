package com.example.user.finalproject.asynchtasks;

import android.os.AsyncTask;

import com.example.user.finalproject.model.Menu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class MenuImageDownLoadTask extends AsyncTask<Void, Void, ArrayList<Menu> >{

    private ArrayList<Menu> menus;

    public MenuImageDownLoadTask(ArrayList<Menu> menuArrayList){
        menus = menuArrayList;
    }


    @Override
    protected ArrayList<Menu> doInBackground(Void... params) {
        for(int i = 0; i < menus.size(); i++){
            Menu menu = menus.get(i);
            String imageURL = menu.getImage_link();
            byte[] image = downloadImage(imageURL);
            menu.setMenuImage(image);
        }

        return menus;
    }

    private byte[] downloadImage(String imgURL) {
        byte[] image = null;
        try {
            URL url = new URL(imgURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query     
            conn.connect();
            image = readIt(conn.getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    /**
     * The method provides to get byte[] from inputStream.
     * @param stream - inputStream from url.
     * @return - image as byte[]
     * @throws java.io.IOException
     */
    private byte[] readIt(InputStream stream) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int size = 0;
        while ((size = stream.read(buffer)) > 0) {
            bos.write(buffer, 0, size);
        }
        return bos.toByteArray();
    }

    @Override
    protected  void onPostExecute(ArrayList<Menu> productArrayList){

    }
}
