package com.android.test.download.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2015/10/23 0023.
 */
@DatabaseTable(tableName="download")
public class DownLoadBean {

    @DatabaseField(id = true)
    public String id;
    @DatabaseField(columnName = "file_path")
    public String dlLocalFile;
    @DatabaseField(columnName = "base_url")
    public String baseUrl;
    @DatabaseField(columnName = "real_url")
    public String realUrl;
    @DatabaseField(columnName = "onThreadProgress")
    public int progress;
    @DatabaseField(columnName = "file_length")
    public int length;
    @DatabaseField(columnName = "start")
    public int start;
    @DatabaseField(columnName = "end")
    public int end;
    @DatabaseField(columnName = "state")
    public int state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDlLocalFile() {
        return dlLocalFile;
    }

    public void setDlLocalFile(String dlLocalFile) {
        this.dlLocalFile = dlLocalFile;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
