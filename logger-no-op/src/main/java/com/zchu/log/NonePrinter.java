package com.zchu.log;

/**
 * Created by Chu on 2016/11/16.
 */

public class NonePrinter implements Printer{
    private static final  Settings settings = new Settings();

    @Override
    public Printer t(String tag, int methodCount) {
        return this;
    }

    @Override
    public Settings init(String tag) {
        return settings;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public void d(Object message) {

    }

    @Override
    public void e(Object message) {

    }

    @Override
    public void e(Throwable throwable, Object message) {

    }

    @Override
    public void w(Object message) {

    }

    @Override
    public void i(Object message) {

    }

    @Override
    public void v(Object message) {

    }

    @Override
    public void a(Object message) {

    }
}
