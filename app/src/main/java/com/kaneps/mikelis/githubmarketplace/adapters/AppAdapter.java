package com.kaneps.mikelis.githubmarketplace.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;

import com.kaneps.mikelis.githubmarketplace.R;
import com.kaneps.mikelis.githubmarketplace.event.UrlRequest;
import com.kaneps.mikelis.githubmarketplace.models.Edge;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * Created by AM on 3/29/2018.
 */

public class AppAdapter extends GenericRecycleAdapter<Edge, Holders.TextImageHolder> {
    public AppAdapter(List<Edge> list, Context context) {
        super(list, context);
    }

    @Override
    protected void onItem(Edge edge) {
        EventBus.getDefault().post(new UrlRequest(edge.getNode().getCompanyUrl()));
    }

    @Override
    public int getLayout() {
        return R.layout.app_row_layout;
    }

    @Override
    public void onSet(Edge item, Holders.TextImageHolder holder) {
        holder.text.setText(item.getNode().getName());
        holder.descrption.setText(item.getNode().getShortDescription());
        Picasso.get().load(item.getNode().getLogoUrl()).into(holder.image);
    }
}
