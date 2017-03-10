package com.thelearnedfriends.thirdparty;

/**
 * Created by Ravi on 29/07/15.
 */
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.thelearnedfriends.thelearnedfriends.R;

import java.util.Collections;
import java.util.List;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder>
{
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    private Typeface myTypeface;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data)
    {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

        myTypeface = Typeface.createFromAsset(context.getAssets(), "etisalat2.TTF");

    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());

        switch (position)
        {
            case 0:
                holder.titleImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_myaccount_24dp));
                break;
            case 1:
                holder.titleImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_prepaid_24dp));
                break;
            case 2:
                holder.titleImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_dataservices_24dp));
                break;
            case 3:
                holder.titleImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_stores_24dp));
                break;
            case 4:
                holder.titleImg.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_support_24dp));
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView title;
        ImageView titleImg;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            titleImg = (ImageView) itemView.findViewById(R.id.titleImg);

            title.setTypeface(myTypeface);
            title.setTextSize(20.0f);
        }
    }
}
