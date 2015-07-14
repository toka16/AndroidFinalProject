package com.example.user.finalproject.Activities;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.finalproject.App;
import com.example.user.finalproject.R;
import com.example.user.finalproject.Server.ServerHelper;

public class Log_in_Activity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);

        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = ((EditText)findViewById(R.id.mail_txt)).getText().toString();
                String password = ((EditText)findViewById(R.id.password_txt)).getText().toString();
                if(email.equals("") || password.equals("")){
                    ((TextView)findViewById(R.id.login_status)).setText("All fields are required!!!");
                }else{
                    ServerHelper.ServerResponse response = ServerHelper.getInstance().login(email, password);
                    if(response == ServerHelper.ServerResponse.OK){
                        String newCookie = ServerHelper.getInstance().getCookie();
                        SharedPreferences pref = getSharedPreferences(App.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString(SpinnerActivity.SHARED_PREFERENCES_COOKIE, newCookie);
                        editor.commit();
                        Intent intent = new Intent(Log_in_Activity.this, SpinnerActivity.class);
                        Log_in_Activity.this.startActivity(intent);
                    }else if(response == ServerHelper.ServerResponse.INVALID_USERNAME_OR_PASSWORD){
                        ((TextView)findViewById(R.id.login_status)).setText("Invalid Username or Password");
                    }else{
                        ((TextView)findViewById(R.id.login_status)).setText("Unknown error");
                    }
                }
            }
        });

        findViewById(R.id.sign_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Log_in_Activity.this, Sign_Up_Activity.class);
                Log_in_Activity.this.startActivity(intent);
            }
        });

    }
}
