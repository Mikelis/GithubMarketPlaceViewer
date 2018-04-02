package com.kaneps.mikelis.githubmarketplace.fragments;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaneps.mikelis.githubmarketplace.R;
import com.kaneps.mikelis.githubmarketplace.adapters.AppAdapter;
import com.kaneps.mikelis.githubmarketplace.dialog.FilterDialog;
import com.kaneps.mikelis.githubmarketplace.models.AllApps;
import com.kaneps.mikelis.githubmarketplace.models.FilteredApps;
import com.kaneps.mikelis.githubmarketplace.views.recyclerview.VerticalRecycleView;

import net.bohush.geometricprogressview.GeometricProgressView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by AM on 4/2/2018.
 */

public class AppsListFragment extends ParentFragment {
    private static final int STEP = 7;
    @BindView(R.id.list)
    protected VerticalRecycleView list;
    @BindView(R.id.filter)
    FloatingActionButton filter;
    Unbinder unbinder;
    @BindView(R.id.progressView)
    GeometricProgressView progressView;
    private FilterDialog mFilterDailog;
    private AppAdapter mListAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.app_list_layout;
    }

    @Override
    public void getData() {

    }

    @Override
    public void setListeners() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onAppListData(AllApps apps) {
        progressView.setVisibility(View.GONE);

        if (mListAdapter == null) {
            mListAdapter = new AppAdapter(apps.getApps(), getContext());
            filter.setVisibility(View.VISIBLE);
            list.setAdapter(mListAdapter);
        }

        mListAdapter.refresh(apps.getApps());
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onAppListData(FilteredApps apps) {
        if (mListAdapter == null) {
            mListAdapter = new AppAdapter(apps.getApps(), getContext());
            filter.setVisibility(View.VISIBLE);
            list.setAdapter(mListAdapter);
        }
        filter.setVisibility(View.VISIBLE);
        mListAdapter.refresh(apps.getApps());
    }


    @OnClick(R.id.filter)
    public void onViewClicked() {
        if (mFilterDailog != null) {
            if (!mFilterDailog.isShowing()) {
                mFilterDailog.show();
            }
            return;
        }

        mFilterDailog = new FilterDialog(getContext());
        mFilterDailog.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
