package com.thelearnedfriends.thelearnedfriends;

/**
 * Created by portalservices on 04/12/15.
 */


import android.content.Context;
import android.os.AsyncTask;

import com.afollestad.materialdialogs.MaterialDialog;

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

public class WebServiceAD extends AsyncTask<String, Void, String>
{
    private String newUrl = "http://thelearnedfriends.com/wp-admin/admin-ajax.php";
    private int requester;
    private Context ctx;
    private HttpURLConnection conn;

    private MaterialDialog dialog;

    public WebServiceAD(Context context)
    {
        ctx = context;
    }

    public String getactivities(String token, String page, String per_page, String activity_user_id)throws InterruptedException, ExecutionException
    {
        String method = "bp_get_activities";
        String requestData = "action=rtmedia_api&method="+method+"&page="+page+"&per_page="+per_page+"&token="+token+"&activity_user_id="+activity_user_id;

        System.out.println("body ::: "+requestData);

        return execute(newUrl,requestData).get();
    }


    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    protected String doInBackground(String... params)
    {
        InputStream is = null;

        try
        {

            String urlParameters  = params[1];
            byte[] postDataBytes = urlParameters.getBytes(Charset.forName("UTF-8"));
            int postDataLength = postDataBytes.length;


            URL url = new URL(params[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput( true );
            conn.setInstanceFollowRedirects( false );
            conn.setRequestMethod( "POST" );
            conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty( "charset", "utf-8");
            conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            conn.setUseCaches( false );
            conn.getOutputStream().write(postDataBytes);

            // Starts the query
            //conn.connect();

            int status = conn.getResponseCode();

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

    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);
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
