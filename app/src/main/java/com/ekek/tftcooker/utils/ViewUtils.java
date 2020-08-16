package com.ekek.tftcooker.utils;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekek.tftcooker.R;

import java.util.ArrayList;
import java.util.List;

public class ViewUtils {

    public static final int STRING_RESOURCE_ID_NONE = -1;

    public static List<View> getAllChildrenViews(View view) {
        List<View> allChildren = new ArrayList<View>();
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup)view;
            for (int i = 0; i < vp.getChildCount(); i++) {

                View child = vp.getChildAt(i);
                allChildren.add(child);
                allChildren.addAll(getAllChildrenViews(child));
            }
        }
        return allChildren;
    }

    public static void setText(TextView textView, String value) {
        textView.setText(value);
        textView.setTag(R.id.tag_string_resource, STRING_RESOURCE_ID_NONE);
    }
    public static void setText(TextView textView, int value) {
        textView.setText(value);
        textView.setTag(R.id.tag_string_resource, value);
    }
    public static void refreshText(TextView textView) {
        Object tag = textView.getTag(R.id.tag_string_resource);
        if (tag == null) return;
        int stringResource = (int)tag;
        if (stringResource == STRING_RESOURCE_ID_NONE) return;
        textView.setText(stringResource);
    }

    public static boolean isShade(View view) {

        if (view.getVisibility() != View.VISIBLE) {
            return true;
        }

        View currentView = view;
        Rect currentViewRect = new Rect();
        boolean isCovered = currentView.getGlobalVisibleRect(currentViewRect);
        if(!isCovered){
            // 如果在屏幕外肯定是不可见的
            return true;
        }

        if (currentViewRect.width() * currentViewRect.height() <= view.getMeasuredHeight() * view.getMeasuredWidth() / 2) {
            // 如果移出屏幕的面积大于 50% 则认为被遮罩了
            return true;
        }

        // 记录下被移出屏幕外的面积
        int outScreenArea = view.getMeasuredHeight() * view.getMeasuredWidth() - currentViewRect.width() * currentViewRect.height();
        // 循环查找父布局及兄弟布局
        while (currentView.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();

            if (currentParent.getVisibility() != View.VISIBLE) {
                // 如果父控件不可见
                return true;
            }

            int start = indexOfViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++) {
                View otherView = currentParent.getChildAt(i);
                // 这里主要是为了排除 invisible 属性标记的 view
                if (otherView.getVisibility() != View.VISIBLE) {
                    break;
                }
                Rect viewRect = new Rect();
                Rect otherViewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                otherView.getGlobalVisibleRect(otherViewRect);

                // 这个方法用来检测两个区域是否重叠，并且如果重叠的话
                // 就将当前 Rect 修改为重叠的区域
                if (otherViewRect.intersect(viewRect)) {
                    if ((outScreenArea + otherViewRect.width() * otherViewRect.height())
                            >= viewRect.width() * viewRect.height() / 2)
                        // 表示相交区域 + 屏幕外的区域 大于 50% 则也认为被遮罩了
                        return true;
                }
            }
            currentView = currentParent;
        }
        return false;
    }

    public static int indexOfViewInParent(View view, ViewGroup parent) {
        int index = 0;
        // 查找出应该从第几个子 view 开始 参考事件分发机制从用户可以见到的最上层开始分发，
        // 最上层的子 view，index 值总是更大
        while (index < parent.getChildCount()) {
            if (parent.getChildAt(index) == view) {
                break;
            }

            index++;
        }
        return index;
    }
}
