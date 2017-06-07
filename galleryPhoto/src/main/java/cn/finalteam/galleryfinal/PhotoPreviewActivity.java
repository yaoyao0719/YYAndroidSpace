package cn.finalteam.galleryfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.adapter.PhotoPreviewAdapter;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.widget.GFViewPager;
import cn.finalteam.toolsfinal.io.FileUtils;


public class PhotoPreviewActivity extends PhotoBaseActivity{

    static final String PHOTO_LIST = "photo_list";
    static final String UNSELECT_LIST = "UNSELECT_LIST";
    static final String IS_CONFIRM = "isConfirm";

    private RelativeLayout mTitleBar;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvIndicator;
    private Button btnConfirm;

    private GFViewPager mVpPager;
    private List<PhotoInfo> mPhotoList;
    private ArrayList<PhotoInfo> unSelectPhoto;
    private PhotoPreviewAdapter mPhotoPreviewAdapter;

    private ThemeConfig mThemeConfig;
    private CheckBox mCheckBoxIsSelect,mCheckBoxIsFull;
    private int currentSelectedPage = 0;
    private Intent mIntent;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThemeConfig = GalleryFinal.getGalleryTheme();
        unSelectPhoto = new ArrayList<>();
        if ( mThemeConfig == null) {
            resultFailureDelayed(getString(R.string.please_reopen_gf), true);
        } else {
            setContentView(R.layout.gf_activity_photo_preview);
            findViews();
            setListener();
            setTheme();
            mIntent = getIntent();
            mPhotoList = (List<PhotoInfo>) mIntent.getSerializableExtra(PHOTO_LIST);
            mPhotoPreviewAdapter = new PhotoPreviewAdapter(this, mPhotoList);
            mVpPager.setAdapter(mPhotoPreviewAdapter);
        }
        refreshbtnConfirmText();
        refreshCheckBoxState();

    }

    private void findViews() {
        mTitleBar = (RelativeLayout) findViewById(R.id.titlebar);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvTitle = (TextView) findViewById(R.id.tv_title);
        mTvIndicator = (TextView) findViewById(R.id.tv_indicator);
        mCheckBoxIsSelect = (CheckBox) findViewById(R.id.checkbox);
        mVpPager = (GFViewPager) findViewById(R.id.vp_pager);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        mCheckBoxIsFull = (CheckBox) findViewById(R.id.isFull);
        mCheckBoxIsFull.setVisibility(GalleryFinal.getFunctionConfig().isShowFull() ? View.VISIBLE : View.GONE );
    }

    private void setListener() {
//        mVpPager.addOnPageChangeListener(this);
        mCheckBoxIsFull.setChecked(GalleryFinal.isFull);
        mIvBack.setOnClickListener(mBackListener);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(true);
                PhotoPreviewActivity.this.finish();
            }
        });
        mCheckBoxIsSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean isSetResult = false;
                if(isChecked && unSelectPhoto.contains(mPhotoList.get(currentSelectedPage))){
                    unSelectPhoto.remove(mPhotoList.get(currentSelectedPage));
                    isSetResult = true;
                }else if (!isChecked&&!unSelectPhoto.contains(mPhotoList.get(currentSelectedPage))){
                    unSelectPhoto.add(mPhotoList.get(currentSelectedPage));
                    isSetResult = true;
                }
                if(isSetResult){
                    refreshCheckBoxState();
                    refreshbtnConfirmText();
                    setResult(false);
                }


            }
        });
        mCheckBoxIsFull.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                GalleryFinal.isFull = isChecked;
            }
        });
        mVpPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTvIndicator.setText((position + 1) + "/" + mPhotoList.size());
            }

            @Override
            public void onPageSelected(int position) {
                currentSelectedPage = position;
                if(unSelectPhoto.contains(mPhotoList.get(position))){
                    mCheckBoxIsSelect.setChecked(false);
                }else {
                    mCheckBoxIsSelect.setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }



    private void refreshCheckBoxState(){
        long totalSize = 0;
        for (PhotoInfo info : mPhotoList){
            if(!TextUtils.isEmpty(info.getSize())){
                totalSize = totalSize + Long.parseLong(info.getSize());
            }
        }
        for (PhotoInfo info : unSelectPhoto){
            if(TextUtils.isEmpty(info.getSize())){
                totalSize = totalSize - Long.parseLong(info.getSize());
            }
        }
        if(totalSize<=0) return;
        mCheckBoxIsFull.setText(getString(R.string.full, FileUtils.byteCountToDisplaySize(totalSize)));
    }

    private void refreshbtnConfirmText(){
        int selectSize = mPhotoList.size()-unSelectPhoto.size();
        if(selectSize<=0){
            btnConfirm.setEnabled(false);
        }else {
            btnConfirm.setEnabled(true);
        }
        btnConfirm.setText(getString(R.string.confirm,selectSize,GalleryFinal.getFunctionConfig().getMaxSize()));
    }

    private void setResult(boolean isConfirm){
        mIntent.putExtra(UNSELECT_LIST,unSelectPhoto);
        mIntent.putExtra(IS_CONFIRM,isConfirm);
        setResult(RESULT_OK,mIntent);
    }


    private void setTheme() {
        /*mIvBack.setImageResource(mThemeConfig.getIconBack());
        if (mThemeConfig.getIconBack() == R.drawable.ic_gf_back) {
            mIvBack.setColorFilter(mThemeConfig.getTitleBarIconColor());
        }

        mTitleBar.setBackgroundColor(mThemeConfig.getTitleBarBgColor());
        mTvTitle.setTextColor(mThemeConfig.getTitleBarTextColor());
        if(mThemeConfig.getPreviewBg() != null) {
            mVpPager.setBackgroundDrawable(mThemeConfig.getPreviewBg());
        }*/
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void takeResult(PhotoInfo info) {
    }


    private View.OnClickListener mBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

}
