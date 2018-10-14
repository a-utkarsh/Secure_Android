package com.example.utkarsh.secureandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class help extends AppCompatActivity {
    android.support.v4.view.ViewPager viewPager;
    int images[] = {R.drawable.imagea, R.drawable.imageb,R.drawable.imagec,R.drawable.imaged,R.drawable.imagee, R.drawable.imgaa, R.drawable.imgbb, R.drawable.imgcc, R.drawable.imgdd, R.drawable.imgee, R.drawable.imgeee, R.drawable.imgeeee, R.drawable.imgeeeee };
    MyCustomPageAdapter myCustomPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        viewPager = (android.support.v4.view.ViewPager)findViewById(R.id.viewPager);

        myCustomPagerAdapter = new MyCustomPageAdapter(help.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);


    }
}
