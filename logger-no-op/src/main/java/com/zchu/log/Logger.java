package com.zchu.log;

public final class Logger {

    private static final Printer printer = new NonePrinter();
    //no instance
    private Logger() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static Settings init() {
        return printer.init("");
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
        return printer;
    }

    public static Printer t(int methodCount) {
        return printer;
    }

    public static Printer t(String tag, int methodCount) {
        return printer;
    }

    public static void d(Object message) {
    }

    public static void e(Object message) {

    }

    public static void e(Throwable throwable, Object message) {

    }

    public static void i(Object message) {

    }

    public static void v(Object message) {

    }

    public static void w(Object message) {

    }

    public static void a(Object message) {

    }

    public static String fJson(String json){
      return json;
    }

    public static String fJson(String json, int indent){
        return json;
    }

    public static String fXml(String xml){
       return xml;
    }




}
