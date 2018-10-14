package com.example.utkarsh.secureandroid;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppMain extends AppCompatActivity {

    ListView lw;
    ArrayList<String> Al;

    String value;
    public static Drawable icons;
    public static ArrayList<String> installedApps;
    public static ArrayList<String> getlockedApps = new ArrayList<String>();
    int i;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        startService(new Intent(this, BackgroundService.class));


        button = (Button) findViewById(R.id.list);
        lw = (ListView) findViewById(R.id.ListView);
        Al = new ArrayList<>();

        PackageManager pm = getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(0);

        installedApps = new ArrayList<String>();
        List<Drawable> icons = new ArrayList<Drawable>();
        String title;

        for (ApplicationInfo app : apps) {
            //checks for flags; if flagged, check if updated system app
            if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
                title = (String) ((app != null) ? pm.getApplicationLabel(app) : "???");

                installedApps.add(title);
                //it's a system app, not interested
            } else if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                //Discard this one
                //in this case, it should be a user-installed app
            } else {
                title = (String) ((app != null) ? pm.getApplicationLabel(app) : "???");
                // Al.add(getPackNameByAppName(title));
                installedApps.add(title);

                Drawable icon = pm.getApplicationIcon(app);
                icons.add(icon);
            }
        }

        Collections.sort(installedApps);

         /*   final ArrayAdapter<String> ad = new ArrayAdapter(MainActivity.this, android.R.layout.simple_expandable_list_item_1, installedApps);
            lw.setAdapter(ad);
*/
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(installedApps, AppMain.this);
        lw.setAdapter(myCustomAdapter);


        getlockedApps = myCustomAdapter.locked();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, getlockedApps.get(0) , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AppMain.this, LockedAppList.class);
                intent.putExtra("FILES_TO_SEND", getlockedApps);
                startActivity(intent);
            }
        });
    }
    public String getPackNameByAppName(String name) {
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> l = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        String packName = " ";
        for (ApplicationInfo ai : l) {
            String n = (String)pm.getApplicationLabel(ai);
            if (n.contains(name) || name.contains(n)){
                packName = ai.packageName;
            }
        }

        return packName;

    }
}
