package com.example.utkarsh.secureandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PermissionAdapter extends BaseAdapter {
    ArrayList<String> Ap = new ArrayList<String>();
    Context context;
    LayoutInflater layoutInflater;
    //  ArrayList<String> lockedApps = new ArrayList<String>();

    public PermissionAdapter(ArrayList<String> Ap, Context context) {

        this.Ap = Ap;
        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Ap.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        TextView tv;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        PermissionAdapter.ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.permission_list, null);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.per);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (PermissionAdapter.ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(Ap.get(position));


        return convertView;
    }
}


