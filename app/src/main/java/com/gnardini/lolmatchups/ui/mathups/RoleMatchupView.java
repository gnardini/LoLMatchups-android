package com.gnardini.lolmatchups.ui.mathups;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gnardini.lolmatchups.Configuration;
import com.gnardini.lolmatchups.R;
import com.gnardini.lolmatchups.model.GameData;
import com.gnardini.lolmatchups.model.InGameChampion;
import com.gnardini.lolmatchups.model.Role;
import com.gnardini.lolmatchups.utils.DimenUtils;

public class RoleMatchupView extends LinearLayout {

    TextView roleName;
    TextView champion1WinRate;
    TextView champion2WinRate;
    ImageView champion1Icon;
    ImageView champion2Icon;

    public RoleMatchupView(Context context) {
        super(context);
        init();
    }

    public RoleMatchupView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoleMatchupView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.role_matchup, this);

        roleName = (TextView) findViewById(R.id.role_name);
        champion1WinRate = (TextView) findViewById(R.id.champion_1_winrate);
        champion2WinRate = (TextView) findViewById(R.id.champion_2_winrate);
        champion1Icon = (ImageView) findViewById(R.id.champion_1_icon);
        champion2Icon = (ImageView) findViewById(R.id.champion_2_icon);

        setOrientation(VERTICAL);
    }

    public void populateWithMatchup(GameData gameData, Role role) {
        InGameChampion champion1 = gameData.getFriendlyTeam().getRole(role);
        InGameChampion champion2 = gameData.getEnemyTeam().getRole(role);

        roleName.setText(role.toString());
        Glide.with(getContext())
                .load(String.format(Configuration.CHAMPION_ICON_URL, champion1.getName()))
                .into(champion1Icon);
        Glide.with(getContext())
                .load(String.format(Configuration.CHAMPION_ICON_URL, champion2.getName()))
                .into(champion2Icon);

        if (champion1.getGames() > 0) {
            champion1WinRate.setText(String.format("%.02f%%", champion1.getWinRate()));
            champion2WinRate.setText(String.format("%.02f%%", champion2.getWinRate()));
        } else {
            champion1WinRate.setText("?");
            champion2WinRate.setText("?");
        }
    }

    public void populateWithTeams(GameData gameData) {
        roleName.setText("Team's Total");
        champion1WinRate.setText(String.format("%.02f%%", gameData.getFriendlyTeam().getWinRate()));
        champion2WinRate.setText(String.format("%.02f%%", gameData.getEnemyTeam().getWinRate()));

        roleName.setTextSize(DimenUtils.convertDpToPixel(9));
        roleName.setTypeface(null, Typeface.BOLD);

        champion1WinRate.setTextSize(DimenUtils.convertDpToPixel(9));
        champion1WinRate.setTypeface(null, Typeface.BOLD);

        champion2WinRate.setTextSize(DimenUtils.convertDpToPixel(9));
        champion2WinRate.setTypeface(null, Typeface.BOLD);

        champion1Icon.setVisibility(GONE);
        champion2Icon.setVisibility(GONE);

        ((LinearLayout) champion1Icon.getParent()).setGravity(Gravity.CENTER);
    }
}
