package com.thelearnedfriends.thelearnedfriends;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by portalservices on 23/08/16.
 */

public class TimelineFragment extends ListFragment implements AbsListView.OnScrollListener
{

    private static WebServiceCall wsc;
    private String jsonResponse;
    private JSONObject jsonobject;
    private JSONArray contacts = null;
    ArrayList<HashMap<String, String>> contactList;
    private Intent intent;
    private MaterialDialog dialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private ListView lv;
    private Boolean userScrolled = false;

    private int page = 0;
    private int preLast;

    public TimelineFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        preferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        View rootView = inflater.inflate(R.layout.fragment_timeline, container, false);

        contactList = new ArrayList<HashMap<String, String>>();

        loadList();

        return rootView;

    }


    @Override
    public void onViewCreated (View view, Bundle savedInstanceState)
    {
        System.out.println("::: am here ::");
        lv = getListView();
        lv.setOnScrollListener(this);

    }


    @Override
    public void onScroll(AbsListView lw, final int firstVisibleItem,
                         final int visibleItemCount, final int totalItemCount) {

        switch(lw.getId())
        {
            case android.R.id.list:

                // Make your calculation stuff here. You have all your
                // needed info from the parameters of this function.

                // Sample calculation to determine if the last
                // item is fully visible.
                final int lastItem = firstVisibleItem + visibleItemCount;
                if(lastItem == totalItemCount)
                {
                    if(preLast!=lastItem)
                    { //to avoid multiple calls for last item
                        System.out.println("::: last last last ::");
                        //Log.d("Last", "Last");
                        preLast = lastItem;
                        loadList();
                    }
                }
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState)
    {
        //
    }


    public void loadList()
    {
        System.out.println("::: timeline ::");

        dialog = new MaterialDialog.Builder(getContext())
                .iconRes(R.drawable.logo)
                .title("Processing")
                .content("Please wait ...")
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .show();


        jsonResponse = null;

        final String token = preferences.getString("TOKEN", "0");
        page += 1;
        System.out.println("::: page in time ::"+page);
        final String pageS = Integer.toString(page);
        final String per_page = "20";
        final String activity_user_id = "";

        Thread background = new Thread()
        {
            public void run()
            {
                try {
                    //Your code goes here
                    wsc = new WebServiceCall(getActivity());
                    jsonResponse = wsc.users();
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


    public void processResult() {

        System.out.println("processResult :::: " + jsonResponse);

        int code = 0;
        String descr = "";

        try {
            jsonobject = new JSONObject(jsonResponse);


            JSONArray jsonArray = jsonobject.getJSONArray("items");
            System.out.println("items :::: " + jsonArray);

            for (int i = 0; i < jsonArray.length(); i++)
            {
                HashMap<String, String> contact = new HashMap<String, String>();

                JSONObject jsonDataObj = jsonArray.getJSONObject(i);
                String avatar = jsonDataObj.getString("avatar_url");
                String name = jsonDataObj.getString("login");

                contact.put("avatar", avatar);
                contact.put("name", name);
                contact.put("time", "");
                contact.put("action", "");

                contactList.add(contact);

            }

            //display(1,"Your Have successfully logged in!");


        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
            System.out.println("Error passing JSON String");
        }

        System.out.println("\n\n contactList :::: " + contactList);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //stuff that updates ui
                ListAdapter adapter = new MySimpleAdapter(
                        getActivity(),
                        contactList,
                        R.layout.list_item,
                        new String[]{"avatar", "name", "time", "action"},
                        new int[]{R.id.avatar, R.id.name, R.id.time, R.id.action});
                TimelineFragment.this.setListAdapter(adapter);
            }
        });

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


    @Override
    public void onDestroy()
    {
        super.onDestroy();

        if(wsc != null)
            wsc = null;
    }

}
