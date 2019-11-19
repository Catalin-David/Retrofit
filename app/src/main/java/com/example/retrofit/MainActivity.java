package com.example.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView txtId, txtUserId, txtBody, txtTitle;
    public static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

        RetrofitClient retrofitClient = retrofit.create(RetrofitClient.class);
        Call<Post> getSinglePost = retrofitClient.getSinglePost();
        getSinglePost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "onResponse: onGettingSinglePost: code: " + response.code());
                Post post = response.body();
                txtId.setText(String.valueOf(post.getId()));
                txtUserId.setText(String.valueOf(post.getUserId()));
                txtBody.setText(post.getBody());
                txtTitle.setText(post.getTitle());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: onGettingSinglePost: t: " + t.getMessage());
            }
        });

        Call<List<Post>> getAllPosts = retrofitClient.getAllPosts();
        getAllPosts.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                Log.d(TAG, "onResponse: code: " + response.code());
                Log.d(TAG, "onResponse: second post title:"+ response.body().get(1).getTitle());
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Log.d(TAG, "onFailure: onGettingAllPosts: t: " + t.getMessage());
            }
        });

        Call<Post> sendPost = retrofitClient.sendPost(new Post(2, 5, "Hello World", "First post"));
        sendPost.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.d(TAG, "onResponse: code: " + response.code());
                Log.d(TAG, "onResponse: post: " + response.body().toString());
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: onSendingPost: t: " + t.getMessage());
            }
        });
    }

    private void initViews(){
        txtId = findViewById(R.id.txtId);
        txtUserId = findViewById(R.id.txtUserId);
        txtBody = findViewById(R.id.txtBody);
        txtTitle = findViewById(R.id.txtTitle);
    }
}
