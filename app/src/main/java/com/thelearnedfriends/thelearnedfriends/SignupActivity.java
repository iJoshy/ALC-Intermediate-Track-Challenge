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

public class SignupActivity extends AppCompatActivity
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
        setContentView(R.layout.activity_signup);
    }

    public void signin(View v)
    {
        System.out.println("::: Signed in ::");

        intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);

        finish();
    }

    public void signup(View v)
    {
        System.out.println("::: signup ::");

        EditText displaynameTxt = (EditText) findViewById(R.id.displaynameTxt);
        EditText usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        EditText emailTxt = (EditText) findViewById(R.id.emailTxt);
        EditText passowrdTxt = (EditText) findViewById(R.id.passowrdTxt);

        final String displayname = displaynameTxt.getText().toString();
        final String username = usernameTxt.getText().toString();
        final String email = emailTxt.getText().toString();
        final String password = passowrdTxt.getText().toString();

        System.out.println("::: displayname ::"+displayname);
        System.out.println("::: username ::"+username);
        System.out.println("::: email ::"+email);
        System.out.println("::: password ::"+password);

        if(!(displayname.equals("") && username.equals("") && email.equals("") && password.equals("")))
        {
            //goHome(displayname,username,email,password);
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