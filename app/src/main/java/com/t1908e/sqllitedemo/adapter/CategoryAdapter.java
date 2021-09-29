package com.t1908e.sqllitedemo.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.t1908e.sqllitedemo.R;
import com.t1908e.sqllitedemo.entity.Category;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {
    private List<Category> listCategories;
    private Activity activity;

    public CategoryAdapter(List<Category> listContacts, Activity activity) {
        this.listCategories = listContacts;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return listCategories.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            //init item view == contact_item when first item call this method
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.item_category, viewGroup, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.txtName = (TextView) view.findViewById(R.id.tvCateName);
            view.setTag(viewHolder);
        }
        //set data for item view when it is initialized
        ViewHolder holder = (ViewHolder) view.getTag();
        Category contact = listCategories.get(i);
        holder.txtName.setText(contact.getName());

        return view;
    }

    static class ViewHolder {
        TextView txtName;
    }
}
