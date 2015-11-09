package com.android.test.download.entities;

import java.io.File;
import java.io.Serializable;

/**
 * 下载实体类
 * Download entity.
 *
 * @author AigeStudio 2015-05-16
 */
public class DownLoadInfo implements Serializable {
    public File dlLocalFile;
    public String baseUrl, realUrl;

    public DownLoadInfo(File dlLocalFile, String baseUrl, String realUrl) {
        this.dlLocalFile = dlLocalFile;
        this.baseUrl = baseUrl;
        this.realUrl = realUrl;
    }
}
