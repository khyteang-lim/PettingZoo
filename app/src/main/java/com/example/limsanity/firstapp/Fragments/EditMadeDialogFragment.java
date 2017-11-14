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

public class EditMadeDialogFragment extends DialogFragment implements View.OnClickListener {
    Context mContext;
    View panel;

    String title;

    public static EditMadeDialogFragment newInstance(String title) {
        EditMadeDialogFragment instance = new EditMadeDialogFragment();
        instance.title = title;
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
        View view = inflater.inflate(R.layout.fixture_edited_popup, container);
        view.setOnClickListener(this);
        panel = view.findViewById(R.id.panel);

        ((TextView)view.findViewById(R.id.editMadeTV)).setText(title);
        view.findViewById(R.id.dismissBtn).setOnClickListener(this);

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
    public void onClick(View view) {
        // Popup animation for the menu
        Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.slide_down);
        popupAnimation.setDuration(100);
        panel.startAnimation(popupAnimation);
        // Once animation is done, close
        panel.postDelayed(new Runnable() {
            @Override
            public void run() {

                EditMadeDialogFragment.this.dismiss();
            }
        }, 100);
    }

    public interface OptionsSelectedItem {
        public void onEditItem();
    }
}
