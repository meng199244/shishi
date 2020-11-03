package com.example.bq.edmp.activity.login;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bq.edmp.R;
import com.example.bq.edmp.base.BaseActivity;
import com.example.bq.edmp.bean.LoginBean;
import com.example.bq.edmp.contract.LoginContract;
import com.example.bq.edmp.presenter.LoginPresenter;
import com.example.bq.edmp.utils.StringUtils;
import com.example.bq.edmp.utils.ToastUtil;

import butterknife.BindView;

/*
* 登录页面
* */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.LoginView {

    @BindView(R.id.code_tv)
    TextView code_tv;
    @BindView(R.id.code_v)
    View code_v;
    @BindView(R.id.password_tv)
    TextView password_tv;
    @BindView(R.id.password_v)
    View password_v;
    @BindView(R.id.phone_et)
    EditText phone_et;
    @BindView(R.id.authcode_et)
    EditText authcode_et;
    @BindView(R.id.password_et)
    EditText password_et;
    @BindView(R.id.gain_authcode_tv)
    TextView gain_authcode_tv;
    @BindView(R.id.login_btn_img)
    ImageView login_btn_img;
    @BindView(R.id.auth_ll)
    LinearLayout auth_ll;
    @BindView(R.id.password_ll)
    LinearLayout password_ll;

    private int type = 0;//0  验证码登录  1  密码登录
    private boolean JUDGE_CODE = false;//倒计时

    private CountDownTimer timer;

    @Override
    protected LoginPresenter loadPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        code_tv.setOnClickListener(this);
        password_tv.setOnClickListener(this);
        gain_authcode_tv.setOnClickListener(this);
        login_btn_img.setOnClickListener(this);

    }

    @Override
    protected void initView() {
        type = getIntent().getIntExtra("type", 0);

        if (this.type == 0) {//验证码登录
            code_v.setVisibility(ViewGroup.VISIBLE);
            password_v.setVisibility(ViewGroup.GONE);
            auth_ll.setVisibility(ViewGroup.VISIBLE);
            password_ll.setVisibility(ViewGroup.GONE);
            code_tv.setTextColor(getResources().getColor(R.color.text_black));
            password_tv.setTextColor(getResources().getColor(R.color.text_gray));
            this.type = 0;
        } else {//密码登录
            password_v.setVisibility(ViewGroup.VISIBLE);
            code_v.setVisibility(ViewGroup.GONE);
            password_ll.setVisibility(ViewGroup.VISIBLE);
            auth_ll.setVisibility(ViewGroup.GONE);
            password_tv.setTextColor(getResources().getColor(R.color.text_black));
            code_tv.setTextColor(getResources().getColor(R.color.text_gray));
            authcode_et.setText("");
            this.type = 1;
        }


        //监听手机号长度
        phone_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Editable editable = phone_et.getText();
                int len = editable.length();
                if (len > 11) {
                    ToastUtil.setToast("手机号最多11位");
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, 11);
                    phone_et.setText(newStr);
                    editable = phone_et.getText();
                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void otherViewClick(View view) {
        String phone = phone_et.getText().toString();
        String authcode = authcode_et.getText().toString();
        String password = password_et.getText().toString();
        switch (view.getId()) {
            case R.id.code_tv:
                code_v.setVisibility(ViewGroup.VISIBLE);
                password_v.setVisibility(ViewGroup.GONE);
                auth_ll.setVisibility(ViewGroup.VISIBLE);
                password_ll.setVisibility(ViewGroup.GONE);
                code_tv.setTextColor(getResources().getColor(R.color.text_black));
                password_tv.setTextColor(getResources().getColor(R.color.text_gray));
                password_et.setText("");
                type = 0;
                break;
            case R.id.password_tv:
                password_v.setVisibility(ViewGroup.VISIBLE);
                code_v.setVisibility(ViewGroup.GONE);
                password_ll.setVisibility(ViewGroup.VISIBLE);
                auth_ll.setVisibility(ViewGroup.GONE);
                password_tv.setTextColor(getResources().getColor(R.color.text_black));
                code_tv.setTextColor(getResources().getColor(R.color.text_gray));
                authcode_et.setText("");
                type = 1;
                break;
            case R.id.gain_authcode_tv://获取验证码
                if (phone.isEmpty()) {
                    phone_et.setError("请输入手机号");
                    break;
                } else if (!StringUtils.isPhone(phone)) {
                    phone_et.setError("请输入正确的手机号");
                    break;
                }
                if (JUDGE_CODE == false) {
                    JUDGE_CODE = true;
                    StartTimer();
                    getAuthCodeMethod(phone);
                } else {
                    ToastUtil.setToast("已发送！");
                }
                break;
            case R.id.login_btn_img://登录
                if (phone.isEmpty()) {
                    phone_et.setError("请输入手机号");
                    break;
                }
//                } else if (!StringUtils.isPhone(phone)) {
//                    phone_et.setError("请输入正确的手机号");
//                    break;
//                }
                if (type == 0) {
                    if (authcode.isEmpty()) {
                        authcode_et.setError("请输入验证码");
                        break;
                    }
                } else {
                    if (password.isEmpty()) {
                        password_et.setError("请输入密码");
                        break;
                    }
                }
                if (type == 0) {//验证码
                    loginMethod(phone, authcode);
                } else {//密码
                    loginMethod(phone, password);
                }

                break;
        }
    }

    //获取验证码
    private void getAuthCodeMethod(String phone) {
        mPresenter.gainSmsCode(phone);
    }

    private void StartTimer() {
        /** 倒计时60秒，一次1秒 */
        // TODO Auto-generated method stub
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                gain_authcode_tv.setText("还剩" + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                gain_authcode_tv.setText("重新获取验证码!");
                JUDGE_CODE = false;
            }
        }.start();
    }

    //登录
    private void loginMethod(String phone, String password) {
        ToastUtil.setToast("登录！");
        if (type == 1) {
            mPresenter.login(phone, password);
        } else {
            mPresenter.logincode(phone, password);
        }
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getPwd() {
        return null;
    }

    @Override
    public void loginSuccess(LoginBean loginBean) {
        String data = loginBean.getData();
        ToastUtil.setToast(data);
        Log.d("login_dalog", "loginFail: ===" + data);
    }

    @Override
    public void loginCode(LoginBean loginBean) {
//        ToastUtil.setToast(data);
    }

    @Override
    public void loginSms(LoginBean loginBean) {
//        ToastUtil.setToast(data);
    }

    @Override
    public void loginFail(String failMsg) {
        ToastUtil.setToast(failMsg);
        Log.d("login_dalog", "loginFail: ===" + failMsg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
