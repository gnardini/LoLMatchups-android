package com.gnardini.lolmatchups;

import android.app.Application;
import android.content.Context;

import com.gnardini.lolmatchups.injector.LocalRepositoryInjector;
import com.gnardini.lolmatchups.injector.NetworkInjector;
import com.gnardini.lolmatchups.injector.RepositoryInjector;

public class LolMatchupsApp extends Application {

    private static Context context;

    private LocalRepositoryInjector localRepositoryInjector;
    private NetworkInjector networkInjector;
    private RepositoryInjector repositoryInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext() {
        return context;
    }

    public LocalRepositoryInjector getLocalRepositoryInjector() {
        if (localRepositoryInjector == null) {
            localRepositoryInjector = new LocalRepositoryInjector();
        }
        return localRepositoryInjector;
    }

    public NetworkInjector getNetworkInjector() {
        if (networkInjector == null) {
            networkInjector = new NetworkInjector(getLocalRepositoryInjector());
        }
        return networkInjector;
    }

    public RepositoryInjector getRepositoryInjector() {
        if (repositoryInjector == null) {
            repositoryInjector =
                    new RepositoryInjector(getLocalRepositoryInjector(), getNetworkInjector());
        }
        return repositoryInjector;
    }

}
