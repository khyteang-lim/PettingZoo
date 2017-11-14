package com.example.limsanity.firstapp.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.limsanity.firstapp.API.AlertService;
import com.example.limsanity.firstapp.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlertFragment extends Fragment implements AlertService.OnGetAlerts {

    Context mContext;
    AlertService alertService;
    RecyclerView alerts_list;

    View currentView;

    AlertCallback callback;

    public AlertFragment() {
        // Required empty public constructor
    }

    public static AlertFragment newInstance(AlertService service, AlertCallback callback) {
        AlertFragment fragment = new AlertFragment();
        fragment.alertService = service;
        fragment.callback = callback;
        return fragment;
    }

    public void updateSelectedItems() {
        int numItems = 0;
        for(AlertGroupHolder holder : ((AlertsGroupAdapter)alerts_list.getAdapter()).holders) {
            for(AlertHolder alertHolder : ((AlertAdapter)holder.list_view.getAdapter()).holders) {
                if(((CheckBox)alertHolder.itemView.findViewById(R.id.alertSelectedCB)).isChecked()) {
                    numItems++;
                }
            }
        }
        CardView cardView = currentView.findViewById(R.id.alertItemSelectedView);
        if(numItems > 0) {
            if(cardView.getVisibility() == View.GONE) {
                // Popup animation for the menu
                Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
                cardView.startAnimation(popupAnimation);
                cardView.setVisibility(View.VISIBLE);
            }
            ((TextView)cardView.findViewById(R.id.itemSelectedTV))
                    .setText(String.format(Locale.ENGLISH, "%d item(s) selected", numItems));
        } else {
            if(cardView.getVisibility() == View.VISIBLE) {
                // Popup animation for the menu
                Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
                cardView.startAnimation(popupAnimation);
                cardView.setVisibility(View.GONE);
            }
        }
    }

    public void alertThreeDotsClicked(AlertService.Alert alert) {
        CardView cardView = currentView.findViewById(R.id.alertItemSelectedView);
        if (cardView.getVisibility() == View.GONE){
            Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_in);
            cardView.startAnimation(popupAnimation);
            cardView.setVisibility(View.VISIBLE);
            ((TextView)cardView.findViewById(R.id.alertSelectTV))
                    .setText(alert.name);
        }
        else {
            // Popup animation for the menu
            Animation popupAnimation = AnimationUtils.loadAnimation(mContext, R.anim.fade_out);
            cardView.startAnimation(popupAnimation);
            cardView.setVisibility(View.GONE);
        }

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
        currentView =  inflater.inflate(R.layout.fragment_alert, container, false);
        alerts_list = currentView.findViewById(R.id.alert_group_list);
        alerts_list.setHasFixedSize(true);
        alertService.getAlerts(this, new Date[]{
                // Last two hours
                new Date(1000 * 60 * 60 * 2),
                // Last five days
                new Date(1000 * 60 * 60 * 24 * 5)
        });
        return currentView;
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
        // Show the list of groups of alerts
        final AlertsGroupAdapter adapter = new AlertsGroupAdapter(list);
        alerts_list.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        alerts_list.setLayoutManager(lm);
        alerts_list.setItemAnimator(new DefaultItemAnimator());

        // Show the date of the first item
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd",
                Locale.ENGLISH);
        ((TextView)(currentView.findViewById(R.id.currentDateTV))).setText(
            format.format(AlertService.parseDate(list.get(0).get(0).date)));
    }

    public class AlertsGroupAdapter extends RecyclerView.Adapter<AlertGroupHolder>{
        List<List<AlertService.Alert>> list;
        List<AlertGroupHolder> holders = new ArrayList<>();

        AlertsGroupAdapter(List<List<AlertService.Alert>> list_grouped) {
            this.list = list_grouped;
        }

        @Override
        public AlertGroupHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alert_group_layout, parent,
                            false);
            AlertGroupHolder holder = new AlertGroupHolder(itemView, list);
            holders.add(holder);
            return holder;
        }

        @Override
        public void onBindViewHolder(AlertGroupHolder holder, int position) {
            RecyclerView list = holder.itemView.findViewById(R.id.alert_list);
            list.setAdapter(new AlertAdapter(holder.list.get(position)));
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            list.setLayoutManager(lm);
            list.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class AlertAdapter extends RecyclerView.Adapter<AlertHolder>{
        List<AlertService.Alert> list;
        List<AlertHolder> holders = new ArrayList<>();

        AlertAdapter(List<AlertService.Alert> list) {
            this.list = list;
        }

        @Override
        public AlertHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.alert_item_layout, parent,
                            false);
            AlertHolder holder = new AlertHolder(itemView);
            holders.add(holder);
            return holder;
        }

        @Override
        public void onBindViewHolder(final AlertHolder holder, int position) {
            ((TextView)holder.itemView.findViewById(R.id.alertNameTV))
                    .setText(list.get(position).name);
            ((TextView)holder.itemView.findViewById(R.id.alertDescTV))
                    .setText(list.get(position).description);
            ((TextView)holder.itemView.findViewById(R.id.alertDateTV))
                    .setText(list.get(position).date);
            final ImageView threeDots = holder.itemView.findViewById(R.id.alertThreeDotsIB);
            threeDots.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    alertThreeDotsClicked(list.get(holder.getAdapterPosition()));
                //callback.onAlertOptions(list.get(holder.getAdapterPosition()));
                }
            });

            ((CheckBox) holder.itemView.findViewById(R.id.alertSelectedCB)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    updateSelectedItems();
                }
            });

            // If the alert is completed, mark it with a checkmark
            if(!list.get(position).status.equals("Active")) {
                ((ImageView)holder.itemView.findViewById(R.id.alarmIcon))
                        .setImageDrawable(ContextCompat.getDrawable(mContext,
                                R.drawable.ic_check_green_24dp));
            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class AlertGroupHolder extends RecyclerView.ViewHolder {
        List<List<AlertService.Alert>> list;
        RecyclerView list_view;
        AlertGroupHolder(View itemView, List<List<AlertService.Alert>> list) {
            super(itemView);
            this.list = list;
            this.list_view = itemView.findViewById(R.id.alert_list);
        }
    }

    class AlertHolder extends RecyclerView.ViewHolder {
        AlertHolder(View itemView) {
            super(itemView);
        }
    }

    public interface AlertCallback {
        void onAlertOptions(AlertService.Alert alert);
    }
}
