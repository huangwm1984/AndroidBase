package com.hwm.test.download.bizs;

import com.hwm.test.download.interfaces.IDListener;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.hwm.test.download.bizs.DLCons.DBCons;

/**
 * 下载实体类
 * Download entity.
 *
 * @author AigeStudio 2015-05-16
 */
@DatabaseTable(tableName=DBCons.TB_TASK)
public class DLInfo {

    @DatabaseField(generatedId = true)
    public int id;
    @DatabaseField(columnName = DBCons.TB_TASK_TOTAL_BYTES)
    public int totalBytes;
    @DatabaseField(columnName = DBCons.TB_TASK_CURRENT_BYTES)
    public int currentBytes;
    @DatabaseField(columnName = DBCons.TB_TASK_FILE_NAME)
    public String fileName;
    @DatabaseField(columnName = DBCons.TB_TASK_DIR_PATH)
    public String dirPath;
    @DatabaseField(columnName = DBCons.TB_TASK_URL_BASE)
    public String baseUrl;
    @DatabaseField(columnName = DBCons.TB_TASK_URL_REAL)
    public String realUrl;

    public int redirect;
    public boolean hasListener;
    public boolean isResume;
    @DatabaseField(columnName = DBCons.TB_TASK_MIME_TYPE)
    public String mimeType;
    @DatabaseField(columnName = DBCons.TB_TASK_ETAG)
    public String eTag;
    @DatabaseField(columnName = DBCons.TB_TASK_DISPOSITION)
    public String disposition;
    @DatabaseField(columnName = DBCons.TB_TASK_LOCATION)
    public String location;
    public List<DLHeader> requestHeaders;
    public final List<DLThreadInfo> threads;
    public IDListener listener;
    public File file;
    @DatabaseField(columnName = DBCons.TB_TASK_STATE, defaultValue = "0")
    public int state;//下载状态
    @DatabaseField(columnName = DBCons.TB_TASK_APP_NAME)
    public String appName;//应用名字
    public long networkSpeed;//下载速度
    @DatabaseField(columnName = DBCons.TB_TASK_APP_PROGRESS)
    public int progress;//下载进度

    public DLInfo() {
        threads = new ArrayList<>();
    }

    public synchronized void addDLThread(DLThreadInfo info) {
        threads.add(info);
    }

    public synchronized void removeDLThread(DLThreadInfo info) {
        threads.remove(info);
    }

    public int getTotalBytes() {
        return totalBytes;
    }

    public int getCurrentBytes() {
        return currentBytes;
    }

    public String getFileName() {
        return fileName;
    }

    public String getDirPath() {
        return dirPath;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public int getRedirect() {
        return redirect;
    }

    public boolean isHasListener() {
        return hasListener;
    }

    public boolean isResume() {
        return isResume;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String geteTag() {
        return eTag;
    }

    public String getDisposition() {
        return disposition;
    }

    public String getLocation() {
        return location;
    }

    public List<DLHeader> getRequestHeaders() {
        return requestHeaders;
    }

    public List<DLThreadInfo> getThreads() {
        return threads;
    }

    public IDListener getListener() {
        return listener;
    }

    public File getFile() {
        return file;
    }

    public int getState() {
        return state;
    }

    public String getAppName() {
        return appName;
    }

    public long getNetworkSpeed() {
        return networkSpeed;
    }

    public int getProgress() {
        return progress;
    }

    public void setTotalBytes(int totalBytes) {
        this.totalBytes = totalBytes;
    }

    public void setCurrentBytes(int currentBytes) {
        this.currentBytes = currentBytes;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setDirPath(String dirPath) {
        this.dirPath = dirPath;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public void setRedirect(int redirect) {
        this.redirect = redirect;
    }

    public void setHasListener(boolean hasListener) {
        this.hasListener = hasListener;
    }

    public void setResume(boolean resume) {
        isResume = resume;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void seteTag(String eTag) {
        this.eTag = eTag;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRequestHeaders(List<DLHeader> requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    public void setListener(IDListener listener) {
        this.listener = listener;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setNetworkSpeed(long networkSpeed) {
        this.networkSpeed = networkSpeed;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}