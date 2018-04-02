package com.kaneps.mikelis.githubmarketplace.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AM on 4/2/2018.
 */

public class FilteredApps {
    private List<Edge> apps = new ArrayList<>();

    public FilteredApps(List<Edge> apps) {
        this.apps = apps;
    }

    public List<Edge> getApps() {
        return apps;
    }
}
