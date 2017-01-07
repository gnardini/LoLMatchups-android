package com.gnardini.lolmatchups.ui.region;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.gnardini.lolmatchups.R;
import com.gnardini.lolmatchups.model.Region;
import com.gnardini.lolmatchups.repository.common.RegionRepository;
import com.gnardini.lolmatchups.ui.BaseActivity;

public class RegionChooserActivity extends BaseActivity {

    private RegionRepository regionRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.region_chooser_activity);
        injectDependencies();
        addRegions();
    }

    private void injectDependencies() {
        regionRepository = getLocalRepositoryInjector().getRegionRepository();
    }

    private void addRegions() {
        LinearLayout regionList = (LinearLayout) findViewById(R.id.region_list);
        for (Region region : Region.values()) {
            RegionItemView regionItemView = new RegionItemView(this);
            regionItemView.setText(region.toString());
            regionItemView.setOnClickListener(view -> chooseRegion(region));
            regionList.addView(regionItemView);
        }
    }

    private void chooseRegion(Region region) {
        regionRepository.updateRegion(region);
        finish();
    }

}
