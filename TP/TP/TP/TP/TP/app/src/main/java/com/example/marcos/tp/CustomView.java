package com.example.marcos.tp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

        AddOnView();
    }

    public void finish(View view) {
        finish();
    }

    ArrayList<Pessoa> contacts = new ArrayList<>();

    public void addArray() {

        //to store name-number pair


        try {
            Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            Cursor emls = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);
            Cursor ends = getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, null, null, null);
            while (phones.moveToNext()) {
                Pessoa obj = new Pessoa();
                String emailaddr = "";
                String postaladdr = "";
                if (emls.moveToNext())
                    emailaddr = emls.getString(emls.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                if (ends.moveToNext())
                    postaladdr = ends.getString(ends.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));


                String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                //String emailaddr = emls.getString(emls.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                //String postaladdr = ends.getString(ends.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));

                obj.setTel(phoneNumber);
                obj.setName(name);
                obj.setEmail(emailaddr);
                obj.setPostal(postaladdr);
                contacts.add(obj);

            }
            phones.close();
            emls.close();
            ends.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    int aux;

    public void AddOnView() {
        addArray();
        ListView lv = (ListView) findViewById(R.id.listViewC);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                final String tel = ((TextView) adapter.findViewById(R.id.telefone9)).getText().toString();
                AlertDialog.Builder diag = new AlertDialog.Builder(CustomView.this);
                diag.setItems(new CharSequence[]{"SMS", "WPP", "CALL", "EMAIL"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int w) {
                        switch(w) {
                            case 0:
                                //SMSTO(tel);
                                break;
                            case 1:
                                CallWpp(tel);
                                break;
                            case 2:
                                //CALLTO(tel);
                                break;
                            case 3:
                                //EMAILTO(email);
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        });
        lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
    }

    public void CallWpp(String tel) {
        Uri uri = Uri.parse("smsto:" + tel);
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



    int NaOrNu = 0;

    public void OrdF(View view) {
        if (NaOrNu == 0) {
            Toast.makeText(this, "ordenado por nome", Toast.LENGTH_SHORT).show();

            Collections.sort(contacts, new Comparator<Pessoa>() {

    /* This comparator will sort AppDetail objects alphabetically. */

                public int compare(Pessoa a1, Pessoa a2) {

                    // String implements Comparable
                    return (a1.getName().toString()).compareTo(a2.getName().toString());
                }
            });


            ListView lv = (ListView) findViewById(R.id.listViewC);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position,
                                        long arg3) {
                    //String nome = (String)adapter.getItemAtPosition(position);
                    String tel = contacts.get(position).getTel();
                    //String tel = ((TextView) adapter.findViewById(R.id.telefone9)).getText().toString();//--------------------------------------------------------------
                    Intent i = new Intent(getApplicationContext(), CustomLvClick.class);
                    //i.putExtra("tel","valueT");
                    i.putExtra("namet", tel);
                    Log.d("namet", tel);
                    startActivity(i);
                }
            });
            lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
            NaOrNu = 1;
        } else {
            Toast.makeText(this, "ordenado por numero", Toast.LENGTH_SHORT).show();

            Collections.sort(contacts, new Comparator<Pessoa>() {

    /* This comparator will sort AppDetail objects alphabetically. */

                public int compare(Pessoa a1, Pessoa a2) {

                    // String implements Comparable
                    return (a1.getTel()).compareTo(a2.getTel());
                }
            });


            ListView lv = (ListView) findViewById(R.id.listViewC);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View v, int position,
                                        long arg3) {
                    //String nome = (String)adapter.getItemAtPosition(position);
                    String tel = ((TextView) adapter.findViewById(R.id.telefone9)).getText().toString();//--------------------------------------------------------------
                    Intent i = new Intent(getApplicationContext(), CustomLvClick.class);
                    //i.putExtra("tel","valueT");
                    i.putExtra("namet", tel);
                    Log.i("namet", tel);
                    startActivity(i);
                }
            });
            lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, contacts));
            NaOrNu = 0;
        }
    }

    public void FiltrateNa(View view) {
        String pesq = ((TextView) findViewById(R.id.editText5)).getText().toString();
        ArrayList<Pessoa> array2 = new ArrayList<>();
        int count = contacts.size();
        String aux = "";
        for(int i=0;i<count;i++){
            Pessoa a= new Pessoa();

            if(contacts.get(i).getName().contains(pesq)){

                array2.add(contacts.get(i));
            }
        }

        ListView lv = (ListView) findViewById(R.id.listViewC);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                //String nome = (String)adapter.getItemAtPosition(position);
                String tel = ((TextView) adapter.findViewById(R.id.telefone9)).getText().toString();//--------------------------------------------------------------
                Intent i = new Intent(getApplicationContext(), CustomLvClick.class);
                //i.putExtra("tel","valueT");
                i.putExtra("namet", tel);
                Log.i("namet", tel);
                startActivity(i);
            }
        });


        lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, array2));
    }



    public void FiltrateEnd(View view) {

        String pesq = ((TextView) findViewById(R.id.editText5)).getText().toString();
        ArrayList<Pessoa> array2 = new ArrayList<>();
        int count = contacts.size();
        String aux = "";
        for(int i=0;i<count;i++){
            Pessoa a= new Pessoa();

            if(contacts.get(i).getPostal().contains(pesq)){

                array2.add(contacts.get(i));
            }
        }

        ListView lv = (ListView) findViewById(R.id.listViewC);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                //String nome = (String)adapter.getItemAtPosition(position);
                String tel = ((TextView) adapter.findViewById(R.id.telefone9)).getText().toString();//--------------------------------------------------------------
                Intent i = new Intent(getApplicationContext(), CustomLvClick.class);
                //i.putExtra("tel","valueT");
                i.putExtra("namet", tel);
                Log.i("namet", tel);
                startActivity(i);
            }
        });


        lv.setAdapter(new CustomAdapterLV(this, R.layout.custom_adpater_lv, array2));
    }
}
