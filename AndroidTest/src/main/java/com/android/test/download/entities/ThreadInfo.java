package com.android.test.download.entities;

import java.io.File;
import java.io.Serializable;

/**
 * 线程实体类
 * Thread entity.
 *
 * @author AigeStudio 2015-05-16
 * @author AigeStudio 2015-05-29
 *         修改构造方法
 */
public class ThreadInfo extends DownLoadInfo implements Serializable {
    public String id;
    public int start, end;

    public ThreadInfo(File dlLocalFile, String baseUrl, String realUrl, int start, int end, String id) {
        super(dlLocalFile, baseUrl, realUrl);
        this.start = start;
        this.end = end;
        this.id = id;
    }
}
