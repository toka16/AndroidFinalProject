package com.example.user.finalproject.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.user.finalproject.Adapters.Product_Tab_Adapter;
import com.example.user.finalproject.R;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;

public class Product_Fragment extends Fragment {

    // buttonis damateba momiwevs rom es ragc chavagdo basketshi danarcheni jigruladaa iyoss informaciis gmaotana ise rogor gmaogaqvs
    // anu selected rom basketshi anu selected indexis sheotana isev mogiwevs ganakomentareb da
    // romelic aris selectied gaushveb imas basketshi easy as hell

    //    private boolean clicked;
    private LayoutInflater inf;


    private ArrayList<Product> products;
    private Product selected_product;
 //   private int selected_item_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        clicked = false;
        View view = inflater.inflate(R.layout.product_list_view, container, false);
//        selected_item_index = -1;
        ListView productsListView = (ListView)view.findViewById(R.id.list_view_for_products);
        inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
  //              selected_item_index = position;
                selected_product = products.get(position);
                Toast toast = Toast.makeText(inf.getContext(), selected_product.getDescription(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();

            }
        });
        products = fromBase();
        Product_Tab_Adapter adapter = new Product_Tab_Adapter(inf.getContext(),products);
        productsListView.setAdapter(adapter);
        return view;
    }

    private ArrayList<Product> fromBase(){

        ArrayList<Product> result = new ArrayList<>();

        // aq realurad basidan unda wamovigot es productebi


        Product temp = new Product("burger","gemrielia simon");
        result.add(temp);

        Product temp2 = new Product("nayini","esec gemrielia simon");
        result.add(temp2);

        Product temp3 = new Product("free","ramdens cham ra ubedurebaa");
        result.add(temp3);
        return result;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
