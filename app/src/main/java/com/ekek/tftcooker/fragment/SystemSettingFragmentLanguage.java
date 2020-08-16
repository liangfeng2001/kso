package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.common.CheckIdleThread;
import com.ekek.tftcooker.common.NumberPickerView;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.SendSystemSettingOrder;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SystemSettingFragmentLanguage extends BaseFragment
        implements NumberPickerView.OnValueChangeListener,
        NumberPickerView.OnBottomViewChangeListener,
        CheckIdleThread.CheckIdleThreadListener {

    // Widgets
    Unbinder unbinder;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.set_language)
    NumberPickerView setLanguage;
    @BindView(R.id.FramelayoutTemp_FrameLayout2)
    FrameLayout FramelayoutTempFrameLayout2;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    // Constants
    private static final int HANDLE_OK_BUTTON = 0;
    private static final int HANDLE_IDLE = 1;

    // Fields
    private MessageHandler handler;
    private int language = TFTCookerConstant.LANGUAGE_ENGLISH;
    private CheckIdleThread checkIdleThread;

    // Override functions
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    @Override
    public int initLayout() {
        return R.layout.system_setting_language_layout;
    }
    @Override
    public void initListener() {

    }
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
        initData();
    }
    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_language);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            switch (getArgAppStart()) {
                case TFTCookerConstant.APP_START_FIRST_TIME:
                    back.setVisibility(View.GONE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setEnabled(false);
                    TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_F1);
                    checkIdleThread = CheckIdleThread.start(checkIdleThread, CheckIdleThread.DURATION_FIVE_MINUTES);
                    checkIdleThread.setCheckIdleThreadListener(this);
                    break;
                case TFTCookerConstant.APP_START:
                case TFTCookerConstant.APP_START_NONE:
                    back.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setEnabled(true);
                    TFTCookerApplication.getInstance().setCookerSettingMode(TFTCookerConstant.COOKER_MODE_SETTING);
                    break;
            }
        } else {
            CheckIdleThread.cancel(checkIdleThread);
        }
    }
    @OnClick({R.id.back, R.id.ivOk, R.id.ok, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                if (getArgAppStart() == TFTCookerConstant.APP_START_FIRST_TIME) {
                    EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_back));
                } else {
                    EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));
                }
                break;
            case R.id.ok:
            case R.id.ivOk:
                saveAndQuit(getArgAppStart());
                break;
        }
    }
    @Override
    public void onValueChange(NumberPickerView picker, int oldVal, int newVal) {
        language = newVal;
    }
    @Override
    public void onBottomViewChange(boolean visible) {

    }
    @Override
    public void onIdle() {
        handler.sendEmptyMessage(HANDLE_IDLE);
    }

    // Private functions
    private void initData() {

        //初始化 语言列表
        setLanguage.setDisplayedValues(TFTCookerConstant.Oven_Language_GEAR_LIST);
        setLanguage.setOnValueChangedListener(this);
        setLanguage.setOnBottomViewChangeListener(this);
        setLanguage.setMaxValue(TFTCookerConstant.Oven_Language_GEAR_LIST.length - 1);
        setLanguage.setMinValue(0);
        language = SettingPreferencesUtil.getDefaultLanguage(getActivity());
        if (language == TFTCookerConstant.LANGUAGE_UNDEFINED) {
            language = CataSettingConstant.DEFAULT_LANGUAGE;
        }
        setLanguage.setValue(language);
        setLanguage.setFriction(10 * ViewConfiguration.get(getActivity()).getScrollFriction());
        setLanguage.setContentTextTypeface(TFTCookerApplication.goodHomeLight);
        setLanguage.setHintTextTypeface(TFTCookerApplication.goodHomeLight);
    }
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLE_OK_BUTTON:
                back.setEnabled(true);
                ok.setEnabled(true);
                setLanguage.enable();
                break;
            case HANDLE_IDLE:
                saveAndQuit(TFTCookerConstant.APP_START_IDLE);
                break;
        }
    }
    private void saveAndQuit(int mode) {
        SettingPreferencesUtil.saveDefaultLanguage(getActivity(), language);
        SendSystemSettingOrder order = new SendSystemSettingOrder(TFTCookerConstant.Setting_language_complete);
        order.setParamW(language);
        order.setParamL(mode);
        EventBus.getDefault().post(order);
        back.setEnabled(false);
        ok.setEnabled(false);
        setLanguage.disable();
        handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
    }

    // Classes
    private static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentLanguage> master;

        private MessageHandler(SystemSettingFragmentLanguage master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }
}
