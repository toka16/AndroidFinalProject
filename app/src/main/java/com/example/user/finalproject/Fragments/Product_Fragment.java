package com.example.user.finalproject.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.finalproject.Activities.Basket_Activity;
import com.example.user.finalproject.Activities.Categories_List_Activity;
import com.example.user.finalproject.Activities.Deletable_Product_List_Activity;
import com.example.user.finalproject.Adapters.Product_Tab_Adapter;
import com.example.user.finalproject.R;
import com.example.user.finalproject.database.DBHelper;
import com.example.user.finalproject.model.Category;
import com.example.user.finalproject.model.Menu;
import com.example.user.finalproject.model.News;
import com.example.user.finalproject.model.Product;

import java.text.ParseException;
import java.util.ArrayList;

public class Product_Fragment extends Fragment {

    // buttonis damateba momiwevs rom es ragc chavagdo basketshi danarcheni jigruladaa iyoss informaciis gmaotana ise rogor gmaogaqvs
    // anu selected rom basketshi anu selected indexis sheotana isev mogiwevs ganakomentareb da
    // romelic aris selectied gaushveb imas basketshi easy as hell

    //    private boolean clicked;
    private LayoutInflater inf;
    private View view;

    private ArrayList<Product> products;
    private ArrayList<Product> basket_products;
    private Product selected_product;
    private int selected_item_index;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        clicked = false;
        view = inflater.inflate(R.layout.product_list_view, container, false);
        selected_item_index = -1;
        ListView productsListView = (ListView)view.findViewById(R.id.list_view_for_products);
        inf = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        productsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_product = products.get(position);
                Toast toast = Toast.makeText(inf.getContext(), selected_product.getDescription(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER|Gravity.CENTER, 0, 0);
                toast.show();

                TextView temp = (TextView)view.findViewById(R.id.chosen_product_text);
                temp.setText("მონიშნული პროდუქტი: " + selected_product.getName());

            }
        });

        // aqac bazidan unda sheavso
        basket_products = new ArrayList<>();

        DBHelper bla =  DBHelper.getInstance(inf.getContext());

        insertElements();

        products = (ArrayList) bla.allProducts();
        Product_Tab_Adapter adapter = new Product_Tab_Adapter(inf.getContext(),products);
        productsListView.setAdapter(adapter);

        Button add_to_basket = (Button) view.findViewById(R.id.add_to_basket);
        add_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (selected_item_index < 0) {
                    Toast toast;
                    toast = Toast.makeText(inf.getContext(), "You haven`t marked Product yet", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }

                basket_products.add(products.get(selected_item_index));

                // !!!!!!!!! tableshic udna chaagdo basketis


                selected_item_index = -1;
                TextView temp = (TextView)view.findViewById(R.id.chosen_product_text);
                temp.setText("მონიშნული პროდუქტი: ");
            }
        });

        Button go_to_basket = (Button) view.findViewById(R.id.go_to_basket);
        go_to_basket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // egreve basketis intents davustartav

                Intent intent = new Intent(getActivity(),Basket_Activity.class);
                startActivity(intent);

            }
        });

        Button categories = (Button) view.findViewById(R.id.categories);
        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // egreve categories intents davustartav

                Intent intent = new Intent(getActivity(), Categories_List_Activity.class);
                startActivity(intent);

            }
        });


        return view;
    }

    private void insertElements(){
        DBHelper bla =  DBHelper.getInstance(inf.getContext());
        Product temp = new Product("burger axali","gemrielia simon");
        bla.insertNewProduct(temp);

        Product temp7 = new Product("stake"," delicious");
        bla.insertNewProduct(temp7);

        Product temp2 = new Product("nayini axali","esec gemrielia simon");
        bla.insertNewProduct(temp2);

        Product temp3 = new Product("free axali","ramdens cham ra ubedurebaa");
        bla.insertNewProduct(temp3);

        Category cat = new Category();
        cat.setName("xorciani sachmelebi");
        cat.getProducts().add(temp7);
        cat.getProducts().add(temp);

        bla.insertNewCategory(cat);

        Menu menu = new Menu();
        menu.setName("sauzme");
        menu.getProducts().add(temp2);
        menu.getProducts().add(temp3);

        bla.insertNewMenu(menu);

        News news = new News();
        news.setName("siaxlea gvewviet ra ");
        news.setDescription("viketebit rame tu ar iyide ");

//        try {
//            bla.insertNews(news);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
