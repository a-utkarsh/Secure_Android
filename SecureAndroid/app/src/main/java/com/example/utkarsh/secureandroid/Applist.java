package com.example.utkarsh.secureandroid;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Applist extends AppCompatActivity {


    ListView lw;
    ArrayList<String> Al;
    String value;
    ArrayList<String> installedApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applist);
        lw=(ListView)findViewById(R.id.ListView);
        Al=new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);


        installedApps = new ArrayList<String>();
        String title;
        for(ApplicationInfo app : apps) {
            //checks for flags; if flagged, check if updated system app
            if((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                title = (String)((app != null) ? pm.getApplicationLabel(app) : "???");

                installedApps.add(title);
                //it's a system app, not interested
            } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //Discard this one
                //in this case, it should be a user-installed app
            } else {
                title = (String)((app != null) ? pm.getApplicationLabel(app) : "???");

                installedApps.add(title);
            }
        }

        Collections.sort(installedApps);
        final ArrayAdapter<String> ad= new ArrayAdapter(Applist.this,android.R.layout.simple_expandable_list_item_1,installedApps
        );
        lw.setAdapter(ad);


 /*final AnalyzerCustomAdapter ad  = new AnalyzerCustomAdapter(installedApps , Applist.this);
 lw.setAdapter(ad);
  */  lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                value=ad.getItem(position);
                Intent i=new Intent(Applist.this,Permissions.class);
                i.putExtra("value",value);
                startActivity(i);

            }
        });
    }
}
