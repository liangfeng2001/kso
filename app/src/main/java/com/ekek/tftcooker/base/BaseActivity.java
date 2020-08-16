package com.ekek.tftcooker.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    // Fields
    protected Context activity;
    protected FragmentManager fragmentManager;
    protected FragmentTransaction transaction;
    protected List<Fragment> fragments;
    protected boolean isPaused;

    // Abstract functions
    /**
     * 获取布局文件
     * @return 布局文件资源ID
     */
    public abstract int initLyaout();
    public abstract void initView();
    protected abstract void initListener();
    /**
     * 获取装fragment的容器ID
     * @return
     */
    protected abstract int getContainerID();

    // Override functions
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLyaout());
        getWindow().setBackgroundDrawable(null);
        activity = this;
        ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();
        fragments = new ArrayList<>();
        this.initView();
        this.initListener();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragments.clear();
    }
    @Override
    protected void onResume() {
        super.onResume();
        isPaused = false;
        hideBottomUIMenu();
    }
    @Override
    protected void onPause() {
        super.onPause();
        isPaused = true;
    }

    // Public functions
    public void showFragment(Fragment fragment) {
        transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.add(getContainerID(), fragment);
            transaction.show(fragment);
        }
        transaction.commit();
    }
    public void hideAllFragment(FragmentTransaction transaction) {
        for (int i = 0; i < fragments.size(); i++) {
            transaction.hide(fragments.get(i));
        }
    }


    // Protected functions
    /**
     * 隐藏虚拟按键，并且全屏
     */
    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }
}
