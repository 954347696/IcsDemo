package com.ics.demo.ui.act;

import android.Manifest;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.ics.demo.Constant;
import com.ics.demo.LoginApiDomain;
import com.keepfun.aiservice.ServiceSystem;
import com.keepfun.aiservice.entity.ServiceUser;
import com.keepfun.aiservice.network.OkHttpUtils;
import com.keepfun.aiservice.network.myokhttp.response.GsonResponseHandler;
import com.keepfun.aiservice.ui.impl.CheckClickListener;
import com.keepfun.aiservice.utils.JsonUtil;
import com.keepfun.base.PanActivity;
import com.keepfun.blankj.util.ActivityUtils;
import com.keepfun.blankj.util.KeyboardUtils;
import com.keepfun.blankj.util.StringUtils;
import com.ics.demo.R;
import com.ics.demo.entity.GlCountryEntity;
import com.ics.demo.entity.UserBasicInfoBean;

import java.util.HashMap;


/**
 * @author yang
 * @description
 * @date 2020/9/5 4:16 PM
 */
public class PwdLoginActivity extends PanActivity implements View.OnClickListener {

    EditText et_login_user;
    EditText et_login_pwd;
    ImageView iv_clear;
    TextView tv_country_code;

    private static final int REQUEST_COUNTRY_CODE = 0x11;
    private GlCountryEntity glCountryEntity;

    @Override
    public int getLayoutId() {
        return R.layout.service_activity_pwd_login;
    }

    @Override
    public String[] getPermissions() {
        return new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    }


    @Override
    public void bindUI(View rootView) {
        et_login_user = findViewById(R.id.et_login_user);
        et_login_pwd = findViewById(R.id.et_login_pwd);
        iv_clear = findViewById(R.id.iv_clear);
        tv_country_code = findViewById(R.id.tv_country_code);
    }

    @Override
    public void initData() {

    }

    @Override
    public void bindEvent() {
        findViewById(R.id.tv_login).setOnClickListener(new CheckClickListener(this));
        findViewById(R.id.tv_code).setOnClickListener(new CheckClickListener(this));
        findViewById(R.id.iv_clear).setOnClickListener(new CheckClickListener(this));
        findViewById(R.id.tv_country_code).setOnClickListener(new CheckClickListener(this));
        et_login_user.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String content = et_login_user.getText().toString();
                iv_clear.setVisibility(StringUtils.isEmpty(content) ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                KeyboardUtils.hideSoftInput(this);
                String user = et_login_user.getText().toString().trim();
                if (StringUtils.isEmpty(user)) {
                    showToast("请输入用户名/手机号");
                    return;
                }
                String pwd = et_login_pwd.getText().toString().trim();
                if (StringUtils.isEmpty(pwd)) {
                    showToast("请输入密码");
                    return;
                }
                showLoading();
                login(user, pwd);
                break;
            case R.id.tv_code:
                ActivityUtils.startActivity(CodeLoginActivity.class);
                break;
            case R.id.iv_clear:
                et_login_user.setText("");
                break;
            case R.id.tv_country_code:
                ActivityUtils.startActivityForResult(this, SelectCountryActivity.class, REQUEST_COUNTRY_CODE);
                break;
            default:
                break;
        }
    }

    private void login(String user, String pwd) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("password", pwd);
        params.put("phone", user);
        params.put("countryId", glCountryEntity == null ? "" : glCountryEntity.getId());
        HashMap<String, String> headers = new HashMap<>();
        headers.put("appCode", "keepfun_sportlive");
        headers.put("terminalType", "1");
        OkHttpUtils.getClient().post()
                .url(Constant.loginUrl + LoginApiDomain.LOGIN_PWD)
                .headers(headers)
                .jsonParams(JsonUtil.encode(params))
                .enqueue(new GsonResponseHandler<String>() {

                    @Override
                    public void onFailure(String s, String s1) {
                        dismissLoading();
                    }

                    @Override
                    public void onSuccess(String token) {
                        getUserInfo(token);
                    }
                });
    }

    private void getUserInfo(String result) {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("token", result);
        headers.put("appCode", "keepfun_sportlive");
        headers.put("terminalType", "1");
        headers.put("Content-Type", "application/json;charset=utf-8");
        OkHttpUtils.getClient().get()
                .url(Constant.loginUrl + LoginApiDomain.USER_BASE_INFO)
                .headers(headers)
                .enqueue(new GsonResponseHandler<UserBasicInfoBean>() {

                    @Override
                    public void onFailure(String s, String s1) {
                        dismissLoading();
                    }

                    @Override
                    public void onSuccess(UserBasicInfoBean userInfo) {
                        dismissLoading();
                        if (userInfo != null) {
                            ServiceUser user = new ServiceUser();
                            user.setUserUid(userInfo.getUserNo());
                            user.setUsername(userInfo.getPhone());
                            user.setNickname(userInfo.getNickname());
                            user.setAvatar(userInfo.getAvatar());
                            user.setMobile(userInfo.getPhone());
                            ServiceSystem.setUserInfo(user);
                            ServiceSystem.startServiceList("password login");
                        }
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK || data == null) {
            return;
        }
        if (requestCode == REQUEST_COUNTRY_CODE) {
            glCountryEntity = (GlCountryEntity) data.getSerializableExtra("country");
            tv_country_code.setText("+" + glCountryEntity.getCode());
        }
    }
}
