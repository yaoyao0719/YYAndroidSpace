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

package cn.finalteam.galleryfinal.model;

import java.io.Serializable;


public class PhotoInfo implements Serializable {

    private int photoId;
    private String photoPath;
    //private String thumbPath;
    private int width;
    private int height;
    private boolean isFull;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    private String size;

    public PhotoInfo() {}

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    @Override
    public boolean equals(Object ob) {
        PhotoInfo info = (PhotoInfo) ob;
        if(photoId== info.photoId && photoPath!=null && photoPath.equals(info.getPhotoPath()) && width == info.getWidth() && height == info.getHeight()){
            return true;
        }else {
            return false;
        }
    }

    //
    //public String getThumbPath() {
    //    return thumbPath;
    //}
    //
    //public void setThumbPath(String thumbPath) {
    //    this.thumbPath = thumbPath;
    //}
}
