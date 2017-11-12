package com.example.limsanity.firstapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.limsanity.firstapp.API.AlertService;
import com.example.limsanity.firstapp.API.ProductService;
import com.example.limsanity.firstapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ProductFragment extends Fragment implements ProductService.OnGetProducts {
    Context mContext;
    ProductService productService;
    RecyclerView productsList;

    View currentView;

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(ProductService service) {
        ProductFragment fragment = new ProductFragment();
        fragment.productService = service;
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
        currentView =  inflater.inflate(R.layout.fragment_product, container, false);
        productsList = currentView.findViewById(R.id.product_list);
        productsList.setHasFixedSize(true);
        productService.getProducts(this);
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
    public void productsLoaded(List<ProductService.Product> list) {
        // Show the list of groups of alerts
        final ProductAdapter adapter = new ProductAdapter(list);
        productsList.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(mContext);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        productsList.setLayoutManager(lm);
        productsList.setItemAnimator(new DefaultItemAnimator());
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductHolder>{
        List<ProductService.Product> list;
        ProductAdapter(List<ProductService.Product> list) {
            this.list = list;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_card_layout, parent,
                            false);
            return new ProductHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            RecyclerView fixture_list = holder.itemView.findViewById(R.id.product_group_list);
            final FixtureAdapter adapter = new FixtureAdapter(list.get(position).Fixtures);
            fixture_list.setAdapter(adapter);
            LinearLayoutManager lm = new LinearLayoutManager(mContext);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            fixture_list.setLayoutManager(lm);
            fixture_list.setItemAnimator(new DefaultItemAnimator());
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class FixtureAdapter extends RecyclerView.Adapter<FixtureHolder>{
        List<ProductService.Fixture> list;
        FixtureAdapter(List<ProductService.Fixture> list) {
            this.list = list;
        }

        @Override
        public FixtureHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.product_item_layout, parent,
                            false);

            return new FixtureHolder(itemView);
        }

        @Override
        public void onBindViewHolder(FixtureHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        ProductHolder(View itemView) {
            super(itemView);
        }
    }

    class FixtureHolder extends RecyclerView.ViewHolder {
        FixtureHolder(View itemView) {
            super(itemView);
        }
    }
}
