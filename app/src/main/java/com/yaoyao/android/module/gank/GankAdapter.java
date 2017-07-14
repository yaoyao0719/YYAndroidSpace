package com.yaoyao.android.module.gank;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yaoyao.android.R;
import com.yaoyao.android.module.web.WebActivity;
import com.yaoyao.android.services.ImageLoaderService;

import java.util.List;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/7/12
 * @desc  :
 */

public class GankAdapter extends BaseQuickAdapter<GankModel,BaseViewHolder>{

    public GankAdapter( @Nullable List<GankModel> data) {
        super(R.layout.gank_item, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GankModel item) {
        helper.setText(R.id.title,item.desc)
                .setText(R.id.pubDate,item.publishedAt.toString())
                .setText(R.id.who,item.who);
        if(item.images!=null&&item.images.size()>0){
            ImageLoaderService.loadImage(item.images.get(0),(ImageView) helper.getView(R.id.imageView));
        }
        View view = helper.getConvertView();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebActivity.runActivity(mContext, item.desc, item.url);
            }
        });
    }
}
