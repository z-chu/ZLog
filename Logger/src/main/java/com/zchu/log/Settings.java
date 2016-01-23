package com.zchu.log;

import android.content.Context;

/**
 * @author Orhan Obut
 */
public final class Settings {

    private int methodCount = 2;
    private int methodOffset = 0;
    private boolean showThreadInfo = true;
    private boolean smartTag = false;
    private boolean fileIsShowLog = true;
    private boolean fileIsShowTime = true;
    private String fileDir = null;//文件存储目录
    private Context fileWithContext = null;
    /**
     * Determines how logs will printed
     */
    private LogLevel logLevel = LogLevel.FULL;

    public Settings hideThreadInfo() {
        showThreadInfo = false;
        return this;
    }

    public Settings setMethodCount(int methodCount) {
        if (methodCount < 0) {
            methodCount = 0;
        }
        this.methodCount = methodCount;
        return this;
    }

    public Settings setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
        return this;
    }

    public Settings setMethodOffset(int offset) {
        this.methodOffset = offset;
        return this;
    }

    public Settings smartTag(boolean smartTag) {
        this.smartTag = smartTag;
        return this;
    }

    public Settings fileIsShowLog(boolean fileIsShowLog) {
        this.fileIsShowLog = fileIsShowLog;
        return this;
    }

    public Settings fileDir(String fileDir) {
        this.fileDir = fileDir;
        return this;
    }

    public Settings fileIsShowTime(boolean fileIsShowTime) {
        this.fileIsShowTime = fileIsShowTime;
        return this;
    }

    public Settings fileWithContext(Context fileWithContext) {
        this.fileWithContext = fileWithContext;
        return this;
    }

    public int getMethodCount() {
        return methodCount;
    }

    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public int getMethodOffset() {
        return methodOffset;
    }

    public boolean isSmartTag() {
        return smartTag;
    }

    public Context getFileWithContext() {
        return fileWithContext;
    }

    public String getFileDir() {
        return fileDir;
    }

    public boolean isFileIsShowLog() {
        return fileIsShowLog;
    }

    public boolean isFileIsShowTime() {
        return fileIsShowTime;
    }
}
