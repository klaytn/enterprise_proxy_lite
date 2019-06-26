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

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Arrays;


public class ObjectUtil {
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];


    public static <T,S extends T> T defaultIfNull(T object,S defaultValue) {
        return object == null ? defaultValue : object;
    }


    public static boolean isEquals(Object target,Object source) {
        if (target == source) {
            return true;
        }

        if (target == null || source == null) {
            return false;
        }

        if (!target.getClass().equals(source.getClass())) {
            return false;
        }

        if (target instanceof Object[]) {
            return Arrays.deepEquals((Object[]) target, (Object[]) source);
        }

        if (target instanceof int[]) {
            return Arrays.equals((int[]) target,(int[]) source);
        }

        if (target instanceof long[]) {
            return Arrays.equals((long[]) target,(long[]) source);
        }

        if (target instanceof short[]) {
            return Arrays.equals((short[]) target,(short[]) source);
        }

        if (target instanceof byte[]) {
            return Arrays.equals((byte[]) target,(byte[]) source);
        }

        if (target instanceof double[]) {
            return Arrays.equals((double[]) target,(double[]) source);
        }

        if (target instanceof float[]) {
            return Arrays.equals((float[]) target,(float[]) source);
        }

        if (target instanceof char[]) {
            return Arrays.equals((char[]) target,(char[]) source);
        }

        if (target instanceof boolean[]) {
            return Arrays.equals((boolean[]) target,(boolean[]) source);
        }

        return target.equals(source);
    }


    public static int hashCode(Object object) {
        if (object == null) {
            return 0;
        }
        else if (object instanceof Object[]) {
            return Arrays.deepHashCode((Object[]) object);
        }
        else if (object instanceof int[]) {
            return Arrays.hashCode((int[]) object);
        }
        else if (object instanceof long[]) {
            return Arrays.hashCode((long[]) object);
        }
        else if (object instanceof short[]) {
            return Arrays.hashCode((short[]) object);
        }
        else if (object instanceof byte[]) {
            return Arrays.hashCode((byte[]) object);
        }
        else if (object instanceof double[]) {
            return Arrays.hashCode((double[]) object);
        }
        else if (object instanceof float[]) {
            return Arrays.hashCode((float[]) object);
        }
        else if (object instanceof char[]) {
            return Arrays.hashCode((char[]) object);
        }
        else if (object instanceof boolean[]) {
            return Arrays.hashCode((boolean[]) object);
        }
        else {
            return object.hashCode();
        }
    }


    public static int identityHashCode(Object object) {
        return object == null ? 0 : System.identityHashCode(object);
    }


    public static String identityToString(Object object) {
        if (object == null) {
            return null;
        }

        return appendIdentityToString(new StringBuilder(),object).toString();
    }


    public static String identityToString(Object object,String nullStr) {
        if (object == null) {
            return nullStr;
        }

        return appendIdentityToString(new StringBuilder(),object).toString();
    }


    public static <A extends Appendable> A appendIdentityToString(A buffer, Object object) {
        try {
            if (object == null) {
                buffer.append("null");
            } else {
                buffer.append(ClassUtil.getFriendlyClassNameForObject(object));
                buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return buffer;
    }


    public static String toString(Object object) {
        return toString(object,StringUtil.EMPTY_STRING);
    }


    public static String toString(Object object, String nullStr) {
        if (object == null) {
            return nullStr;
        } else if (object instanceof Object[]) {
            return Arrays.deepToString((Object[]) object);
        } else if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        } else if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        } else if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        } else if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        } else if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        } else if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        } else if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        } else if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        } else {
            return object.toString();
        }
    }


    public static boolean isSameType(Object object1, Object object2) {
        if ((object1 == null) || (object2 == null)) {
            return true;
        }

        return object1.getClass().equals(object2.getClass());
    }


    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }

        if (object instanceof String) {
            return StringUtil.isEmpty(object.toString());
        }

        if (object.getClass().isArray()) {
            return Array.getLength(object) == 0;
        }

        return false;
    }


    public static boolean isNotEmpty(Object object) {
        return !isEmpty(object);
    }


    public static boolean isNull(Object object) {
        return (object == null);
    }


    public static boolean isNotNull(Object object) {
        return (object != null);
    }


    public static boolean isAllNull(Object...objects) {
        if (objects == null) {
            return true;
        }

        for (Object object : objects) {
            if (object != null) {
                return false;
            }
        }
        return true;
    }


    public static boolean isAnyNull(Object...objects) {
        if (objects == null) {
            return true;
        }

        for (Object object : objects) {
            if (object == null) {
                return true;
            }
        }
        return false;
    }


    public static int nullNum(Object...objects) {
        if (objects == null) {
            return -1;
        }

        int result = 0;
        for (Object object : objects) {
            if (object == null) {
                result++;
            }
        }
        return result;
    }


    public static StringBuilder appendIdentityToString(StringBuilder builder, Object object) {
        if (object == null) {
            return null;
        }

        if (builder == null) {
            builder = new StringBuilder();
        }
        builder.append(ClassUtil.getSimpleClassNameForObject(object));

        return builder.append('@').append(Integer.toHexString(identityHashCode(object)));
    }


    public static StringBuffer appendIdentityToString(StringBuffer buffer, Object object) {
        if (object == null) {
            return null;
        }

        if (buffer == null) {
            buffer = new StringBuffer();
        }
        buffer.append(ClassUtil.getSimpleClassNameForObject(object));

        return buffer.append('@').append(Integer.toHexString(identityHashCode(object)));
    }


    public static Object clone(Object array) throws CloneNotSupportedException {
        if (array == null) {
            return null;
        }

        if (array instanceof Object[]) {
            return ArrayUtil.clone((Object[]) array);
        }

        if (array instanceof long[]) {
            return ArrayUtil.clone((long[]) array);
        }

        if (array instanceof int[]) {
            return ArrayUtil.clone((int[]) array);
        }

        if (array instanceof short[]) {
            return ArrayUtil.clone((short[]) array);
        }

        if (array instanceof byte[]) {
            return ArrayUtil.clone((byte[]) array);
        }

        if (array instanceof double[]) {
            return ArrayUtil.clone((double[]) array);
        }

        if (array instanceof float[]) {
            return ArrayUtil.clone((float[]) array);
        }

        if (array instanceof boolean[]) {
            return ArrayUtil.clone((boolean[]) array);
        }

        if (array instanceof char[]) {
            return ArrayUtil.clone((char[]) array);
        }

        // Not cloneable
        if (!(array instanceof Cloneable)) {
            throw new CloneNotSupportedException("Object of class " + array.getClass().getName() + " is not Cloneable");
        }

        Class<?> clazz = array.getClass();

        try {
            Method cloneMethod = clazz.getMethod("clone", EMPTY_CLASS_ARRAY);
            return cloneMethod.invoke(array,EMPTY_OBJECT_ARRAY);
        } catch (Exception e) {
            throw new CloneNotSupportedException(e.getMessage());
        }
    }


    private ObjectUtil() {
        throw new IllegalStateException("Utility Class");
    }
}