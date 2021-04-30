package com.example.catapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.catapi.CatsDetailActivity;
import com.example.catapi.R;
import com.example.catapi.api.Api;
import com.example.catapi.modal.Cat;
import com.example.catapi.modal.CatsModal;

import java.util.ArrayList;
import java.util.List;

public class CatsAdapter extends RecyclerView.Adapter<CatViewHolder> {

    List<Cat> catsList;
    Context context;
    private OnItemClickListener mListener;

    public CatsAdapter(List<Cat> catsList,OnItemClickListener mListener) {
        this.catsList = catsList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public CatViewHolder  onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item, parent, false);
        this.context = parent.getContext();
        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, int position) {
        final Cat catAtPosition = catsList.get(position);

     //   holder.itemView.setOnClickListener(view -> mListener.onCatListClickListener(catAtPosition.getId()));

        Glide.
                with(context).load(catAtPosition.getUrl()).into(holder.catsAdapImg);




    }

    public void setData(List<Cat> cats) {
        this.catsList = cats;
    }


    public interface OnItemClickListener {
        void onCatListClickListener(String id);

    }

    @Override
    public int getItemCount() {
        return catsList == null ? 0 : catsList.size();
    }
}
