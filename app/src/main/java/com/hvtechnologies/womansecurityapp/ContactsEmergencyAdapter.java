package com.hvtechnologies.womansecurityapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactsEmergencyAdapter extends BaseAdapter {


    private Context mcontext5 ;
    private List<ContactsEmergencyClass> mCont ;


    public ContactsEmergencyAdapter(Context mcontext5, List<ContactsEmergencyClass> mCont) {
        this.mcontext5 = mcontext5;
        this.mCont = mCont;
    }

    @Override
    public int getCount() {
        return mCont.size();
    }

    @Override
    public Object getItem(int position) {
        return mCont.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mcontext5 , R.layout.see_contacts_list , null);


        TextView N123 = (TextView)v.findViewById(R.id.Name12);

        N123.setText(mCont.get(position).getName());

        v.setTag(mCont.get(position).getPhNumber());
        return v;



    }
}
