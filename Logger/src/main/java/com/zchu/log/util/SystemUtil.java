package com.zchu.log.util;

import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by pengwei08 on 2015/7/20.
 *
 * @see "https://github.com/pengwei1024/LogUtils"
 */
public class SystemUtil {

    /**
     * 获取StackTraceElement对象
     */
    public static StackTraceElement getStackTrace() {
        return Thread.currentThread().getStackTrace()[4];
    }


    // 基本数据类型
    private final static String[] types = {"int", "java.lang.String", "boolean", "char",
            "float", "double", "long", "short", "byte"};

    /**
     * 将对象转化为String
     */
    public static <T> String objectToString(T object) {
        if (object == null) {
            return "Object{object is null}";
        }
        if (object.toString().startsWith(object.getClass().getName() + "@")) {
            StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + "{");
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                boolean flag = false;
                for (String type : types) {
                    if (field.getType().getName().equalsIgnoreCase(type)) {
                        flag = true;
                        Object value = null;
                        try {
                            value = field.get(object);
                        } catch (IllegalAccessException e) {
                            value = e;
                        } finally {
                            builder.append(String.format("%s=%s, ", field.getName(),
                                    value == null ? "null" : value.toString()));
                            break;
                        }
                    }
                }
                if (!flag) {
                    builder.append(String.format("%s=%s, ", field.getName(), "Object"));
                }
            }
            return builder.replace(builder.length() - 2, builder.length() - 1, "}").toString();
        } else {
            return object.toString();
        }
    }

    public static String jsonToMessage(String json, int indent) throws JSONException {
        if (TextUtils.isEmpty(json)) {
            return "Empty/Null json content";
        }

        if (startsWith(json, '{')) {
            JSONObject jsonObject = new JSONObject(json);
            String message = jsonObject.toString(indent);
            return message;
        }
        if (startsWith(json, '[')) {
            JSONArray jsonArray = new JSONArray(json);
            String message = jsonArray.toString(indent);
            return message;
        }
        return "Content is not a json \n" + json;
    }

    /**
     * 判断起始字符是否相同，排除空格
     */
    public static boolean startsWith(String json, char c) {
        for (int i = 0; i < json.length(); i++) {
            char star = json.charAt(i);
            if (star == c) {
                return true;
            } else if (star <= ' ') {
                return false;
            }
        }
        return false;
    }


    public static String xmlToMessage(String xml) throws TransformerException {
        if (TextUtils.isEmpty(xml)) {
            return "Empty/Null xml content";
        }
        Source xmlInput = new StreamSource(new StringReader(xml));
        StreamResult xmlOutput = new StreamResult(new StringWriter());
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(xmlInput, xmlOutput);
        return xmlOutput.getWriter().toString().replaceFirst(">", ">\n");

    }

    /**
     * Only support for Collection、map、array
     *
     * @see "https://github.com/pengwei1024/LogUtils"
     */
    public static String objectToMessage(Object object) {
        if (object != null) {
            if (object instanceof String) {
                return (String) object;
            } else if (object instanceof Throwable) {
                return Log.getStackTraceString((Throwable) object);
            }
            final String simpleName = object.getClass().getSimpleName();
            if (object.getClass().isArray()) {
                String msg = "Temporarily not support more than two dimensional Array!";
                int dim = ArrayUtil.getArrayDimension(object);
                switch (dim) {
                    case 1:
                        Pair pair = ArrayUtil.arrayToString(object);
                        msg = simpleName.replace("[]", "[" + pair.first + "] {\n");
                        msg += pair.second + "\n";
                        break;
                    case 2:
                        Pair pair1 = ArrayUtil.arrayToObject(object);
                        Pair pair2 = (Pair) pair1.first;
                        msg = simpleName.replace("[][]", "[" + pair2.first + "][" + pair2.second + "] {\n");
                        msg += pair1.second + "\n";
                        break;
                    default:
                        break;
                }
                return msg + "}";
            } else if (object instanceof Collection) {
                Collection collection = (Collection) object;
                String msg = "%s size = %d [\n";
                msg = String.format(msg, simpleName, collection.size());
                if (!collection.isEmpty()) {
                    Iterator iterator = collection.iterator();
                    int flag = 0;
                    while (iterator.hasNext()) {
                        String itemString = "[%d]:%s%s";
                        Object item = iterator.next();
                        msg += String.format(itemString, flag, SystemUtil.objectToString(item),
                                flag++ < collection.size() - 1 ? ",\n" : "\n");
                    }
                }
                return msg + "\n]";
            } else if (object instanceof Map) {
                String msg = simpleName + " {\n";
                Map map = (Map) object;
                Set keys = map.keySet();
                for (Object key : keys) {
                    String itemString = "[%s -> %s]\n";
                    Object value = map.get(key);
                    msg += String.format(itemString, SystemUtil.objectToString(key),
                            SystemUtil.objectToString(value));
                }
                return msg + "}";
            } else {
                return SystemUtil.objectToString(object);
            }
        } else {
            return null;
        }
    }


}
