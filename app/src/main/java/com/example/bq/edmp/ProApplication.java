package com.example.bq.edmp;


import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.weavey.loading.lib.LoadingLayout;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by GaoSheng on 2016/9/13.
 * 应用,主要用来做一下初始化的操作
 */

public class ProApplication extends Application {
    private static Context mContext;
    private String a="git 测试";
    // 保存所有的Activity
    private List<Activity> activityList;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        initLodingLayout();

        AutoLayoutConifg.getInstance().useDeviceSize();
    }
    private void initLodingLayout() {
        LoadingLayout.getConfig()
                .setErrorText("出错啦~请稍后重试！")
                .setEmptyText("抱歉，暂无数据")
                .setNoNetworkText("无网络连接，请检查您的网络···")
                /*.setErrorImage(R.mipmap.ic_launcher)
                .setEmptyImage(R.mipmap.ic_launcher)
                .setLoadingPageLayout(R.layout.loading)
                .setNoNetworkImage(R.mipmap.ic_launcher)*/
                .setAllTipTextColor(android.R.color.darker_gray)
                .setAllTipTextSize(14)
                .setReloadButtonText("点我重试哦")
                .setReloadButtonTextSize(14)
                .setReloadButtonTextColor(android.R.color.darker_gray)
                .setReloadButtonWidthAndHeight(50, 40);
    }

    /**
     * @return
     * 全局的上下文
     */
    public static Context getmContext() {
        return mContext;
    }

    /**
     * 添加activity到activityList集合中
     *
     * @param activity 每一個activity
     */
    public void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new ArrayList<Activity>();
        }
        activityList.add(activity);
    }

    public int getListSize() {
        if (activityList != null) {
            return activityList.size();
        }
        return 0;
    }

    public void removeActivity(Activity activity) {
        if (activityList != null) {

            if (activityList.contains(activity)) {
                activityList.remove(activity);
            }
        }

    }

    /**
     * 清空列表，取消引用
     */
    public void clearActivity() {
        activityList.clear();
    }

    /**
     * app退出
     */
    public void exit() {
        for (Activity activity : activityList) {
            if (!activity.isFinishing() && activity != null) {
                activity.finish();
            }
        }
        clearActivity();
        System.exit(0);
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Activity activity) {

        if (activity != null) {
            activityList.remove(activity);
            activity.finish();
            activity = null;

        }

    }

    /**
     * 结束指定类名的Activity
     *
     * @param cls
     */
    public void finishActivity(Class<?> cls) {
        for (int i = 0; i < activityList.size(); i++) {

            if (activityList.get(i).getClass().equals(cls)) {
                finishActivity(activityList.get(i));
            }
        }

    }
}
