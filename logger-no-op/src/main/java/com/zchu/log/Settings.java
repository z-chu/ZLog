package com.zchu.log;

/**
 * @author Orhan Obut
 */
public final class Settings {

    public Settings hideThreadInfo() {
        return this;
    }

    public Settings setMethodCount(int methodCount) {
        return this;
    }

    public Settings setLogLevel(LogLevel logLevel) {
        return this;
    }

    public Settings setMethodOffset(int offset) {
        return this;
    }

    public Settings smartTag(boolean smartTag) {
        return this;
    }


    public int getMethodCount() {
        return 0;
    }

    public boolean isShowThreadInfo() {
        return false;
    }

    public LogLevel getLogLevel() {
        return LogLevel.NONE;
    }

    public int getMethodOffset() {
        return 0;
    }

    public boolean isSmartTag() {
        return false;
    }

}
