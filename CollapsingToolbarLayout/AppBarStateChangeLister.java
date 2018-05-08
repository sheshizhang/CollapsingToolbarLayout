package com.InterPolator.View;

import android.support.design.widget.AppBarLayout;

/**
 * Created by feiran.zhang on 2018/5/8.
 */

public abstract class AppBarStateChangeLister implements AppBarLayout.OnOffsetChangedListener{
    public enum State{
        EXPANDED,
        COLLAPSED,
        IDLE
    }
    private State mCurrentState=State.IDLE;
    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onstateChanged(appBarLayout, State.EXPANDED);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onstateChanged(appBarLayout, State.COLLAPSED);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onstateChanged(appBarLayout, State.IDLE);
            }
            mCurrentState = State.IDLE;
        }
    }

    public abstract void onstateChanged(AppBarLayout appBarLayout,State state);
}
