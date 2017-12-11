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
import com.kingoftheill.app1.domain2.Receita;

import java.util.List;

public class ReceitasAdapter extends RecyclerView.Adapter<ReceitasAdapter.ViewHolder> {

    private Context context;
    private List<Receita> mDataset;
    private RecyclerViewClickListener mListener;
    //private ItemClickListener mClickListener;

    public ReceitasAdapter(Context context, List<Receita> dataset, RecyclerViewClickListener listener) {
        this.context = context;
        this.mDataset = dataset;
        this.mListener = listener;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receita_grid, parent, false);
        return new ViewHolder(view , mListener);
    }


    // binds the data to the textview in each cell
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receita r = mDataset.get(position);
            holder.myTextView.setText(r.getName()+"");
//            holder.image.setBackgroundResource(context
//                    .getResources()
//                    .getIdentifier("bubonic_plague_doc_icon_3", "drawable", context.getPackageName()));
    }

    // total number of cells
    @Override
    public int getItemCount() {return mDataset.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;
        ImageView image;
        private RecyclerViewClickListener mListener;

        ViewHolder(View receitaView, RecyclerViewClickListener listener) {
            super(receitaView);
            myTextView = receitaView.findViewById(R.id.textView);
            image = receitaView.findViewById(R.id.imageView);
            mListener = listener;
            receitaView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(view, mDataset.get(getAdapterPosition()));
        }

    }

    // allows clicks events to be caught
    /*public  void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }*/

    // Method that executes your code for the action received
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number , which is at cell position " + position);
    }

    /*// parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }*/

    public interface RecyclerViewClickListener {

        void onClick(View view, Receita receita);
    }

}
