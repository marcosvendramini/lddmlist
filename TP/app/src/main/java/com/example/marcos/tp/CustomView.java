package com.example.marcos.tp;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
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


    /*public void addArray() {

        //to store name-number pair


        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            Cursor ends = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);
            Cursor emls = getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {


                Pessoa obj = new Pessoa();
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if(ends.moveToNext()==false)continue;
                String emailaddr = emls.getString(emls.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                if(emls.moveToNext()==false)continue;
                String postaladdr = ends.getString(ends.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                /*
                      poBox      = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
      street     = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
      city       = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
      state      = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
      postalCode = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
      country    = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
      type       = address.getString(address.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                obj.setTel(phoneNumber);
                obj.setName(name);
                obj.setEmail(emailaddr);
                obj.setPostal(postaladdr);
                contacts.add(obj);

            }
            phones.close();
            ends.close();
            emls.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/
    public void AddOnView(){
        addArray();
        ListView lv = (ListView) findViewById(R.id.listViewC);
        //CustomAdapterLV customAdapter = new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts);

        lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
    }


    int NaOrNu=0;

    public void OrdF (View view) {
        if (NaOrNu == 0) {

            Collections.sort(contacts, new Comparator<Pessoa>() {

    /* This comparator will sort AppDetail objects alphabetically. */

                public int compare(Pessoa a1, Pessoa a2) {

                    // String implements Comparable
                    return (a1.getName().toString()).compareTo(a2.getName().toString());
                }
            });


            ListView lv = (ListView) findViewById(R.id.listViewC);
            lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
            NaOrNu = 1;
        } else {

            Collections.sort(contacts, new Comparator<Pessoa>() {

    /* This comparator will sort AppDetail objects alphabetically. */

                public int compare(Pessoa a1, Pessoa a2) {

                    // String implements Comparable
                    return (a1.getTel()).compareTo(a2.getTel());
                }
            });


            ListView lv = (ListView) findViewById(R.id.listViewC);
            lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
            NaOrNu=0;
        }
    }
}
