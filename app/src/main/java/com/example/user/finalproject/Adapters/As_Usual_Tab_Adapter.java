package com.example.user.finalproject.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.user.finalproject.R;
import com.example.user.finalproject.model.As_Usual;

import java.util.ArrayList;


public class As_Usual_Tab_Adapter extends BaseAdapter {

    private Context context;
    private ArrayList<As_Usual> as_Usuals;

    public As_Usual_Tab_Adapter(Context context , ArrayList<As_Usual> as_Usuals) {
        this.context = context;
        this.as_Usuals = as_Usuals;
    }

    @Override
    public int getCount() {
        return this.as_Usuals.size();
    }

    @Override
    public Object getItem(int position) {
        return this.as_Usuals.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.as_usual_list_view_item, null);
            Holder h = new Holder();

            h.txtV = (TextView)convertView.findViewById(R.id.text_as_usual);
            convertView.setTag(h);
        }
        Holder h = (Holder)convertView.getTag();
        As_Usual info = this.as_Usuals.get(position);

        h.txtV.setText(info.getName());
        return convertView;
    }

    public static class Holder {
        TextView txtV;
    }
}
