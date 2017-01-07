package com.gnardini.lolmatchups.ui.mathups;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gnardini.lolmatchups.R;
import com.gnardini.lolmatchups.model.GameData;
import com.gnardini.lolmatchups.model.RepoError;
import com.gnardini.lolmatchups.model.Role;
import com.gnardini.lolmatchups.repository.RepoCallback;
import com.gnardini.lolmatchups.repository.common.MatchCalculatorRepository;
import com.gnardini.lolmatchups.repository.common.RegionRepository;
import com.gnardini.lolmatchups.repository.common.StringValueRepository;
import com.gnardini.lolmatchups.ui.BaseActivity;
import com.gnardini.lolmatchups.ui.region.RegionChooserActivity;
import com.gnardini.lolmatchups.utils.KeyboardUtils;

public class MatchupsActivity extends BaseActivity {

    private RegionRepository regionRepository;
    private StringValueRepository summonerSearchRepository;
    private MatchCalculatorRepository matchCalculatorRepository;

    private View resultsHolder;
    private TextView errorView;
    private View loadingView;

    private EditText summonerName;
    private View resetTextButton;
    private View searchButton;
    private TextView regionChooser;

    private RoleMatchupView[] roleMatchups;
    private RoleMatchupView teamResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matchups_activity);
        injectDependencies();
        bindUiActions();
        loadData();
    }

    private void injectDependencies() {
        regionRepository = getLocalRepositoryInjector().getRegionRepository();
        summonerSearchRepository = getLocalRepositoryInjector().getSummonerSearchRepository();
        matchCalculatorRepository = getRepositoryInjector().getMatchCalculatorRepository();

        // TODO: Replace all this with Butterknife.
        resultsHolder = findViewById(R.id.results_holder);
        errorView = (TextView) findViewById(R.id.error_view);
        loadingView = findViewById(R.id.loading_view);

        summonerName = (EditText) findViewById(R.id.summoner_name);
        resetTextButton = findViewById(R.id.summoner_delete);
        searchButton = findViewById(R.id.summoner_search);
        regionChooser = (TextView) findViewById(R.id.region_chooser);

        teamResults = (RoleMatchupView) findViewById(R.id.team_results);
        roleMatchups = new RoleMatchupView[5];
        roleMatchups[0] = (RoleMatchupView) findViewById(R.id.top_matchup);
        roleMatchups[1] = (RoleMatchupView) findViewById(R.id.jungle_matchup);
        roleMatchups[2] = (RoleMatchupView) findViewById(R.id.mid_matchup);
        roleMatchups[3] = (RoleMatchupView) findViewById(R.id.adc_matchup);
        roleMatchups[4] = (RoleMatchupView) findViewById(R.id.support_matchup);
    }

    private void bindUiActions() {
        summonerName.setText(summonerSearchRepository.getSavedValue());
        regionChooser.setText(regionRepository.getSavedRegion().toString());
        resetTextButton.setOnClickListener(view -> summonerName.setText(""));
        searchButton.setOnClickListener(view -> loadData());
        regionChooser.setOnClickListener(view -> openRegionChooser());
        summonerName.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            loadData();
            return true;
        });
    }

    private void loadData() {
        String summonerNameText = summonerName.getText().toString().toLowerCase().replace(" ", "");
        if (!TextUtils.isEmpty(summonerNameText)) {
            showLoading();
            KeyboardUtils.hideKeyboard(summonerName);
            summonerSearchRepository.updateValue(summonerNameText);
            matchCalculatorRepository.getDataForPlayer(summonerNameText,
                    new RepoCallback<GameData>() {
                        @Override
                        public void onSuccess(GameData gameData) {
                            fillGameData(gameData);
                            showResults();
                        }

                        @Override
                        public void onError(RepoError repoError, int code) {
                            errorView.setText(repoError.getMessage() + "\n\n"
                                    + "Error Code: " + repoError.getBaseCode() + "-" + code);
                            showError();
                        }
                    });
        }
    }

    private void fillGameData(GameData gameData) {
        for (Role role : Role.values()) {
            RoleMatchupView roleMatchupView = roleMatchups[role.ordinal()];
            roleMatchupView.populateWithMatchup(gameData, role);
        }
        teamResults.populateWithTeams(gameData);
    }

    private void openRegionChooser() {
        startActivity(new Intent(this, RegionChooserActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        regionChooser.setText(regionRepository.getSavedRegion().toString());
    }

    private void showLoading() {
        loadingView.setVisibility(View.VISIBLE);
        resultsHolder.setVisibility(View.GONE);
        errorView.setVisibility(View.GONE);
    }

    private void showError() {
        loadingView.setVisibility(View.GONE);
        resultsHolder.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);
    }

    private void showResults() {
        loadingView.setVisibility(View.GONE);
        resultsHolder.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

}
