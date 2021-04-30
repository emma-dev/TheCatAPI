package com.example.catapi.fragment;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.catapi.BuildConfig;
import com.example.catapi.R;
import com.example.catapi.adapter.CatsAdapter;
import com.example.catapi.api.CatApi;
import com.example.catapi.modal.CatsModal;
import com.example.catapi.modal.UploadCat;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;

public class UploadFragment extends Fragment {


    ImageView viewImage;

    private static final String TAG = UploadCat.class.getSimpleName();
    private FloatingActionButton fabView;
    private static final int INTENT_REQUEST_CODE = 100;
    private static final int PICK_IMAGE_REQUEST =1 ;
    public static final String BASE_URL = "https://api.thecatapi.com/v1/images/upload/";

    private Button mBtImageShow;
    private String mImageUrl = "";
    private ProgressBar mProgressBar;

    private static final String TOKEN_KEY = "x-api-key";
    private static final String TOKEN_VALUE = "9b7e282d-2a67-4c7b-a9fd-3f3e4056e949";
    public static final int TIME_OUT_CONNECTION =10000;

   String file ="";


    //Declare XML Links
    RecyclerView recyclerView;
    CatsModal listCat;

    //Declare Adapaters and LayoutManagers
    RecyclerView.LayoutManager layoutManager;
    CatsAdapter catsAdapter;
    ImageView img;



    public UploadFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload, container, false);


        viewImage = view.findViewById(R.id.viewImage);
        fabView = view.findViewById(R.id.fabb);
        mBtImageShow = view.findViewById(R.id.btn_show_image);
        mProgressBar = view.findViewById(R.id.progress);

        initViews();

        return  view;
    }

    private void initViews() {


        fabView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // mBtImageShow.setVisibility(View.GONE);

             /*   Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpg");
                intent.putExtra(Intent.EXTRA_MIME_TYPES,"image/*");


                try {
                    // startActivityForResult(intent, INTENT_REQUEST_CODE);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } catch (ActivityNotFoundException e) {

                    e.printStackTrace();
                }*/
                selectImage();

            }
        });

        mBtImageShow.setOnClickListener(view -> {

            Intent intentIn = new Intent(Intent.ACTION_VIEW);
            intentIn.setData(Uri.parse(mImageUrl));
            startActivity(intentIn);

        });



    }
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/jpg");
                    intent.putExtra(Intent.EXTRA_MIME_TYPES,"image/*");
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    /*    if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
        }*/

        if (requestCode == 1) {
    /*     Bitmap bitmap = (Bitmap)data.getExtras().get("data");
         viewImage.setImageBitmap(bitmap);*/
        } else if (requestCode == 2) {

        }
        if (resultCode == RESULT_OK) {

                try {
//                   InputStream  is = getActivity().getApplicationContext().getContentResolver().openInputStream(data.getData());

                    uploadImage(getBytes(data));


                } catch (FileNotFoundException e) {
                    e.printStackTrace();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }

    }



    public byte[] getBytes( @NonNull  Intent data) throws IOException {
        InputStream  is = getActivity().getApplicationContext().getContentResolver().openInputStream(data.getData());
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();

        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }



    // Upload own cat image: byte[] imageBytes


    private void uploadImage(byte[] imageBytes) {

        OkHttpClient client = this.createHttpClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        CatApi retrofitInterface = retrofit.create(CatApi.class);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"),imageBytes);

        MediaType mediaType = MediaType.parse("multipart/form-data");
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", "file", RequestBody.create(MediaType.parse("application/octet-stream"),
                        new File("/path/to/file")))
                .addFormDataPart("sub_id", "")
                .build();


                 /*   Request request = new Request.Builder()
                            .url("https://api.thecatapi.com/v1/images/upload")
                            .method("POST", requestFile)
                            .addHeader("Content-Type", "multipart/form-data")
                            .addHeader("x-api-key", "9b7e282d-2a67-4c7b-a9fd-3f3e4056e949")
                            .build();*/
//file.getName()

        MultipartBody.Part parts = MultipartBody.Part.createFormData("image", "image", requestFile);
        Call<UploadCat> call = retrofitInterface.getUploadCat(parts);


        call.enqueue(new Callback<UploadCat>() {
            @Override
            public void onResponse(Call<UploadCat> call, retrofit2.Response<UploadCat> response) {

                mProgressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    UploadCat responseBody = response.body();
                    mBtImageShow.setVisibility(View.VISIBLE);
                    mImageUrl = BASE_URL + responseBody.getFile();
                  //  Snackbar.make(findViewById(R.id.content), responseBody.getMessage(), Snackbar.LENGTH_SHORT).show();

                } else {

                    ResponseBody errorBody = response.errorBody();

                    Gson gson = new Gson();

                    try {

                        UploadCat errorResponse = gson.fromJson(errorBody.string(), UploadCat.class);
                 //  Snackbar.make(findViewById(android.R.id.content), errorResponse.getMessage(), Snackbar.LENGTH_LONG).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<UploadCat> call, Throwable t) {
                mProgressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: " + t.getLocalizedMessage());

            }


        });

    }

    private OkHttpClient createHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        if (BuildConfig.DEBUG)
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return (new OkHttpClient.Builder())
                .connectTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_CONNECTION, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    request = request.newBuilder()
                            .addHeader(TOKEN_KEY, TOKEN_VALUE)
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(interceptor)
                .cache(null).build();
    }









}
