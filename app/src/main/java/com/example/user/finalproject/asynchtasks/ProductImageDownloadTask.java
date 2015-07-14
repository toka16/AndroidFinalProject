package com.example.user.finalproject.asynchtasks;

import android.os.AsyncTask;

import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class ProductImageDownloadTask extends AsyncTask<Void, Void, ArrayList<Product> > {

    private ArrayList<Product> products;

    public ProductImageDownloadTask(ArrayList<Product> productList){
        products = productList;
    }

    @Override
    protected ArrayList<Product> doInBackground(Void... params) {
        System.out.println("products background: "+products.size());
        for (int i = 0; i < products.size(); i++){
            Product product = products.get(i);
            System.out.println("product: "+product.getImage_link());
            if(product.getProductImage() == null && product.getImage_link() != null) {
                System.out.println("downloading product image: "+product.getImage_link());
                String imageURL = product.getImage_link();
                byte[] image = downloadImage(imageURL);
                product.setProductImage(image);
                System.out.println("downloaded image: "+image);
                DBHelper.getInstance(null).updateProductImage(product.getDb_ID(), image);
            }
        }
        return products;
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

}
