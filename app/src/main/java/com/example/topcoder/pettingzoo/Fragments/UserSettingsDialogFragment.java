package com.example.topcoder.pettingzoo.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.topcoder.pettingzoo.R;

public class UserSettingsDialogFragment extends DialogFragment implements View.OnClickListener {
    Context mContext;
    View panel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_user_settings, container);
        view.setOnClickListener(this);
        panel = view;

        // Popup animation for the menu
        Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
        view.startAnimation(popupAnimation);
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
                UserSettingsDialogFragment.this.dismiss();
            }
        }, 200);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // setup dialog: buttons, title etc
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity())
                .setNegativeButton(R.string.dialog_fragment_disagree,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                )
                .setPositiveButton(R.string.dialog_fragment_agree,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        // call default fragment methods and set view for dialog
        View view = onCreateView(getActivity().getLayoutInflater(), null, null);
        onViewCreated(view, null);
        dialogBuilder.setView(view);
        return dialogBuilder.create();
    }
}
