package com.example.retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitClient {

    @GET("posts/1")
    Call<Post> getSinglePost();

    @GET("posts")
    Call<List<Post>> getAllPosts();

    @POST("posts")
    Call<Post> sendPost(@Body Post post);
}
