package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.constraint.ConstraintLayout;
import android.view.View;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.time.DigitalClockViewEx;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;


public class CookerIntroFragment extends BaseFragment {

    @BindView(R.id.cl_hob_intro)
    ConstraintLayout clHobIntro;
    @BindView(R.id.dcv)
    DigitalClockViewEx digitalClockView;

    private static final int MSG_UPDATE_UI = 1;

    Unbinder unbinder;
    private MessageHandler handler;

    @Override
    public int initLayout() {
        return R.layout.intromodule_fragment_hob_intro_with_digital_clock;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRootView != null) {
            if (!hidden) {
                updateUI();
            } else {
                SettingPreferencesUtil.saveEnteredClockScreen(getActivity(), false);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        SettingPreferencesUtil.saveEnteredClockScreen(getActivity(), false);
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        digitalClockView.refreshText();
    }

    @OnClick(R.id.cl_hob_intro)
    public void onClick() {
        ((OnHobIntroFragmentListener)mListener).onHobIntroFragmentFinish();
    }

    private void updateUI() {
        if (!isVisible()) {
            SettingPreferencesUtil.saveEnteredClockScreen(getActivity(), false);
            return;
        }
        if (TFTCookerApplication.getInstance().isPoweredOn()) {
            digitalClockView.setVisibility(View.VISIBLE);
            SettingPreferencesUtil.saveEnteredClockScreen(getActivity(), true);
        } else {
            digitalClockView.setVisibility(View.INVISIBLE);
            SettingPreferencesUtil.saveEnteredClockScreen(getActivity(), false);
        }

        handler.sendEmptyMessageDelayed(MSG_UPDATE_UI, 400);
    }

    public interface OnHobIntroFragmentListener extends OnFragmentListener {
        void onHobIntroFragmentFinish();
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<CookerIntroFragment> master;

        private MessageHandler(CookerIntroFragment master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_UI:
                updateUI();
                break;
        }
    }
}
