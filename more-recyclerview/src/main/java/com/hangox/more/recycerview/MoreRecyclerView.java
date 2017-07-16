package com.hangox.more.recycerview;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created With Android Studio
 * User hangox
 * Date 15/5/12
 * Time 下午5:41
 * 更多加载RecyclerView
 */
public class MoreRecyclerView extends RecyclerView {
    private MoreAdapter mMoreAdapter;

    /**
     * 是否已经锁定在更多
     */
    private boolean isLockMore;

    /**
     * 更多ViewID*
     */
    private int mMoreViewId;
    /**
     * 应用状态*
     */
    private MoreDelegate.ViewState mViewState;

    private OnMoreListener mOnMoreListener;


    private OnScrollListener mOnScrollListener = new OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (!isLockMore && checkScrollToDown() && mOnMoreListener != null  )
                mOnMoreListener.onMoreShow(MoreRecyclerView.this);
        }
    };
    private ViewHolder mMoreViewHolder;
    private MoreDelegate mMoreDelegate;
    private int mDefaultCount;

    public MoreRecyclerView(Context context) {
        super(context);
        init();
    }

    public MoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MoreRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        super.addOnScrollListener(mOnScrollListener);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        if(layout == null) return;
        super.setLayoutManager(layout);
    }

    /**
     * 设置更多显示代理
     *
     * @param moreDelegate 更多的代理
     */
    public void setMoreDelegate(MoreDelegate moreDelegate) {
        if (moreDelegate == null) return;
        if (mViewState != null) moreDelegate.setViewState(mViewState);
        mMoreDelegate = moreDelegate;
        View moreView = mMoreDelegate.getView();
        if (moreView.getId() == View.NO_ID) {
            moreView.setId(moreView.hashCode());
        }
        mMoreViewId = moreView.getId();
        mMoreViewHolder = new ViewHolder(moreView) {};
    }

    public MoreDelegate getMoreDelegete(){
        return mMoreDelegate;
    }

    public OnMoreListener getMoreListener() {
        return mOnMoreListener;
    }

    /**
     * 设置更多的监听器
     *
     * @param listener 监听器
     */
    public void setMoreListener(OnMoreListener listener) {
        if (listener == null) return;
        mOnMoreListener = listener;
    }

    @Override
    public Adapter getAdapter() {
        return mMoreAdapter.getWrapAdapter();
    }

    @Override
    public void setAdapter(Adapter adapter) {
        if(adapter == null) return;
        if (mMoreAdapter != null && adapter == mMoreAdapter.getWrapAdapter()){
            mMoreAdapter.notifyDataSetChanged();
            return;
        }
        mMoreAdapter = new MoreAdapter();
        mMoreAdapter.setWrapAdapter(adapter);
        super.setAdapter(mMoreAdapter);
    }

    /**
     * 锁定MoreCall,用户重新到达底部的时候不会call onMoreShow
     */
    public void lockMoreCall() {
        isLockMore = true;
    }


    /**
     * 解锁MoreCall
     *
     * @see #lockMoreCall()
     */
    public void unlockMoreCall() {
        isLockMore = false;
    }

    public boolean islockMore(){return isLockMore;}



    /**
     * 判断是否到了最后一个
     *
     * @return
     */
    private boolean checkScrollToDown() {
        if (getChildCount() < 0) {
            return false;
        }

        View child = getChildAt(getChildCount() - 1);
        return child != null && child.getId() == mMoreViewId;
    }

    public void setDefaultItemCount(int count){
        mDefaultCount = count;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
//        SaveState saveState = new SaveState(super.onSaveInstanceState());
//        if (mMoreDelegate != null) saveState.viewState = mMoreDelegate.getViewState();
//        saveState.isLockMore = isLockMore;
        return super.onSaveInstanceState();
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        super.onRestoreInstanceState(state);
//        SaveState saveState = (SaveState) state;
//        super.onRestoreInstanceState(saveState.getSuperState());
//        isLockMore = saveState.isLockMore;
//        if (mMoreDelegate != null) mMoreDelegate.setViewState(saveState.viewState);
//        else mViewState = saveState.viewState;
    }


    public void registerAdapterDataObserver(AdapterDataObserver observer){
        mMoreAdapter.registerAdapterDataObserver(observer);
    }

    public void unregisterAdapterDataObserver(AdapterDataObserver observer){
        mMoreAdapter.unregisterAdapterDataObserver(observer);
    }

    /**
     * @author 47
     * 设置更多显示时的监听器
     */
    public interface OnMoreListener {
        void onMoreShow(MoreRecyclerView recyclerView);
    }

    static class SaveState extends View.BaseSavedState {

        public static final Creator<SaveState> CREATOR = new Creator<SaveState>() {
            @Override
            public SaveState createFromParcel(Parcel source) {
                return new SaveState(source);
            }

            @Override
            public SaveState[] newArray(int size) {
                return new SaveState[size];
            }
        };
        MoreDelegate.ViewState viewState  ;
        boolean isLockMore;

        public SaveState(Parcel source) {
            super(source);
            viewState = (MoreDelegate.ViewState) source.readSerializable();
        }

        public SaveState(Parcelable superState) {
            super(superState);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeSerializable(viewState);
        }
    }


    /**
     * Created With Android Studio
     * User 47
     * Date 15/5/12
     * Time 上午10:07
     * 一个Load Adapter
     */
    private class MoreAdapter extends Adapter<ViewHolder> {

        public static final int LAST_ITEM_TYPE = -2333333;
        private Adapter mAdapter;
        private Adapter mLastAdapter;


        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            mAdapter.onAttachedToRecyclerView(recyclerView);
        }


        @Override
        public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
            super.onDetachedFromRecyclerView(recyclerView);
            mAdapter.onDetachedFromRecyclerView(recyclerView);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            super.onViewRecycled(holder);
            mAdapter.onViewRecycled(holder);
        }

        //TODO 解决CastClass 错误问题
//        private boolean isMoreViewHolder(ViewHolder holder){
//            return holder instanceof More
//        }


        @Override
        public boolean onFailedToRecycleView(ViewHolder holder) {
            return mAdapter.onFailedToRecycleView(holder);

        }

        @Override
        public void onViewAttachedToWindow(ViewHolder holder) {
            super.onViewAttachedToWindow(holder);
            mAdapter.onViewAttachedToWindow(holder);
        }

        @Override
        public void onViewDetachedFromWindow(ViewHolder holder) {
            super.onViewDetachedFromWindow(holder);
            mAdapter.onViewDetachedFromWindow(holder);
        }

        @Override
        public void registerAdapterDataObserver(AdapterDataObserver observer) {
            super.registerAdapterDataObserver(observer);
            mAdapter.registerAdapterDataObserver(observer);
        }

        @Override
        public void unregisterAdapterDataObserver(AdapterDataObserver observer) {
            super.unregisterAdapterDataObserver(observer);
            if(mLastAdapter != null){
                mLastAdapter.unregisterAdapterDataObserver(observer);

            }
            if (mAdapter.hasObservers()) mAdapter.unregisterAdapterDataObserver(observer);

        }

        public Adapter getWrapAdapter(){
            return mAdapter;
        }

        public void setWrapAdapter(Adapter adapter){
            if(adapter == null) throw new IllegalStateException("Adapter can not be null");
            mLastAdapter = mAdapter;
            mAdapter = adapter;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType == LAST_ITEM_TYPE)
                return  mMoreViewHolder;
            return onRealCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if(position == getRealItemCount()) {
                return;
            }
            onRealBindViewHolder(holder, position);
        }



        @Override
        public int getItemViewType(int position) {
            if(position == getRealItemCount()) return LAST_ITEM_TYPE;
            return mAdapter.getItemViewType(position);
        }



        @Override
        public int getItemCount() {
            return getRealItemCount() == 0 ?  0 : getRealItemCount() + 1;
        }

        public int getRealItemCount(){
            return mAdapter.getItemCount();
        }

        public ViewHolder onRealCreateViewHolder (ViewGroup parent, int viewType){
            return  mAdapter.createViewHolder(parent,viewType);
        }

        public void onRealBindViewHolder(ViewHolder holder, int position){
            mAdapter.bindViewHolder(holder, position);
        }




    }
}
