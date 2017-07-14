package com.yaoyao.android.module.gank;

import java.io.Serializable;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/7/13
 * @desc  :
 */

public class GankResponse<T> implements Serializable {
    public boolean error;
    public T results;
}
