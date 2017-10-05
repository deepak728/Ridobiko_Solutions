package com.example.ritik.instabike.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import com.example.ritik.instabike.Activities.SelectedBikes;
import com.example.ritik.instabike.Models.BikeDetail;
import com.example.ritik.instabike.R;
import com.example.ritik.instabike.Models.Bike;
import com.example.ritik.instabike.Utility.GlobalData;

/**
 * Created by Abhishek on 3/13/2017.
 */

public class SelectedBikesAdapter extends RecyclerView.Adapter<SelectedBikesAdapter.ViewHolder> {
    LayoutInflater inflater;
    List<BikeDetail> bikeList;
    Context context;
    SelectedBikes activity;
    public SelectedBikesAdapter(Context context, SelectedBikes activity, List<BikeDetail> bikeList){
        inflater = LayoutInflater.from(context);
        this.bikeList = bikeList;
        this.context = context;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_selected_bike,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.bikeName.setText(bikeList.get(position).getBikeName());
        holder.bikeCost.setText(bikeList.get(position).getRentPerDay() + "/day");
        holder.bikeImage.setImageResource(R.drawable.bike);
    }

    @Override
    public int getItemCount() {
        return bikeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bikeName)
        TextView bikeName;
        @Bind(R.id.bikeCost)
        TextView bikeCost;
        @Bind(R.id.bikeImage)
        ImageView bikeImage;
        @Bind(R.id.cancelBikeButton)
        ImageView cancelBikeButton;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

            cancelBikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    GlobalData globalData = GlobalData.getInstance();
                    globalData.removeBike(bikeList.get(index).getBikeName());
                    activity.loadBikesList();
                    Toast.makeText(context,"Bike removed",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
