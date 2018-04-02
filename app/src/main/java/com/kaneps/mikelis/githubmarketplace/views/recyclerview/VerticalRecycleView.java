package com.kaneps.mikelis.githubmarketplace.views.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.kaneps.mikelis.githubmarketplace.R;
import com.kaneps.mikelis.githubmarketplace.views.decoration.RecycleViewDecoration;


/**
 * Created by mikelis.kaneps on 01.06.2015.
 */
public class VerticalRecycleView extends RecyclerView {
    private final LinearLayoutManager layoutManager;

    public VerticalRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);

        this.setLayoutManager(layoutManager);
        this.addItemDecoration(new RecycleViewDecoration(getResources().getDrawable(R.drawable.abc_list_divider_mtrl_alpha)));
    }


}
