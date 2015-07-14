package com.example.user.finalproject.Adapters;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.finalproject.R;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;

public class Menu_Tab_Adapter extends BaseAdapter{
    private Context context;
    private ArrayList<Menu> products;

    public Menu_Tab_Adapter(Context context, ArrayList<Menu> arr) {
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
            convertView = View.inflate(context, R.layout.menu_list_view_item, null);
            Holder h = new Holder();

            h.txtV = (TextView)convertView.findViewById(R.id.menu_text);
            h.img = (ImageView)convertView.findViewById(R.id.default_img_for_menu);
            h.price = (TextView)convertView.findViewById(R.id.menu_price);
            // aqve suratis setic unda gauketo
            convertView.setTag(h);
        }
        Holder h = (Holder)convertView.getTag();
        Menu info = products.get(position);
        h.txtV.setText(info.getName());
        h.price.setText(info.getPrice()+" $");

        //aqac suratis set imis mixedvit rogorc manuka gaaketebs
        //h.img.setImageBitmap(info);
        byte[] menuImage = info.getMenuImage();
        if(menuImage != null)
            h.img.setImageBitmap(BitmapFactory.decodeByteArray(menuImage, 0, menuImage.length));
        return convertView;
    }

    public static class Holder {
        TextView txtV;
        TextView price;
        ImageView img;
    }
}
