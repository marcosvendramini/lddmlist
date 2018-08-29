package com.example.marcos.tp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import java.util.ArrayList;


public class Leitor extends AppCompatActivity {
    Pessoa obj = new Pessoa();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leitor);

    }

    public void Next(View view) {
        Intent i = new Intent(getApplicationContext(), CustomView.class);

        startActivity(i);
    }

    public void Wpp(View view) {
        EditText mPhoneNumber = (EditText) findViewById(R.id.editText2);
        String value = mPhoneNumber.getText().toString();
        Uri uri = Uri.parse("smsto:" + value);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            i.setPackage("com.whatsapp");
            startActivity(Intent.createChooser(i, ""));
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void AddContact(View view) {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        EditText mEmailAddress = (EditText) findViewById(R.id.editText4);
        EditText mPhoneNumber = (EditText) findViewById(R.id.editText2);
        EditText mEndereco = (EditText) findViewById(R.id.editText3);
        EditText mNome = (EditText) findViewById(R.id.editText);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, mEmailAddress.getText());
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, mPhoneNumber.getText());
        intent.putExtra(ContactsContract.Intents.Insert.NAME, mNome.getText().toString());
        intent.putExtra(ContactsContract.Intents.Insert.POSTAL, mEndereco.getText());
        startActivity(intent);
    }

    public void Google_maps(View view) {
        EditText mAddress = (EditText) findViewById(R.id.editText3);
        String value = mAddress.getText().toString();
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + value);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("com.google.android.apps.maps", PackageManager.GET_ACTIVITIES);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(this, "Google Maps not Installed", Toast.LENGTH_SHORT)
                    .show();

        }
    }

    ArrayList<Pessoa> contacts = new ArrayList<>();

    public void addArray() {

        //to store name-number pair


        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (phones.moveToNext()) {
                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String emailaddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
                String postaladdr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
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


            }
            phones.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public void AddOnView(){
       addArray();
       ListView lv = (ListView) findViewById(R.id.listViewC);
       CustomAdapterLV customAdapter = new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts);

       lv.setAdapter(customAdapter);
   }
}


