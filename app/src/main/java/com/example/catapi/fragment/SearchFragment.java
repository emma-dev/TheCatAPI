package com.example.catapi.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import com.example.catapi.R;
import com.example.catapi.adapter.SearchAdapter;
import com.example.catapi.modal.CatsModal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

    SearchView catSearch;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_search, container, false);


        String url = "https://api.thecatapi.com/v1/breeds?api_key=9b7e282d-2a67-4c7b-a9fd-3f3e4056e949";

        final RequestQueue requestQueue = Volley.newRequestQueue(view.getContext());

        //Create Response Listeners
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println(response);

                //If response -> Convert to GSON Objects
                Gson gson = new Gson();
                Type collectionType = new TypeToken<Collection<CatsModal>>(){}.getType();
                Collection<CatsModal> catAPI =gson.fromJson(response, collectionType);
                requestQueue.stop();

                //Get Array Created by GSON
                ArrayList<CatsModal> catsList = new ArrayList<>(catAPI);

                // Set the adapter
                recyclerView = view.findViewById(R.id.searchRecycler);
                layoutManager = new LinearLayoutManager(view.getContext());
                recyclerView.setLayoutManager(layoutManager);
                searchAdapter = new SearchAdapter(catsList);
                recyclerView.setAdapter(searchAdapter);

                //SearchView
                catSearch = view.findViewById(R.id.catSearch);
                search(catSearch);


            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("GSON VOLLEY ERROR !");
            }
        };

        //StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(stringRequest);



        return view;
    }

    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchAdapter.getFilter().filter(query);
                System.out.println("submit");
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchAdapter.getFilter().filter(newText);
                System.out.println("change");
                return true;
            }
        });
    }
}