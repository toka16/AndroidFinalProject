package com.example.user.finalproject.Adapters;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.finalproject.R;
import com.example.user.finalproject.model.Product;
import java.util.ArrayList;

public class Product_Tab_Adapter extends BaseAdapter{

    private Context context;
    private ArrayList<Product> products;

    public Product_Tab_Adapter(Context context, ArrayList<Product> arr) {
        this.context = context;
        this.products = arr;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int position) {
        return products.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.product_list_view_item, null);
            Holder h = new Holder();

            h.txtV = (TextView)convertView.findViewById(R.id.product_text);
            h.img = (ImageView)convertView.findViewById(R.id.default_img);
            h.price = (TextView)convertView.findViewById(R.id.product_price);
            convertView.setTag(h);
        }
        Holder h = (Holder)convertView.getTag();
        Product info = products.get(position);
        h.txtV.setText(info.getName());
        h.price.setText(info.getPrice()+" $");
        //aqac suratis set imis mixedvit rogorc manuka gaaketebs
        //h.img.setImageBitmap(info);
        byte[] productImage = info.getProductImage();
        if(productImage != null)
            h.img.setImageBitmap(BitmapFactory.decodeByteArray(productImage, 0, productImage.length));
        return convertView;
    }

    public static class Holder {
        TextView txtV;
        TextView price;
        ImageView img;
    }
}
