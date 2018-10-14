package com.example.utkarsh.secureandroid;

import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Analyzer extends AppCompatActivity {
    ArrayList<String> Al;
    private static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyzer);

        try {
            fillStats();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void fillStats() throws PackageManager.NameNotFoundException {
        if (hasPermission()) {
            getStats();
        } else {
            requestPermission();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("MainActivity", "resultCode " + resultCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS:
                try {
                    fillStats();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void requestPermission() {
        Toast.makeText(this, "Need to request permission", Toast.LENGTH_SHORT).show();
        startActivityForResult(new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS);
    }

    private boolean hasPermission() {
        AppOpsManager appOps = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        int mode = appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                android.os.Process.myUid(), getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED;
    }

    private void getStats() throws PackageManager.NameNotFoundException {
        long lasttimeused = 500;
        long TimeInforground = 500;
        int minutes = 500, seconds = 500, hours = 500;
        long time = System.currentTimeMillis();
        final PackageManager pm = getApplicationContext().getPackageManager();
        ApplicationInfo ai;

        UsageStatsManager lUsageStatsManager = (UsageStatsManager) getSystemService(Context.USAGE_STATS_SERVICE);
        List<UsageStats> lUsageStatsList = lUsageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 10, time);

        ListView lw = (ListView) findViewById(R.id.list1);
        Al = new ArrayList<String>();
        String result;


        if (lUsageStatsList != null) {
            SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
            Object PackageName = null;
            for (UsageStats lUsageStats : lUsageStatsList) {


                lasttimeused = lUsageStats.getLastTimeUsed();
                TimeInforground = lUsageStats.getTotalTimeInForeground();
                PackageName = lUsageStats.getPackageName();
                ai = pm.getApplicationInfo(String.valueOf(PackageName), 0);
                Drawable a = pm.getApplicationIcon(PackageName.toString());

                final String applicationName = (String) (ai != null ? pm.getApplicationLabel(ai) : "(unknown)");


                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm");
                Date netDate = (new Date(lasttimeused));
                String lasttime = sdf.format(netDate);


                minutes = (int) ((TimeInforground / (1000 * 60)) % 60);

                seconds = (int) (TimeInforground / 1000) % 60;

                hours = (int) ((TimeInforground / (1000 * 60 * 60)) % 24);
                result = applicationName + " \nForeground Time: " + hours + "h:" + +minutes + "m:" + seconds + "s" + "\nLast Time Used: " + lasttime;
                Al.add(result);
                Collections.sort(Al);
            }

            AnalyzerCustomAdapter ad = new AnalyzerCustomAdapter(Al, Analyzer.this);
            lw.setAdapter(ad);


        }

    }
}
