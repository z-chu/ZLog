package com.zchu.log;

import android.util.Log;

import org.json.JSONException;

import javax.xml.transform.TransformerException;


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
     * @return the settings object
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

    public static void e(Object message) {
        printer.e(message);
    }

    public static void e(Throwable throwable, Object message) {
        printer.e(throwable, message);
    }

    public static void i(Object message) {
        printer.i(message);
    }

    public static void v(Object message) {
        printer.v(message);
    }

    public static void w(Object message) {
        printer.w(message);
    }

    public static void a(Object message) {
        printer.a(message);
    }

    public static String fJson(String json){
      return fJson(json,4);
    }

    public static String fJson(String json, int indent){
        try {
            return  SystemUtil.jsonToMessage(json,indent);
        } catch (JSONException e) {
            return   Log.getStackTraceString(e) + "\n" + json;
        }
    }

    public static String fXml(String xml){
        try {
            return  SystemUtil.xmlToMessage(xml);
        } catch (TransformerException e) {
           return  Log.getStackTraceString(e)+ "\n" + xml;
        }
    }




}
