package com.kingoftheill.app1.presentation.ui.activities;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingoftheill.app1.R;
import com.kingoftheill.app1.domain2.PlayerItem;

import java.util.List;

/**
 * Created by Andrade on 08/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private Context context;
    private List<PlayerItem> mDataset;
    private RecyclerViewClickListener mListener;

    public MyAdapter(Context context, List<PlayerItem> dataset, RecyclerViewClickListener listener) {
        this.context = context;
        this.mDataset = dataset;
        this.mListener = listener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.grid_cell_layout, parent, false);
        return new ViewHolder(view , mListener);
    }

    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlayerItem pl = mDataset.get(position);
        if (pl.getQuantity() > 0) {
            holder.myTextView.setText(pl.getQuantity() + "");
        /*holder.image.setBackgroundResource(context
                .getResources()
                .getIdentifier(pl.getImage(), "drawable", context.getPackageName()));*/
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView image;
        private RecyclerViewClickListener mListener;

        ViewHolder(View itemView, RecyclerViewClickListener listener) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.quantity_item);
            image = itemView.findViewById(R.id.image_item);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mDataset.get(getAdapterPosition()).getQuantity() > 0)
                mListener.onClick(view, getAdapterPosition());
        }

    }

    // Method that executes your code for the action received
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number , which is at cell position " + position);
    }

    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }

}
