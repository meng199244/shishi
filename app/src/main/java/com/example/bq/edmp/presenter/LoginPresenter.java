package com.example.bq.edmp.presenter;

import com.example.bq.edmp.activity.login.LoginActivity;
import com.example.bq.edmp.base.BasePresenter;
import com.example.bq.edmp.bean.LoginBean;
import com.example.bq.edmp.contract.LoginContract;
import com.example.bq.edmp.model.LoginModel;

/**
 * Created by wkk on 2020/10/20.
 */

public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.LoginPresenter {

    private final LoginModel loginModel;

    public LoginPresenter() {
        this.loginModel = new LoginModel();
    }

    @Override
    public void login(String phone, String pwd) {
        loginModel.getData(phone, pwd, new LoginModel.InfoHint() {
            @Override
            public void successInfo(LoginBean loginBean) {
                getIView().loginSuccess(loginBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().loginFail(str);
            }
        });

    }

    public void logincode(String phone, String code) {
        loginModel.getDataCode(phone, code, new LoginModel.CodeInfoHint() {
            @Override
            public void successInfo(LoginBean loginBean) {
                getIView().loginSuccess(loginBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().loginFail(str);
            }
        });

    }

    public void gainSmsCode(String username) {
        loginModel.getDataSmsCode(username, new LoginModel.SmsCodeHint() {
            @Override
            public void successInfo(LoginBean loginBean) {
                getIView().loginSuccess(loginBean);
            }

            @Override
            public void failInfo(String str) {
                getIView().loginFail(str);
            }
        });
    }
}
