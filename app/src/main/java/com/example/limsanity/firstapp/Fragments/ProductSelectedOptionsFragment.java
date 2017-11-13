package com.example.limsanity.firstapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.limsanity.firstapp.R;

/**
 * Created by rkrishnan on 11/13/17.
 */

public class SettingsDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_settings, container);
//        getDialog().setTitle("User Settings");
        return view;
    }
}
