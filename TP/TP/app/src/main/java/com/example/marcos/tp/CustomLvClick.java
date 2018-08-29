package com.example.marcos.tp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class CustomLvClick extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_lv_click);
    }

    public void finish (View view){
        finish();
    }

    public void SendEmail (View view){

    }

    public void CallWpp(View view) {
        Intent j = getIntent();
        String value = j.getStringExtra("tel");//-------------------------------------------------------------------------------------------------------
        Uri uri = Uri.parse("smsto:" + value);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        PackageManager pm = getPackageManager();
        Log.e("value","value");
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void CallTo (View view){

    }

    public void TextTo (View view){

    }
}
