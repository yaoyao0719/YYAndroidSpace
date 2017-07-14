package com.yaoyao.android.module.gank;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.model.Response;
import com.yaoyao.android.R;
import com.yaoyao.android.base.BaseFragment;
import com.yaoyao.android.constants.Urls;
import com.yaoyao.android.network.JsonCallback;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.android.customrecyclerview.listener.OnLoadMoreListener;
import cn.android.customrecyclerview.listener.OnRefreshListener;
import cn.android.customrecyclerview.ptr.CustomRecyclerView;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/7/11
 * @desc  :访问极客开放接口
 */

public class GankFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.recyclerView)
    CustomRecyclerView recyclerView;

    private static final int PAGE_SIZE=20;
    private GankAdapter adapter;
    private List<GankModel> gankModels;
    private int currentPage;
    private LayoutInflater inflater;

    public GankFragment() {

    }

    public static GankFragment newInstance() {
        GankFragment fragment = new GankFragment();
        return fragment;
    }


    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater=inflater;
        View view = inflater.inflate(R.layout.fragment_gank, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    protected void initData() {
        initWidget();
        gankModels=new ArrayList<>();
        loadFirstPage();
    }

    private void initWidget() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new GankAdapter(null);
        recyclerView.setAdapter(adapter);
        recyclerView.setDefaultColorSchemeResources();
        recyclerView.setProgressBackgroundColorSchemeColor(Color.WHITE);
        recyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadFirstPage();
            }
        });
        recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadNextPage();
            }
        });
        recyclerView.startRefreshing();
    }

    private void loadFirstPage() {
        Type type=new TypeToken<GankResponse<List<GankModel>>>(){}.getType();
        OkGo.<GankResponse<List<GankModel>>>get(Urls.URL_GANK_BASE+"Android/"+PAGE_SIZE+"/1")
                .tag(this)
                .cacheKey("android")
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new JsonCallback<GankResponse<List<GankModel>>>(type) {
                    @Override
                    public void onSuccess(Response<GankResponse<List<GankModel>>> response) {
                        recyclerView.stopRefresh();
                        List<GankModel> results = response.body().results;
                        if (results != null && results.size() > 0) {
                            currentPage=2;
                            adapter.setNewData(results);
                        }
                    }

                    @Override
                    public void onError(Response<GankResponse<List<GankModel>>> response) {
                        Snackbar.make(recyclerView,response.getException().getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                });
    }
    private void loadNextPage() {
        Type type=new TypeToken<GankResponse<List<GankModel>>>(){}.getType();
        OkGo.<GankResponse<List<GankModel>>>get(Urls.URL_GANK_BASE+"Android/"+PAGE_SIZE+"/"+currentPage)
                .tag(this)
                .cacheMode(CacheMode.NO_CACHE)
                .execute(new JsonCallback<GankResponse<List<GankModel>>>(type) {
                    @Override
                    public void onSuccess(Response<GankResponse<List<GankModel>>> response) {
                        recyclerView.stopRefresh();
                        List<GankModel> results = response.body().results;
                        if (results != null && results.size() > 0) {
                            currentPage++;
                            adapter.addData(results);
                        }else{
                            //显示没有更多数据
                            adapter.loadMoreComplete();
                            View noDataView = inflater.inflate(R.layout.item_no_data, (ViewGroup) recyclerView.getParent(), false);
                            adapter.addFooterView(noDataView);
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
