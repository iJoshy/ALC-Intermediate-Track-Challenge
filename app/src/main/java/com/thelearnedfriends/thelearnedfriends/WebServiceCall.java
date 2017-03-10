
package com.thelearnedfriends.thelearnedfriends;

import android.app.ProgressDialog;
import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

public class WebServiceCall
{

    private String newUrl = "https://api.github.com/search/users";

    private HttpURLConnection conn;
    private ProgressDialog progress;
    private Context ctx;


    public WebServiceCall(Context context)
    {
        ctx = context;
    }

    public String login(String username, String password)throws InterruptedException, ExecutionException
    {
        String method = "wp_login";
        String requestData = "action=rtmedia_api&method="+method+"&username="+username+"&password="+password;
        System.out.println("body ::: "+requestData);

        return execute(newUrl,requestData);
    }


    public String signup(String displayname, String username, String email, String password)throws InterruptedException, ExecutionException
    {
        String method = "wp_register";
        String requestData = "action=rtmedia_api&method="+method+"&field_1="+displayname+"&signup_username="+username+"&signup_email="+email+"&signup_password="+password+"&signup_password_confirm="+password;

        System.out.println("body ::: "+requestData);

        return execute(newUrl,requestData);
    }


    public String forgot(String email)throws InterruptedException, ExecutionException
    {
        String method = "wp_forgot_password";
        String requestData = "action=rtmedia_api&method="+method+"&user_login="+email;

        System.out.println("body ::: "+requestData);

        return execute(newUrl,requestData);
    }


    public String getactivities(String token, String page, String per_page, String activity_user_id)throws InterruptedException, ExecutionException
    {
        String method = "bp_get_activities";
        String requestData = "action=rtmedia_api&method="+method+"&page="+page+"&per_page="+per_page+"&token="+token+"&activity_user_id="+activity_user_id;

        System.out.println("body ::: "+requestData);

        return execute(newUrl,requestData);
    }


    public String profile(String token)throws InterruptedException, ExecutionException
    {
        String method = "bp_get_profile";
        String requestData = "action=rtmedia_api&method="+method+"&token="+token;

        System.out.println("body ::: "+requestData);

        return execute(newUrl,requestData);
    }


    public String users()throws InterruptedException, ExecutionException
    {
        newUrl += "?q=language:java+location:lagos";
        String requestData = "";

        System.out.println("newUrl ::: "+newUrl);

        return execute(newUrl,requestData);
    }


    protected String execute(String params1, String params2)
    {
        InputStream is = null;
        OutputStream os = null;
        String result = "";

        try
        {
            URL url = new URL(params1);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setUseCaches(false);
            conn.setDoInput(true);

            // Starts the query
            conn.connect();


            int status = conn.getResponseCode();
            //System.out.println("status :::: "+status);

            if (status == 200)
            {
                is = conn.getInputStream();
                int len = conn.getContentLength();

                // Convert the InputStream into a string
                String contentAsString = readIt(is);
                //System.out.println("result ::: "+contentAsString);

                return contentAsString;
            }
            else
            {
                return "request could not be processed at this time, pls try gain later !";
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
        }
        finally
        {
            if(conn != null)
            {
                conn.disconnect();
            }
            newUrl = "";
        }

    }


    public String readIt(InputStream stream) throws IOException, UnsupportedEncodingException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        StringBuilder out = new StringBuilder();
        String newLine = System.getProperty("line.separator");
        String line;
        while ((line = reader.readLine()) != null)
        {
            out.append(line);
            out.append(newLine);
        }
        return out.toString();
    }

}
