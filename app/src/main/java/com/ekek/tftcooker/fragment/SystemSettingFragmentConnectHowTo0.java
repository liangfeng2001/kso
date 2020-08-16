package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.SendSystemSettingOrder;

import org.greenrobot.eventbus.EventBus;

import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemSettingFragmentConnectHowTo0 extends BaseFragment {

    @BindView(R.id.back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ibFinish)
    ImageButton ibFinish;

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public int initLayout() {
        return R.layout.system_setting_connect_how_to_layout_0;
    }

    @Override
    public void initListener() {

    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
    }

    @OnClick({R.id.back, R.id.ibFinish, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_connect));
                break;
            case R.id.ibFinish:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_connect_how_to_1));
                break;
        }
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_connect);
    }
}
