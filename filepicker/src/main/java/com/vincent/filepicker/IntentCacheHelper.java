package com.vincent.filepicker;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 项目名称：XiangFa
 * 创建者：强晨晨
 * 创建时间：15
 */

public final class IntentCacheHelper {

    private static volatile IntentCacheHelper instance;
    private HashMap<String, Object> cacheMap;

    public static synchronized IntentCacheHelper getInstance(){
        if (instance == null){
            instance = new IntentCacheHelper();
        }
        return instance;
    }

    private IntentCacheHelper() {
        cacheMap = new HashMap<>();
    }

    public void putIntentValue(String key, Object value) {
        if (cacheMap != null)
            cacheMap.put(key, value);
    }

    public Object getIntentValue(String key) {
        if (cacheMap != null)
            return cacheMap.get(key);
        else
            return new ArrayList<>();
    }
}
