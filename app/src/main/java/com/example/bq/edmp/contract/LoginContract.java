package com.example.bq.edmp.contract;

import com.example.bq.edmp.bean.LoginBean;

/**
 * Created by wkk on 2020/10/19.
 */

public class LoginContract {

    public interface LoginView {
        String getUserName();

        String getPwd();

        void loginSuccess(LoginBean loginBean);

        void loginCode(LoginBean loginBean);

        void loginSms(LoginBean loginBean);

        void loginFail(String failMsg);


    }

    public interface LoginPresenter {
        void login(String name, String pwd);
    }
}
