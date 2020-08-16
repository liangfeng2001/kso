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

public class SystemSettingFragmentConnectHowTo1 extends BaseFragment {

    @BindView(R.id.back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ibFinish)
    ImageButton ibFinish;

    @BindView(R.id.tvTitleConnect)
    TextView tvTitleConnect;
    @BindView(R.id.tvTitleConnect2)
    TextView tvTitleConnect2;
    @BindView(R.id.tvHowTo)
    TextView tvHowTo;
    @BindView(R.id.tvHowTo2)
    TextView tvHowTo2;
    @BindView(R.id.tvHoodConnection)
    TextView tvHoodConnection;
    @BindView(R.id.tvHoodConnection2)
    TextView tvHoodConnection2;

    @Override
    public int initLayout() {
        return R.layout.system_setting_connect_how_to_layout_1;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
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
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_connect_how_to_0));
                break;
            case R.id.ibFinish:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_connect_how_to_complete));
                break;
        }
    }

    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_connect);
        tvTitleConnect.setText(R.string.title_connect);
        tvTitleConnect2.setText(R.string.title_connect);
        tvHowTo.setText(R.string.title_how_to_connect_hood);
        tvHowTo2.setText(R.string.title_how_to_connect_hood);
        tvHoodConnection.setText(R.string.title_hood_connection);
        tvHoodConnection2.setText(R.string.title_hood_connection);
    }
}
