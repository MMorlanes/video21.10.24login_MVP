package com.example.login_mvp.login.model;

import android.service.autofill.UserData;

import com.example.login_mvp.beans.User;
import com.example.login_mvp.login.LoginUserContract;
import com.example.login_mvp.login.data.LoginUserData;
import com.example.login_mvp.utils.ApiService;
import com.example.login_mvp.utils.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserModel implements LoginUserContract.model {
    private static final String ip = "http://192.168.1.69:3000/";

    @Override
    public void loginUserAPI(User user, OnLoginUserListener onLoginUserListener) {
        ApiService apiService = RetrofitClient.getClient(ip).create(ApiService.class);

        User userL = new User();
        userL.setEmail(user.getEmail());
        userL.setPassword(user.getPassword());

        Call<LoginUserData> call = apiService.login(userL);

        call.enqueue(new Callback<LoginUserData>() {
            @Override
            public void onResponse(Call<LoginUserData> call, Response<LoginUserData> response) {

                if (response.isSuccessful()) {
                    LoginUserData myData = response.body();
                    if (myData != null && myData.getUser() != null) {
                        onLoginUserListener.onFinished(userL);
                    } else {
                        onLoginUserListener.onFailure("No se encontró el usuario");
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginUserData> call, Throwable t) {
                handleNetworkError(t, onLoginUserListener);
            }
        });

    }

    private void handleNetworkError(Throwable t, OnLoginUserListener listener) {
        listener.onFailure("" + t);
    }
}
