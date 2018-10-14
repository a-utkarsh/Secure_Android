package com.example.utkarsh.secureandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AnalyzerCustomAdapter extends BaseAdapter{

    ArrayList<String> Al =  new ArrayList<String>();
    Context context;
    LayoutInflater layoutInflater;
    //  ArrayList<String> lockedApps = new ArrayList<String>();

    public AnalyzerCustomAdapter(ArrayList<String> Al , Context context) {

        this.Al =  Al;
        this.context = context;
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Al.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        TextView tv;


    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null) {
            convertView=layoutInflater.inflate(R.layout.list_item, null);
            viewHolder.tv= (TextView) convertView.findViewById(R.id.abc);
            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(Al.get(position));

        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });



        return convertView;
    }

    public ArrayList<String> locked() {


        return Al;

    }

}

