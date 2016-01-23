package com.zchu.log;

import android.content.pm.PackageInfo;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.zchu.log.util.SystemUtil;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.transform.TransformerException;

/**
 * Logger is a wrapper of {@link Log}
 * But more pretty, simple and powerful
 *
 * @author Orhan Obut
 */
final class LoggerPrinter implements Printer {

    /**
     * Android's max limit for a log entry is ~4076 bytes,
     * so 4000 bytes is used as chunk size since default charset
     * is UTF-8
     */
    private static final int CHUNK_SIZE = 4000;

    /**
     * It is used for json pretty print
     */
    private static final int JSON_INDENT = 4;

    /**
     * The minimum stack trace index, starts at this class after two native calls.
     */
    private static final int MIN_STACK_OFFSET = 3;

    /**
     * It is used to determine log settings such as method count, thread info visibility
     */
    private static final Settings settings = new Settings();

    /**
     * Drawing toolbox
     */
    private static final char TOP_LEFT_CORNER = '╔';
    private static final char BOTTOM_LEFT_CORNER = '╚';
    private static final char MIDDLE_CORNER = '╟';
    private static final char HORIZONTAL_DOUBLE_LINE = '║';
    private static final String DOUBLE_DIVIDER = "════════════════════════════════════════════";
    private static final String SINGLE_DIVIDER = "────────────────────────────────────────────";
    private static final String TOP_BORDER = TOP_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String BOTTOM_BORDER = BOTTOM_LEFT_CORNER + DOUBLE_DIVIDER + DOUBLE_DIVIDER;
    private static final String MIDDLE_BORDER = MIDDLE_CORNER + SINGLE_DIVIDER + SINGLE_DIVIDER;

    /**
     * TAG is used for the Log, the name is a little different
     * in order to differentiate the logs easily with the filter
     */
    private static String TAG = "PRETTYLOGGER";

    /**
     * Localize single tag and method count for each thread
     */
    private static final ThreadLocal<String> LOCAL_TAG = new ThreadLocal<>();
    private static final ThreadLocal<Integer> LOCAL_METHOD_COUNT = new ThreadLocal<>();

    /**
     * It is used to change the tag
     *
     * @param tag is the given string which will be used in Logger
     */
    @Override
    public Settings init(String tag) {
        if (tag == null) {
            throw new IllegalStateException("tag may not be null");
        }
        LoggerPrinter.TAG = tag;
        return settings;
    }

    @Override
    public Settings getSettings() {
        return settings;
    }

    @Override
    public Printer t(String tag, int methodCount) {
        if (tag != null) {
            LOCAL_TAG.set(tag);
        }
        LOCAL_METHOD_COUNT.set(methodCount);
        return this;
    }

    @Override
    public void d(Object message) {
        log(Log.DEBUG, SystemUtil.objectToMessage(message));
    }

    @Override
    public void dJson(String json) {
        try {
            log(Log.DEBUG, SystemUtil.jsonToMessage(json, JSON_INDENT));
        } catch (JSONException e) {
            e(Log.getStackTraceString(e) + "\n" + json);
        }
    }

    @Override
    public void e(Object message) {
        log(Log.ERROR, SystemUtil.objectToMessage(message));
        // e(null, objectToMessage(message), args);
    }

    @Override
    public void eJson(String json) {
        try {
            log(Log.ERROR, SystemUtil.jsonToMessage(json, JSON_INDENT));
        } catch (JSONException e) {
            e( Log.getStackTraceString(e)+ "\n" + json);
        }
    }


    @Override
    public void e(Throwable throwable, Object message) {
        String msg = SystemUtil.objectToMessage(message);
        if (throwable != null && msg != null) {
            msg += " : " + Log.getStackTraceString(throwable);
        }
        if (throwable != null && msg == null) {
            msg = throwable.toString();
        }
        if (msg == null) {
            msg = "No message/exception is set";
        }
        log(Log.ERROR, msg);
    }

    @Override
    public void w(Object message) {
        log(Log.WARN, SystemUtil.objectToMessage(message));
    }

    @Override
    public void wJson(String json) {
        try {
            log(Log.WARN, SystemUtil.jsonToMessage(json, JSON_INDENT));
        } catch (JSONException e) {
            e(Log.getStackTraceString(e)+ "\n" + json);
        }
    }

    @Override
    public void i(Object message) {
        log(Log.INFO, SystemUtil.objectToMessage(message));
    }

    @Override
    public void iJson(String json) {
        try {
            log(Log.INFO, SystemUtil.jsonToMessage(json, JSON_INDENT));
        } catch (JSONException e) {
            e(Log.getStackTraceString(e) + "\n" + json);
        }
    }

    @Override
    public void v(Object message) {
        log(Log.VERBOSE, SystemUtil.objectToMessage(message));
    }

    @Override
    public void vJson(String json) {
        try {
            log(Log.VERBOSE, SystemUtil.jsonToMessage(json, JSON_INDENT));
        } catch (JSONException e) {
            e(Log.getStackTraceString(e) + "\n" + json);
        }
    }

    @Override
    public void a(Object message) {
        log(Log.ASSERT, SystemUtil.objectToMessage(message));
    }

    @Override
    public void aJson(String json) {
        try {
            log(Log.ASSERT, SystemUtil.jsonToMessage(json, JSON_INDENT));
        } catch (JSONException e) {
            e(Log.getStackTraceString(e) + "\n" + json);
        }
    }

    @Override
    public void dXml(String xml) {
        try {
            log(Log.DEBUG, SystemUtil.xmlToMessage(xml));
        } catch (TransformerException e) {
            e(Log.getStackTraceString(e) + "\n" + xml);
        }
    }

    @Override
    public void eXml(String xml) {
        try {
            log(Log.ERROR, SystemUtil.xmlToMessage(xml));
        } catch (TransformerException e) {
            e(Log.getStackTraceString(e)+ "\n" + xml);
        }
    }

    @Override
    public void wXml(String xml) {
        try {
            log(Log.WARN, SystemUtil.xmlToMessage(xml));
        } catch (TransformerException e) {
            e(Log.getStackTraceString(e) + "\n" + xml);
        }
    }

    @Override
    public void iXml(String xml) {
        try {
            log(Log.INFO, SystemUtil.xmlToMessage(xml));
        } catch (TransformerException e) {
            e(Log.getStackTraceString(e)+ "\n" + xml);
        }
    }

    @Override
    public void vXml(String xml) {
        try {
            log(Log.VERBOSE, SystemUtil.xmlToMessage(xml));
        } catch (TransformerException e) {
            e(Log.getStackTraceString(e) + "\n" + xml);
        }
    }

    @Override
    public void aXml(String xml) {
        try {
            log(Log.ASSERT, SystemUtil.xmlToMessage(xml));
        } catch (TransformerException e) {
            e(Log.getStackTraceString(e) + "\n" + xml);
        }
    }

    @Override
    public boolean file(Object message, String prefixName) {
        if (settings.isFileIsShowLog()) {
            e(message);
        }
        return toFile(SystemUtil.objectToMessage(message), prefixName);
    }

    public boolean toFile(String message, String prefixName) {
        if (settings.getLogLevel() == LogLevel.NONE) {
            return false;
        }
        boolean flag = true;
        PackageInfo pkg = null;
        StringBuffer suffixName = new StringBuffer("ZLog"); //目录名
        String versionName = "";
        try {
            pkg = settings.getFileWithContext().getPackageManager().getPackageInfo(settings.getFileWithContext().getPackageName(), 0);
            suffixName.delete(0, suffixName.length());
            suffixName.append(pkg.applicationInfo.loadLabel(settings.getFileWithContext().getPackageManager()));
            versionName = "_" + pkg.versionName;
            suffixName.append(versionName);
            suffixName.append("_");
            suffixName.append(pkg.versionCode);
        } catch (Exception e) {
        }
        //    String fileName = suffixName.toString();
        String dirFilePath = null;
        if (settings.getFileDir() == null) {
            dirFilePath = Environment.getExternalStorageDirectory().getPath() + "/" + suffixName.toString();
        } else {
            dirFilePath = settings.getFileDir();
        }

        File dirfile = new File(dirFilePath);
        if (!dirfile.exists()) {
            // 如果目录不存在则创建该目录
            if(!dirfile.mkdir()){
                e("Failed to create the directory :"+dirFilePath+", please custom path");
                return false;
            }
        }
        StringBuffer str = new StringBuffer("");
        if (settings.isFileIsShowTime()) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日HH时mm分ss秒");
            Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
            str.append("_" + formatter.format(curDate));
        }
        File tofile = new File(dirFilePath + "/"
                + prefixName + str + versionName + ".txt");
        if (!tofile.getParentFile().exists()) {
            tofile.getParentFile().mkdirs();
        }
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;
        try {

            bufferedReader = new BufferedReader(new StringReader(message));
            bufferedWriter = new BufferedWriter(new FileWriter(tofile));
            char buf[] = new char[1024]; // 字符缓冲区
            int len;
            while ((len = bufferedReader.read(buf)) != -1) {
                bufferedWriter.write(buf, 0, len);
            }
            bufferedWriter.flush();
            bufferedReader.close();
            bufferedWriter.close();
            e("Exception to file succeed ----->" + tofile);
        }
        catch (FileNotFoundException e){
            e(e,"Write failed");
            return false;
        }catch (IOException e) {
            e(e, "Exception to file fail --/-->" + tofile);
            return false;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e(e);
                }
            }

        }
        return flag;
    }

    /**
     * This method is synchronized in order to avoid messy of logs' order.
     */
    private synchronized void log(int logType, String msg) {
        LogLevel logLevel = settings.getLogLevel();
        if (logLevel == LogLevel.NONE||(logLevel == LogLevel.ERROR && logType != Log.ERROR)) {
            return;
        }
        String tag = getTag();
        //String message = createMessage(msg, args);
        int methodCount = getMethodCount();

        logTopBorder(logType, tag);
        logHeaderContent(logType, tag, methodCount);

        //get bytes of message with system's default charset (which is UTF-8 for Android)
        byte[] bytes = msg.getBytes();
        int length = bytes.length;
        if (length <= CHUNK_SIZE) {
            if (methodCount > 0) {
                logDivider(logType, tag);
            }
            logContent(logType, tag, msg);
            logBottomBorder(logType, tag);
            return;
        }
        if (methodCount > 0) {
            logDivider(logType, tag);
        }
        for (int i = 0; i < length; i += CHUNK_SIZE) {
            int count = Math.min(length - i, CHUNK_SIZE);
            //create a new String with system's default charset (which is UTF-8 for Android)
            logContent(logType, tag, new String(bytes, i, count));
        }
        logBottomBorder(logType, tag);
    }

    private void logTopBorder(int logType, String tag) {
        logChunk(logType, tag, TOP_BORDER);
    }

    private void logHeaderContent(int logType, String tag, int methodCount) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (settings.isShowThreadInfo()) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " Thread: " + Thread.currentThread().getName());
            logDivider(logType, tag);
        }
        String level = "";

        int stackOffset = getStackOffset(trace) + settings.getMethodOffset();

        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = methodCount; i > 0; i--) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            level += "   ";
            logChunk(logType, tag, "║ "
                    + level + getSimpleClassName(trace[stackIndex].getClassName())
                    + "." + trace[stackIndex].getMethodName() + " "
                    + " (" + trace[stackIndex].getFileName() + ":" + trace[stackIndex].getLineNumber() + ")");
        }
    }

    private void logBottomBorder(int logType, String tag) {
        logChunk(logType, tag, BOTTOM_BORDER);
    }

    private void logDivider(int logType, String tag) {
        logChunk(logType, tag, MIDDLE_BORDER);
    }

    private void logContent(int logType, String tag, String chunk) {
        String[] lines = chunk.split(System.getProperty("line.separator"));
        for (String line : lines) {
            logChunk(logType, tag, HORIZONTAL_DOUBLE_LINE + " " + line);
        }
    }

    private void logChunk(int logType, String tag, String chunk) {
        final String finalTag;
        if (settings.isSmartTag()) {
            finalTag = getCurrentClassName() + formatTag(tag);
        } else {
            finalTag = formatTag(tag);
        }
        switch (logType) {
            case Log.ERROR:
                Log.e(finalTag, chunk);
                break;
            case Log.INFO:
                Log.i(finalTag, chunk);
                break;
            case Log.VERBOSE:
                Log.v(finalTag, chunk);
                break;
            case Log.WARN:
                Log.w(finalTag, chunk);
                break;
            case Log.ASSERT:
                Log.wtf(finalTag, chunk);
                break;
            case Log.DEBUG:
                // Fall through, log debug by default
            default:
                Log.d(finalTag, chunk);
                break;
        }
    }

    private String getCurrentClassName() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        int offset = getStackOffset(trace) + settings.getMethodOffset();
        StackTraceElement thisMethodStack = (new Exception()).getStackTrace()[offset - 1];
        String result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1, result.length());
        return result;
    }

    private String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    private String formatTag(String tag) {
        if (!TextUtils.isEmpty(tag) && !TextUtils.equals(TAG, tag)) {
            return TAG + "-" + tag;
        }
        return TAG;
    }

    /**
     * @return the appropriate tag based on local or global
     */
    private String getTag() {
        String tag = LOCAL_TAG.get();
        if (tag != null) {
            LOCAL_TAG.remove();
            return tag;
        }
        return TAG;
    }

    private String createMessage(String message, Object... args) {
        if (message == null) {
            message = "null";
        }
        return args.length == 0 ? message : String.format(message, args);
    }

    private int getMethodCount() {
        Integer count = LOCAL_METHOD_COUNT.get();
        int result = settings.getMethodCount();
        if (count != null) {
            LOCAL_METHOD_COUNT.remove();
            result = count;
        }
        if (result < 0) {
            throw new IllegalStateException("methodCount cannot be negative");
        }
        return result;
    }

    /**
     * Determines the starting index of the stack trace, after method calls made by this class.
     *
     * @param trace the stack trace
     * @return the stack offset
     */
    private int getStackOffset(StackTraceElement[] trace) {
        for (int i = MIN_STACK_OFFSET; i < trace.length; i++) {
            StackTraceElement e = trace[i];
            String name = e.getClassName();
            if (!name.equals(LoggerPrinter.class.getName()) && !name.equals(Logger.class.getName())) {
                return --i;
            }
        }
        return -1;
    }

}
