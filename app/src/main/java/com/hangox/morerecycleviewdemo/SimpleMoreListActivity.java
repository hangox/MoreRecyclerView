package com.hangox.morerecycleviewdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hangox.morerecycleviewdemo.databinding.ActivitySimpleMoreListBinding;
import com.hangox.morerecycleviewdemo.databinding.VHolderAsyBinding;

import java.util.ArrayList;
import java.util.List;

public class SimpleMoreListActivity extends AppCompatActivity {
    ActivitySimpleMoreListBinding mBinding;
    List<String> mNames = new ArrayList<>();
    MoreAdapter mAsyAdapter = new MoreAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_more_list);
        addData();
        addData();
        mBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mBinding.recyclerView.setAdapter(mAsyAdapter);
    }



    private void addData(){
        for (int i = 0; i < 10; i++) {
            mNames.add(System.nanoTime()+"");
        }

    }

    private class MoreAdapter extends RecyclerView.Adapter<MoreViewHolder> {


        @Override
        public MoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.v_holder_asy, parent, false);
            return new MoreViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MoreViewHolder holder, int position) {
            holder.mVHolderAsyBinding.setName(mNames.get(position));
        }


        @Override
        public int getItemCount() {
            return mNames.size();
        }
    }

    public static class MoreViewHolder extends RecyclerView.ViewHolder {
        protected VHolderAsyBinding mVHolderAsyBinding;

        public MoreViewHolder(View itemView) {
            super(itemView);
            mVHolderAsyBinding = DataBindingUtil.bind(itemView);
        }

    }
}

