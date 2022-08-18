package com.example.examenad;

import com.example.examenad.model.User;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface ServiceAPI {
    @GET("users/")
    Call<List<User>> getUser(@QueryMap Map<String, String> params);

    @POST("users/")
    Call<User> postUser(@Body User user);

}
