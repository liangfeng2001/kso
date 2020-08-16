package com.ekek.tftcooker.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.event.BluetoothEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;

public class SystemSettingFragmentUpgrade extends BaseFragment {

    // Widgets
    @BindView(R.id.tvDestVersion)
    TextView tvDestVersion;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.pbProgress)
    ProgressBar pbProgress;

    // Constants
    public static final String KEY_TOTAL = "TOTAL";
    public static final String KEY_VERSION = "VERSION";

    // Override functions for BaseFragment
    @Override
    public int initLayout() {
        return R.layout.system_settings_upgrade_layout;
    }
    @Override
    public void initListener() {

    }
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }
    @Override
    protected void refreshTextWhenLanguageChanged(Locale locale) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            registerEventBus();
            if (mRootView != null) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    String dest = String.format(
                            mContext.getResources().getString(R.string.title_destination_software),
                            bundle.getString(KEY_VERSION),
                            bundle.getInt(KEY_TOTAL));
                    tvDestVersion.setText(dest);
                    tvMessage.setText(R.string.msg_downloading_apk);
                }
            }
        } else {
            unregisterEventBus();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BluetoothEvent event) {
        switch (event.getOrder()) {
            case BluetoothEvent.ORDER_START:
                break;
            case BluetoothEvent.ORDER_REPORT_PROGRESS:
                TFTCookerApplication.getInstance().updateLatestTouchTime();
                if (event.getlParam() > 0) {
                    tvMessage.setText(event.getlParam());
                }
                pbProgress.setProgress(event.getwParam());
                break;
            case BluetoothEvent.ORDER_FINISHED:

                Logger.getInstance().i("onMessageEvent(BluetoothEvent.ORDER_FINISHED)");
                pbProgress.setProgress(pbProgress.getMax());
                tvMessage.setText(R.string.msg_download_complete);

                Uri uri = Uri.fromFile(new File(event.getsParam()));
                //安装程序
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                installIntent.setDataAndType(uri,
                        "application/vnd.android.package-archive");
                mContext.startActivity(installIntent);
                break;
        }
    }
}
