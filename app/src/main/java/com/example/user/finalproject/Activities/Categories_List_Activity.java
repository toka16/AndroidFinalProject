package com.example.user.finalproject.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.finalproject.Adapters.Category_Adapter;
import com.example.user.finalproject.Adapters.Product_Tab_Adapter;
import com.example.user.finalproject.R;
import com.example.user.finalproject.model.Category;
import com.example.user.finalproject.model.Product;

import java.util.ArrayList;

public class Categories_List_Activity extends ActionBarActivity {



    private ArrayList<Category> categories ;
    private ListView categorieListView;
    public static Category_Adapter adapter;


    private Category selected_Category;
    private int selected_item_index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_list_view);

        selected_item_index = -1;
        categorieListView = (ListView) findViewById(R.id.list_view_for_categories);

        // itogshi aq bazidan unda davitrio es arraylist
        // !!!!!!!!!!!!!!!!

        categories = new ArrayList<>();

        adapter = new Category_Adapter(this,categories);
        adapter.notifyDataSetChanged();
        categorieListView.setAdapter(adapter);



        categorieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapter, View v, int position, long id) {
                selected_item_index = position;
                selected_Category = categories.get(position);
                TextView temp = (TextView)findViewById(R.id.chosen_category_name);
                temp.setText("არჩეული კატეგორია: " + selected_Category.getName());
            }
        });



        Button go_to_detailed_category = (Button) findViewById(R.id.go_for_category_details);
        go_to_detailed_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                if(selected_item_index < 0){
//                    Toast toast;
//                    toast = Toast.makeText(getApplicationContext(), "You haven`t marked category yet", Toast.LENGTH_LONG);
//                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 0);
//                    toast.show();
//                    return;
//                }

                //!!!!!!!!!!!!!!!!!!!!!!!!!!!


                // axal intents davstartav da gavatan category_ID-s
                Intent intent = new Intent(getApplicationContext(),Category_Products_Activity.class);
                startActivity(intent);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
