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
    @DatabaseField(columnName = DBCons.TB_TASK_APP_SPEED)
    public long networkSpeed;//下载速度
    @DatabaseField(columnName = DBCons.TB_TASK_APP_PROGRESS)
    public int progress;//下载进度
    @DatabaseField(columnName = DBCons.TB_TASK_APP_POSITION)
    public int position;

    public DLInfo() {
        threads = new ArrayList<>();
    }

    public synchronized void addDLThread(DLThreadInfo info) {
        threads.add(info);
    }

    public synchronized void removeDLThread(DLThreadInfo info) {
        threads.remove(info);
    }


}