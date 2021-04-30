package com.example.catapi.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.catapi.R;
import com.example.catapi.adapter.LoveAdapter;
import com.example.catapi.api.FavouriteDatabase;

/**
 * A simple {@link Fragment} subclass.

 */
public class LoveFragment extends Fragment {

    //Declare XML Links
    RecyclerView recyclerView;

    //Declare Adapaters and LayoutManagers
    RecyclerView.LayoutManager layoutManager;
    LoveAdapter favouriteAdapter;

    public LoveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_love, container, false);

        recyclerView = view.findViewById(R.id.favouriteRecycler);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        favouriteAdapter = new LoveAdapter(FavouriteDatabase.getFavouriteCats());
        recyclerView.setAdapter(favouriteAdapter);

        return view;


    }

}