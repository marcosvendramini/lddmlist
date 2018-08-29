package com.example.marcos.tp;

import android.content.ClipData;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
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

import java.util.List;


public class CustomAdapterLV extends ArrayAdapter<Pessoa> {

    public CustomAdapterLV(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public CustomAdapterLV(Context context, int resource, List<Pessoa> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.custom_adpater_lv, null);
        }

        Pessoa p = new Pessoa();
        p = getItem(position);
        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.nome9);
            TextView tt2 = (TextView) v.findViewById(R.id.telefone9);
            TextView tt3 = (TextView) v.findViewById(R.id.email9);
            TextView tt4 = (TextView) v.findViewById(R.id.postal9);

            if (tt1 != null) {
                tt1.setText(p.getName());


            }if (tt2 != null) {
                tt2.setText(p.getTel());


            }if (tt3 != null) {
                tt3.setText(p.getEmail());


            }   if (tt4 != null) {
                tt4.setText(p.getPostal());
            }
        }

        return v;
    }



}