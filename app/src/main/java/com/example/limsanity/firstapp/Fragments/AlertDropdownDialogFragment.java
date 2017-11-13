package com.example.limsanity.firstapp.Fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.limsanity.firstapp.R;

public class AlertDropdownDialogFragment extends DialogFragment implements View.OnClickListener {
    Context mContext;
    View panel;

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
            window.setAttributes(windowParams);
        }
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
}
