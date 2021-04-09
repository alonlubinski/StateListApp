package com.alon.statelistapp.Adapters;

import android.content.Context;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.alon.statelistapp.Models.State;
import com.alon.statelistapp.R;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;

import java.util.ArrayList;

public class StateAdapter extends RecyclerView.Adapter<StateAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<State> dataSet;
    private OnItemClickListener onItemClickListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView state_LBL_english_name, state_LBL_origin_name;
        private AppCompatImageView state_IMG_flag;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            state_LBL_english_name = itemView.findViewById(R.id.state_LBL_english_name);
            state_LBL_origin_name = itemView.findViewById(R.id.state_LBL_origin_name);
            state_IMG_flag = itemView.findViewById(R.id.state_IMG_flag);
        }

        public void bind(final State state, final OnItemClickListener onItemClickListener){
            state_LBL_english_name.setText(state.getName());
            state_LBL_origin_name.setText(state.getNativeName());
            RequestBuilder<PictureDrawable> requestBuilder = GlideToVectorYou
                    .init()
                    .with(context)
                    .getRequestBuilder();
            requestBuilder
                    .load(Uri.parse(state.getFlag()))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .apply(new RequestOptions().centerCrop())
                    .into(state_IMG_flag);
            if(onItemClickListener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onItemClick(state);
                    }
                });
            }
        }
    }

    public StateAdapter(Context context, ArrayList<State> dataSet, OnItemClickListener onItemClickListener){
        this.context = context;
        this.dataSet = dataSet;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rowlayout, viewGroup, false);
        StateAdapter.MyViewHolder vh = new StateAdapter.MyViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(dataSet.get(i), onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public interface OnItemClickListener {
        void onItemClick(State state);
    }
}
