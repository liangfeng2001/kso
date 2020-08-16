package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.common.CheckIdleThread;
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

public class SystemSettingFragmentFilter extends BaseFragment implements CheckIdleThread.CheckIdleThreadListener  {
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.llTop)
    RelativeLayout llTop;
    @BindView(R.id.carbon_image)
    TextView tvCarbonImage;
    @BindView(R.id.frame_layout_carbon)
    FrameLayout flCarbon;
    @BindView(R.id.frame_layout_aluminium)
    FrameLayout flAluminium;
    @BindView(R.id.aluminium_image)
    TextView tvAluminiumImage;
    @BindView(R.id.ivOk)
    ImageView ivOk;
    @BindView(R.id.ok)
    TextView ok;
    Unbinder unbinder;

    private static final int HANDLE_OK_BUTTON = 0;
    private static final int HANDLE_IDLE = 1;

    private MessageHandler handler;
    private int filter;
    private CheckIdleThread checkIdleThread;

    @Override
    public int initLayout() {
        return R.layout.system_setting_filter_layout;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
        filter = SettingPreferencesUtil.getFilter(getActivity());
        refreshUI();
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_filter);
    }

    private void refreshUI() {
        tvCarbonImage.setBackground(getActivity().getResources().getDrawable(R.mipmap.setting_carbon));
        tvAluminiumImage.setBackground(getActivity().getResources().getDrawable(R.mipmap.setting_aluminium));

        switch (filter) {
            case CataSettingConstant.FILTER_NONE:
                break;
            case CataSettingConstant.FILTER_CARBON_150:
                tvCarbonImage.setBackground(getActivity().getResources().getDrawable(R.mipmap.setting_carbon_selected));
                break;
            case CataSettingConstant.FILTER_ALUMINIUM_50:
                tvAluminiumImage.setBackground(getActivity().getResources().getDrawable(R.mipmap.setting_aluminium_selected));
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
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
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            filter = SettingPreferencesUtil.getFilter(getActivity());
            refreshUI();
            if (getArgAppStart() == TFTCookerConstant.APP_START_FIRST_TIME) {
                checkIdleThread = CheckIdleThread.start(checkIdleThread, CheckIdleThread.DURATION_FIVE_MINUTES);
                checkIdleThread.setCheckIdleThreadListener(this);
            }
        } else {
            CheckIdleThread.cancel(checkIdleThread);
        }
    }

    @OnClick({R.id.back, R.id.ok, R.id.ivOk, R.id.tv_title, R.id.frame_layout_carbon, R.id.frame_layout_aluminium})
    public void onClick(View view) {
        SendSystemSettingOrder order;
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                if (getArgAppStart() == TFTCookerConstant.APP_START_FIRST_TIME) {
                    order = new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_time);
                    order.setParamL(getArgAppStart());
                    EventBus.getDefault().post(order);
                } else {
                    EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));  // 点击 back 按钮
                }
                break;
            case R.id.frame_layout_carbon:
                filter = CataSettingConstant.FILTER_CARBON_150;
                refreshUI();
                break;
            case R.id.frame_layout_aluminium:
                filter = CataSettingConstant.FILTER_ALUMINIUM_50;
                refreshUI();
                break;
            case R.id.ivOk:
            case R.id.ok:
                saveAndQuit(getArgAppStart());
                break;
        }
    }

    private void saveAndQuit(int mode) {
        if (filter == CataSettingConstant.FILTER_NONE) {
            return;
        }
        SettingPreferencesUtil.saveFilter(getActivity(), filter);
        SendSystemSettingOrder order = new SendSystemSettingOrder(TFTCookerConstant.Setting_filter_complete);
        order.setParamL(mode);
        EventBus.getDefault().post(order);
        back.setEnabled(false);
        ok.setEnabled(false);
        flCarbon.setEnabled(false);
        flAluminium.setEnabled(false);
        handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
    }

    @Override
    public void onIdle() {
        handler.sendEmptyMessage(HANDLE_IDLE);
    }

    private static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentFilter> master;

        private MessageHandler(SystemSettingFragmentFilter master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }

    private void handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLE_OK_BUTTON:
                back.setEnabled(true);
                ok.setEnabled(true);
                flCarbon.setEnabled(true);
                flAluminium.setEnabled(true);
                break;
            case HANDLE_IDLE:
                saveAndQuit(TFTCookerConstant.APP_START_IDLE);
                break;
        }
    }
}
