package com.example.utkarsh.secureandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

class MyCustomAdapter extends BaseAdapter{

    ArrayList<String> icon=  new ArrayList<>();
    ArrayList<String> apps =  new ArrayList<String>();
    Context context;
    LayoutInflater layoutInflater;
    ArrayList<String> lockedApps = new ArrayList<String>();

    public MyCustomAdapter(ArrayList<String> apps , Context context) {

        this.apps =  apps;
        this.context = context;
        layoutInflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return apps.size();
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
        Switch aswitch;
        ImageView iv;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();
        if(convertView==null) {
            convertView=layoutInflater.inflate(R.layout.mylist, null);
            viewHolder.tv= (TextView) convertView.findViewById(R.id.title);
            viewHolder.aswitch = (Switch) convertView.findViewById(R.id.aswitch);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.img);
            convertView.setTag(viewHolder);

        }
        else
        {
            viewHolder=(ViewHolder) convertView.getTag();
        }
        viewHolder.tv.setText(apps.get(position));
        Drawable icone = AppMain.icons;
        //viewHolder.iv.setImageDrawable(icone);


        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.aswitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MyCustomAdapter.this, "Hello" , Toast.LENGTH_SHORT).show();
                if(finalViewHolder.aswitch.isChecked()){
                    lockedApps.add(apps.get(position));
                }else{
                    lockedApps.remove(apps.get(position));
                }

            }
        });


        return convertView;
    }

    public ArrayList<String> locked() {


        return lockedApps;

    }

}
