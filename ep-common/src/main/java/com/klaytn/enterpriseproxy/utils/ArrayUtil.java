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

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;


public class ArrayUtil {
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];
    private static final int INDEX_NOT_FOUND = -1;


    private ArrayUtil() {
        throw new IllegalStateException("Utility Class");
    }


    private static int length(Object array, int defaultIfNull, int defaultIfNotArray) {
        if (array == null) {
            return defaultIfNull; // null
        }
        if (array instanceof Object[]) {
            return ((Object[]) array).length;
        }
        if (array instanceof long[]) {
            return ((long[]) array).length;
        }
        if (array instanceof int[]) {
            return ((int[]) array).length;
        }
        if (array instanceof short[]) {
            return ((short[]) array).length;
        }
        if (array instanceof byte[]) {
            return ((byte[]) array).length;
        }
        if (array instanceof double[]) {
            return ((double[]) array).length;
        }
        if (array instanceof float[]) {
            return ((float[]) array).length;
        }
        if (array instanceof boolean[]) {
            return ((boolean[]) array).length;
        }
        if (array instanceof char[]) {
            return ((char[]) array).length;
        }
        return defaultIfNotArray; // not an array
    }


    public static int length(Object array) {
        return length(array, 0, 0);
    }


    public static boolean isEmpty(final Object array) {
        return length(array, 0, INDEX_NOT_FOUND) == 0;
    }


    public static boolean isNotEmpty(Object array) {
        return length(array, 0, INDEX_NOT_FOUND) > 0;
    }


    public static <T, S extends T> T defaultIfEmpty(T array, S defaultValue) {
        return isEmpty(array) ? defaultValue : array;
    }


    public static <T> Iterable<T> arrayAsIterable(final Class<T> componentType, Object array) {
        if (array == null) {
            return new ArrayIterable<T>(0) {
                @Override
                protected T get(int i) {
                    return null;
                }
            };
        }
        if (array instanceof Object[]) {
            final Object[] objectArray = (Object[]) array;

            return new ArrayIterable<T>(objectArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(objectArray[i]);
                }
            };
        } else if (array instanceof int[]) {
            final int[] intArray = (int[]) array;

            return new ArrayIterable<T>(intArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(intArray[i]);
                }
            };
        } else if (array instanceof long[]) {
            final long[] longArray = (long[]) array;

            return new ArrayIterable<T>(longArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(longArray[i]);
                }
            };
        } else if (array instanceof short[]) {
            final short[] shortArray = (short[]) array;

            return new ArrayIterable<T>(shortArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(shortArray[i]);
                }
            };
        } else if (array instanceof byte[]) {
            final byte[] byteArray = (byte[]) array;

            return new ArrayIterable<T>(byteArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(byteArray[i]);
                }
            };
        } else if (array instanceof double[]) {
            final double[] doubleArray = (double[]) array;

            return new ArrayIterable<T>(doubleArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(doubleArray[i]);
                }
            };
        } else if (array instanceof float[]) {
            final float[] floatArray = (float[]) array;

            return new ArrayIterable<T>(floatArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(floatArray[i]);
                }
            };
        } else if (array instanceof boolean[]) {
            final boolean[] booleanArray = (boolean[]) array;

            return new ArrayIterable<T>(booleanArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(booleanArray[i]);
                }
            };
        } else if (array instanceof char[]) {
            final char[] charArray = (char[]) array;

            return new ArrayIterable<T>(charArray.length) {
                @Override
                protected T get(int i) {
                    return componentType.cast(charArray[i]);
                }
            };
        } else {
            throw new IllegalArgumentException(array + " is not an array");
        }
    }


    private static abstract class ArrayIterable<T> implements Iterable<T> {
        private final int length;

        public ArrayIterable(int length) {
            this.length = length;
        }

        public final Iterator<T> iterator() {
            return new Iterator<T>() {
                private int index;

                public final boolean hasNext() {
                    return index < length;
                }

                public final T next() {
                    if (index >= length) {
                        throw new ArrayIndexOutOfBoundsException(index);
                    }

                    return get(index++);
                }
            };
        }

        protected abstract T get(int i);
    }


    public static <K, V> Map<K, V> toMap(Object[][] keyValueArray,Class<K> keyType,Class<V> valueType) {
        return toMap(keyValueArray, keyType, valueType, null);
    }


    public static <K, V> Map<K, V> toMap(Object[][] keyValueArray, Class<K> keyType, Class<V> valueType, Map<K, V> map) {
        if (keyValueArray == null) {
            return map;
        }

        if (map == null) {
            map = CollectionUtil.createLinkedHashMap((int) (keyValueArray.length * 1.5));
        }

        for (int i = 0; i < keyValueArray.length; i++) {
            Object[] keyValue = keyValueArray[i];
            Object[] entry = keyValue;

            if (entry == null || entry.length < 2) {
                throw new IllegalArgumentException("Array element " + i + " is not an array of 2 elements");
            }

            map.put(keyType.cast(entry[0]), valueType.cast(entry[1]));
        }

        return map;
    }


    public static boolean isSameLength(Object[] array1, Object[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(long[] array1, long[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(int[] array1, int[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(short[] array1, short[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(byte[] array1, byte[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(double[] array1, double[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(float[] array1, float[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static boolean isSameLength(char[] array1, char[] array2) {
        int length1 = array1 == null ? 0 : array1.length;
        int length2 = array2 == null ? 0 : array2.length;

        return length1 == length2;
    }


    public static void reverse(Object[] array) {
        if (array == null) {
            return;
        }

        Object tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(long[] array) {
        if (array == null) {
            return;
        }

        long tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(int[] array) {
        if (array == null) {
            return;
        }

        int tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(short[] array) {
        if (array == null) {
            return;
        }

        short tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(byte[] array) {
        if (array == null) {
            return;
        }

        byte tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(double[] array) {
        if (array == null) {
            return;
        }

        double tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(float[] array) {
        if (array == null) {
            return;
        }

        float tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(boolean[] array) {
        if (array == null) {
            return;
        }

        boolean tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static void reverse(char[] array) {
        if (array == null) {
            return;
        }

        char tmp;

        for (int i = 0, j = array.length - 1; j > i; i++, j--) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
        }
    }


    public static int indexOf(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind, 0);
    }


    public static int indexOf(Object[] array, Object[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }


    public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(Object[] array, Object[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        Object first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && !ObjectUtil.isEquals(array[i], first)) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (!ObjectUtil.isEquals(array[j++], arrayToFind[k++])) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(Object[] array, Object objectToFind) {
        return lastIndexOf(array, objectToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(Object[] array, Object[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(Object[] array, Object[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        Object last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && !ObjectUtil.isEquals(array[i], last)) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (!ObjectUtil.isEquals(array[j--], arrayToFind[k--])) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(Object[] array, Object objectToFind) {
        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(Object[] array, Object[] arrayToFind) {
        return lastIndexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static int indexOf(long[] array, long longToFind) {
        return indexOf(array, longToFind, 0);
    }


    public static int indexOf(long[] array, long[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }


    public static int indexOf(long[] array, long longToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (longToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(long[] array, long[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        long first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(long[] array, long longToFind) {
        return lastIndexOf(array, longToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(long[] array, long longToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (longToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(long[] array, long[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        long last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(long[] array, long longToFind) {
        return indexOf(array, longToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(long[] array, long[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static int indexOf(int[] array, int intToFind) {
        return indexOf(array, intToFind, 0);
    }


    public static int indexOf(int[] array, int[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }


    public static int indexOf(int[] array, int intToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (intToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(int[] array, int[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(int[] array, int intToFind) {
        return lastIndexOf(array, intToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(int[] array, int[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(int[] array, int intToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (intToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(int[] array, int[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        int last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(int[] array, int intToFind) {
        return indexOf(array, intToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(int[] array, int[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static int indexOf(short[] array, short shortToFind) {
        return indexOf(array, shortToFind, 0);
    }


    public static int indexOf(short[] array, short[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }


    public static int indexOf(short[] array, short shortToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (shortToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(short[] array, short[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        short first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }

    public static int lastIndexOf(short[] array, short shortToFind) {
        return lastIndexOf(array, shortToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(short[] array, short[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(short[] array, short shortToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (shortToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(short[] array, short[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        short last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(short[] array, short shortToFind) {
        return indexOf(array, shortToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(short[] array, short[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static int indexOf(byte[] array, byte byteToFind) {
        return indexOf(array, byteToFind, 0);
    }


    public static int indexOf(byte[] array, byte[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }


    public static int indexOf(byte[] array, byte byteToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (byteToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(byte[] array, byte[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        byte first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(byte[] array, byte byteToFind) {
        return lastIndexOf(array, byteToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(byte[] array, byte[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(byte[] array, byte byteToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (byteToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(byte[] array, byte[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        byte last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(byte[] array, byte byteToFind) {
        return indexOf(array, byteToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(byte[] array, byte[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static int indexOf(double[] array, double doubleToFind) {
        return indexOf(array, doubleToFind, 0, 0);
    }


    public static int indexOf(double[] array, double doubleToFind, double tolerance) {
        return indexOf(array, doubleToFind, 0, tolerance);
    }


    public static int indexOf(double[] array, double[] arrayToFind) {
        return indexOf(array, arrayToFind, 0, 0);
    }


    public static int indexOf(double[] array, double[] arrayToFind, double tolerance) {
        return indexOf(array, arrayToFind, 0, tolerance);
    }


    public static int indexOf(double[] array, double doubleToFind, int startIndex) {
        return indexOf(array, doubleToFind, startIndex, 0);
    }


    public static int indexOf(double[] array, double doubleToFind, int startIndex, double tolerance) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        double min = doubleToFind - tolerance;
        double max = doubleToFind + tolerance;

        for (int i = startIndex; i < array.length; i++) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(double[] array, double[] arrayToFind, int startIndex) {
        return indexOf(array, arrayToFind, startIndex, 0);
    }


    public static int indexOf(double[] array, double[] arrayToFind, int startIndex, double tolerance) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        double firstMin = arrayToFind[0] - tolerance;
        double firstMax = arrayToFind[0] + tolerance;
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && (array[i] < firstMin || array[i] > firstMax)) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (Math.abs(array[j++] - arrayToFind[k++]) > tolerance) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(double[] array, double doubleToFind) {
        return lastIndexOf(array, doubleToFind, Integer.MAX_VALUE, 0);
    }


    public static int lastIndexOf(double[] array, double doubleToFind, double tolerance) {
        return lastIndexOf(array, doubleToFind, Integer.MAX_VALUE, tolerance);
    }


    public static int lastIndexOf(double[] array, double[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, 0);
    }


    public static int lastIndexOf(double[] array, double[] arrayToFind, double tolerance) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, tolerance);
    }


    public static int lastIndexOf(double[] array, double doubleToFind, int startIndex) {
        return lastIndexOf(array, doubleToFind, startIndex, 0);
    }


    public static int lastIndexOf(double[] array, double doubleToFind, int startIndex, double tolerance) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        double min = doubleToFind - tolerance;
        double max = doubleToFind + tolerance;

        for (int i = startIndex; i >= 0; i--) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(double[] array, double[] arrayToFind, int startIndex) {
        return lastIndexOf(array, arrayToFind, startIndex, 0);
    }


    public static int lastIndexOf(double[] array, double[] arrayToFind, int startIndex, double tolerance) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        double lastMin = arrayToFind[lastIndex] - tolerance;
        double lastMax = arrayToFind[lastIndex] + tolerance;
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && (array[i] < lastMin || array[i] > lastMax)) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (Math.abs(array[j--] - arrayToFind[k--]) > tolerance) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(double[] array, double doubleToFind) {
        return indexOf(array, doubleToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(double[] array, double doubleToFind, double tolerance) {
        return indexOf(array, doubleToFind, tolerance) != INDEX_NOT_FOUND;
    }


    public static boolean contains(double[] array, double[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(double[] array, double[] arrayToFind, double tolerance) {
        return indexOf(array, arrayToFind, tolerance) != INDEX_NOT_FOUND;
    }


    public static int indexOf(float[] array, float floatToFind) {
        return indexOf(array, floatToFind, 0, 0);
    }


    public static int indexOf(float[] array, float floatToFind, float tolerance) {
        return indexOf(array, floatToFind, 0, tolerance);
    }


    public static int indexOf(float[] array, float[] arrayToFind) {
        return indexOf(array, arrayToFind, 0, 0);
    }


    public static int indexOf(float[] array, float[] arrayToFind, float tolerance) {
        return indexOf(array, arrayToFind, 0, tolerance);
    }


    public static int indexOf(float[] array, float floatToFind, int startIndex) {
        return indexOf(array, floatToFind, startIndex, 0);
    }


    public static int indexOf(float[] array, float floatToFind, int startIndex, float tolerance) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        float min = floatToFind - tolerance;
        float max = floatToFind + tolerance;

        for (int i = startIndex; i < array.length; i++) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(float[] array, float[] arrayToFind, int startIndex) {
        return indexOf(array, arrayToFind, startIndex, 0);
    }


    public static int indexOf(float[] array, float[] arrayToFind, int startIndex, float tolerance) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        float firstMin = arrayToFind[0] - tolerance;
        float firstMax = arrayToFind[0] + tolerance;
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && (array[i] < firstMin || array[i] > firstMax)) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (Math.abs(array[j++] - arrayToFind[k++]) > tolerance) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(float[] array, float floatToFind) {
        return lastIndexOf(array, floatToFind, Integer.MAX_VALUE, 0);
    }


    public static int lastIndexOf(float[] array, float floatToFind, float tolerance) {
        return lastIndexOf(array, floatToFind, Integer.MAX_VALUE, tolerance);
    }


    public static int lastIndexOf(float[] array, float[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, 0);
    }


    public static int lastIndexOf(float[] array, float[] arrayToFind, float tolerance) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE, tolerance);
    }


    public static int lastIndexOf(float[] array, float floatToFind, int startIndex) {
        return lastIndexOf(array, floatToFind, startIndex, 0);
    }


    public static int lastIndexOf(float[] array, float floatToFind, int startIndex, float tolerance) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        float min = floatToFind - tolerance;
        float max = floatToFind + tolerance;

        for (int i = startIndex; i >= 0; i--) {
            if (array[i] >= min && array[i] <= max) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(float[] array, float[] arrayToFind, int startIndex) {
        return lastIndexOf(array, arrayToFind, startIndex, 0);
    }


    public static int lastIndexOf(float[] array, float[] arrayToFind, int startIndex, float tolerance) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        float lastMin = arrayToFind[lastIndex] - tolerance;
        float lastMax = arrayToFind[lastIndex] + tolerance;
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && (array[i] < lastMin || array[i] > lastMax)) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (Math.abs(array[j--] - arrayToFind[k--]) > tolerance) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(float[] array, float floatToFind) {
        return indexOf(array, floatToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(float[] array, float floatToFind, float tolerance) {
        return indexOf(array, floatToFind, tolerance) != INDEX_NOT_FOUND;
    }


    public static boolean contains(float[] array, float[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(float[] array, float[] arrayToFind, float tolerance) {
        return indexOf(array, arrayToFind, tolerance) != INDEX_NOT_FOUND;
    }


    public static int indexOf(boolean[] array, boolean booleanToFind) {
        return indexOf(array, booleanToFind, 0);
    }


    public static int indexOf(boolean[] array, boolean[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }


    public static int indexOf(boolean[] array, boolean[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        boolean first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(boolean[] array, boolean booleanToFind) {
        return lastIndexOf(array, booleanToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(boolean[] array, boolean[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(boolean[] array, boolean booleanToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (booleanToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(boolean[] array, boolean[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        boolean last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static int indexOf(char[] array, char charToFind) {
        return indexOf(array, charToFind, 0);
    }


    public static int indexOf(char[] array, char[] arrayToFind) {
        return indexOf(array, arrayToFind, 0);
    }


    public static boolean contains(boolean[] array, boolean[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(boolean[] array, boolean booleanToFind) {
        return indexOf(array, booleanToFind) != INDEX_NOT_FOUND;
    }


    public static int indexOf(boolean[] array, boolean booleanToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (booleanToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(char[] array, char charToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < array.length; i++) {
            if (charToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int indexOf(char[] array, char[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        if (startIndex >= sourceLength) {
            return targetLength == 0 ? sourceLength : INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        char first = arrayToFind[0];
        int i = startIndex;
        int max = sourceLength - targetLength;

        startSearchForFirst: while (true) {
            while (i <= max && array[i] != first) {
                i++;
            }

            if (i > max) {
                return INDEX_NOT_FOUND;
            }

            int j = i + 1;
            int end = j + targetLength - 1;
            int k = 1;

            while (j < end) {
                if (array[j++] != arrayToFind[k++]) {
                    i++;

                    continue startSearchForFirst;
                }
            }

            return i;
        }
    }


    public static int lastIndexOf(char[] array, char charToFind) {
        return lastIndexOf(array, charToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(char[] array, char[] arrayToFind) {
        return lastIndexOf(array, arrayToFind, Integer.MAX_VALUE);
    }


    public static int lastIndexOf(char[] array, char charToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        } else if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }

        for (int i = startIndex; i >= 0; i--) {
            if (charToFind == array[i]) {
                return i;
            }
        }

        return INDEX_NOT_FOUND;
    }


    public static int lastIndexOf(char[] array, char[] arrayToFind, int startIndex) {
        if (array == null || arrayToFind == null) {
            return INDEX_NOT_FOUND;
        }

        int sourceLength = array.length;
        int targetLength = arrayToFind.length;

        int rightIndex = sourceLength - targetLength;

        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }

        if (startIndex > rightIndex) {
            startIndex = rightIndex;
        }

        if (targetLength == 0) {
            return startIndex;
        }

        int lastIndex = targetLength - 1;
        char last = arrayToFind[lastIndex];
        int min = targetLength - 1;
        int i = min + startIndex;

        startSearchForLast: while (true) {
            while (i >= min && array[i] != last) {
                i--;
            }

            if (i < min) {
                return INDEX_NOT_FOUND;
            }

            int j = i - 1;
            int start = j - (targetLength - 1);
            int k = lastIndex - 1;

            while (j > start) {
                if (array[j--] != arrayToFind[k--]) {
                    i--;
                    continue startSearchForLast;
                }
            }

            return start + 1;
        }
    }


    public static boolean contains(char[] array, char charToFind) {
        return indexOf(array, charToFind) != INDEX_NOT_FOUND;
    }


    public static boolean contains(char[] array, char[] arrayToFind) {
        return indexOf(array, arrayToFind) != INDEX_NOT_FOUND;
    }


    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }

        return (T[]) array.clone();
    }


    public static long[] clone(long[] array) {
        if (array == null) {
            return null;
        }

        return (long[]) array.clone();
    }


    public static int[] clone(int[] array) {
        if (array == null) {
            return null;
        }

        return (int[]) array.clone();
    }


    public static short[] clone(short[] array) {
        if (array == null) {
            return null;
        }

        return (short[]) array.clone();
    }


    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }

        return (byte[]) array.clone();
    }


    public static double[] clone(double[] array) {
        if (array == null) {
            return null;
        }

        return (double[]) array.clone();
    }


    public static float[] clone(float[] array) {
        if (array == null) {
            return null;
        }

        return (float[]) array.clone();
    }


    public static boolean[] clone(boolean[] array) {
        if (array == null) {
            return null;
        }

        return (boolean[]) array.clone();
    }


    public static char[] clone(char[] array) {
        if (array == null) {
            return null;
        }

        return (char[]) array.clone();
    }


    public static <T> T[] addAll(T[] array1, T[] array2) {
        if (array1 == null) {
            return (T[]) clone(array2);
        } else if (array2 == null) {
            return (T[]) clone(array1);
        }
        T[] joinedArray = (T[]) Array.newInstance(array1.getClass().getComponentType(), array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        try {
            System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        } catch (ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because: - it would be a wasted check most of the time -
             * safer, in case check turns out to be too strict
             */
            final Class<?> type1 = array1.getClass().getComponentType();
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of "
                        + type1.getName());
            }
            throw ase; // No, so rethrow original
        }
        return joinedArray;
    }


    public static boolean[] addAll(boolean[] array1, boolean[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        boolean[] joinedArray = new boolean[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }


    public static char[] addAll(char[] array1, char[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        char[] joinedArray = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static short[] addAll(short[] array1, short[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        short[] joinedArray = new short[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static int[] addAll(int[] array1, int[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        int[] joinedArray = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static long[] addAll(long[] array1, long[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        long[] joinedArray = new long[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static float[] addAll(float[] array1, float[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        float[] joinedArray = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    public static double[] addAll(double[] array1, double[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        double[] joinedArray = new double[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }


    public static String toString(Object array) {
        if (ObjectUtil.isEmpty(array)) {
            return null;
        }
        Class<?> type = array.getClass();
        if (type.isArray()) {
            if (type == char[].class) {
                return toString((char[]) array);
            }

            if (type == int[].class) {
                return toString((int[]) array);
            }
            if (type == long[].class) {
                return toString((long[]) array);
            }
            if (type == byte[].class) {
                return toString((byte[]) array);
            }
            if (type == float[].class) {
                return toString((float[]) array);
            }
            if (type == double[].class) {
                return toString((double[]) array);
            }
            if (type == short[].class) {
                return toString((short[]) array);
            }
            if (type == boolean[].class) {
                return toString((boolean[]) array);
            }
            return toString((Object[]) array);
        }

        return array.toString();
    }

    private static String toString(int[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(long[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(short[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(char[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(byte[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(boolean[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(float[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(double[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(array[i]);
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }

    private static String toString(Object[] array) {
        int iMax = array.length - 1;

        StringBuilder builder = new StringBuilder();
        for (int i = 0;; i++) {
            builder.append(String.valueOf(array[i]));
            if (i == iMax) {
                return builder.toString();
            }
            builder.append(",");
        }
    }


    public static String toString(Object array, String nullArrayStr) {
        return toString(array, nullArrayStr, "<null>");
    }


    public static String toString(Object array, String nullArrayStr, String nullElementStr) {
        if (array == null) {
            return nullArrayStr;
        }

        StringBuilder builder = new StringBuilder();

        toString(builder, array, nullArrayStr, nullElementStr);

        return builder.toString();
    }


    private static void toString(StringBuilder builder, Object array, String nullArrayStr, String nullElementStr) {
        if (array == null) {
            builder.append(nullElementStr);
            return;
        }

        if (!array.getClass().isArray()) {
            builder.append(ObjectUtil.toString(array, nullElementStr));
            return;
        }

        builder.append('[');

        if (long[].class.isInstance(array)) {
            long[] longArray = (long[]) array;
            int length = longArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(longArray[i]);
            }
        } else if (int[].class.isInstance(array)) {
            int[] intArray = (int[]) array;
            int length = intArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(intArray[i]);
            }
        } else if (short[].class.isInstance(array)) {
            short[] shortArray = (short[]) array;
            int length = shortArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(shortArray[i]);
            }
        } else if (byte[].class.isInstance(array)) {
            byte[] byteArray = (byte[]) array;
            int length = byteArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                } else {
                    builder.append("0x");
                }

                String hexStr = Integer.toHexString(0xFF & byteArray[i]).toUpperCase();

                if (hexStr.length() == 0) {
                    builder.append("00");
                } else if (hexStr.length() == 1) {
                    builder.append("0");
                }

                builder.append(hexStr);
            }
        } else if (double[].class.isInstance(array)) {
            double[] doubleArray = (double[]) array;
            int length = doubleArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(doubleArray[i]);
            }
        } else if (float[].class.isInstance(array)) {
            float[] floatArray = (float[]) array;
            int length = floatArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(floatArray[i]);
            }
        } else if (boolean[].class.isInstance(array)) {
            boolean[] booleanArray = (boolean[]) array;
            int length = booleanArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(booleanArray[i]);
            }
        } else if (char[].class.isInstance(array)) {
            char[] charArray = (char[]) array;
            int length = charArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                builder.append(charArray[i]);
            }
        } else {
            Object[] objectArray = (Object[]) array;
            int length = objectArray.length;

            for (int i = 0; i < length; i++) {
                if (i > 0) {
                    builder.append(", ");
                }

                toString(builder, objectArray[i], nullArrayStr, nullElementStr);
            }
        }

        builder.append(']');
    }


    public static <T> boolean isCompatible(T[] arrayA, T[] arrayB) {
        if (arrayA == null) {
            return arrayB == null;
        }
        if (arrayB == null) {
            return false;
        }
        if (arrayA.length > arrayB.length) {
            final T[] tmp = arrayA;
            arrayA = arrayB;
            arrayB = tmp;
        }
        boolean flag;
        for (final T a : arrayA) {
            flag = false;
            for (final T b : arrayB) {
                if (a.equals(b)) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }


    public static <T> T[] resize(T[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        Class<T> componentType = (Class<T>) buffer.getClass().getComponentType();
        T[] result = (T[]) Array.newInstance(componentType, newSize);
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static String[] resize(String[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        String[] result = new String[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static byte[] resize(byte[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        byte[] result = new byte[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static char[] resize(char[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        char[] result = new char[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static short[] resize(short[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        short[] result = new short[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static int[] resize(int[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        int[] result = new int[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static long[] resize(long[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        long[] result = new long[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static float[] resize(float[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        float[] result = new float[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static double[] resize(double[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        double[] result = new double[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }

    public static boolean[] resize(boolean[] buffer, int newSize) {
        if (buffer == null) {
            return null;
        }

        boolean[] result = new boolean[newSize];
        System.arraycopy(buffer, 0, result, 0, buffer.length >= newSize ? newSize : buffer.length);
        return result;
    }


    public static String[] toStringArray(Object[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = StringUtil.toString(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(String[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(byte[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(char[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(short[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(int[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(long[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(float[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(double[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(boolean[] array) {
        if (array == null) {
            return null;
        }
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = String.valueOf(array[i]);
        }
        return result;
    }

    public static String[] toStringArray(Object value) {
        if (value == null) {
            return new String[0];
        }
        Class<?> type = value.getClass();

        if (!type.isArray()) {
            return new String[] { value.toString() };
        }

        Class<?> componentType = type.getComponentType();

        if (componentType.isPrimitive()) {
            if (componentType == int.class) {
                return toStringArray((int[]) value);
            }
            if (componentType == long.class) {
                return toStringArray((long[]) value);
            }
            if (componentType == double.class) {
                return toStringArray((double[]) value);
            }
            if (componentType == float.class) {
                return toStringArray((float[]) value);
            }
            if (componentType == boolean.class) {
                return toStringArray((boolean[]) value);
            }
            if (componentType == short.class) {
                return toStringArray((short[]) value);
            }
            if (componentType == byte.class) {
                return toStringArray((byte[]) value);
            }
            throw new IllegalArgumentException();
        }

        return toStringArray((Object[]) value);
    }


    public static <T> T[] remove(T[] array, int index) {
        return (T[]) remove((Object) array, index);
    }

    public static Object[] removeElement(Object[] array, Object element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static boolean[] remove(boolean[] array, int index) {
        return (boolean[]) remove((Object) array, index);
    }

    public static boolean[] removeElement(boolean[] array, boolean element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static byte[] remove(byte[] array, int index) {
        return (byte[]) remove((Object) array, index);
    }

    public static byte[] removeElement(byte[] array, byte element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static char[] remove(char[] array, int index) {
        return (char[]) remove((Object) array, index);
    }

    public static char[] removeElement(char[] array, char element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static double[] remove(double[] array, int index) {
        return (double[]) remove((Object) array, index);
    }

    public static double[] removeElement(double[] array, double element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static float[] remove(float[] array, int index) {
        return (float[]) remove((Object) array, index);
    }

    public static float[] removeElement(float[] array, float element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static int[] remove(int[] array, int index) {
        return (int[]) remove((Object) array, index);
    }

    public static int[] removeElement(int[] array, int element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static long[] remove(long[] array, int index) {
        return (long[]) remove((Object) array, index);
    }

    public static long[] removeElement(long[] array, long element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    public static short[] remove(short[] array, int index) {
        return (short[]) remove((Object) array, index);
    }

    public static short[] removeElement(short[] array, short element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return clone(array);
        }

        return remove(array, index);
    }

    private static Object remove(Object array, int index) {
        int length = getLength(array);
        if (index < 0 || index >= length) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }

        return result;
    }

    public static <T> T[] remove(T[] buffer, int offset, int length) {
        return remove(buffer, offset, length, null);
    }

    public static <T> T[] remove(T[] buffer, int offset, int length, Class<?> componentType) {
        if (buffer == null) {
            return null;
        }

        if (componentType == null) {
            componentType = buffer.getClass().getComponentType();
        }

        int len = buffer.length - length;
        @SuppressWarnings({ "unchecked" })
        T[] result = (T[]) Array.newInstance(componentType, len);
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static String[] remove(String[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        String result[] = new String[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static byte[] remove(byte[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        byte result[] = new byte[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static char[] remove(char[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        char result[] = new char[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static short[] remove(short[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        short result[] = new short[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static int[] remove(int[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        int result[] = new int[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static long[] remove(long[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        long result[] = new long[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static float[] remove(float[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        float result[] = new float[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static double[] remove(double[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        double result[] = new double[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static boolean[] remove(boolean[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        int len = buffer.length - length;
        boolean result[] = new boolean[len];
        System.arraycopy(buffer, 0, result, 0, offset);
        System.arraycopy(buffer, offset + length, result, offset, len - offset);
        return result;
    }

    public static int getLength(Object array) {
        if (array == null) {
            return 0;
        }

        return Array.getLength(array);
    }


    public static double[] subarray(double[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        double result[] = new double[length];
        System.arraycopy(buffer, offset, result, 0, length);

        return result;
    }

    public static boolean[] subarray(boolean[] buffer, int offset, int length) {
        if (buffer == null) {
            return null;
        }

        boolean result[] = new boolean[length];
        System.arraycopy(buffer, offset, result, 0, length);

        return result;
    }




    public static int size(final Object array) {
        return array == null ? 0 : Array.getLength(array);
    }


    public static <T> T[] shallowCopy(final T[] array) {
        if (isEmpty(array) == true) {
            return null;
        }

        return array.clone();
    }


    public static <T> T[] removeItem(T[] array,int pos) {
        int length = Array.getLength(array);
        T[] dest = (T[]) Array.newInstance(array.getClass().getComponentType(),length - 1);

        System.arraycopy(array,0,dest,0,pos);
        System.arraycopy(array,pos+1,dest,pos,length-pos-1);

        return dest;
    }


    public static <T> T[] getArraySubset(T[] array,int start,int end) {
        return Arrays.copyOfRange(array,start,end);
    }
}