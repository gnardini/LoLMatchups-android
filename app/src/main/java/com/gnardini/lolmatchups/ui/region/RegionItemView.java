package com.gnardini.lolmatchups.ui.region;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;

import com.gnardini.lolmatchups.utils.DimenUtils;

public class RegionItemView extends AppCompatTextView {

    public RegionItemView(Context context) {
        super(context);
        init();
    }

    public RegionItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RegionItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setGravity(Gravity.CENTER);
        setTextColor(Color.BLACK);
        setTextSize(DimenUtils.convertDpToPixel(12));
        final int verticalPadding = DimenUtils.convertDpToPixel(12);
        final int horizontalPadding = DimenUtils.convertDpToPixel(20);
        setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding);
    }

}
