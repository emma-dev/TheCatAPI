package com.example.catapi.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.catapi.CatsDetailActivity;
import com.example.catapi.R;
import com.example.catapi.adapter.CatViewHolder;
import com.example.catapi.api.Api;
import com.example.catapi.modal.Cat;
import com.example.catapi.modal.CatsModal;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<CatViewHolder> implements Filterable {

    ArrayList<CatsModal> catsList;
    ArrayList<CatsModal> mFilteredList;
    Context context;



    public SearchAdapter(ArrayList<CatsModal> catsList) {
        this.catsList = catsList;
        this.mFilteredList = new ArrayList<>();
    }


    @Override
    public CatViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat, parent, false);
        this.context = parent.getContext();
        CatViewHolder catViewHolder = new CatViewHolder(view);
        return catViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CatViewHolder holder, final int position){
        if (mFilteredList.isEmpty()){
            mFilteredList = catsList;
        }

        final CatsModal catAtPosition = mFilteredList.get(position);

        String url = "https://api.thecatapi.com/v1/images/search?breed_id="+mFilteredList.get(position).getId()+"&size=small";

        StringRequest request =  new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        final Cat[] catData = gson.fromJson(response,Cat[].class);
        if(catData.length != 0){
            Glide.with(holder.img.getContext()).load(catData[0].getUrl()).into(holder.img);
            Log.i("imageUrl",catData[0].getUrl());
        }

        System.out.println("Filtered Cat List: "  +catAtPosition.getName()+" "+ catAtPosition.getId() +"imageUrl" );


                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ERROR","error => "+error.toString());
                        Toast.makeText(holder.img.getContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Log.d("Setting params","I am setting them");
                Map<String, String>  params = new HashMap<String, String>();
                params.put("x-api-key", "9b7e282d-2a67-4c7b-a9fd-3f3e4056e949");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(holder.img.getContext());
        queue.add(request);

        holder.catName.setText(catAtPosition.getName());
        holder.catLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CatsDetailActivity.class);
                System.out.println(catAtPosition.getId());
                intent.putExtra("catID",catAtPosition.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount(){
        if (mFilteredList.isEmpty()){
            return catsList.size();
        } else {
            return mFilteredList.size();
        }

    }

    public void setData(ArrayList<CatsModal> cats) {
        this.catsList = cats;
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {
                    mFilteredList = catsList;
                } else {
                    ArrayList<CatsModal> filteredList = new ArrayList<>();
                    for (CatsModal cat : catsList) {
                        if (cat.getName().toLowerCase().contains(searchString)) {
                            filteredList.add(cat);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<CatsModal>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
