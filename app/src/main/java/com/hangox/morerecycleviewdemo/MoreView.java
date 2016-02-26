package com.hangox.morerecycleviewdemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.hangox.xlog.XLog;

/**
 * Created With Android Studio
 * User hangox
 * Date 16/2/11
 * Time 下午11:07
 */
public class MoreView extends FrameLayout{


    public MoreView(Context context) {
        super(context);
    }

    public MoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MoreView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        if(child instanceof RecyclerView){
            RecyclerView recyclerView = (RecyclerView) child;
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    XLog.v("(" + dx + "," + dy + ")");
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    XLog.v(newState);
                }
            });
        }else if(child instanceof ListView){
            ListView listView = (ListView) child;
            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {

                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });
        }
    }
}
