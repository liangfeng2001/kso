package com.ekek.tftcooker.base;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.utils.ViewUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {
    protected View mRootView;
    protected Context mContext;
    protected OnFragmentListener mListener;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentListener) {
            mListener = (OnFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(initLayout(), container, false);
        this.mContext = getActivity();
        unbinder = ButterKnife.bind(this, mRootView);
        initAllMembersView(savedInstanceState);
        initListener();
        initAllViews();
        return mRootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        refreshTextWhenLanguageChanged(newConfig.locale);
    }

    protected boolean isAttachedContext(){
        return getActivity() != null;
    }
    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }
    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

    public abstract int initLayout();
    public abstract void initListener();
    protected abstract void initAllMembersView(Bundle savedInstanceState);
    protected abstract void refreshTextWhenLanguageChanged(Locale locale);

    private void initAllViews() {
        List<View> allChildrenViews = ViewUtils.getAllChildrenViews(mRootView);
        for (View v: allChildrenViews) {
            v.setSoundEffectsEnabled(false);
            if (v instanceof TextView) {
                ((TextView)v).setTypeface(TFTCookerApplication.goodHomeLight);
            }
        }
    }
    protected int getArgAppStart() {
        int argAppStart = TFTCookerConstant.APP_START_NONE;
        Bundle arguments = getArguments();
        if (arguments != null) {

            argAppStart = arguments.getInt(
                    TFTCookerConstant.ARGUMENT_APP_START,
                    TFTCookerConstant.APP_START_NONE);
        }
        return argAppStart;
    }

    protected void unregisterEventBus() {
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    protected void registerEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public interface OnFragmentListener {

    }
}
