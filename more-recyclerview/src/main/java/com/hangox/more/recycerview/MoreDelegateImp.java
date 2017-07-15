package com.hangox.more.recycerview;

import android.content.Context;
import android.view.View;


/**
 * Created With Android Studio
 * User hangox
 * Date 15/4/1
 * Time 下午4:08
 */
public class MoreDelegateImp implements MoreDelegate {
    private LoadingView mBallView;
    private ViewState mViewState = ViewState.LOADING;

    public MoreDelegateImp(Context context) {
        mBallView = new LoadingView(context);
        mBallView.setState(LoadingView.State.Loading);
        mBallView.setError(context.getString(R.string.mr_load_failure));
        mBallView.setLastPageTip(context.getString(R.string.mr_last_page));
    }

    @Override
    public View getView() {
        return mBallView;
    }

    @Override
    public void hide() {
        mBallView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void setViewState(ViewState viewState) {
        mViewState = viewState;
        switch (viewState) {
            case LOADING:
                mBallView.setState(LoadingView.State.Loading);
                break;
            case ERROR:
                mBallView.setState(LoadingView.State.Error);
                break;
            case LAST_PAGE:
                mBallView.setState(LoadingView.State.LastPage);
                break;
        }
    }

    @Override
    public ViewState getViewState() {
        return mViewState;
    }

    @Override
    public void requestLayout() {
        mBallView.requestLayout();
    }

    @Override
    public void show() {
        mBallView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean isHide() {
        return mBallView.getVisibility() == View.INVISIBLE;
    }
}
