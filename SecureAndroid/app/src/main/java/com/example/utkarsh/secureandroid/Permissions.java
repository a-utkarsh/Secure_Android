package com.example.utkarsh.secureandroid;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Permissions extends AppCompatActivity {
    String[] requestedPermissions;
    ArrayList<String> Ap;
    ListView lw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions);

        Bundle extras = getIntent().getExtras();

        Ap=new ArrayList<>();
        lw=(ListView)findViewById(R.id.permissionlist);
        String appname=extras.getString("value");
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackNameByAppName( appname), PackageManager.GET_PERMISSIONS);
            requestedPermissions = packageInfo.requestedPermissions;
            if (requestedPermissions != null) {
                for (int i = 0; i < requestedPermissions.length; i++) {
                    Log.d("test", requestedPermissions[i]);
                    Ap.add(requestedPermissions[i]);
                }

            }
            PermissionAdapter ad = new PermissionAdapter(Ap, Permissions.this);
            lw.setAdapter(ad);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

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
