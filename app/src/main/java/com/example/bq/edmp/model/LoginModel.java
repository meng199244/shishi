package com.example.bq.edmp.model;

import com.example.bq.edmp.ProApplication;
import com.example.bq.edmp.base.BaseModel;
import com.example.bq.edmp.bean.LoginBean;
import com.example.bq.edmp.exception.ApiException;
import com.example.bq.edmp.utils.MD5Util;
import com.example.bq.edmp.utils.SpUtils;
import com.example.bq.edmp.utils.ToastUtil;
import com.example.bq.edmp.utils.subscriber.CommonSubscriber;
import com.example.bq.edmp.utils.transformer.CommonTransformer;

/**
 * Created by wkk on 2020/10/20.
 */

public class LoginModel extends BaseModel {

    public void getData(String phone, String password, final InfoHint infoHint) {

        String sign = MD5Util.encode("password=" + password + "&username=" + phone);

        httpService.login(password, phone, sign)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonSubscriber<LoginBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(LoginBean loginBean) {

                        if (loginBean.getCode().equals("200")) {
                            SpUtils.put("accesstoken", loginBean.getData());
                            infoHint.successInfo(loginBean);
                        } else {
                            ToastUtil.setToast("请检查手机号和密码是否填写错误!");
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        infoHint.failInfo(e.message);
                    }
                });


    }

    public void getDataCode(String username, String code, final CodeInfoHint codeInfoHint) {
        String sign = MD5Util.encode("smscode=" + code + "&username=" + username);

        httpService.logincode(code, username, sign)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonSubscriber<LoginBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(LoginBean loginBean) {

                        if (loginBean.getCode().equals("200")) {
                            SpUtils.put("accesstoken", loginBean.getData());
                            codeInfoHint.successInfo(loginBean);
                        } else {
                            ToastUtil.setToast("请检查手机号和密码是否填写错误!");
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        codeInfoHint.failInfo(e.message);
                    }
                });

    }

    public void getDataSmsCode(String username, final SmsCodeHint smsCodeHint) {
        String sign = MD5Util.encode("username=" + username);

        httpService.gainAuthCode(username, sign)
                .compose(new CommonTransformer<LoginBean>())
                .subscribe(new CommonSubscriber<LoginBean>(ProApplication.getmContext()) {
                    @Override
                    public void onNext(LoginBean loginBean) {

                        if (loginBean.getCode().equals("200")) {
                            SpUtils.put("accesstoken", loginBean.getData());
                            smsCodeHint.successInfo(loginBean);
                        } else {
                            ToastUtil.setToast("请检查手机号和密码是否填写错误!");
                        }
                    }

                    @Override
                    protected void onError(ApiException e) {
                        super.onError(e);
                        smsCodeHint.failInfo(e.message);
                    }
                });

    }


    //密码登录回调
    public interface InfoHint {
        void successInfo(LoginBean loginBean);

        void failInfo(String str);

    }

    //验证码登录回调
    public interface CodeInfoHint {
        void successInfo(LoginBean loginBean);

        void failInfo(String str);

    }

    //获取验证码回调
    public interface SmsCodeHint {
        void successInfo(LoginBean loginBean);

        void failInfo(String str);
    }
}
