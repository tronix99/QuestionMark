package com.arx_era.rentmyvehicle.Adapters;

import java.util.ArrayList;
import java.util.List;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arx_era.rentmyvehicle.R;

/**
 * Created by Tronix99 on 18-01-2018.
 */

public class profile_list extends RecyclerView.Adapter<profile_list.ViewHolder> {
    private List<String> values;

    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            txtHeader = (TextView) v.findViewById(R.id.title);
        }
    }

    public void add(int position, String item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public profile_list(List<String> myDataset) {
        values = myDataset;
    }

    @Override
    public profile_list.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.listview_profile, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = values.get(position);
        holder.txtHeader.setText(name);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}
