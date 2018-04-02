package com.kaneps.mikelis.githubmarketplace;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;

import com.kaneps.mikelis.githubmarketplace.adapters.AppAdapter;
import com.kaneps.mikelis.githubmarketplace.event.UrlRequest;
import com.kaneps.mikelis.githubmarketplace.fragments.AppsListFragment;
import com.kaneps.mikelis.githubmarketplace.models.Apps;
import com.kaneps.mikelis.githubmarketplace.models.Edge;
import com.kaneps.mikelis.githubmarketplace.util.QueryManager;
import com.kaneps.mikelis.githubmarketplace.views.recyclerview.VerticalRecycleView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new AppsListFragment()).commit();
        QueryManager.getApps();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUrl(UrlRequest request) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(request.getUrl()));
    }
}
