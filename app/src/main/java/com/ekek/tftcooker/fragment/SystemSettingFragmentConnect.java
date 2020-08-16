package com.ekek.tftcooker.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.TFTCookerApplication;
import com.ekek.tftcooker.base.BaseFragment;
import com.ekek.tftcooker.constants.CataSettingConstant;
import com.ekek.tftcooker.constants.TFTCookerConstant;
import com.ekek.tftcooker.database.SettingPreferencesUtil;
import com.ekek.tftcooker.event.HoodConnectStatusChangedEvent;
import com.ekek.tftcooker.event.SendSystemSettingOrder;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

public class SystemSettingFragmentConnect extends BaseFragment implements GestureDetector.OnGestureListener {

    // Views
    Unbinder unbinder;
    @BindView(R.id.back)
    TextView back;
    @BindView(R.id.click_on)
    ImageView clickOn;
    @BindView(R.id.click_off)
    ImageView clickOff;
    @BindView(R.id.devices)
    TextView devices;
    @BindView(R.id.dot_line)
    ImageView dotLine;
    @BindView(R.id.cata_hood)
    TextView cataHood;
    @BindView(R.id.ok)
    TextView ok;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tvHowTo)
    TextView tvHowTo;
    @BindView(R.id.tvHoodConnection)
    TextView tvHoodConnection;

    // Constants
    private static final int FLING_MIN_DISTANCE = 20;
    private static final int FLING_MIN_VELOCITY = 200;
    private static final int MSG_HOOD_CONNECTION_ACTIVED = 2;

    // Fields
    public MessageHandler handler;
    private GestureDetector gestureDetector;

    // Override functions
    @Override
    public int initLayout() {
        return R.layout.system_setting_connect_layout;
    }
    @Override
    public void initListener() {

    }
    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        handler = new MessageHandler(this);
        gestureDetector = new GestureDetector(this.getActivity(), this);
        intUI();
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
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onStop() {
        super.onStop();
        SettingPreferencesUtil.saveRequireHoodConnection(getActivity(), false);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mRootView != null) {
            int hoodConnectStatus = SettingPreferencesUtil.getHoodConnectStatus(getActivity());
            boolean hoodConnection = hoodConnectStatus == CataSettingConstant.HOOD_CONNECTED;
            setHoodConnectionUI(hoodConnection);
            SettingPreferencesUtil.saveRequireHoodConnection(getActivity(), !hidden && hoodConnection);
            clickOff.setEnabled(true);
            clickOn.setEnabled(true);
            back.setEnabled(true);
            tvTitle.setEnabled(true);
            tvHowTo.setEnabled(true);
        }
    }
    @Override
    public void refreshTextWhenLanguageChanged(Locale locale) {
        tvTitle.setText(R.string.title_connect);
        tvHowTo.setText(R.string.title_how_to_connect_hood);
        tvHoodConnection.setText(R.string.title_hood_connection);
    }

    // GestureDetector.OnGestureListener
    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {

    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {

    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (Math.abs(velocityX) < FLING_MIN_VELOCITY) {
            return false;
        }

        if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE) {
            if (clickOn.isEnabled()) {
                onClick(clickOn);
            }
            return true;
        } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE) {
            if (clickOff.isEnabled()) {
                onClick(clickOff);
            }
            return true;
        }
        return false;
    }

    // Event handlers
    @OnClick({R.id.back, R.id.click_on, R.id.click_off, R.id.ok, R.id.tvHowTo, R.id.tv_title})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
            case R.id.tv_title:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_panel));
                break;
            case R.id.click_on:
                clickOn.setVisibility(View.INVISIBLE);
                clickOff.setVisibility(View.VISIBLE);
                SettingPreferencesUtil.saveHoodConnectStatus(
                        this.getActivity(),
                        CataSettingConstant.HOOD_NOT_CONNECTED);
                EventBus.getDefault().post(new HoodConnectStatusChangedEvent(CataSettingConstant.HOOD_NOT_CONNECTED));
                SettingPreferencesUtil.saveRequireHoodConnection(getActivity(), false);
                if (handler.hasMessages(MSG_HOOD_CONNECTION_ACTIVED)) {
                    handler.removeMessages(MSG_HOOD_CONNECTION_ACTIVED);
                }
                break;
            case R.id.click_off:
                clickOff.setVisibility(View.INVISIBLE);
                clickOn.setVisibility(View.VISIBLE);
                SettingPreferencesUtil.saveHoodConnectStatus(
                        this.getActivity(),
                        CataSettingConstant.HOOD_CONNECTED);
                EventBus.getDefault().post(new HoodConnectStatusChangedEvent(CataSettingConstant.HOOD_CONNECTED));
                SettingPreferencesUtil.saveRequireHoodConnection(getActivity(), true);
                if (handler.hasMessages(MSG_HOOD_CONNECTION_ACTIVED)) {
                    handler.removeMessages(MSG_HOOD_CONNECTION_ACTIVED);
                }
                handler.sendEmptyMessageDelayed(MSG_HOOD_CONNECTION_ACTIVED, 2 * 1000);
                break;
            case R.id.ok:

                break;
            case R.id.tvHowTo:
                EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Show_Setting_connect_how_to_0));
                break;
        }
    }
    @OnTouch({R.id.click_on, R.id.click_off})
    public boolean onTouch(View view, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    // Public functions
    public void setHoodConnectionUI(boolean connected) {
        if (connected) {
            clickOn.setVisibility(View.VISIBLE);
            clickOff.setVisibility(View.INVISIBLE);
        } else {
            clickOff.setVisibility(View.VISIBLE);
            clickOn.setVisibility(View.INVISIBLE);
        }
    }

    // Private functions
    private void intUI() {

        int hoodConnectStatus = SettingPreferencesUtil.getHoodConnectStatus(getActivity());
        boolean hoodConnection = hoodConnectStatus == CataSettingConstant.HOOD_CONNECTED;
        setHoodConnectionUI(hoodConnection);
        SettingPreferencesUtil.saveRequireHoodConnection(getActivity(), hoodConnection);

        devices.setVisibility(View.INVISIBLE);
        dotLine.setVisibility(View.INVISIBLE);
        cataHood.setVisibility(View.INVISIBLE);
    }
    private void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_HOOD_CONNECTION_ACTIVED:
                if (clickOn.getVisibility() == View.VISIBLE) {
                    TFTCookerApplication.getInstance().setHoodConnected(true);
                    EventBus.getDefault().post(new SendSystemSettingOrder(TFTCookerConstant.Setting_connect_complete));
                    clickOff.setEnabled(false);
                    clickOn.setEnabled(false);
                    back.setEnabled(false);
                    tvTitle.setEnabled(false);
                    tvHowTo.setEnabled(false);
                }
                break;
        }
    }

    // Classes
    public static class MessageHandler extends Handler {
        private final WeakReference<SystemSettingFragmentConnect> master;

        private MessageHandler(SystemSettingFragmentConnect master) {
            this.master = new WeakReference<>(master);
        }

        @Override
        public void handleMessage(Message msg) {
            this.master.get().handleMessage(msg);
        }
    }
}
