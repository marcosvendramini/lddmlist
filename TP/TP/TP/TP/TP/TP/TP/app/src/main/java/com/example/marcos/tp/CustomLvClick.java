package com.example.marcos.tp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
        setContentView(R.layout.activity_custom_lv_click);
    }

    public void finish(View view) {
        finish();
    }

    public void SendEmail(View view) {
        Bundle extras = getIntent().getExtras();
        String email = (String) extras.getString("emailt");
        Uri uri = Uri.parse("mailto:" + email)
                .buildUpon()
                .build();

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
        startActivity(Intent.createChooser(emailIntent, ""));

    }

    public void CallWpp(View view) {
        Bundle extras = getIntent().getExtras();
        String value = (String) extras.getString("namet");
        Uri uri = Uri.parse("smsto:" + value);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        PackageManager pm = getPackageManager();
        Log.e("value", "value");
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void CallTo(View view) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    1);
        }
        Bundle extras = getIntent().getExtras();
        String value = (String) extras.getString("namet");
        Uri uri = Uri.parse("tel:" + value);
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(uri);
        startActivity(callIntent);

    }


    public void TextTo (View view){
        Bundle extras = getIntent().getExtras();
        String value = (String) extras.getString("namet");
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address",value);
        startActivity(smsIntent);
    }
}
