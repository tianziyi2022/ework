package com.hebutgo.ework.common.utils;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;

/**
 * json转换类
 * @author zuozhiwei
 */
@Component
public class JsonUtil {

    /**
     * 单例Gson对象
     */
    private static Gson gson = null;

    public static Gson getGson() {
        if (gson == null) {
            synchronized (JsonUtil.class) {
                gson = new Gson();
            }
        }
        return gson;
    }
}
