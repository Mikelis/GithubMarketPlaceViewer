package com.kaneps.mikelis.githubmarketplace.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.kaneps.mikelis.githubmarketplace.R;
import com.kaneps.mikelis.githubmarketplace.adapters.FilterAdapter;
import com.kaneps.mikelis.githubmarketplace.util.QueryManager;
import com.kaneps.mikelis.githubmarketplace.views.recyclerview.VerticalRecycleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by AM on 4/2/2018.
 */

public class FilterDialog extends Dialog {

    @BindView(R.id.list)
    protected VerticalRecycleView list;
    private List<String> categorieList;

    public FilterDialog(@NonNull Context context) {
        super(context);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_list_layout);
        ButterKnife.bind(this);


        setAdapter();

    }

    private void setAdapter() {
        list.setAdapter(new FilterAdapter(QueryManager.categorieList, getContext()));
    }
}
