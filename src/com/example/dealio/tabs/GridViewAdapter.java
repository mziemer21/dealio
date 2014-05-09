package com.example.dealio.tabs;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.dealio.R;

public class GridViewAdapter extends BaseAdapter{
	 // Declare Variables
    Context context;
    LayoutInflater inflater;
    ImageLoader imageLoader;
    private List<ImageList> picarraylist = null;
    private ArrayList<ImageList> arraylist;
 
    public GridViewAdapter(Context context, List<ImageList> picarraylist) {
        this.context = context;
        this.picarraylist = picarraylist;
        inflater = LayoutInflater.from(context);
        this.arraylist = new ArrayList<ImageList>();
        this.arraylist.addAll(picarraylist);
        imageLoader = new ImageLoader(context);
    }
 
    public class ViewHolder {
        ImageView pic;
    }
 
    @Override
    public int getCount() {
        return picarraylist.size();
    }
 
    @Override
    public Object getItem(int position) {
        return picarraylist.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.gridview_item, null);
            // Locate the ImageView in gridview_item.xml
            holder.pic = (ImageView) view.findViewById(R.id.picture);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Load image into GridView
        imageLoader.DisplayImage(picarraylist.get(position).getPicture(),
                holder.pic);
        // Capture GridView item click
        view.setOnClickListener(new OnClickListener() {
 
            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(context, SingleItemView.class);
                // Pass all data 
                intent.putExtra("picture", picarraylist.get(position)
                        .getPicture());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
