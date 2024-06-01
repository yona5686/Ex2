package com.example.ex2.http;

import com.example.ex2.allObjects.Root;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET("/api")
    public Call<Root> getUser(
            @Query("limit") Integer limit,
            @Query("skip") Integer skip,
            @Query("select") String select
    );
}
