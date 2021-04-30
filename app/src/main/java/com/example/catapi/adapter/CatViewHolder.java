package com.example.catapi.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.catapi.R;

import java.text.BreakIterator;

public class CatViewHolder extends RecyclerView.ViewHolder {

    LinearLayout catLinear;
    TextView catName;
    ImageView img;
    ImageView catsAdapImg;
    public CatViewHolder(View view){
        super(view);
        catsAdapImg =view.findViewById(R.id.imageV);
        img =view.findViewById(R.id.catIm);
        catName =view.findViewById(R.id.catName);
        catLinear = view.findViewById(R.id.catLinear);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + catName.getText() +  "'" ;
    }


}
