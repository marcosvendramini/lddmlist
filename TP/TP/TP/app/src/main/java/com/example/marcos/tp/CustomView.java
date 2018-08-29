package com.example.marcos.tp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomView extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    1);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }

//        AddOnView();
        addArray();
    }
    public void finish (View view){
        finish();
    }

    //Pessoa obj = new Pessoa();
    ArrayList<Pessoa> contacts = new ArrayList<>();

//    public void addArray() {
//
//        //to store name-number pair
//
//
//        try {
//            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
//            while (phones.moveToNext()) {
//                Pessoa obj = new Pessoa();
//                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
//                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                String emailaddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));//----------------------------------------
//                String postaladdr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));//----------------------------
//
//                obj.setTel(phoneNumber);
//                obj.setName(name);
//                obj.setEmail(emailaddr);
//                obj.setPostal(postaladdr);
//                contacts.add(obj);
//
//            }
//            phones.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    ArrayList<String> arrayL = new ArrayList<>();
    ArrayAdapter<String> arrayA;
    ListView lv;

    public void addArray() {

        //to store name-number pair
        lv = (ListView) findViewById(R.id.listViewC);


        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            Cursor emls = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);
            Cursor ends = getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()&&emls.moveToNext()&&ends.moveToNext()) {


                Pessoa obj = new Pessoa();
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String emailaddr = emls.getString(emls.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                String postaladdr = ends.getString(ends.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));

                obj.setTel(phoneNumber);
                obj.setName(name);
                obj.setEmail(emailaddr);
                obj.setPostal(postaladdr);
                contacts.add(obj);
                arrayL.add("\n" +
                        "Nome: " + name + "\n" +
                        "Numero: " + phoneNumber + "\n" +
                        "E-mail: " + emailaddr + "\n" +
                        "Endereco : " + postaladdr +
                        "\n");


            }
            arrayA = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, arrayL);


            lv.setAdapter(arrayA);
            phones.close();
            ends.close();
            emls.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void AddOnView(){
        addArray();
        ListView lv = (ListView) findViewById(R.id.listViewC);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                //String nome = (String)adapter.getItemAtPosition(position);
                String tel = ((TextView) adapter.findViewById(R.id.telefone9)).getText().toString();//--------------------------------------------------------------
                Intent i = new Intent(getApplicationContext(), CustomLvClick.class);
                Log.e("tel","tel");
                i.putExtra("tel","tel");
                startActivity(i);
            }
        });
        //CustomAdapterLV customAdapter = new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts);

        lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
    }


    int NaOrNu=0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

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

    public void FiltrateNa (View view){

    }

    public void FiltrateEnd (View view){

    }
}
