package com.zchu.log;

/**
 * @author Orhan Obut
 */
public interface Printer {

    Printer t(String tag, int methodCount);

    Settings init(String tag);

    Settings getSettings();

    void d(Object message);

    void dJson(String json);

    void e(Object message);

    void eJson(String json);

    void e(Throwable throwable, Object message);

    void w(Object message);

    void wJson(String json);

    void i(Object message);

    void iJson(String json);

    void v(Object message);

    void vJson(String json);

    void a(Object message);

    void aJson(String json);

    void dXml(String xml);

    void eXml(String xml);

    void wXml(String xml);

    void iXml(String xml);

    void vXml(String xml);

    void aXml(String xml);

    boolean file(Object message, String prefixName);


}
