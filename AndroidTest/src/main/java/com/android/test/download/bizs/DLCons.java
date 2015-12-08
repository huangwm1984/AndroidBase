package com.android.test.download.bizs;

import android.provider.BaseColumns;

public class DLCons {
    private DLCons() {
    }

    public static boolean DEBUG = true;

    public static final class Base {
        public static final int DEFAULT_TIMEOUT = 20000;
        public static final int MAX_REDIRECTS = 5;
        public static final int LENGTH_PER_THREAD = 10485760;
    }

    public static final class Code {
        public static final int HTTP_CONTINUE = 100;
        public static final int HTTP_SWITCHING_PROTOCOLS = 101;
        public static final int HTTP_PROCESSING = 102;

        public static final int HTTP_OK = 200;
        public static final int HTTP_CREATED = 201;
        public static final int HTTP_ACCEPTED = 202;
        public static final int HTTP_NOT_AUTHORITATIVE = 203;
        public static final int HTTP_NO_CONTENT = 204;
        public static final int HTTP_RESET = 205;
        public static final int HTTP_PARTIAL = 206;
        public static final int HTTP_MULTI_STATUS = 207;

        public static final int HTTP_MULT_CHOICE = 300;
        public static final int HTTP_MOVED_PERM = 301;
        public static final int HTTP_MOVED_TEMP = 302;
        public static final int HTTP_SEE_OTHER = 303;
        public static final int HTTP_NOT_MODIFIED = 304;
        public static final int HTTP_USE_PROXY = 305;
        public static final int HTTP_TEMP_REDIRECT = 307;

        public static final int HTTP_BAD_REQUEST = 400;
        public static final int HTTP_UNAUTHORIZED = 401;
        public static final int HTTP_PAYMENT_REQUIRED = 402;
        public static final int HTTP_FORBIDDEN = 403;
        public static final int HTTP_NOT_FOUND = 404;
        public static final int HTTP_BAD_METHOD = 405;
        public static final int HTTP_NOT_ACCEPTABLE = 406;
        public static final int HTTP_PROXY_AUTH = 407;
        public static final int HTTP_CLIENT_TIMEOUT = 408;
        public static final int HTTP_CONFLICT = 409;
        public static final int HTTP_GONE = 410;
        public static final int HTTP_LENGTH_REQUIRED = 411;
        public static final int HTTP_PRECON_FAILED = 412;
        public static final int HTTP_ENTITY_TOO_LARGE = 413;
        public static final int HTTP_REQ_TOO_LONG = 414;
        public static final int HTTP_UNSUPPORTED_TYPE = 415;
        public static final int HTTP_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
        public static final int HTTP_EXPECTATION_FAILED = 417;
        public static final int HTTP_UNPROCESSABLE_ENTITY = 422;
        public static final int HTTP_LOCKED = 423;
        public static final int HTTP_FAILED_DEPENDENCY = 424;

        public static final int HTTP_INTERNAL_ERROR = 500;
        public static final int HTTP_NOT_IMPLEMENTED = 501;
        public static final int HTTP_BAD_GATEWAY = 502;
        public static final int HTTP_UNAVAILABLE = 503;
        public static final int HTTP_GATEWAY_TIMEOUT = 504;
        public static final int HTTP_VERSION = 505;
        public static final int HTTP_INSUFFICIENT_STORAGE = 507;
    }

    public static final class DBCons {
        public static final String TB_TASK = "task_info";
        public static final String TB_TASK_URL_BASE = "base_url";
        public static final String TB_TASK_URL_REAL = "real_url";
        public static final String TB_TASK_DIR_PATH = "file_path";
        public static final String TB_TASK_CURRENT_BYTES = "currentBytes";
        public static final String TB_TASK_TOTAL_BYTES = "totalBytes";
        public static final String TB_TASK_FILE_NAME = "file_name";
        public static final String TB_TASK_MIME_TYPE = "mime_type";
        public static final String TB_TASK_ETAG = "e_tag";
        public static final String TB_TASK_DISPOSITION = "disposition";
        public static final String TB_TASK_LOCATION = "location";
        public static final String TB_TASK_STATE = "state";
        public static final String TB_TASK_APP_NAME = "name";
        public static final String TB_TASK_APP_PROGRESS = "progress";

        public static final String TB_THREAD = "thread_info";
        public static final String TB_THREAD_URL_BASE = "base_url";
        public static final String TB_THREAD_START = "start";
        public static final String TB_THREAD_END = "end";
        public static final String TB_THREAD_ID = "id";

        public static final String TB_TASK_SQL_CREATE = "CREATE TABLE " +
                DBCons.TB_TASK + "(" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBCons.TB_TASK_URL_BASE + " CHAR, " +
                DBCons.TB_TASK_URL_REAL + " CHAR, " +
                DBCons.TB_TASK_DIR_PATH + " CHAR, " +
                DBCons.TB_TASK_FILE_NAME + " CHAR, " +
                DBCons.TB_TASK_MIME_TYPE + " CHAR, " +
                DBCons.TB_TASK_ETAG + " CHAR, " +
                DBCons.TB_TASK_DISPOSITION + " CHAR, " +
                DBCons.TB_TASK_LOCATION + " CHAR, " +
                DBCons.TB_TASK_CURRENT_BYTES + " INTEGER, " +
                DBCons.TB_TASK_TOTAL_BYTES + " INTEGER)";
        public static final String TB_THREAD_SQL_CREATE = "CREATE TABLE " +
                DBCons.TB_THREAD + "(" +
                BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBCons.TB_THREAD_URL_BASE + " CHAR, " +
                DBCons.TB_THREAD_START + " INTEGER, " +
                DBCons.TB_THREAD_END + " INTEGER, " +
                DBCons.TB_THREAD_ID + " CHAR)";

        public static final String TB_TASK_SQL_UPGRADE = "DROP TABLE IF EXISTS " +
                DBCons.TB_TASK;
        public static final String TB_THREAD_SQL_UPGRADE = "DROP TABLE IF EXISTS " +
                DBCons.TB_THREAD;
    }

    public static final class DLState {

        //==============State=================
        public static final int DOWNLOAD = 0;//准备
        public static final int WAIT = 1;//等待
        public static final int DOWNLOADING = 2;//下载中
        public static final int PAUSE = 3;//暂停
        public static final int COMPLETE = 4;//完成
        public static final int FAIL = 5;//失败
    }

    public static final class DLDescription {

        //==============State=================
        public static final int ALREADY_EXISTS = 0;//本地apk已经存在
        public static final int DOWNLOAD_COMPLETE = 1;//下载完成
    }
}