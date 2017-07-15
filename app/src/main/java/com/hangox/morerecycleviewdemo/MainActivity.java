package com.hangox.morerecycleviewdemo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hangox.more.recycerview.MoreDelegateImp;
import com.hangox.more.recycerview.MoreRecyclerView;
import com.hangox.morerecycleviewdemo.databinding.ActivityMainBinding;
import com.hangox.morerecycleviewdemo.databinding.VHolderAsyBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding mActivityMainBinding;
    List<String> mNames = new ArrayList<>();
    AsyAdapter mAsyAdapter = new AsyAdapter();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addData();
        mActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        mActivityMainBinding.moreRecyclerView.setMoreDelegate(new MoreDelegateImp(this));
        mActivityMainBinding.moreRecyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        mActivityMainBinding.moreRecyclerView.setAdapter(mAsyAdapter);
        mActivityMainBinding.moreRecyclerView.setMoreListener(new MoreRecyclerView.OnMoreListener() {
            @Override
            public void onMoreShow(MoreRecyclerView recyclerView) {
                mActivityMainBinding.moreRecyclerView.lockMoreCall();
                mActivityMainBinding.moreRecyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int length  = mNames.size();
                        addData();
                        mAsyAdapter.notifyItemRangeInserted(length,mNames.size() - length);
//                        mAsyAdapter.notifyDataSetChanged();
                        mActivityMainBinding.moreRecyclerView.unlockMoreCall();
                    }
                },3000);
            }
        });
//        startActivity(new Intent(this,SimpleMoreListActivity.class));
    }

    private void addData(){
        for (int i = 0; i < 10; i++) {
            mNames.add(System.nanoTime()+"");
        }

    }


    private class AsyAdapter extends RecyclerView.Adapter<AsyViewHolder>{


        @Override
        public AsyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.v_holder_asy,parent,false);
            return new AsyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(AsyViewHolder holder, int position) {
            holder.mVHolderAsyBinding.setName(mNames.get(position));
        }


        @Override
        public int getItemCount() {
            return mNames.size();
        }
    }

    public static class AsyViewHolder extends RecyclerView.ViewHolder{
        protected VHolderAsyBinding mVHolderAsyBinding;
        public AsyViewHolder(View itemView) {
            super(itemView);
            mVHolderAsyBinding = DataBindingUtil.bind(itemView);
        }

    }
}
