package com.example.login_mvp.utils;

import com.example.login_mvp.beans.User;
import com.example.login_mvp.login.data.LoginUserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginUserData> login(@Body User user);
}

