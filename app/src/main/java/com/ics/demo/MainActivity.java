package com.ics.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.keepfun.aiservice.ServiceManager;
import com.keepfun.aiservice.ServiceSystem;
import com.keepfun.aiservice.entity.ServiceUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServiceSystem.initialize(this.getApplication(), Constant.appKey, Constant.appSecret, Constant.privateKey);


        findViewById(R.id.tv_ics_center).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceSystem.setUserInfo(getUserInfo());
                ServiceSystem.startService("password login");
            }
        });
        findViewById(R.id.tv_ics_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceSystem.setUserInfo(getUserInfo());
                ServiceSystem.startServiceList("password login");
            }
        });
        findViewById(R.id.tv_ics_list_only).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServiceSystem.setUserInfo(getUserInfo());
                ServiceSystem.startServiceListOnly("password login");
            }
        });
    }

    public ServiceUser getUserInfo() {
        ServiceUser user = new ServiceUser();
        user.setUserUid("100000");
        user.setUsername("test");
        user.setNickname("test");
        user.setAvatar("");
        user.setMobile("13800000000");
        return user;
    }
}