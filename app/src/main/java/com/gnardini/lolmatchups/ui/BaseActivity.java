package com.gnardini.lolmatchups.ui;

import android.support.v7.app.AppCompatActivity;

import com.gnardini.lolmatchups.LolMatchupsApp;
import com.gnardini.lolmatchups.injector.LocalRepositoryInjector;
import com.gnardini.lolmatchups.injector.NetworkInjector;
import com.gnardini.lolmatchups.injector.RepositoryInjector;

public abstract class BaseActivity extends AppCompatActivity {

    protected LocalRepositoryInjector getLocalRepositoryInjector() {
        return ((LolMatchupsApp) getApplication()).getLocalRepositoryInjector();
    }

    protected NetworkInjector getNetworkInjector() {
        return ((LolMatchupsApp) getApplication()).getNetworkInjector();
    }

    protected RepositoryInjector getRepositoryInjector() {
        return ((LolMatchupsApp) getApplication()).getRepositoryInjector();
    }

}
