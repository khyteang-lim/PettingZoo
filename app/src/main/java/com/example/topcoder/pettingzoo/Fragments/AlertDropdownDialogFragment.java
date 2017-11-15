package com.example.topcoder.pettingzoo.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.topcoder.pettingzoo.R;

public class AlertDropdownDialogFragment extends DialogFragment implements View.OnClickListener {
    Context mContext;
    View panel;

    AlertDropdownInterface callback;

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            assert window != null;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0;
            windowParams.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimaryDark));
            window.setAttributes(windowParams);
        }
    }

    public static AlertDropdownDialogFragment newInstance(AlertDropdownInterface callback) {
        AlertDropdownDialogFragment fragment = new AlertDropdownDialogFragment();
        fragment.callback = callback;
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DropdownDialog);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_three_dot_header_window_pop, container);
        view.setOnClickListener(this);
        panel = view.findViewById(R.id.panel);

        view.findViewById(R.id.connectedDeviceCL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onClickConnectedDevices();
            }
        });

        // Popup animation for the menu
        Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        panel.startAnimation(popupAnimation);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onClick(View view) {
        // Popup animation for the menu
        Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
        panel.startAnimation(popupAnimation);
        // Once animation is done, close
        panel.postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertDropdownDialogFragment.this.dismiss();
            }
        }, 200);
    }

    public interface AlertDropdownInterface {
        public void onClickConnectedDevices();
    }
}
