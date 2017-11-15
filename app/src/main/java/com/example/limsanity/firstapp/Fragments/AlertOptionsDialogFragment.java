package com.example.limsanity.firstapp.Fragments;

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
import android.widget.TextView;

import com.example.limsanity.firstapp.R;

import org.w3c.dom.Text;

/**
 * Created by Limsanity on 11/13/17.
 */

public class AlertOptionsDialogFragment extends DialogFragment implements View.OnClickListener {
    Context mContext;
    View panel;

    String title;
    OptionsSelectedItem callback;

    public static AlertOptionsDialogFragment newInstance(String title, OptionsSelectedItem callback) {
        AlertOptionsDialogFragment instance = new AlertOptionsDialogFragment();
        instance.title = title;
        instance.callback = callback;
        return instance;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = getDialog().getWindow();
            assert window != null;
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(),R.color.colorPrimary));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alert_three_dots_dialog_popup, container);
        view.setOnClickListener(this);
        ((TextView)view.findViewById(R.id.alertSelectTV)).setText(title);
        panel = view.findViewById(R.id.panel);
        panel.setOnClickListener(this);
        // Popup animation for the menu
        Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_up);
        popupAnimation.setDuration(100);
        panel.startAnimation(popupAnimation);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Override
    public void onClick(final View view) {
        // Popup animation for the menu
        Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
        popupAnimation.setDuration(100);
        panel.startAnimation(popupAnimation);
        // Once animation is done, close
        panel.postDelayed(new Runnable() {
            @Override
            public void run() {
                AlertOptionsDialogFragment.this.dismiss();
                if(view.getId() == R.id.panel) {
                    callback.onEditItem();
                }
            }
        }, 100);
    }

    public interface OptionsSelectedItem {
        public void onEditItem();
    }
}
