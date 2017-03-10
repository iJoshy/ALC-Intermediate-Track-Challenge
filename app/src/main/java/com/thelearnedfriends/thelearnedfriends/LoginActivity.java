package com.thelearnedfriends.thelearnedfriends;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity
{

    private Intent intent;
    private WebServiceCall wsc;
    private JSONObject jsonobject;
    private String jsonResponse;
    private MaterialDialog dialog;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        String loggedin = preferences.getString("LOGGEDIN","0");
        if(loggedin.equals("1"))
        {
            intent = new Intent(getBaseContext(), MainActivity.class);
            startActivity(intent);
        }

    }

    public void signin(View v)
    {
        System.out.println("::: Signed in ::");

        EditText usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        EditText passwordTxt = (EditText) findViewById(R.id.passwordTxt);

        final String username = usernameTxt.getText().toString();
        final String password = passwordTxt.getText().toString();

        System.out.println("::: username ::"+username);
        System.out.println("::: password ::"+password);

        /*
        if(!(username.equals("") && password.equals("")))
        {
            goLogin(username,password);
        }
        */

        goLogin("username","password");

    }

    public void goLogin(final String username, final String password)
    {
        System.out.println("::: signup ::");

        display(1,"Your Have successfully logged in!");

        /*
        dialog = new MaterialDialog.Builder(this)
                .iconRes(R.drawable.logo)
                .title("Verifying")
                .content("Please wait ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();

        jsonResponse = null;

        Thread background = new Thread()
        {
            public void run()
            {
                try
                {
                    //Your code goes here
                    wsc = new WebServiceCall(getBaseContext());
                    jsonResponse = wsc.login(username,password);
                    dialog.dismiss();
                    processResult();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        };

        background.start();
        background = null;
        */
    }

    public void processResult()
    {
        System.out.println("processResult :::: "+jsonResponse);

        int code = 0;
        String descr = "";

        try
        {
            jsonobject = new JSONObject(jsonResponse);

            code = jsonobject.getInt("status_code");
            descr = jsonobject.getString("message");

            //code = 200;
            //descr = "Successful";

            System.out.println("\n\n code is :::: " +code);
            System.out.println("\n\n description is :::: "+descr);

            if (code == 200004)
            {
                String data = jsonobject.getString("data");
                JSONObject jsonDataObj = new JSONObject(data);
                String token = jsonDataObj.getString("access_token");
                System.out.println("\n\n token is :::: "+token);

                editor.putString("TOKEN", token);
                editor.putString("LOGGEDIN", "1");
                editor.commit();

                display(1,"Your Have successfully logged in!");
            }
            else
            {
                display(0,descr);
            }

        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("Error passing JSON String");
        }

    }

    public void display(final int type, final String descr)
    {

        LoginActivity.this.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this, descr, Toast.LENGTH_LONG).show();

                        if (type == 1) {
                            intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
        );


        //intent = new Intent(getBaseContext(), MainActivity.class);
        //startActivity(intent);

    }


    public void signup(View v)
    {
        System.out.println("::: signup ::");

        intent = new Intent(getBaseContext(), SignupActivity.class);
        startActivity(intent);

    }

    public void forgot(View v)
    {
        System.out.println("::: forgot password ::");

        intent = new Intent(getBaseContext(), ForgotpasswordActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(wsc != null)
            wsc = null;
    }
}
