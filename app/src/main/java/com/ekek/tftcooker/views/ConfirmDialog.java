package com.ekek.tftcooker.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ekek.tftcooker.R;
import com.ekek.tftcooker.base.BaseDialog;

public class ConfirmDialog extends BaseDialog implements View.OnClickListener {

    // Widgets
    TextView tvMessage;
    ImageButton ibOk;
    ImageButton ibCancel;

    // Constants
    public static final int ACTION_OK = 1;
    public static final int ACTION_CANCEL = 2;

    // Fields
    private OnConfirmedListener listener;
    private int paramW;
    private int paramL;
    private Object paramObj;

    // Constructors
    public ConfirmDialog(@NonNull Context context) {
        super(context);
    }

    // Interfaces
    public interface OnConfirmedListener {
        void onConfirm(ConfirmDialog dialog, int action);
    }

    // Override functions
    @Override
    protected void initialize() {
        tvMessage = (TextView)rootView.findViewById(R.id.tvMessage);
        ibOk = (ImageButton)rootView.findViewById(R.id.ibOk);
        ibOk.setOnClickListener(this);
        ibCancel = (ImageButton)rootView.findViewById(R.id.ibCancel);
        ibCancel.setOnClickListener(this);
    }
    @Override
    protected int getLayoutResource() {
        return R.layout.dialog_confirm;
    }
    @Override
    protected int getWidth() {
        return 1280;
    }
    @Override
    protected int getHeight() {
        return 720;
    }

    // Public functions
    public void setMessage(int message) {
        tvMessage.setText(message);
    }
    public void setMessage(String message) {
        tvMessage.setText(message);
    }

    // Private functions
    private void onConfirm(int action) {
        if (listener != null) {
            listener.onConfirm(this, action);
        }
    }

    // Properties
    public void setListener(OnConfirmedListener listener) {
        this.listener = listener;
    }
    public TextView getTvMessage() {
        return tvMessage;
    }
    public void setTvMessage(TextView tvMessage) {
        this.tvMessage = tvMessage;
    }
    public int getParamW() {
        return paramW;
    }
    public void setParamW(int paramW) {
        this.paramW = paramW;
    }
    public int getParamL() {
        return paramL;
    }
    public void setParamL(int paramL) {
        this.paramL = paramL;
    }
    public Object getParamObj() {
        return paramObj;
    }
    public void setParamObj(Object paramObj) {
        this.paramObj = paramObj;
    }

    // Event handlers
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibOk:
                onConfirm(ACTION_OK);
                break;
            case R.id.ibCancel:
                onConfirm(ACTION_CANCEL);
                break;
        }
    }
}
