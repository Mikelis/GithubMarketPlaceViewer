package com.kaneps.mikelis.githubmarketplace.adapters;

import android.content.Context;
import android.util.Log;

import com.kaneps.mikelis.githubmarketplace.R;
import com.kaneps.mikelis.githubmarketplace.models.Edge;
import com.kaneps.mikelis.githubmarketplace.util.QueryManager;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by AM on 3/29/2018.
 */

public class FilterAdapter extends GenericRecycleAdapter<String, Holders.TextImageHolder> {
    public FilterAdapter(List<String> list, Context context) {
        super(list, context);
    }

    @Override
    protected void onItem(String edge) {

    }

    @Override
    protected void onItemChecked(String key, boolean isChecked) {
        Log.e("", key);
        QueryManager.toggleCategories(key, isChecked);
    }

    @Override
    public int getLayout() {
        return R.layout.filter_row_layout;
    }

    @Override
    public void onSet(String item, Holders.TextImageHolder holder) {
        holder.text.setText(item);
        holder.box.setChecked(QueryManager.isChecked(item));
        holder.box.setTag(item);
    }
}
