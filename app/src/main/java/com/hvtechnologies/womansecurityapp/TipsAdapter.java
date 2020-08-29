package com.hvtechnologies.womansecurityapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TipsAdapter extends BaseAdapter {


    private Context mcontext ;
    private List<TipsClass> mTip ;

    public TipsAdapter(Context mcontext, List<TipsClass> mTip) {
        this.mcontext = mcontext;
        this.mTip = mTip;
    }


    @Override
    public int getCount() {
        return mTip.size() ;
    }

    @Override
    public Object getItem(int position) {
        return mTip.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View v = View.inflate(mcontext , R.layout.see_tip_list , null);
        TextView Head ,Desc ;


        Head = v.findViewById(R.id.Name);
        Desc = v.findViewById(R.id.Full);

        Head.setText(mTip.get(position).getHeading());
        Desc.setText(mTip.get(position).getTip());

        return v ;

    }




}
