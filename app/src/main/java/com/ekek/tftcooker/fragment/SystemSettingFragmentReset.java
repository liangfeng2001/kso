package com.ekek.tftcooker.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.base.ProductManager;
import com.ekek.tftcooker.common.BluetoothHelper;
import com.ekek.tftcooker.common.Logger;
import com.ekek.tftcooker.common.SoundUtil;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.HoodConnectStatusChangedEvent;
import com.ekek.tftcooker.event.SendSystemSettingOrder;
import com.ekek.tftcooker.utils.BrightnessUtil;
import com.ekek.tftcooker.utils.ContextUtils;
import com.ekek.tftcooker.views.ToggleButtonView;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;


public class SystemSettingFragmentReset extends BaseFragment implements ToggleButtonView.OnToggleListener {

    // Views
    Unbinder unbinder;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.tvRestoreDefault)
    TextView tvRestoreDefault;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.sv_versions)
    ScrollView svVersions;
    @BindView(R.id.tv_software_version)
    TextView tvSoftwareVersion;
    @BindView(R.id.tv_software_version_title)
    TextView tvSoftwareVersionTitle;
    @BindView(R.id.tv_model_title)
    TextView tvModelTitle;
    @BindView(R.id.tv_model)
    TextView tvModel;
    @BindView(R.id.tv_version_display_title)
    TextView tvVersionDisplayTitle;
    @BindView(R.id.tv_bluetooth_title)
    TextView tvBluetoothTitle;
    @BindView(R.id.tv_version_display)
    TextView tvVersionDisplay;
    @BindView(R.id.tv_bluetooth)
    TextView tvBluetooth;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tbvDemoMode)
    ToggleButtonView tbvDemoMode;
    @BindView(R.id.tvDemoMode)
    TextView tvDemoMode;
    @BindView(R.id.tbvDebugMode)
    ToggleButtonView tbvDebugMode;
    @BindView(R.id.tvDebugMode)
    TextView tvDebugMode;

    // Constants
    private static final int HANDLE_OK_BUTTON = 0;
    private static final long LONG_CLICK_DURATION = 10 * 1000;

    // Fields
    private MessageHandler handler;
    private long longClickStart = 0;

    // Override functions
    @Override
    public int initLayout() {
        return R.layout.system_setting_reset_layout;
    }
    @Override
    public void initListener() {

    }
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
        tvSoftwareVersion.setText("v " + ContextUtils.getVersionName(getActivity()));
        tvModel.setText(ProductManager.getModelInfo());
        if (TFTCookerConstant.BRAND_EKEK.equals(Build.BRAND)) {
            tvVersionDisplay.setText(Build.DISPLAY);
        } else {
            tvVersionDisplay.setText(TFTCookerConstant.VERSION_DISPLAY_UNKNOWN);
        }

        BluetoothHelper helper = TFTCookerApplication.getInstance().getBluetoothHelper();
        tvBluetooth.setText(helper.getBluetoothAddress());
        tbvDemoMode.setListener(this);
        tbvDebugMode.setListener(this);
    }
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
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_reset);
        tvRestoreDefault.setText(R.string.title_restore_default_setting);
        tvDemoMode.setText(R.string.title_demo_mode);
        tvDebugMode.setText(R.string.title_debug_mode);
        tvModelTitle.setText(R.string.title_model);
        tvSoftwareVersionTitle.setText(R.string.title_software_version);
        tvVersionDisplayTitle.setText(R.string.title_version_display);
        tvBluetoothTitle.setText(R.string.title_bluetooth);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRootView != null) {
            showSoftwareVersion(false);
            BluetoothHelper helper = TFTCookerApplication.getInstance().getBluetoothHelper();
            if (hidden) {
                helper.closeDiscoverableTimeout();
            } else {
                helper.setDiscoverableTimeout(120 * 1000);
            }
        }
    }
    @Override
    public void onToggle(ToggleButtonView sender, boolean on) {
        switch (sender.getId()) {
            case R.id.tbvDemoMode:
                SettingPreferencesUtil.saveDemonstrationMode(getActivity(), on);
                TFTCookerApplication.getInstance().setInDemonstrationMode(on);
                break;
            case R.id.tbvDebugMode:
                SettingPreferencesUtil.saveDebugMode(getActivity(), on);
                TFTCookerApplication.getInstance().setInDebugMode(on);
                break;
        }

    }

    // Event handlers
    @OnClick({R.id.back, R.id.ivOk, R.id.ok, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));
                break;
            case R.id.ok:
            case R.id.ivOk:
                restoreSettings();
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Setting_reset_complete));
                back.setEnabled(false);
                ok.setEnabled(false);
                handler.sendEmptyMessageDelayed(HANDLE_OK_BUTTON, 2500);
                break;
        }
    }
    @OnTouch({R.id.rlBackground})
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                handleActionDown(view);
                break;
            case MotionEvent.ACTION_MOVE:
                handleActionMove(view);
                break;
            case MotionEvent.ACTION_UP:
                handleActionUp(view);
                break;
        }

        return true;
    }

    // Private functions
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case HANDLE_OK_BUTTON:
                back.setEnabled(true);
                ok.setEnabled(true);
                break;
        }
    }
    private void handleActionDown(View view) {
        longClickStart = SystemClock.elapsedRealtime();
    }
    private void handleActionMove(View view) {
        switch (view.getId()) {
            case R.id.rlBackground:
                if (SystemClock.elapsedRealtime() - longClickStart >= LONG_CLICK_DURATION) {
                    showSoftwareVersion(true);
                }
                break;
        }
    }
    private void handleActionUp(View view) {
        longClickStart = SystemClock.elapsedRealtime();
    }
    private void restoreSettings() {

        // Language
        SettingPreferencesUtil.saveDefaultLanguage(getActivity(), TFTCookerConstant.LANGUAGE_UNDEFINED);

        // Brightness
        int brightness = Integer.parseInt(CataSettingConstant.DEFAULT_BRIGHTNESS);
        BrightnessUtil.changeAppBrightness(getActivity(), brightness);
        SettingPreferencesUtil.saveDefaultBrightness(getActivity(), "" + brightness);

        // Volume
        int volume = Integer.parseInt(CataSettingConstant.DEFAULT_SOUND_LEVEL);
        SoundUtil.setSystemVolume(getActivity(), volume);
        SettingPreferencesUtil.saveDefaultSound(getActivity(), "" + volume);

        // Stand By
        SettingPreferencesUtil.saveActivationTime(getActivity(), CataSettingConstant.DEFAULT_ACTIVATION_TIME);

        // Filter
        SettingPreferencesUtil.saveFilter(getActivity(), CataSettingConstant.FILTER_NONE);

        // Connect
        SettingPreferencesUtil.saveHoodConnectStatus(
                this.getActivity(),
                CataSettingConstant.HOOD_NOT_CONNECTED);
        EventBus.getDefault().post(new HoodConnectStatusChangedEvent(CataSettingConstant.HOOD_NOT_CONNECTED));
        SettingPreferencesUtil.saveRequireHoodConnection(getActivity(), false);

        // Debug mode
        SettingPreferencesUtil.saveDebugMode(getActivity(), false);
        TFTCookerApplication.getInstance().setInDebugMode(false);

        Logger.getInstance().i("System settings restored.");
    }
    private void showSoftwareVersion(boolean visible) {
        int value = visible ? View.VISIBLE : View.INVISIBLE;
        if (tvSoftwareVersionTitle.getVisibility() != value) {
            tvSoftwareVersionTitle.setVisibility(value);
            tvSoftwareVersion.setVisibility(value);
            svVersions.setVisibility(value);
            tbvDemoMode.setVisibility(value);
            tvDemoMode.setVisibility(value);
            tbvDebugMode.setVisibility(value);
            tvDebugMode.setVisibility(value);
            Boolean inDemoMode = SettingPreferencesUtil.getDemonstrationMode(getActivity());
            TFTCookerApplication.getInstance().setInDemonstrationMode(inDemoMode);
            tbvDemoMode.setSwitchedOn(inDemoMode);

            Boolean inDebugMode = SettingPreferencesUtil.getDebugMode(getActivity());
            TFTCookerApplication.getInstance().setInDebugMode(inDebugMode);
            tbvDebugMode.setSwitchedOn(inDebugMode);
        }
    }

    // Classes
    private static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentReset> master;

        private MessageHandler(SystemSettingFragmentReset master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }
}
