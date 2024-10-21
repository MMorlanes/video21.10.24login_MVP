package com.example.login_mvp.login.presenter;

import com.example.login_mvp.beans.User;
import com.example.login_mvp.login.LoginUserContract;
import com.example.login_mvp.login.model.LoginUserModel;

public class LoginUserPresenter implements LoginUserContract.presenter, LoginUserContract.model.OnLoginUserListener {
    private LoginUserContract.view view;
    private LoginUserContract.model model;

    public LoginUserPresenter(LoginUserContract.view view){
        this.view = view;
        model = new LoginUserModel();
    }

    @Override
    public void loginAction(User user) {
        if(user != null){
            model.loginUserAPI(user, this);
        } else {
            view.failureLogin("Hay muchos campos vacios");
        }
    }

    @Override
    public void onFinished(User user) {
        view.successLogin(user);
    }

    @Override
    public void onFailure(String messageError) {
        view.failureLogin(messageError);
    }
}
