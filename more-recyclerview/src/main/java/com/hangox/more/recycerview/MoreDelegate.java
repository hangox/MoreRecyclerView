package com.hangox.more.recycerview;

import android.view.View;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/4/1
 * Time 下午3:59
 * 底部更多view的代理
 */
public interface MoreDelegate {
    View getView();

    boolean isHide();

    void show();

    void hide();

    void setViewState(ViewState viewState);

    ViewState getViewState();

    void requestLayout();


    enum ViewState {
        LOADING, ERROR, LAST_PAGE
    }
}

