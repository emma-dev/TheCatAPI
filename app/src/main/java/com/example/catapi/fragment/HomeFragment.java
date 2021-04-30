package com.example.catapi.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.catapi.CatsDetailActivity;
import com.example.catapi.adapter.CatsAdapter;
import com.example.catapi.R;
import com.example.catapi.api.Api;
import com.example.catapi.api.ApiService;
import com.example.catapi.modal.Cat;
import com.example.catapi.modal.CatsModal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class HomeFragment extends Fragment implements CatsAdapter.OnItemClickListener {



    private CompositeDisposable catCompositeDisposable = new CompositeDisposable();
    private ApiService catApiService = new ApiService();
    private boolean loading = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount, page = INITIAL_PAGE;
    private static final int INITIAL_PAGE = 12;

    //Declare XML Links
    private RecyclerView recyclerView;
    CatsModal  listCat;
    private CatsAdapter catsAdapter;
    private List<Cat> cat = new ArrayList<>();
    //Declare Adapaters and LayoutManagers
    RecyclerView.LayoutManager layoutManager;

    ImageView img;
    private LinearLayoutManager layout;


    public HomeFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);



        // Set the adapter
        recyclerView = view.findViewById(R.id.homeRecycler);
      //  layoutManager = new LinearLayoutManager(view.getContext());
       // recyclerView.setLayoutManager(layoutManager);
        //  catsAdapter = new CatsAdapter(listCat);
      //  recyclerView.setAdapter(catsAdapter);
        layout = new LinearLayoutManager(
                getContext(), RecyclerView.VERTICAL, false);
        fetchCatList();


      return  view;
    }


    private void fetchCatList() {
        catCompositeDisposable.add(catApiService.getCatList(page).subscribe(
                this::catListSuccessResponse,
                this::catListErrorResponse));
    }

    private void catListSuccessResponse(List<Cat> apis) {
        setFilterVisible();
        int catListSize = apis.size();
        for (int i = 0; i < catListSize; i++) {
            // catsModalList.get(i).setTag(randomColor());
        }

        recyclerAdapterLoader(apis);
    }

    private void setFilterVisible() {
    }


    private void catListErrorResponse(Throwable throwable) {
        loading = false;
        if(cat.size() <= 0) {
//         connectionErrorView.setVisibility(View.VISIBLE);
//        connectionErrorView.setOnTryAgainButtonListener(this);
            return;
        }

     //   startSnack(getString(R.string.on_infinity_loading_error));
    }




    //add All cat
    public void setCatList(List<Cat> app) {
        this.cat.addAll(app);
    }


    private void recyclerAdapterLoader(List<Cat> catListResponse) {
        loading = false;
        setCatList(catListResponse);

        if(page > INITIAL_PAGE) {
            catsAdapter.notifyDataSetChanged();
        } else {
            initCatRecycler(catListResponse);
        }
        recyclerScrollListener(layout);
    }

    private void recyclerScrollListener(LinearLayoutManager layoutManager) {

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener()
        {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy)
            {
                if(dy > 0)
                {
                    visibleItemCount = layoutManager.getChildCount();
                    totalItemCount = layoutManager.getItemCount();
                    pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                    if (!loading)
                    {
                        if ( (visibleItemCount + pastVisibleItems) >= totalItemCount)
                        {
                            loading = true;
                          //  startSnack(getString(R.string.cat_loader_txt));
                            page++;
                            fetchCatList();
                        }
                    }
                }
            }
        });
    }


    //RecyclerView CatsModel
    private void initCatRecycler(List<Cat> cats) {

        initRecyclerProgress();
        catsAdapter = new CatsAdapter(cats,  this::onCatListClickListener);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 3));
        recyclerView.setAdapter(catsAdapter);
    }

    private void initRecyclerProgress() {
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onCatListClickListener(String id) {

        Intent intent = new Intent(getContext(), CatsDetailActivity.class);
        intent.putExtra("catID", id);
        startActivity(intent);

    }
}