package com.thelearnedfriends.thelearnedfriends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.thelearnedfriends.thirdparty.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by portalservices on 30/08/16.
 */
public class ProfileFragment extends Fragment
{
    private static WebServiceCall wsc;
    private String jsonResponse;
    private JSONObject jsonobject;
    private JSONArray contacts = null;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private Intent intent;
    private MaterialDialog dialog;

    private RoundedImageView profileImage;
    private RoundedImageView profileImageNav;
    private TextView nameTxt;
    private TextView dateTxt;

    public ProfileFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImage = (RoundedImageView) rootView.findViewById(R.id.profileImg);
        nameTxt = (TextView) rootView.findViewById(R.id.nameTxt);
        dateTxt = (TextView) rootView.findViewById(R.id.dateTxt);

        // Inflate the layout for this fragment
        String mypic = "";
        mypic = preferences.getString("MYPIC","0");
        if (!mypic.equals("0"))
        {
            loadpicture(mypic);
            System.out.println("mypic path :::: "+mypic);
        }

        loadProfile();

        return rootView;

    }


    public void loadProfile()
    {

        System.out.println("::: loadProfile ::");

        dialog = new MaterialDialog.Builder(getContext())
                .iconRes(R.drawable.logo)
                .title("Loading")
                .content("Please wait ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();

        jsonResponse = null;
        final String token = preferences.getString("TOKEN", "0");

        Thread background = new Thread()
        {
            public void run()
            {
                try {
                    //Your code goes here
                    wsc = new WebServiceCall(getActivity());
                    jsonResponse = wsc.profile(token);
                    dialog.dismiss();
                    processResult();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        background.start();
        background = null;

    }


    public void processResult()
    {

        System.out.println("processResult :::: " + jsonResponse);

        int code = 0;
        String descr = "";

        try
        {
            jsonobject = new JSONObject(jsonResponse);

            code = jsonobject.getInt("status_code");
            descr = jsonobject.getString("message");

            System.out.println("\n\n code is :::: " + code);
            System.out.println("\n\n description is :::: " + descr);

            if (code == 400002)
            {
                String data = jsonobject.getString("data");
                JSONObject jsonDataObj = new JSONObject(data);

                String data1 = jsonDataObj.getString("avatar");
                JSONObject jsonDataObj1 = new JSONObject(data1);

                final String image = jsonDataObj1.getString("src");

                String data2 = jsonDataObj.getString("fields");
                JSONObject jsonDataObj2 = new JSONObject(data2);

                String data21 = jsonDataObj2.getString("Name");
                JSONObject jsonDataObj21 = new JSONObject(data21);

                final String name = jsonDataObj21.getString("value");

                String data22 = jsonDataObj2.getString("Date of Birth");
                JSONObject jsonDataObj22 = new JSONObject(data22);

                final String date = jsonDataObj22.getString("value");

                System.out.println("\n\n image is :::: " + image);
                System.out.println("\n\n name is :::: " + name);
                System.out.println("\n\n date is :::: " + date);


                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //stuff that updates ui
                        if (image.startsWith("//www.gravatar.com"))
                        {
                            profileImage.setImageResource(R.drawable.ic_profile);
                        }
                        else
                        {
                            new DownloadTask(profileImage,"1", getActivity()).execute(image);
                        }

                        nameTxt.setText(name);
                        dateTxt.setText(date);
                    }
                });

            }
            else
            {
                display(code, descr);
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

        getActivity().runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                //stuff that updates ui
                Toast.makeText(getActivity(), descr, Toast.LENGTH_LONG).show();
                if (type == 600003)
                {
                    getActivity().getSharedPreferences("MyPreferences", 0).edit().clear().commit();

                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void loadpicture(String mypic)
    {
        String selectedImagePath = mypic;

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        editor.putString("MYPIC", selectedImagePath);
        editor.commit();

        profileImage.setImageBitmap(bm);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void signin(View v)
    {

    }
}
