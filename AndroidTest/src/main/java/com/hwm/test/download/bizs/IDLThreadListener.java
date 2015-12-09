package com.hwm.test.download.bizs;

interface IDLThreadListener {
    void onProgress(int progress);

    void onStop(DLThreadInfo threadInfo);

    void onFinish(DLThreadInfo threadInfo);
}