package com.thelearnedfriends.thelearnedfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joshuabalogun on 8/19/16.
 */

public class ForgotpasswordActivity extends AppCompatActivity
{

    private Intent intent;
    private WebServiceCall wsc;
    private JSONObject jsonobject;
    private String jsonResponse;
    private MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
    }

    public void signin(View v)
    {
        System.out.println("::: Signed in ::");

        intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);

        finish();
    }

    public void send2email(View v)
    {
        System.out.println("::: Send to email ::");

        EditText emailTxt = (EditText) findViewById(R.id.emailTxt);

        final String email = emailTxt.getText().toString();

        System.out.println("::: email ::"+email);

        if(!(email.equals("")))
        {
            //goLogin(email);
        }
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(wsc != null)
            wsc = null;
    }

}
