package com.kaneps.mikelis.githubmarketplace.util;

import com.github.florent37.okgraphql.Field;
import com.github.florent37.okgraphql.OkGraphql;
import com.github.florent37.okgraphql.converter.GsonConverter;
import com.google.gson.Gson;
import com.kaneps.mikelis.githubmarketplace.event.ProgressEvent;
import com.kaneps.mikelis.githubmarketplace.models.AllApps;
import com.kaneps.mikelis.githubmarketplace.models.Apps;
import com.kaneps.mikelis.githubmarketplace.models.Edge;
import com.kaneps.mikelis.githubmarketplace.models.FilteredApps;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by AM on 3/29/2018.
 */

public class QueryManager {
    private static final String ENDPOINT = "https://api.github.com/graphql";
    public static List<String> categorieList;
    public static String[] categories = {
            "code-quality",
            "code-review",
            "continuous-integration",
            "dependency-management",
            "deployment",
            "localization",
            "monitoring",
            "project-management",
            "publishing",
            "security",
            "support",
            "testing"
    };
    private static final List<Edge> allCategorieApps = new ArrayList<>();
    public static HashMap<String, Boolean> filterOptions = new HashMap<>();
    private static final int ITEM_COUNT = 5;

    private static OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
        @Nullable
        @Override
        public Request authenticate(Route route, Response response) throws IOException {
            if (response.request().header("Authorization") != null) {
                return null; // Give up, we've already attempted to authenticate.
            }
            return response.request().newBuilder()
                    .header("Authorization", "bearer 3f26c3200589cedef1249d7fa3f8c6d13a300120")
                    .build();
        }
    }).build();

    private static OkGraphql okGraphql = new OkGraphql.Builder()
            .okClient(client)
            .baseUrl(ENDPOINT)
            .build();



    public static void getApps() {
        categorieList = Arrays.asList(QueryManager.categories);
        Observable.fromCallable(new Callable<List>() {
            @Override
            public List call() throws Exception {
                List requests = new ArrayList<>();
                allCategorieApps.clear();
                for (String categorie : categories) {
                    requests.add(okGraphql
                            .query(getAppsRequest(categorie))
                            .toSingle().toObservable());
                    filterOptions.put(categorie, true);
                }
                return requests;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<List>() {
                    @Override
                    public void accept(List list) throws Exception {
                        Observable.mergeDelayError(list)
                                .subscribeOn(Schedulers.io())
                                .observeOn(Schedulers.io())
                                .subscribe(new Consumer<String>() {
                                    @Override
                                    public void accept(String json) throws Exception {
                                        try {
                                            Apps apps = new Gson().fromJson(json, Apps.class);
                                            if (apps == null || apps.getData() == null) {
                                                return;
                                            }
                                            allCategorieApps.addAll(apps.getData().getMarketplaceListings().getEdges());
                                        } catch (IllegalStateException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        if (throwable != null) {
                                            throwable.printStackTrace();
                                        }

                                    }
                                }, new Action() {
                                    @Override
                                    public void run() throws Exception {
                                        //On Completed Data from ALL
                                        EventBus.getDefault().postSticky(new AllApps(allCategorieApps));
                                    }
                                });
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });


    }

    private static String getAppsRequest(String categories) {
        return Field.newField().field(Field.newField("marketplaceListings").argument("first: " + ITEM_COUNT + ", categorySlug: \\\"" + categories + "\\\"")
                .field(Field.newField("edges")
                        .field(Field.newField("node")
                                .field("id,")
                                .field("name,")
                                .field("fullDescription,")
                                .field("companyUrl,")
                                .field("resourcePath,")
                                .field("logoUrl,")
                                .field("shortDescription,")
                                .field(Field.newField("primaryCategory")
                                        .field("description,")
                                        .field("name,")
                                        .field("resourcePath,")
                                        .field("slug")
                                )))).toString().trim().replace("\t", "").replaceAll("\\s+", "");
    }

    public static boolean isChecked(String key) {
        Boolean checked = filterOptions.get(key);
        if (checked == null) {
            checked = false;
        }
        return checked;
    }

    public static void toggleCategories(String key, boolean state) {
        filterOptions.put(key, state);
        List<Edge> filteredCategories = new ArrayList<>();
        for (Edge item : allCategorieApps) {
            if (filterOptions.get(item.getNode().getPrimaryCategory().getSlug())) {
                filteredCategories.add(item);
            }
        }
        EventBus.getDefault().post(new FilteredApps(filteredCategories));
    }

}
