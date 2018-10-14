package com.example.utkarsh.secureandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LockScreen extends AppCompatActivity {
    Button mOK,mCancel;
    EditText mPassword;
    DatabaseHandler db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        mOK=(Button)findViewById(R.id.ok_btn);
        mCancel=(Button)findViewById(R.id.cancel_btn);
        db = new DatabaseHandler(this);
        mPassword=(EditText)findViewById(R.id.password_edit);

        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password  = mPassword.getText().toString();

                Intent i=new Intent(LockScreen.this,MainActivity.class);
                //String pass=mPassword.getText().toString();
                db.addContact(new LockModal(password));
                i.putExtra("password",password);
                startActivity(i);
            }
        });


    }
}
