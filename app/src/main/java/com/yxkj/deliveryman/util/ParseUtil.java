package com.yxkj.deliveryman.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 解析各个数据结构
 */
public class ParseUtil {

    /**
     * 转换为json字符串
     */
    public static String toJsonString(Object object) {

        Gson gson = new Gson();
        return gson.toJson(object);
    }

    /**
     * 解析对象
     */
    public static <T> T parseObject(String json, Class<T> class1) {

        if (!TextUtils.isEmpty(json)) {

            try {
                Gson gson = new Gson();
                return gson.fromJson(json, class1);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析数组
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> parseArray(String json, final Class<T> class1) {

        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeHierarchyAdapter(Object.class, new JsonDeserializer<Object>() {

            @Override
            public Object deserialize(JsonElement json, Type typeOfT,
                                      JsonDeserializationContext context) throws JsonParseException {

                Gson objGson = new Gson();
                List<T> result = new ArrayList<T>();
                for (int i = 0; i < json.getAsJsonArray().size(); i++) {

                    JsonElement childJson = json.getAsJsonArray().get(i);
                    T object = objGson.fromJson(childJson, class1);
                    result.add(object);
                }

                return result;
            }
        });

        List<T> result = null;
        if (!TextUtils.isEmpty(json)) {

            Gson gson = builder.create();
            result = gson.fromJson(json, List.class);

        }

        if (result == null) {

            result = new ArrayList<T>();
        }
        return result;
    }
}
