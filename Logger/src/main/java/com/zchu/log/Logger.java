package com.zchu.log;

import android.util.Log;


/**
 * Logger is a wrapper of {@link Log}
 * But more pretty, simple and powerful
 *
 * @author Orhan Obut
 */
public final class Logger {

    private static final Printer printer = new LoggerPrinter();
    private static final String DEFAULT_TAG = "PRETTYLOGGER";

    //no instance
    private Logger() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * It is used to get the settings object in order to change settings
     *
     * @return the settings object
     */
    public static Settings init() {
        return printer.init(DEFAULT_TAG);
    }


    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger
     */
    public static Settings init(String tag) {
        return printer.init(tag);
    }


    public static Printer t(String tag) {
        return printer.t(tag, printer.getSettings().getMethodCount());
    }

    public static Printer t(int methodCount) {
        return printer.t(null, methodCount);
    }

    public static Printer t(String tag, int methodCount) {
        return printer.t(tag, methodCount);
    }

    public static void d(Object message) {
        printer.d(message);
    }

    public static void dJson(String json) {
        printer.dJson(json);
    }

    public static void e(Object message) {
        printer.e(message);
    }

    public static void eJson(String json) {
        printer.eJson(json);
    }

    public static void e(Throwable throwable, Object message) {
        printer.e(throwable, message);
    }


    public static void i(Object message) {
        printer.i(message);
    }

    public static void iJson(String json) {
        printer.iJson(json);
    }

    public static void v(Object message) {
        printer.v(message);
    }

    public static void vJson(String json) {
        printer.vJson(json);
    }

    public static void w(Object message) {
        printer.w(message);
    }

    public static void wJson(String json) {
        printer.wJson(json);
    }

    public static void a(Object message) {
        printer.a(message);
    }

    public static void aJson(String json) {
        printer.aJson(json);
    }


    public static void dXml(String xml) {
        printer.dXml(xml);
    }

    public static void eXml(String xml) {
        printer.eXml(xml);
    }

    public static void iXml(String xml) {
        printer.iXml(xml);
    }

    public static void vXml(String xml) {
        printer.vXml(xml);
    }

    public static void wXml(String xml) {
        printer.wXml(xml);
    }

    public static void aXml(String xml) {
        printer.aXml(xml);
    }

    /**
     * @param message    信息
     * @param prefixName 文件的前缀名 ,完整名称为:prefixName_time_versionName.txt
     * @return 是否写入成功
     */
    public static boolean file(Object message, String prefixName) {
        return printer.file(message, prefixName);
    }

}
