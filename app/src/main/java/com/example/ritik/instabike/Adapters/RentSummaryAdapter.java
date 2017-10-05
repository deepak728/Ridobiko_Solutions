package com.example.ritik.instabike.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.example.ritik.instabike.Models.BikeDetail;
import com.example.ritik.instabike.R;
import com.example.ritik.instabike.Models.Bike;

public class RentSummaryAdapter extends RecyclerView.Adapter<RentSummaryAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<BikeDetail> bikeList;
    Context context;
    public RentSummaryAdapter(Context context, List<BikeDetail> bikeList){
        inflater = LayoutInflater.from(context);
        this.bikeList = bikeList;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_rent_summary,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bikeName.setText(bikeList.get(position).getBikeName());
        holder.bikeCost.setText("Rs. "+bikeList.get(position).getRentPerDay());
    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.bikeName)
        TextView bikeName;
        @Bind(R.id.bikeCost)
        TextView bikeCost;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
