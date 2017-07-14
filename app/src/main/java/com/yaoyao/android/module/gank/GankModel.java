package com.yaoyao.android.module.gank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author :yaoyao
 * @email  :yaoyao@jingzhengu.com
 * @date   :2017/7/12
 * @desc   :
 * @example: {
              "_id": "595f7f6d421aa90ca209c416",
              "createdAt": "2017-07-07T20:32:45.22Z",
              "desc": "\u6839\u636e\u5b9e\u9645\u5f00\u53d1\u4e2d\u9047\u5230\u7684\u9700\u6c42\uff0c\u4f7f\u7528 Gradle \u5bf9\u5e94\u7528\u7684\u4e0d\u540c\u7248\u672c\u8fdb\u884c\u4e2a\u6027\u5316\u5b9a\u5236\u3002",
              "images":  ["http://img.gank.io/0be70b9b-dc5a-4778-bb7b-0f5ff0e73d2a"],
              "publishedAt": "2017-07-11T13:46:33.911Z",
              "source": "chrome",
              "type": "Android",
              "url": "http://www.imliujun.com/gradle1.html",
              "used": true,
              "who": "LiuJun"
             }
 */

public class GankModel implements Serializable{
    public String _id;
    public Date createdAt;
    public String desc;
    public List<String> images;
    public Date publishedAt;
    public String source;
    public String type;
    public String url;
    public boolean used;
    public String who;
}
