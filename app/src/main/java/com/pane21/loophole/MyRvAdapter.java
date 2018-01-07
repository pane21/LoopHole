package com.pane21.loophole;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRvAdapter extends RecyclerView.Adapter<MyRvAdapter.AdapterViewHolder>{
//    private List<BusinessObject> businesses;
    private ArrayList<BusinessObject> values;
    private int rowLayout;
    private Context mContext;


    public MyRvAdapter(ArrayList<BusinessObject> values, int rowLayout, Context context) {
        this.values = values;
//        this.businesses = businesses;
        this.rowLayout = rowLayout;
        mContext = context;
    }

    static class AdapterViewHolder extends RecyclerView.ViewHolder{
        LinearLayout busLayout;
        TextView name;
        TextView address;

        public AdapterViewHolder(View v) {
            super(v);
            this.busLayout = v.findViewById(R.id.single_bus);
            this.name = v.findViewById(R.id.name);
            this.address = v.findViewById(R.id.address);
        }
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
//        holder.name.setText("blah");

        holder.name.setText(values.get(position).getName());
        holder.address.setText(values.get(position).getAddress());

    }


    @Override
    public int getItemCount() {
        return values.size();
    }
}
