package com.example.limsanity.firstapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.limsanity.firstapp.API.AlertService;
import com.example.limsanity.firstapp.R;

import java.util.Date;
import java.util.List;

public class AlertFragment extends Fragment implements AlertService.OnGetAlerts {

    Context mContext;
    AlertService alertService;
    RecyclerView alerts_list;

    public AlertFragment() {
        // Required empty public constructor
    }

    public static AlertFragment newInstance(AlertService service) {
        AlertFragment fragment = new AlertFragment();
        fragment.alertService = service;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_alert, container, false);
        alerts_list = view.findViewById(R.id.alert_group_list);
        alerts_list.setHasFixedSize(true);
        alertService.getAlerts(this, new Date[]{
                // Last two hours
                new Date(0, 0, 0, 2, 0),
                // Last five days
                new Date(0, 0, 5, 0, 0)
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    @Override
    public void postsLoaded(List<List<AlertService.Alert>> list) {
        final AlertsGroupAdapter adapter = new AlertsGroupAdapter(list);
        alerts_list.setAdapter(adapter);
        alerts_list.setLayoutManager(new LinearLayoutManager(mContext));
        alerts_list.setItemAnimator(new DefaultItemAnimator());
    }

    public class AlertsGroupAdapter extends RecyclerView.Adapter<AlertGroupHolder>{
        List<List<AlertService.Alert>> list;

        AlertsGroupAdapter(List<List<AlertService.Alert>> list_grouped) {
            this.list = list_grouped;
        }

        @Override
        public AlertGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alert_group_layout, parent,
                            false);

            return new AlertGroupHolder(itemView, list);
        }

        @Override
        public void onBindViewHolder(AlertGroupHolder holder, int position) {
            RecyclerView list = holder.itemView.findViewById(R.id.alert_list);
            list.setAdapter(new AlertAdapter(holder.list.get(position)));
            alerts_list.setLayoutManager(new LinearLayoutManager(mContext));
            alerts_list.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class AlertAdapter extends RecyclerView.Adapter<AlertHolder>{
        List<AlertService.Alert> list;

        AlertAdapter(List<AlertService.Alert> list) {
            this.list = list;
        }

        @Override
        public AlertHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alert_group_layout, parent,
                            false);
            return new AlertHolder(itemView);
        }

        @Override
        public void onBindViewHolder(AlertHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class AlertGroupHolder extends RecyclerView.ViewHolder {
        List<List<AlertService.Alert>> list;
        AlertGroupHolder(View itemView, List<List<AlertService.Alert>> list) {
            super(itemView);
            this.list = list;
        }
    }

    class AlertHolder extends RecyclerView.ViewHolder {
        AlertHolder(View itemView) {
            super(itemView);
        }
    }
}
