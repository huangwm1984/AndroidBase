package com.android.test.download.cons;

/**
 * 公共常量
 * Public Constants.
 */
public final class PublicCons {
    /**
     * 文件访问模式
     */
    public static final class AccessModes {
        public static final String ACCESS_MODE_R = "r";
        public static final String ACCESS_MODE_RW = "rw";
        public static final String ACCESS_MODE_RWS = "rws";
        public static final String ACCESS_MODE_RWD = "rwd";
    }

    /**
     * 网络类型
     */
    public static final class NetType {
        public static final int INVALID = 0;
        public static final int WAP = 1;
        public static final int G2 = 2;
        public static final int G3 = 3;
        public static final int WIFI = 4;
        public static final int NO_WIFI = 5;
    }

    /**
     * 数据库常量
     */
    public static final class DBCons {
        public static final String TB_TASK_ID = "id";
        public static final String TB_TASK_URL_BASE = "base_url";
        public static final String TB_TASK_URL_REAL = "real_url";
        public static final String TB_TASK_FILE_PATH = "file_path";
        public static final String TB_TASK_PROGRESS = "onThreadProgress";
        public static final String TB_TASK_FILE_LENGTH = "file_length";
        public static final String TB_THREAD_START = "start";
        public static final String TB_THREAD_END = "end";
        public static final String TB_THREAD_ID = "id";
    }

    /**
     * 下载状态
     *
     */
    public static final class DownLoadState {
        public static int DOWNLOAD_NORMAL = 0;//未下载
        public static int DOWNLOAD_START = 1;//开始下载
        public static int DOWNLOAD_SUCCESS = 2;//下载成功
        public static int DOWNLOAD_STOP = 3;//下载停止
        public static int DOWNLOAD_FAIL = 4;//下载失败
        public static int DOWNLOAD_CANCEL = 5;//取消下载
    }
}