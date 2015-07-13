package com.example.user.finalproject.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.finalproject.MainActivity;
import com.example.user.finalproject.R;
import com.example.user.finalproject.Server.ServerHelper;

public class Sign_Up_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        findViewById(R.id.registration_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.email_field)).getText().toString();
                String password = ((EditText)findViewById(R.id.password_field)).getText().toString();
                String first_name = ((EditText)findViewById(R.id.name_field)).getText().toString();
                String last_name = ((EditText)findViewById(R.id.last_name_field)).getText().toString();
                String primary_number = ((EditText)findViewById(R.id.primary_number_field)).getText().toString();
                String mobile_number = ((EditText)findViewById(R.id.mobile_field)).getText().toString();
                String card_number = ((EditText)findViewById(R.id.card_number_field)).getText().toString();

                if(email.equals("") ||
                        password.equals("") ||
                        first_name.equals("") ||
                        last_name.equals("") ||
                        primary_number.equals("") ||
                        mobile_number.equals("") ||
                        card_number.equals("")){
                    ((TextView)findViewById(R.id.register_status)).setText("All fields are required!!!");
                }else{
                    ServerHelper.ServerResponse response = ServerHelper.getInstance().register(email, password, first_name, last_name, primary_number, mobile_number, card_number);
                    if(response == ServerHelper.ServerResponse.BAD_REQUEST){
                        ((TextView)findViewById(R.id.register_status)).setText("Email already in use");
                    }else if(response == ServerHelper.ServerResponse.OK){
                        Intent intent = new Intent(Sign_Up_Activity.this, SpinnerActivity.class);
                        Sign_Up_Activity.this.startActivity(intent);
                    }else{
                        ((TextView)findViewById(R.id.register_status)).setText("Unknown error");
                    }
                }
            }
        });
    }
}
