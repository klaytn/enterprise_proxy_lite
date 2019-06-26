/**
 * Copyright 2019 Enterprise Proxy Authors
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.klaytn.enterpriseproxy.utils;

import com.google.gson.Gson;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


public class JsonUtil {
    private static final Gson gson = new Gson();



    /**
     * convert to gson
     * 
     * @param object
     * @return
     */
    public static String converToGson(final Object object) {
        if (ObjectUtil.isEmpty(object)) {
            return "";
        }

        return gson.toJson(object);
    }


    /**
     * gson to object
     *
     * @param jsonStr
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> T gsonToObject(String jsonStr, Class<T> cls) {
        if (ObjectUtil.isEmpty(jsonStr)) {
            return null;
        }
        
        return gson.fromJson(jsonStr, cls);
    }


    /**
     * Object to JSONObject
     *
     * @param convertData
     * @return
     */
    public static JSONObject convertJsonObject(Object convertData) {
        JSONObject convert = new JSONObject();
        if (ObjectUtil.isEmpty(convertData)) {
            return convert;
        }

        try {
            convert = (JSONObject) JSONSerializer.toJSON(convertData);
        } catch (Exception ignored) {}

        return convert;
    }


    /**
     * List to JSONArray
     *
     * @param convertList
     * @return
     */
    public static JSONArray convertJsonArray(Object convertList) {
        JSONArray convert = new JSONArray();
        if (ObjectUtil.isEmpty(convertList)) {
            return convert;
        }

        try {
            convert = (JSONArray) JSONSerializer.toJSON(convertList);
        } catch (Exception ignored) {}

        return convert;
    }

    
    private JsonUtil() {
        throw new IllegalStateException("Utility Class");
    }
}