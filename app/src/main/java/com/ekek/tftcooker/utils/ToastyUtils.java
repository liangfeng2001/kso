package com.ekek.tftcooker.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ekek.tftcooker.R;

import es.dmoral.toasty.Toasty;

/**
 * Created by Samhung on 2017/5/16.
 */

public class ToastyUtils {

/*    public static void error(Context context, CharSequence message) {
        Toasty.error(context, message.toString()).show();
    }

    public static void success(Context context, CharSequence message) {
        Toasty.success(context, message.toString()).show();
    }*/

    public static void successAtPlace(Context context, CharSequence message) {
        Toasty.success(context, message.toString()).show();

    }

    public static void error(Context context, CharSequence message) {
        Toast toast = new Toast(context);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_view_fail, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message_toast);
        //设置文本
        tvMessage.setText(message);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();
    }

    public static void success(Context context, CharSequence message) {
        Toast toast = new Toast(context);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.CENTER, 0, 0);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.layout_toast_view_success, null);
        TextView tvMessage = (TextView) view.findViewById(R.id.tv_message_toast);
        //设置文本
        tvMessage.setText(message);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();
    }
}
