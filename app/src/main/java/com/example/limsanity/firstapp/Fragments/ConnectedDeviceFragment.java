package com.example.limsanity.firstapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.limsanity.firstapp.API.SettingsService;
import com.example.limsanity.firstapp.R;

public class ConnectedDeviceFragment extends Fragment {
    SettingsService settingsService;

    public ConnectedDeviceFragment() {
        // Required empty public constructor
    }

    public static ConnectedDeviceFragment newInstance(SettingsService settingsService) {
        ConnectedDeviceFragment fragment = new ConnectedDeviceFragment();
        fragment.settingsService = settingsService;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connected_device, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
