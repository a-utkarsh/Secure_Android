package com.example.utkarsh.secureandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class LockedAppList extends AppCompatActivity {
    ListView lw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locked_app_list);
        lw = (ListView) findViewById(R.id.lockedListView);

        ArrayList<String> installedApps = (ArrayList<String>) getIntent().getSerializableExtra("FILES_TO_SEND");

        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(installedApps, LockedAppList.this);
        lw.setAdapter(myCustomAdapter);

    }
}
