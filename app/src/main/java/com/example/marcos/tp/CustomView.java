package com.example.marcos.tp;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("tex","tex");
        setContentView(R.layout.list_view);
        AddOnView();
    }
    public void finish (View view){
        finish();
    }

    //Pessoa obj = new Pessoa();
    ArrayList<Pessoa> contacts = new ArrayList<>();

    public void addArray() {

        //to store name-number pair


        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            Log.i("texto","texto");
            while (phones.moveToNext()) {
                Pessoa obj = new Pessoa();
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String emailaddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                String postaladdr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                /*
                      poBox      = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
      street     = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
      city       = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
      state      = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
      postalCode = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
      country    = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
      type       = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                 */
                obj.setTel(phoneNumber);
                obj.setName(name);
                obj.setEmail(emailaddr);
                obj.setPostal(postaladdr);
                contacts.add(obj);
                Log.i("name",name);
                Log.i("email",emailaddr);

            }
            phones.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddOnView(){
        addArray();
        ListView lv = (ListView) findViewById(R.id.listViewC);
        //CustomAdapterLV customAdapter = new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts);

        lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
    }
}
