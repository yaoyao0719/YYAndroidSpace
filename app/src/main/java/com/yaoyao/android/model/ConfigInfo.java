package com.yaoyao.android.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/6/15
 * @desc  :配置信息
 */

@Entity(nameInDb = "config")
public class ConfigInfo {

    @Id @NotNull @Index
    @Property(nameInDb = "key")
    private String key;

    @Property(nameInDb = "value")
    private String value;

    @Generated(hash = 214226567)
    public ConfigInfo(@NotNull String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Generated(hash = 724259026)
    public ConfigInfo() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
