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


import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;


public class MapUtil {
    public static void addUpValue(Map<String, Object> map,String key,int value) {
        if (map.containsKey(key)) {
            map.put(key, (int) map.get(key) + value);
        } else {
            map.put(key, value);
        }
    }


    public static void addUpValue(Map<String, Object> map, String key, long value) {
        if (map.containsKey(key)) {
            map.put(key, (long) map.get(key) + value);
        } else {
            map.put(key, value);
        }
    }


    public static List<Map<String, Object>> castMapList(List<Object> srcList) {
        List<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        if (!srcList.isEmpty()) {
            for (Object object : srcList) {
                Map<String, Object> ret = (Map<String, Object>) object;
                retList.add(ret);
            }
        }

        return retList;
    }


    public static boolean compareString(Map<?, ?> map, String keyvalue, String compareValue) {
        if (map == null) {
            return false;
        } else {
            return (MapUtil.getString(map, keyvalue).equals(compareValue));
        }
    }


    public static boolean containPath(Map<String, Object> map, String... arrayPath) {
        Map<String, Object> currentMap = map;
        int length = arrayPath.length;

        for (int i = 0; i < length; i++) {
            String key = arrayPath[i];
            if (i == length - 1) {
                return currentMap.containsKey(key);
            } else {
                if (currentMap.containsKey(key)) {
                    Object value = currentMap.get(key);
                    if (value instanceof Map) {
                        currentMap = (Map<String, Object>) currentMap.get(key);
                    } else {
                        return false;
                    }
                } else {
                    break;
                }
            }
        }
        return false;
    }


    public static Map<String, Object> filterMapWithKeyset(Map<String, Object> map, Set<String> keyset) {
        Map<String, Object> filterdMap = new HashMap<String, Object>();

        for (String key : keyset) {
            filterdMap.put(key, map.get(key));
        }

        return filterdMap;
    }


    public static boolean getBooleanValue(Map<?, ?> map, String key) {
        return MapUtils.getBooleanValue(map, key);
    }


    public static boolean getBooleanValue(Map<?, ?> map, String key, boolean defaultValue) {
        return MapUtils.getBooleanValue(map, key, defaultValue);
    }


    public static int getIntValue(Map<?, ?> map, String key) {
        return MapUtils.getIntValue(map, key);
    }


    public static int getIntValue(Map<?, ?> map, String key, int defaultValue) {
        return MapUtils.getIntValue(map, key, defaultValue);
    }


    public static long getLongValue(Map<?, ?> map, String key) {
        return MapUtils.getLongValue(map, key);
    }


    public static long getLongValue(Map<?, ?> map, String key, long defaultValue) {
        return MapUtils.getLongValue(map, key, defaultValue);
    }


    public static Object getObject(Map<?, ?> map, String key, Object defaultValue) {
        return MapUtils.getObject(map, key, defaultValue);
    }


    public static Object getObject(Map<?, ?> map, String key) {
        return MapUtils.getObject(map, key);
    }


    public static String getString(Map<?, ?> map, String key) {
        return MapUtils.getString(map, key, StringUtil.EMPTY_STRING);
    }


    public static String getString(Map<?, ?> map, String key, String defaultValue) {
        return MapUtils.getString(map, key, defaultValue);
    }


    public static Object getValue(Map<String, Object> map, String... arrayPath) {
        if (map == null) {
            return null;
        }

        Map<String, Object> currentMap = map;
        int length = arrayPath.length;
        Object value = null;

        for (int i = 0; i < length; i++) {
            String key = arrayPath[i];
            if (i == length - 1) {
                value = currentMap.get(key);
            } else {
                if (currentMap.containsKey(key)) {
                    currentMap = (Map<String, Object>) currentMap.get(key);
                } else {
                    break;
                }
            }
        }

        return value;
    }


    public static String gSV(Map<?, ?> map, String keyvalue) {
        if (map == null) {
            return "";
        } else {
            return getString(map, keyvalue);
        }
    }


    public static void putValue(Map<String, Object> map, Object value, String... arrayPath) {
        Map<String, Object> currentMap = map;
        int length = arrayPath.length;

        for (int i = 0; i < length; i++) {
            String key = arrayPath[i];
            if (i == length - 1) {
                currentMap.put(key, value);
            } else {
                if (currentMap.containsKey(key)) {
                    currentMap = (Map<String, Object>) currentMap.get(key);
                } else {
                    Map<String, Object> subMap = new HashMap<String, Object>();
                    currentMap.put(key, subMap);
                    currentMap = subMap;
                }
            }
        }
    }


    public static void putValueWithStringCheck(Map<String, Object> map, String value, String... arrayPath) {
        if (StringUtils.isEmpty(value)) {
            return;
        }

        putValue(map, value, arrayPath);
    }


    public static Map<String, Object> sliceMap(LinkedHashMap<String, Object> map, int start, int length) {
        int count = 0;
        int insertCount = 0;

        Map<String, Object> slicedMap = new HashMap<String, Object>();
        for (Map.Entry<String, Object> mapEntry : map.entrySet()) {

            if (count >= start) {
                slicedMap.put(mapEntry.getKey(), mapEntry.getValue());
                insertCount++;
                if (insertCount == length) {
                    break;
                }
            }
            count++;
        }
        return slicedMap;
    }


    public static Map<String, Object> extractMap(Map<String, Object> map, Set<String> keySet) {
        Map<String, Object> extractedMap = new HashMap<String, Object>();
        for (String key : keySet) {
            if (map.containsKey(key)) {
                extractedMap.put(key, map.get(key));
            }
        }
        return extractedMap;
    }


    public static String[] array_keys(Map<String, Object> srcMap) {
        String[] retVal = null;
        if (!srcMap.isEmpty()) {
            Set<String> keyset = srcMap.keySet();
            retVal = new String[keyset.size()];

            int idx = 0;
            for (String key : keyset) {
                retVal[idx++] = key;
            }
        }

        return retVal;
    }


    public static boolean hasValue(Map<?, ?> map, String keyvalue) {
        if (!hasMap(map) || map.get(keyvalue) == null) {
            return false;
        } else if (map.get(keyvalue) instanceof String && map.get(keyvalue).toString().trim().length() < 1) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean hasMap(Map<?, ?> map) {
        if (map == null || map.size() < 1) {
            return false;
        } else {
            return true;
        }
    }


    public static boolean isEmpty(Map<?, ?> map) {
        return MapUtils.isEmpty(map);
    }


    public static boolean isNotEmpty(Map<?, ?> map) {
        return MapUtils.isNotEmpty(map);
    }

    private MapUtil() {
        throw new IllegalStateException("Utility Class");
    }
}