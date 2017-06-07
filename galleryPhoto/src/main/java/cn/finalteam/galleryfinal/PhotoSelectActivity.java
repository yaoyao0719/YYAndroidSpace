/*
 * Copyright (C) 2014 pengjianbo(pengjianbosoft@gmail.com), Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package cn.finalteam.galleryfinal;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.finalteam.galleryfinal.adapter.FolderListAdapter;
import cn.finalteam.galleryfinal.adapter.PhotoListAdapter;
import cn.finalteam.galleryfinal.model.PhotoFolderInfo;
import cn.finalteam.galleryfinal.model.PhotoInfo;
import cn.finalteam.galleryfinal.utils.PhotoTools;
import cn.finalteam.toolsfinal.DeviceUtils;
import cn.finalteam.toolsfinal.StringUtils;
import cn.finalteam.toolsfinal.io.FilenameUtils;


public class PhotoSelectActivity extends PhotoBaseActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private final int HANLDER_TAKE_PHOTO_EVENT = 1000;
    private final int HANDLER_REFRESH_LIST_EVENT = 1002;
    private final int RESULT_PREVIEW = 1003;

    private GridView mGvPhotoList;
    private ListView mLvFolderList;
    private LinearLayout mLlFolderPanel;
    private ImageView mIvBack;
    private TextView mTvEmptyView;
    private List<PhotoFolderInfo> mAllPhotoFolderList;
    private FolderListAdapter mFolderListAdapter;
    private List<PhotoInfo> mCurPhotoList;
    private PhotoListAdapter mPhotoListAdapter;
    private Button btnConfirm;
    private TextView btnPreview;
    private TextView btnFolder;
    private TextView btnTakePhoto;
    private View bottomLayout;
    private View ll_folder_panel;
    //是否需要刷新相册
    private boolean mHasRefreshGallery = false;
    //    private HashMap<String, PhotoInfo> mSelectPhotoMap = new HashMap<>();
    private ArrayList<PhotoInfo> mSelectedPhotoList = new ArrayList<>();

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("selectPhotoMap", mSelectedPhotoList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSelectedPhotoList = (ArrayList<PhotoInfo>) savedInstanceState.getSerializable("selectPhotoMap");
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == HANLDER_TAKE_PHOTO_EVENT) {
                PhotoInfo photoInfo = (PhotoInfo) msg.obj;
                takeRefreshGallery(photoInfo);
                refreshSelectCount();
            } else if (msg.what == HANDLER_REFRESH_LIST_EVENT) {
                refreshSelectCount();
                mPhotoListAdapter.notifyDataSetChanged();
                mFolderListAdapter.notifyDataSetChanged();
                if (mAllPhotoFolderList.get(0).getPhotoList() == null ||
                        mAllPhotoFolderList.get(0).getPhotoList().size() == 0) {
                    mTvEmptyView.setText(R.string.no_photo);
                }

                mGvPhotoList.setEnabled(true);
//                mLlTitle.setEnabled(true);
//                mIvTakePhoto.setEnabled(true);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (GalleryFinal.getFunctionConfig() == null || GalleryFinal.getGalleryTheme() == null) {
            resultFailureDelayed(getString(R.string.please_reopen_gf), true);
        } else {
            setContentView(R.layout.gf_activity_photo_select);
            mPhotoTargetFolder = null;

            findViews();
            setListener();

            mAllPhotoFolderList = new ArrayList<>();
            mFolderListAdapter = new FolderListAdapter(this, mAllPhotoFolderList, GalleryFinal.getFunctionConfig());
            mLvFolderList.setAdapter(mFolderListAdapter);

            mCurPhotoList = new ArrayList<>();
            mPhotoListAdapter = new PhotoListAdapter(this, mCurPhotoList, mSelectedPhotoList, mScreenWidth);
            mGvPhotoList.setAdapter(mPhotoListAdapter);
            if(!GalleryFinal.getFunctionConfig().isMutiSelect()){
                btnPreview.setVisibility(View.GONE);
                btnConfirm.setVisibility(View.GONE);
            }
            setTheme();
            mGvPhotoList.setEmptyView(mTvEmptyView);

            refreshSelectCount();
//            requestGalleryPermission();
            getPhotos();
            mGvPhotoList.setOnScrollListener(GalleryFinal.getCoreConfig().getPauseOnScrollListener());
        }

        Global.mPhotoSelectActivity = this;
        GalleryFinal.isFull = false;
    }

    private void setTheme() {
    }

    private void findViews() {
        mGvPhotoList = (GridView) findViewById(R.id.gv_photo_list);
        mLvFolderList = (ListView) findViewById(R.id.lv_folder_list);
        mLlFolderPanel = (LinearLayout) findViewById(R.id.ll_folder_panel);
        mIvBack = (ImageView) findViewById(R.id.iv_back);
        mTvEmptyView = (TextView) findViewById(R.id.tv_empty_view);
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnPreview = (TextView) findViewById(R.id.btnPreview);
        btnFolder = (TextView) findViewById(R.id.btnFolder);
        btnTakePhoto = (TextView) findViewById(R.id.btnTakePhoto);
        bottomLayout = findViewById(R.id.bottomLayout);
        ll_folder_panel = findViewById(R.id.ll_folder_panel);

    }

    private void setListener() {

        mIvBack.setOnClickListener(this);
//        mIvFolderArrow.setOnClickListener(this);

        mLvFolderList.setOnItemClickListener(this);
        mGvPhotoList.setOnItemClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnPreview.setOnClickListener(this);
        btnFolder.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);
        bottomLayout.setOnClickListener(this);
        ll_folder_panel.setOnClickListener(this);
    }

    protected void deleteSelect(int photoId) {

        for (PhotoInfo info : mSelectedPhotoList) {
            if (info.getPhotoId() == photoId) {
                mSelectedPhotoList.remove(info);
                break;
            }
        }
        refreshAdapter();
    }

    protected void deleteSelect(List<PhotoInfo> deleteList){
        for (PhotoInfo info : deleteList){
            if(mSelectedPhotoList.contains(info)){
                mSelectedPhotoList.remove(info);
            }
        }
        refreshAdapter();
    }

    private void refreshAdapter() {
        mHanlder.sendEmptyMessageDelayed(HANDLER_REFRESH_LIST_EVENT, 100);
    }

    protected void takeRefreshGallery(PhotoInfo photoInfo, boolean selected) {
        if (isFinishing() || photoInfo == null) {
            return;
        }

        Message message = mHanlder.obtainMessage();
        message.obj = photoInfo;
        message.what = HANLDER_TAKE_PHOTO_EVENT;
//        mSelectPhotoMap.put(photoInfo.getPhotoPath(), photoInfo);
        mSelectedPhotoList.add(photoInfo);
        mHanlder.sendMessageDelayed(message, 100);
    }

    /**
     * 解决在5.0手机上刷新Gallery问题，从startActivityForResult回到Activity把数据添加到集合中然后理解跳转到下一个页面，
     * adapter的getCount与list.size不一致，所以我这里用了延迟刷新数据
     *
     * @param photoInfo
     */
    private void takeRefreshGallery(PhotoInfo photoInfo) {
        mCurPhotoList.add(0, photoInfo);
        mPhotoListAdapter.notifyDataSetChanged();

        //添加到集合中
        List<PhotoInfo> photoInfoList = mAllPhotoFolderList.get(0).getPhotoList();
        if (photoInfoList == null) {
            photoInfoList = new ArrayList<>();
        }
        photoInfoList.add(0, photoInfo);
        mAllPhotoFolderList.get(0).setPhotoList(photoInfoList);

        if (mFolderListAdapter.getSelectFolder() != null) {
            PhotoFolderInfo photoFolderInfo = mFolderListAdapter.getSelectFolder();
            List<PhotoInfo> list = photoFolderInfo.getPhotoList();
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(0, photoInfo);
            if (list.size() == 1) {
                photoFolderInfo.setCoverPhoto(photoInfo);
            }
            mFolderListAdapter.getSelectFolder().setPhotoList(list);
        } else {
            String folderA = new File(photoInfo.getPhotoPath()).getParent();
            for (int i = 1; i < mAllPhotoFolderList.size(); i++) {
                PhotoFolderInfo folderInfo = mAllPhotoFolderList.get(i);
                String folderB = null;
                if (!StringUtils.isEmpty(photoInfo.getPhotoPath())) {
                    folderB = new File(photoInfo.getPhotoPath()).getParent();
                }
                if (TextUtils.equals(folderA, folderB)) {
                    List<PhotoInfo> list = folderInfo.getPhotoList();
                    if (list == null) {
                        list = new ArrayList<>();
                    }
                    list.add(0, photoInfo);
                    folderInfo.setPhotoList(list);
                    if (list.size() == 1) {
                        folderInfo.setCoverPhoto(photoInfo);
                    }
                }
            }
        }

        mFolderListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void takeResult(PhotoInfo photoInfo) {

        Message message = mHanlder.obtainMessage();
        message.obj = photoInfo;
        message.what = HANLDER_TAKE_PHOTO_EVENT;

        if (!GalleryFinal.getFunctionConfig().isMutiSelect()) { //单选
            mSelectedPhotoList.clear();
//            mSelectPhotoMap.put(photoInfo.getPhotoPath(), photoInfo);
            mSelectedPhotoList.add(photoInfo);
            if (GalleryFinal.getFunctionConfig().isEditPhoto()) {//裁剪
                mHasRefreshGallery = true;
                toPhotoEdit();
            } else {
                ArrayList<PhotoInfo> list = new ArrayList<>();
                list.add(photoInfo);
                resultData(list);
            }

            mHanlder.sendMessageDelayed(message, 100);
        } else {//多选
//            mSelectPhotoMap.put(photoInfo.getPhotoPath(), photoInfo);
            mSelectedPhotoList.add(photoInfo);
            mHanlder.sendMessageDelayed(message, 100);
        }
    }

    /**
     * 执行裁剪
     */
    protected void toPhotoEdit() {
        Intent intent = new Intent(this, PhotoEditActivity.class);
        intent.putExtra(PhotoEditActivity.SELECT_MAP, mSelectedPhotoList);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {
            if (mLlFolderPanel.getVisibility() == View.VISIBLE) {
                btnFolder.performLongClick();
//                mLlTitle.performClick();
            } else {
                finish();
            }
        }else if (id == R.id.btnConfirm) {
            if (mSelectedPhotoList.size() > 0) {
//                ArrayList<PhotoInfo> photoList = new ArrayList<>(mSelectPhotoMap.values());
                if (!GalleryFinal.getFunctionConfig().isEditPhoto()) {
                    resultData(mSelectedPhotoList);
                } else {
                    toPhotoEdit();
                }
            }
        } else if (id == R.id.btnPreview) {
            Intent intent = new Intent(this, PhotoPreviewActivity.class);
//            intent.putExtra(PhotoPreviewActivity.PHOTO_LIST, new ArrayList<>(mSelectPhotoMap.values()));
            intent.putExtra(PhotoPreviewActivity.PHOTO_LIST, mSelectedPhotoList);
            startActivityForResult(intent, RESULT_PREVIEW);
//            startActivity(intent);
        } else if (id == R.id.btnFolder) {
            if (mLlFolderPanel.getVisibility() == View.VISIBLE) {
                mLlFolderPanel.setVisibility(View.GONE);
                mLlFolderPanel.setAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_up_bottom));
            } else {
                mLlFolderPanel.setAnimation(AnimationUtils.loadAnimation(this, R.anim.animation_bottom_up));
                mLlFolderPanel.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.btnTakePhoto) {
            performCodeWithPermission(new PermissionCallback() {
                @Override
                public void hasPermission() {
                    if (GalleryFinal.getFunctionConfig().isMutiSelect() && mSelectedPhotoList.size() == GalleryFinal.getFunctionConfig().getMaxSize()) {
                        toast(getString(R.string.select_max_tips));
                        return;
                    }

                    if (!DeviceUtils.existSDCard()) {
                        toast(getString(R.string.empty_sdcard));
                        return;
                    }
                    takePhotoAction();
                }

                @Override
                public void noPermission() {

                }
            }, Manifest.permission.CAMERA);

        }else if(id == R.id.bottomLayout){

        }else if(id == R.id.ll_folder_panel){
           btnFolder.performClick();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_PREVIEW && resultCode == RESULT_OK) {
            ArrayList<PhotoInfo> unSelectList = (ArrayList<PhotoInfo>) data.getSerializableExtra(PhotoPreviewActivity.UNSELECT_LIST);
            boolean isSend = data.getBooleanExtra(PhotoPreviewActivity.IS_CONFIRM,false);
            deleteSelect(unSelectList);
            if(isSend){
                btnConfirm.performClick();
            }

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int parentId = parent.getId();
        if (parentId == R.id.lv_folder_list) {
            folderItemClick(position);
        } else {
            photoItemClick(view, position);
        }
    }

    private void folderItemClick(int position) {
        mLlFolderPanel.setVisibility(View.GONE);
        mCurPhotoList.clear();
        PhotoFolderInfo photoFolderInfo = mAllPhotoFolderList.get(position);
        if (photoFolderInfo.getPhotoList() != null) {
            mCurPhotoList.addAll(photoFolderInfo.getPhotoList());
        }
        mPhotoListAdapter.notifyDataSetChanged();

        if (position == 0) {
            mPhotoTargetFolder = null;
        } else {
            PhotoInfo photoInfo = photoFolderInfo.getCoverPhoto();
            if (photoInfo != null && !StringUtils.isEmpty(photoInfo.getPhotoPath())) {
                mPhotoTargetFolder = new File(photoInfo.getPhotoPath()).getParent();
            } else {
                mPhotoTargetFolder = null;
            }
        }
//        mTvSubTitle.setText(photoFolderInfo.getFolderName());
        mFolderListAdapter.setSelectFolder(photoFolderInfo);
        mFolderListAdapter.notifyDataSetChanged();

        if (mCurPhotoList.size() == 0) {
            mTvEmptyView.setText(R.string.no_photo);
        }
    }

    private void photoItemClick(View view, int position) {
        PhotoInfo info = mCurPhotoList.get(position);
        if (!GalleryFinal.getFunctionConfig().isMutiSelect()) {
            mSelectedPhotoList.clear();
//            mSelectPhotoMap.put(info.getPhotoPath(), info);
            mSelectedPhotoList.add(info);
            String ext = FilenameUtils.getExtension(info.getPhotoPath());
            if (GalleryFinal.getFunctionConfig().isEditPhoto() && (ext.equalsIgnoreCase("png")
                    || ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg"))) {
                toPhotoEdit();
            } else {
                ArrayList<PhotoInfo> list = new ArrayList<>();
                list.add(info);
                resultData(list);
            }
            return;
        }
        boolean checked = false;
//        if (mSelectPhotoMap.get(info.getPhotoPath()) == null) {
        if (!mSelectedPhotoList.contains(info)) {
            if (GalleryFinal.getFunctionConfig().isMutiSelect() && mSelectedPhotoList.size() == GalleryFinal.getFunctionConfig().getMaxSize()) {
                toast(getString(R.string.select_max_tips));
                return;
            } else {
//                mSelectPhotoMap.put(info.getPhotoPath(), info);
                mSelectedPhotoList.add(info);
                checked = true;
            }
        } else {
//            mSelectPhotoMap.remove(info.getPhotoPath());
            mSelectedPhotoList.remove(info);
            checked = false;
        }
        refreshSelectCount();

        PhotoListAdapter.PhotoViewHolder holder = (PhotoListAdapter.PhotoViewHolder) view.getTag();
        if (holder != null) {
            if (checked) {
              //  holder.mIvCheck.setBackgroundColor(GalleryFinal.getGalleryTheme().getCheckSelectedColor());
                holder.mIvCheck.setBackgroundColor(Color.parseColor("#FF7700"));
            } else {
                holder.mIvCheck.setBackgroundColor(Color.parseColor("#d2d2d7"));
            }
        } else {
            mPhotoListAdapter.notifyDataSetChanged();
        }
    }


    public void refreshSelectCount() {
        btnPreview.setText(getString(R.string.selected, mSelectedPhotoList.size(), GalleryFinal.getFunctionConfig().getMaxSize()));
        btnConfirm.setText(getString(R.string.confirm, mSelectedPhotoList.size(), GalleryFinal.getFunctionConfig().getMaxSize()));
        if (mSelectedPhotoList.size() <= 0) {
            btnPreview.setEnabled(false);
            btnConfirm.setEnabled(false);
        } else {
            btnConfirm.setEnabled(true);
            btnPreview.setEnabled(true);
        }
    }

    private void getPhotos() {
        mTvEmptyView.setText(R.string.waiting);
        mGvPhotoList.setEnabled(false);
        /*mLlTitle.setEnabled(false);
        mIvTakePhoto.setEnabled(false);*/
        new Thread() {
            @Override
            public void run() {
                super.run();

                mAllPhotoFolderList.clear();
                List<PhotoFolderInfo> allFolderList = PhotoTools.getAllPhotoFolder(PhotoSelectActivity.this, mSelectedPhotoList);
                mAllPhotoFolderList.addAll(allFolderList);

                mCurPhotoList.clear();
                if (allFolderList.size() > 0) {
                    if (allFolderList.get(0).getPhotoList() != null) {
                        mCurPhotoList.addAll(allFolderList.get(0).getPhotoList());
                    }
                }

                refreshAdapter();
            }
        }.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mLlFolderPanel.getVisibility() == View.VISIBLE) {
//                mLlTitle.performClick();
                btnFolder.performClick();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mHasRefreshGallery) {
            mHasRefreshGallery = false;
//            requestGalleryPermission();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (GalleryFinal.getCoreConfig() != null &&
                GalleryFinal.getCoreConfig().getImageLoader() != null) {
            GalleryFinal.getCoreConfig().getImageLoader().clearMemoryCache();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPhotoTargetFolder = null;
        mSelectedPhotoList.clear();
        System.gc();
    }
}
