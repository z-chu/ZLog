package com.zchu.log;

/**
 * @author Orhan Obut
 */
 public interface Printer {

    Printer t(String tag, int methodCount);

    Settings init(String tag);

    Settings getSettings();

    void d(Object message);

    void e(Object message);

    void e(Throwable throwable, Object message);

    void w(Object message);

    void i(Object message);

    void v(Object message);

    void a(Object message);



}
