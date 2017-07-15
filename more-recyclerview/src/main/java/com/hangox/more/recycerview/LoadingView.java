package com.hangox.more.recycerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 用来显示ListView 最后一下的加载状态的
 *
 * @author 47
 */
public class LoadingView extends RelativeLayout {
    private LinearLayout mLoadingLayout;
    private AVLoadingIndicatorView mLoadingView;
    private TextView mTViewLoadedFail;
    private State mLoadState = State.Loading;
    private OnClickListener mOnClickListener;
    private CharSequence mTip, mError;

    public LoadingView(Context context) {
        super(context);
        init(null, 0);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Context context = getContext();
        mLoadingLayout = new LinearLayout(context);
        mLoadingLayout.setGravity(Gravity.CENTER);
        mLoadingView = new AVLoadingIndicatorView(getContext());
        mLoadingView.setIndicatorColor(Color.RED);
        mLoadingView.setLayoutParams(new LinearLayout.LayoutParams(dpToPx(40), dpToPx(40)));
        mLoadingLayout.addView(mLoadingView);
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        mLoadingLayout.setLayoutParams(params);
        addView(mLoadingLayout, params);
        //添加加载失败模块
        mTViewLoadedFail = new TextView(context);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mTViewLoadedFail.setLayoutParams(layoutParams);
        mTViewLoadedFail.setGravity(Gravity.CENTER);
        addView(mTViewLoadedFail);
        mTViewLoadedFail.setVisibility(View.GONE);
        setMinimumHeight(dpToPx(60));
        mTViewLoadedFail.setMinimumHeight(ViewCompat.getMinimumHeight(this));
        mLoadingLayout.setMinimumHeight(ViewCompat.getMinimumHeight(this));
    }

    public int dpToPx(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void setLastPageTip(String text) {
        mTip = text;
        mTViewLoadedFail.setText(text);
    }



    public TextView getTipView() {
        return mTViewLoadedFail;
    }

    private void showBall() {
        if (mTViewLoadedFail.getVisibility() == VISIBLE)
            mTViewLoadedFail.setVisibility(View.GONE);
        if (mLoadingLayout.getVisibility() == GONE)
            mLoadingLayout.setVisibility(VISIBLE);
    }


    private void showTip() {
        mTViewLoadedFail.setText(mTip);
        showTextView();
    }

    private void showTextView() {
        if (mTViewLoadedFail.getVisibility() == GONE)
            mTViewLoadedFail.setVisibility(View.VISIBLE);
        if (mLoadingLayout.getVisibility() == VISIBLE)
            mLoadingLayout.setVisibility(GONE);
    }

    public void showError() {
        mTViewLoadedFail.setText(mError);
        showTextView();
    }


    public void setError(CharSequence error) {
        mError = error;
        mTViewLoadedFail.setText(mError);
    }

    public void setState(State state) {
        mLoadState = state;
        switch (state) {
            case LastPage:
                showTip();
                break;
            case Loading:
                showBall();
                break;
            case Error:
                showError();
                break;
            case Hide:
                hideAll();
                break;
        }
    }

    private void hideAll() {
        mTViewLoadedFail.setVisibility(View.GONE);
        mLoadingLayout.setVisibility(GONE);
    }


    public State getState() {
        return mLoadState;
    }

    public void setOnClickListener(OnClickListener listener) {
        mOnClickListener = listener;
        mTViewLoadedFail.setOnClickListener(listener);
    }

    public  enum State {
        LastPage, Loading, Error, Hide
    }



}
