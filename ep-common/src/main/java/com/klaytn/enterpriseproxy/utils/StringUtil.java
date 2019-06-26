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


import com.google.common.base.Strings;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StringUtil {
    public static final String EMPTY_STRING = "";
    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static final String PARAM_DELIMITER = "<";

    public static final char[] DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final char[] DIGITS_NOCASE = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();




    public static boolean isEmpty(String str) {
        return ((str == null) || (str.length() == 0));
    }


    public static boolean isAllEmpty(String...strings) {
        if (strings == null) {
            return true;
        }

        for (String string : strings) {
            if (isNotEmpty(string)) {
                return false;
            }
        }

        return true;
    }


    private static boolean isAnyEmpty(String... strings) {
        if (strings == null) {
            return true;
        }

        for (String string : strings) {
            if (isEmpty(string)) {
                return true;
            }
        }

        return false;
    }


    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }


    static boolean isBlank(String str) {
        if (isEmpty(str)) {
            return true;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public static boolean isAllBlank(String...strings) {
        if (strings == null) {
            return true;
        }

        for (String string : strings) {
            if (isNotBlank(string)) {
                return false;
            }
        }

        return true;
    }


    public static boolean isAnyBlank(String...strings) {
        if (strings == null) {
            return true;
        }

        for (String string : strings) {
            if (isBlank(string)) {
                return true;
            }
        }

        return false;
    }


    static boolean isNotBlank(String str) {
        if (isEmpty(str)) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return true;
            }
        }

        return false;
    }


    private static String defaultIfNull(String str) {
        return (str == null) ? EMPTY_STRING : str;
    }


    static String defaultIfNull(String str,String defaultStr) {
        return (str == null) ? defaultStr : str;
    }


    public static String defaultIfEmpty(String str) {
        return (str == null) ? EMPTY_STRING : str;
    }


    public static String defaultIfEmpty(String str,String defaultStr) {
        return (isEmpty(str)) ? defaultStr : str;
    }


    public static String defaultIfBlank(String str) {
        return isBlank(str) ? EMPTY_STRING : str;
    }


    public static String defaultIfBlank(String str,String defaultStr) {
        return isBlank(str) ? defaultStr : str;
    }


    public static String trim(String str) {
        return trim(str,null,0);
    }


    public static void trimAll(String[] strings) {
        if (strings == null) {
            return;
        }

        for (int i=0,cnt=strings.length;i<cnt;i++) {
            String string = strings[i];
            if (string != null) {
                strings[i] = trim(string);
            }
        }
    }


    private static String trim(String str,String stripChars) {
        return trim(str,stripChars,0);
    }


    public static String trimStart(String str) {
        return trim(str,null,-1);
    }


    public static String trimStart(String str,String stripChars) {
        return trim(str,stripChars,-1);
    }


    public static String trimEnd(String str) {
        return trim(str,null,1);
    }


    public static String trimEnd(String str,String stripChars) {
        return trim(str,stripChars,1);
    }


    static String trimToNull(String str) {
        return trimToNull(str,null);
    }


    private static String trimToNull(String str,String stripChars) {
        String result = trim(str,stripChars);
        if (isEmpty(result)) {
            return null;
        }

        return result;
    }


    public static String trimToEmpty(String str) {
        return trimToEmpty(str,null);
    }


    private static String trimToEmpty(String str,String stripChars) {
        String result = trim(str,stripChars);
        if (result == null) {
            return EMPTY_STRING;
        }

        return result;
    }


    private static String trim(String str,String stripChars,int mode) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        int start = 0;
        int end = 0;

        if (mode <= 0) {
            if (stripChars == null) {
                while ((start < end) && (CharUtil.isWhitespace(str.charAt(start)))) {
                    start++;
                }
            }

            else if (stripChars.length() == 0) {
                return str;
            }

            else {
                while ((start < end) && (stripChars.indexOf(str.charAt(start)) != -1 )) {
                    start++;
                }
            }
        }

        if (mode >= 0) {
            if (stripChars == null) {
                while ((start < end) && (CharUtil.isWhitespace(str.charAt(end - 1)))) {
                    end--;
                }
            } else if (stripChars.length() == 0) {
                return str;
            } else {
            }
        }

        if (start == 0 && end == 0) {
            return str;
        }

        if ((start > 0) || (end < length)) {
            return str.substring(start,end);
        }

        return str;
    }


    public static boolean equals(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equals(str2);
    }


    public static boolean equalsIgnoreCase(String str1, String str2) {
        if (str1 == null) {
            return str2 == null;
        }

        return str1.equalsIgnoreCase(str2);
    }


    public static int equalsOne(String src, String[] dest) {
        if (src == null || dest == null) {
            return -1;
        }

        for (int i = 0; i < dest.length; i++) {
            if (src.equals(dest[i])) {
                return i;
            }
        }

        return -1;
    }


    public static int equalsOneIgnoreCase(String src, String[] dest) {
        if (src == null || dest == null) {
            return -1;
        }

        for (int i = 0; i < dest.length; i++) {
            if (src.equalsIgnoreCase(dest[i])) {
                return i;
            }
        }

        return -1;
    }


    public static boolean equalsIgnoreCase(String[] as,String[] as1) {
        if (as == null && as1 == null) {
            return true;
        }

        if (as == null || as1 == null) {
            return false;
        }

        if (as.length != as1.length) {
            return false;
        }

        for (int i = 0; i < as.length; i++) {
            if (!as[i].equalsIgnoreCase(as1[i])) {
                return false;
            }
        }

        return true;
    }


    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!Character.isLetter(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public static boolean isAlphaSpace(String str) {
        if (str == null) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!CharUtil.isLetter(str.charAt(i)) && (str.charAt(i) != ' ')) {
                return false;
            }
        }

        return true;
    }


    public static boolean isAlphanumeric(String str) {
        if (str == null) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!CharUtil.isLetterOrDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public static boolean isAlphanumericSpace(String str) {
        if (str == null) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!CharUtil.isLetterOrDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
                return false;
            }
        }

        return true;
    }


    public static boolean isNumeric(String str) {
        if (str == null) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!CharUtil.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public static boolean isNumericSpace(String str) {
        if (str == null) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!CharUtil.isDigit(str.charAt(i)) && (str.charAt(i) != ' ')) {
                return false;
            }
        }

        return true;
    }


    public static boolean isWhitespace(String str) {
        if (str == null) {
            return false;
        }

        for (int i=0,cnt=str.length();i<cnt;i++) {
            if (!CharUtil.isWhitespace(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }


    public static String toUpperCase(String str) {
        if (isEmpty(str)) {
            return null;
        }

        return str.toUpperCase();
    }


    public static String toLowerCase(String str) {
        if (isEmpty(str)) {
            return null;
        }

        return str.toLowerCase();
    }


    public static String capitalize(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return str;
        }

        return Character.toTitleCase(str.charAt(0)) + str.substring(1);
    }


    public static String uncapitalize(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return str;
        }

        return Character.toLowerCase(str.charAt(0)) + str.substring(1);
    }


    public static String decapitalize(String str) {
        if ((str == null) || str.length() == 0) {
            return str;
        }
        if (str.length() > 1 && Character.isUpperCase(str.charAt(1)) && Character.isUpperCase(str.charAt(0))) {
            return str;
        }

        char[] chars = str.toCharArray();
        char c = chars[0];
        char modifiedChar = Character.toLowerCase(c);
        if (modifiedChar == c) {
            return str;
        }
        chars[0] = modifiedChar;
        return new String(chars);
    }


    public static String swapCase(String str) {
        int strLen;

        if ((str == null) || ((strLen = str.length()) == 0)) {
            return str;
        }

        StringBuilder builder = new StringBuilder(strLen);

        char ch = 0;

        for (int i = 0; i < strLen; i++) {
            ch = str.charAt(i);

            if (Character.isUpperCase(ch)) {
                ch = Character.toLowerCase(ch);
            } else if (Character.isTitleCase(ch)) {
                ch = Character.toLowerCase(ch);
            } else if (Character.isLowerCase(ch)) {
                ch = Character.toUpperCase(ch);
            }

            builder.append(ch);
        }

        return builder.toString();
    }


    public static String fromCamelCase(String str, char separator) {
        int strLen;

        if ((str == null) || ((strLen = str.length()) == 0)) {
            return str;
        }
        StringBuilder result = new StringBuilder(strLen * 2);
        int resultLength = 0;
        boolean prevTranslated = false;
        for (int i = 0; i < strLen; i++) {
            char c = str.charAt(i);
            if (i > 0 || c != separator) {// skip first starting separator
                if (Character.isUpperCase(c)) {
                    if (!prevTranslated && resultLength > 0 && result.charAt(resultLength - 1) != separator) {
                        result.append(separator);
                        resultLength++;
                    }
                    c = Character.toLowerCase(c);
                    prevTranslated = true;
                } else {
                    prevTranslated = false;
                }
                result.append(c);
                resultLength++;
            }
        }
        return resultLength > 0 ? result.toString() : str;
    }


    public static String[] split(String str) {
        return split(str, null, -1);
    }


    public static List<Long> parseStringToLongList(String source,String token) {
        if (isBlank(source) || isEmpty(token)) {
            return null;
        }

        List<Long> result = new ArrayList<Long>();
        String[] units = source.split(token);
        for (String unit : units) {
            result.add(Long.valueOf(unit));
        }

        return result;
    }


    public static String[] splitNoCompress(String src, String delimiter) {
        if (src == null || delimiter == null) {
            return null;
        }

        int maxparts = (src.length() / delimiter.length()) + 2; // one more for
        // the last
        int[] positions = new int[maxparts];
        int dellen = delimiter.length();

        int i, j = 0;
        int count = 0;
        positions[0] = -dellen;
        while ((i = src.indexOf(delimiter, j)) != -1) {
            count++;
            positions[count] = i;
            j = i + dellen;
        }
        count++;
        positions[count] = src.length();

        String[] result = new String[count];

        for (i = 0; i < count; i++) {
            result[i] = src.substring(positions[i] + dellen, positions[i + 1]);
        }
        return result;
    }


    public static String[] splitc(String src, String d) {
        if (isAnyEmpty(src, d)) {
            return new String[] { src };
        }
        char[] delimiters = d.toCharArray();
        char[] srcc = src.toCharArray();

        int maxparts = srcc.length + 1;
        int[] start = new int[maxparts];
        int[] end = new int[maxparts];

        int count = 0;

        start[0] = 0;
        int s = 0, e;
        if (CharUtil.equalsOne(srcc[0], delimiters)) { // string starts with
            // delimiter
            end[0] = 0;
            count++;
            s = CharUtil.findFirstDiff(srcc, 1, delimiters);
            if (s == -1) { // nothing after delimiters
                return new String[] { "", "" };
            }
            start[1] = s; // new start
        }
        while (true) {
            // find new end
            e = CharUtil.findFirstEqual(srcc, s, delimiters);
            if (e == -1) {
                end[count] = srcc.length;
                break;
            }
            end[count] = e;
            // find new start
            count++;
            s = CharUtil.findFirstDiff(srcc, e, delimiters);
            if (s == -1) {
                start[count] = end[count] = srcc.length;
                break;
            }
            start[count] = s;
        }
        count++;
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = src.substring(start[i], end[i]);
        }
        return result;
    }

    public static String[] splitc(String src, char delimiter) {
        if (isEmpty(src)) {
            return new String[] { "" };
        }
        char[] srcc = src.toCharArray();

        int maxparts = srcc.length + 1;
        int[] start = new int[maxparts];
        int[] end = new int[maxparts];

        int count = 0;

        start[0] = 0;
        int s = 0, e;
        if (srcc[0] == delimiter) { // string starts with delimiter
            end[0] = 0;
            count++;
            s = CharUtil.findFirstDiff(srcc, 1, delimiter);
            if (s == -1) { // nothing after delimiters
                return new String[] { "", "" };
            }
            start[1] = s; // new start
        }
        while (true) {
            // find new end
            e = CharUtil.findFirstEqual(srcc, s, delimiter);
            if (e == -1) {
                end[count] = srcc.length;
                break;
            }
            end[count] = e;
            // find new start
            count++;
            s = CharUtil.findFirstDiff(srcc, e, delimiter);
            if (s == -1) {
                start[count] = end[count] = srcc.length;
                break;
            }
            start[count] = s;
        }
        count++;
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = src.substring(start[i], end[i]);
        }
        return result;
    }

    public static String[] splitc(String src, char[] delimiters) {
        if (isEmpty(src) || ArrayUtil.isEmpty(delimiters)) {
            return new String[]{src};
        }
        char[] srcc = src.toCharArray();

        int maxparts = srcc.length + 1;
        int[] start = new int[maxparts];
        int[] end = new int[maxparts];

        int count = 0;

        start[0] = 0;
        int s = 0, e;
        if (CharUtil.equalsOne(srcc[0],delimiters)) { // string start
            // with
            // delimiter
            end[0] = 0;
            count++;
            s = CharUtil.findFirstDiff(srcc, 1, delimiters);
            if (s == -1) { // nothing after delimiters
                return new String[]{"", ""};
            }
            start[1] = s; // new start
        }
        while (true) {
            // find new end
            e = CharUtil.findFirstEqual(srcc, s, delimiters);
            if (e == -1) {
                end[count] = srcc.length;
                break;
            }
            end[count] = e;

            // find new start
            count++;
            s = CharUtil.findFirstDiff(srcc, e, delimiters);
            if (s == -1) {
                start[count] = end[count] = srcc.length;
                break;
            }
            start[count] = s;
        }
        count++;
        String[] result = new String[count];
        for (int i = 0; i < count; i++) {
            result[i] = src.substring(start[i], end[i]);
        }
        return result;
    }


    private static String[] split(String str,char separatorChar) {
        if (str == null) {
            return null;
        }

        int length = str.length();

        if (length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        List<String> list = CollectionUtil.createArrayList();
        int i = 0;
        int start = 0;
        boolean match = false;

        while (i < length) {
            if (str.charAt(i) == separatorChar) {
                if (match) {
                    list.add(str.substring(start, i));
                    match = false;
                }

                start = ++i;
                continue;
            }

            match = true;
            i++;
        }

        if (match) {
            list.add(str.substring(start, i));
        }

        return list.toArray(new String[0]);
    }


    public static String[] split(String str, String separatorChars) {
        return split(str, separatorChars, -1);
    }


    private static String[] split(String str,String separatorChars,int max) {
        if (str == null) {
            return null;
        }

        int length = str.length();
        if (length == 0) {
            return EMPTY_STRING_ARRAY;
        }

        List<String> list = CollectionUtil.createArrayList();
        int sizePlus1 = 1;
        int i = 0;
        int start = 0;
        boolean match = false;

        if (separatorChars == null) {
            while (i < length) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            char sep = separatorChars.charAt(0);

            while (i < length) {
                if (str.charAt(i) == sep) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        } else {
            while (i < length) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (match) {
                        if (sizePlus1++ == max) {
                            i = length;
                        }

                        list.add(str.substring(start, i));
                        match = false;
                    }

                    start = ++i;
                    continue;
                }

                match = true;
                i++;
            }
        }

        if (match) {
            list.add(str.substring(start, i));
        }

        return list.toArray(new String[0]);
    }


    private static <T> String join(T[] array,char separator) {
        if (array == null) {
            return null;
        }

        int arraySize = array.length;
        int bufSize =
                (arraySize == 0) ? 0 : ((((array[0] == null) ? 16 : array[0].toString().length()) + 1) * arraySize);
        StringBuilder buf = new StringBuilder(bufSize);

        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }

            if (array[i] != null) {
                buf.append(array[i]);
            }
        }

        return buf.toString();
    }


    public static <T> String join(T[] array, String separator) {
        if (array == null) {
            return null;
        }

        if (separator == null) {
            separator = EMPTY_STRING;
        }

        int arraySize = array.length;

        int bufSize = arraySize == 0 ? 0
                : arraySize * ((array[0] == null ? 16 : array[0].toString().length()) + separator
                .length());

        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = 0; i < arraySize; i++) {
            if (i > 0) {
                buf.append(separator);
            }

            if (array[i] != null) {
                buf.append(array[i]);
            }
        }

        return buf.toString();
    }


    public static String join(Iterator<?> iterator,char separator) {
        if (iterator == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder(256);

        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (obj != null) {
                buf.append(obj);
            }

            if (iterator.hasNext()) {
                buf.append(separator);
            }
        }

        return buf.toString();
    }


    public static String join(Iterator<?> iterator, String separator) {
        if (iterator == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder(256);

        while (iterator.hasNext()) {
            Object obj = iterator.next();

            if (obj != null) {
                buf.append(obj);
            }

            if ((separator != null) && iterator.hasNext()) {
                buf.append(separator);
            }
        }

        return buf.toString();
    }


    public static int indexOf(String str, char searchChar) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.indexOf(searchChar);
    }


    public static int indexOf(String str, char searchChar, int startPos) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.indexOf(searchChar, startPos);
    }


    public static int indexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.indexOf(searchStr);
    }


    private static int indexOf(String str,String searchStr,int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        if ((searchStr.length() == 0) && (startPos >= str.length())) {
            return str.length();
        }

        return str.indexOf(searchStr, startPos);
    }


    public static int indexOf(int num, String str, String searchStr) {
        int index = 0;
        for (int i = 0; i < num; i++) {
            index = indexOf(str, searchStr, index + 1);
        }
        return index;
    }


    public static int indexOfAny(String str, char[] searchChars) {
        return indexOfAny(str, searchChars, 0);
    }

    private static int indexOfAny(String str,char[] searchChars,int startIndex) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length == 0)) {
            return -1;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < str.length(); i++) {
            char ch = str.charAt(i);

            for (char searchChar : searchChars) {
                if (searchChar == ch) {
                    return i;
                }
            }
        }

        return -1;
    }


    public static int indexOfAny(String str, String searchChars) {
        return indexOfAny(str, searchChars, 0);
    }

    private static int indexOfAny(String str,String searchChars,int startIndex) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        for (int i = startIndex; i < str.length(); i++) {
            char ch = str.charAt(i);

            for (int j = 0; j < searchChars.length(); j++) {
                if (searchChars.charAt(j) == ch) {
                    return i;
                }
            }
        }

        return -1;
    }


    public static int indexOfAny(String str, String[] searchStrs) {
        return indexOfAny(str, searchStrs, 0);
    }

    private static int indexOfAny(String str,String[] searchStrs,int startIndex) {
        if ((str == null) || (searchStrs == null)) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        int sz = searchStrs.length;

        // String's can't have a MAX_VALUEth index.
        int ret = Integer.MAX_VALUE;

        int tmp = 0;

        for (String search : searchStrs) {
            if (search == null) {
                continue;
            }

            tmp = str.indexOf(search);

            if (tmp == -1) {
                continue;
            }

            if (tmp < ret) {
                ret = tmp;
            }
        }

        return (ret == Integer.MAX_VALUE) ? (-1) : ret;
    }


    private static int indexOfAnyBut(String str,char[] searchChars) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length == 0)) {
            return -1;
        }

        outer: for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);

            for (char searchChar : searchChars) {
                if (searchChar == ch) {
                    continue outer;
                }
            }

            return i;
        }

        return -1;
    }


    public static int indexOfAnyBut(String str, String searchChars) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
            return -1;
        }

        for (int i = 0; i < str.length(); i++) {
            if (searchChars.indexOf(str.charAt(i)) < 0) {
                return i;
            }
        }

        return -1;
    }


    public static int lastIndexOf(String str, char searchChar) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.lastIndexOf(searchChar);
    }


    public static int lastIndexOf(String str, char searchChar, int startPos) {
        if ((str == null) || (str.length() == 0)) {
            return -1;
        }

        return str.lastIndexOf(searchChar, startPos);
    }


    public static int lastIndexOf(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.lastIndexOf(searchStr);
    }


    public static int lastIndexOf(String str, String searchStr, int startPos) {
        if ((str == null) || (searchStr == null)) {
            return -1;
        }

        return str.lastIndexOf(searchStr, startPos);
    }


    public static int lastIndexOfAny(String str, String[] searchStrs) {
        if ((str == null) || (searchStrs == null)) {
            return -1;
        }

        int searchStrsLength = searchStrs.length;
        int index = -1;
        int tmp = 0;

        for (String search : searchStrs) {
            if (search == null) {
                continue;
            }

            tmp = str.lastIndexOf(search);

            if (tmp > index) {
                index = tmp;
            }
        }

        return index;
    }


    public static boolean contains(String str, char searchChar) {
        if ((str == null) || (str.length() == 0)) {
            return false;
        }

        return str.indexOf(searchChar) >= 0;
    }


    public static boolean contains(String str, String searchStr) {
        if ((str == null) || (searchStr == null)) {
            return false;
        }

        return str.contains(searchStr);
    }


    private static boolean containsOnly(String str,char[] valid) {
        if ((valid == null) || (str == null)) {
            return false;
        }

        if (str.length() == 0) {
            return true;
        }

        if (valid.length == 0) {
            return false;
        }

        return indexOfAnyBut(str, valid) == -1;
    }


    public static boolean containsOnly(String str, String valid) {
        if ((str == null) || (valid == null)) {
            return false;
        }

        return containsOnly(str, valid.toCharArray());
    }


    public static boolean containsNone(String str, char[] invalid) {
        if ((str == null) || (invalid == null)) {
            return true;
        }

        int strSize = str.length();
        int validSize = invalid.length;

        for (int i = 0; i < strSize; i++) {
            char ch = str.charAt(i);

            for (int j = 0; j < validSize; j++) {
                if (invalid[j] == ch) {
                    return false;
                }
            }
        }

        return true;
    }


    public static boolean containsNone(String str, String invalidChars) {
        if ((str == null) || (invalidChars == null)) {
            return true;
        }

        return containsNone(str, invalidChars.toCharArray());
    }


    public static int countMatches(String str, String subStr) {
        if ((str == null) || (str.length() == 0) || (subStr == null) || (subStr.length() == 0)) {
            return 0;
        }

        int count = 0;
        int index = 0;

        while ((index = str.indexOf(subStr, index)) != -1) {
            count++;
            index += subStr.length();
        }

        return count;
    }


    public static String substring(String str, int start) {
        if (str == null) {
            return null;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (start < 0) {
            start = 0;
        }

        if (start > str.length()) {
            return EMPTY_STRING;
        }

        return str.substring(start);
    }


    public static String substring(String str, int start, int end) {
        if (str == null) {
            return null;
        }

        if (end < 0) {
            end = str.length() + end;
        }

        if (start < 0) {
            start = str.length() + start;
        }

        if (end > str.length()) {
            end = str.length();
        }

        if (start > end) {
            return EMPTY_STRING;
        }

        if (start < 0) {
            start = 0;
        }

        if (end < 0) {
            end = 0;
        }

        return str.substring(start, end);
    }


    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0) {
            return EMPTY_STRING;
        }

        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(0, len);
        }
    }


    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }

        if (len < 0) {
            return EMPTY_STRING;
        }

        if (str.length() <= len) {
            return str;
        } else {
            return str.substring(str.length() - len);
        }
    }


    public static String mid(String str, int pos, int len) {
        if (str == null) {
            return null;
        }

        if ((len < 0) || (pos > str.length())) {
            return EMPTY_STRING;
        }

        if (pos < 0) {
            pos = 0;
        }

        if (str.length() <= (pos + len)) {
            return str.substring(pos);
        } else {
            return str.substring(pos, pos + len);
        }
    }


    public static String substringBefore(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0)) {
            return str;
        }

        if (separator.length() == 0) {
            return EMPTY_STRING;
        }

        int pos = str.indexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }


    public static String substringAfter(String str, String separator) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        if (separator == null) {
            return EMPTY_STRING;
        }

        int pos = str.indexOf(separator);

        if (pos == -1) {
            return EMPTY_STRING;
        }

        return str.substring(pos + separator.length());
    }


    public static String substringBeforeLast(String str, String separator) {
        if ((str == null) || (separator == null) || (str.length() == 0) || (separator.length() == 0)) {
            return str;
        }

        int pos = str.lastIndexOf(separator);

        if (pos == -1) {
            return str;
        }

        return str.substring(0, pos);
    }


    public static String substringAfterLast(String str, String separator) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        if ((separator == null) || (separator.length() == 0)) {
            return EMPTY_STRING;
        }

        int pos = str.lastIndexOf(separator);

        if ((pos == -1) || (pos == (str.length() - separator.length()))) {
            return EMPTY_STRING;
        }

        return str.substring(pos + separator.length());
    }


    public static String substringBetween(String str, String tag) {
        return substringBetween(str, tag, tag, 0);
    }


    public static String substringBetween(String str, String open, String close) {
        return substringBetween(str, open, close, 0);
    }


    private static String substringBetween(String str,String open,String close,int fromIndex) {
        if ((str == null) || (open == null) || (close == null)) {
            return null;
        }

        int start = str.indexOf(open, fromIndex);

        if (start != -1) {
            int end = str.indexOf(close, start + open.length());

            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }

        return str;
    }


    public static String deleteWhitespace(String str) {
        if (str == null) {
            return null;
        }

        int sz = str.length();
        StringBuilder builder = new StringBuilder(sz);

        for (int i = 0; i < sz; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                builder.append(str.charAt(i));
            }
        }

        return builder.toString();
    }


    private static String replaceOnce(String text,String repl,String with) {
        return replace(text, repl, with, 1);
    }


    static String replace(String text,String repl,String with) {
        return replace(text, repl, with, -1);
    }


    private static String replace(String text,String repl,String with,int max) {
        if ((text == null) || (repl == null) || (with == null) || (repl.length() == 0) || (max == 0)) {
            return text;
        }

        StringBuilder buf = new StringBuilder(text.length());
        int start = 0;
        int end = 0;

        while ((end = text.indexOf(repl, start)) != -1) {
            buf.append(text,start,end).append(with);
            start = end + repl.length();

            if (--max == 0) {
                break;
            }
        }

        buf.append(text.substring(start));
        return buf.toString();
    }


    public static String replaceOnce(int startPos, String text, String repl, String with) {
        String target = StringUtil.substring(text, startPos);
        String before = StringUtil.substring(text, 0, startPos);
        String after = StringUtil.replaceOnce(target, repl, with);
        return (before + after);
    }


    public static String replaceChars(String str, char searchChar, char replaceChar) {
        if (str == null) {
            return null;
        }

        return str.replace(searchChar, replaceChar);
    }


    public static String replaceChars(String s, char[] sub, char[] with) {
        if (s == null || sub == null || with == null) {
            return s;
        }

        char[] str = s.toCharArray();
        for (int i = 0; i < str.length; i++) {
            char c = str[i];
            for (int j = 0; j < sub.length; j++) {
                if (c == sub[j]) {
                    str[i] = with[j];
                    break;
                }
            }
        }
        return new String(str);
    }


    public static String replaceChars(String str, String searchChars, String replaceChars) {
        if ((str == null) || (str.length() == 0) || (searchChars == null) || (searchChars.length() == 0)) {
            return str;
        }

        char[] chars = str.toCharArray();
        int len = chars.length;
        boolean modified = false;

        for (int i = 0, isize = searchChars.length(); i < isize; i++) {
            char searchChar = searchChars.charAt(i);

            if ((replaceChars == null) || (i >= replaceChars.length())) {
                int pos = 0;

                for (int j = 0; j < len; j++) {
                    if (chars[j] != searchChar) {
                        chars[pos++] = chars[j];
                    } else {
                        modified = true;
                    }
                }

                len = pos;
            } else {
                for (int j = 0; j < len; j++) {
                    if (chars[j] == searchChar) {
                        chars[j] = replaceChars.charAt(i);
                        modified = true;
                    }
                }
            }
        }

        if (!modified) {
            return str;
        }

        return new String(chars, 0, len);
    }


    public static String replaceOnce(String str, char searchChar, char replaceChar) {
        if (str == null) {
            return null;
        }
        int index = str.indexOf(searchChar);
        if (index == -1) {
            return str;
        }
        char[] ch = str.toCharArray();
        ch[index] = replaceChar;
        return new String(ch);
    }


    public static String replaceLast(String text, String sub, String with) {
        if ((text == null) || (sub == null) || (with == null)) {
            return null;
        }
        int i = text.lastIndexOf(sub);
        if (i == -1) {
            return text;
        }
        return text.substring(0, i) + with + text.substring(i + sub.length());
    }


    public static String replaceLast(String text, char sub, char with) {
        if (text == null) {
            return null;
        }
        int index = text.lastIndexOf(sub);
        if (index == -1) {
            return text;
        }
        char[] str = text.toCharArray();
        str[index] = with;
        return new String(str);
    }


    public static String overlay(String str, String overlay, int start, int end) {
        if (str == null) {
            return null;
        }

        if (overlay == null) {
            overlay = EMPTY_STRING;
        }

        int len = str.length();

        if (start < 0) {
            start = 0;
        }

        if (start > len) {
            start = len;
        }

        if (end < 0) {
            end = 0;
        }

        if (end > len) {
            end = len;
        }

        if (start > end) {
            int temp = start;

            start = end;
            end = temp;
        }

        return str.substring(0,start) +
                overlay + str.substring(end);
    }


    public static String chomp(String str) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        if (str.length() == 1) {
            char ch = str.charAt(0);

            if ((ch == '\r') || (ch == '\n')) {
                return EMPTY_STRING;
            } else {
                return str;
            }
        }

        int lastIdx = str.length() - 1;
        char last = str.charAt(lastIdx);

        if (last == '\n') {
            if (str.charAt(lastIdx - 1) == '\r') {
                lastIdx--;
            }
        } else if (last == '\r') {
        } else {
            lastIdx++;
        }

        return str.substring(0, lastIdx);
    }


    public static String chomp(String str, String separator) {
        if ((str == null) || (str.length() == 0) || (separator == null)) {
            return str;
        }

        if (str.endsWith(separator)) {
            return str.substring(0, str.length() - separator.length());
        }

        return str;
    }


    public static String chop(String str) {
        if (str == null) {
            return null;
        }

        int strLen = str.length();

        if (strLen < 2) {
            return EMPTY_STRING;
        }

        int lastIdx = strLen - 1;
        String ret = str.substring(0, lastIdx);
        char last = str.charAt(lastIdx);

        if (last == '\n') {
            if (ret.charAt(lastIdx - 1) == '\r') {
                return ret.substring(0, lastIdx - 1);
            }
        }

        return ret;
    }


    public static String repeat(String str, int repeat) {
        if (str == null) {
            return null;
        }

        if (repeat <= 0) {
            return EMPTY_STRING;
        }

        int inputLength = str.length();

        if ((repeat == 1) || (inputLength == 0)) {
            return str;
        }

        int outputLength = inputLength * repeat;

        switch (inputLength) {
            case 1:

                char ch = str.charAt(0);
                char[] output1 = new char[outputLength];

                for (int i = repeat - 1; i >= 0; i--) {
                    output1[i] = ch;
                }

                return new String(output1);

            case 2:

                char ch0 = str.charAt(0);
                char ch1 = str.charAt(1);
                char[] output2 = new char[outputLength];

                for (int i = (repeat * 2) - 2; i >= 0; i--, i--) {
                    output2[i] = ch0;
                    output2[i + 1] = ch1;
                }

                return new String(output2);

            default:

                StringBuilder buf = new StringBuilder(outputLength);

                for (int i = 0; i < repeat; i++) {
                    buf.append(str);
                }

                return buf.toString();
        }
    }

    public static String repeat(char c, int count) {
        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = c;
        }
        return new String(result);
    }


    public static String alignLeft(String str, int size) {
        return alignLeft(str, size, ' ');
    }


    private static String alignLeft(String str,int size,char padChar) {
        if (str == null) {
            return null;
        }

        int pads = size - str.length();

        if (pads <= 0) {
            return str;
        }

        return alignLeft(str, size, String.valueOf(padChar));
    }


    private static String alignLeft(String str,int size,String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return str.concat(padStr);
        } else if (pads < padLen) {
            return str.concat(padStr.substring(0, pads));
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return str.concat(new String(padding));
        }
    }


    public static String alignRight(String str, int size) {
        return alignRight(str, size, ' ');
    }


    private static String alignRight(String str,int size,char padChar) {
        if (str == null) {
            return null;
        }

        int pads = size - str.length();

        if (pads <= 0) {
            return str;
        }

        return alignRight(str, size, String.valueOf(padChar));
    }


    private static String alignRight(String str,int size,String padStr) {
        if (str == null) {
            return null;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int padLen = padStr.length();
        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        if (pads == padLen) {
            return padStr.concat(str);
        } else if (pads < padLen) {
            return padStr.substring(0, pads).concat(str);
        } else {
            char[] padding = new char[pads];
            char[] padChars = padStr.toCharArray();

            for (int i = 0; i < pads; i++) {
                padding[i] = padChars[i % padLen];
            }

            return new String(padding).concat(str);
        }
    }


    public static String center(String str, int size) {
        return center(str, size, ' ');
    }


    private static String center(String str,int size,char padChar) {
        if ((str == null) || (size <= 0)) {
            return str;
        }

        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        str = alignRight(str, strLen + (pads / 2), padChar);
        str = alignLeft(str, size, padChar);
        return str;
    }


    public static String center(String str, int size, String padStr) {
        if ((str == null) || (size <= 0)) {
            return str;
        }

        if ((padStr == null) || (padStr.length() == 0)) {
            padStr = " ";
        }

        int strLen = str.length();
        int pads = size - strLen;

        if (pads <= 0) {
            return str;
        }

        str = alignRight(str, strLen + (pads / 2), padStr);
        str = alignLeft(str, size, padStr);
        return str;
    }


    public static String reverse(String str) {
        if ((str == null) || (str.length() == 0)) {
            return str;
        }

        return new StringBuilder(str).reverse().toString();
    }


    public static String reverseDelimited(String str, char separatorChar) {
        if (str == null) {
            return null;
        }

        String[] strs = split(str, separatorChar);

        ArrayUtil.reverse(strs);

        return join(strs, separatorChar);
    }


    public static String reverseDelimited(String str, String separatorChars, String separator) {
        if (str == null) {
            return null;
        }

        String[] strs = split(str, separatorChars);

        ArrayUtil.reverse(strs);

        if (separator == null) {
            return join(strs, ' ');
        }

        return join(strs, separator);
    }


    private static String abbreviate(String str,int maxWidth) {
        return abbreviate(str, 0, maxWidth);
    }


    private static String abbreviate(String str,int offset,int maxWidth) {
        if (str == null) {
            return null;
        }

        if (maxWidth < 4) {
            maxWidth = 4;
        }

        if (str.length() <= maxWidth) {
            return str;
        }

        if (offset > str.length()) {
            offset = str.length();
        }

        if ((str.length() - offset) < (maxWidth - 3)) {
            offset = str.length() - (maxWidth - 3);
        }

        if (offset <= 4) {
            return str.substring(0, maxWidth - 3) + "...";
        }

        if (maxWidth < 7) {
            maxWidth = 7;
        }

        if ((offset + (maxWidth - 3)) < str.length()) {
            return "..." + abbreviate(str.substring(offset), maxWidth - 3);
        }

        return "..." + str.substring(str.length() - (maxWidth - 3));
    }


    public static String difference(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }

        if (str2 == null) {
            return str1;
        }

        int index = indexOfDifference(str1, str2);

        if (index == -1) {
            return EMPTY_STRING;
        }

        return str2.substring(index);
    }


    private static int indexOfDifference(String str1,String str2) {
        if ((str1 == null) || (str2 == null) || (str1.equals(str2))) {
            return -1;
        }

        int i;

        for (i = 0; (i < str1.length()) && (i < str2.length()); ++i) {
            if (str1.charAt(i) != str2.charAt(i)) {
                break;
            }
        }
        return i;
    }


    public static int getLevenshteinDistance(String s, String t) {
        s = defaultIfNull(s);
        t = defaultIfNull(t);

        int[][] d; // matrix
        int n; // length of s
        int m; // length of t
        int i; // iterates through s
        int j; // iterates through t
        char s_i; // ith character of s
        char t_j; // jth character of t
        int cost; // cost

        // Step 1
        n = s.length();
        m = t.length();

        if (n == 0) {
            return m;
        }

        if (m == 0) {
            return n;
        }

        d = new int[n + 1][m + 1];

        // Step 2
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }

        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }

        // Step 3
        for (i = 1; i <= n; i++) {
            s_i = s.charAt(i - 1);

            // Step 4
            for (j = 1; j <= m; j++) {
                t_j = t.charAt(j - 1);

                // Step 5
                if (s_i == t_j) {
                    cost = 0;
                } else {
                    cost = 1;
                }

                // Step 6
                d[i][j] = min(d[i - 1][j] + 1, d[i][j - 1] + 1, d[i - 1][j - 1] + cost);
            }
        }

        // Step 7
        return d[n][m];
    }


    private static int min(int a, int b, int c) {
        if (b < a) {
            a = b;
        }

        if (c < a) {
            a = c;
        }

        return a;
    }


    public static String STAM(String str, String wadChar, int length) {
        if (str == null || length < 0 || length <= str.length())
            return str;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length - str.length(); i++) {
            sb.append(wadChar);
        }
        sb.append(str);
        return sb.toString();
    }


    public static String toUpperCase4First(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) +
                str.substring(1);
    }


    public static String toLowerCase4First(String str) {
        if (StringUtil.isBlank(str)) {
            return str;
        }
        return Character.toLowerCase(str.charAt(0)) +
                str.substring(1);
    }


    public static String getASCII(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder builder = new StringBuilder();
        byte[] byteArray = str.getBytes();
        for (int i = 0; i < byteArray.length; i++) {
            builder.append(Integer.toHexString(byteArray[i] & 0xff));
        }
        return builder.toString();
    }


    public static String toCamelCase(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(Character.toUpperCase(ch));
                } else {
                    buffer.append(Character.toLowerCase(ch));
                }
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                if (ch != UNDERSCORE) {
                    buffer.append(ch);
                }
            }
        }.parse(str);
    }


    public static String toPascalCase(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                if (ch != UNDERSCORE) {
                    buffer.append(ch);
                }
            }
        }.parse(str);
    }


    public static String toUpperCaseWithUnderscores(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toUpperCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }
        }.parse(str);
    }


    public static String toLowerCaseWithUnderscores(String str) {
        return new WordTokenizer() {
            @Override
            protected void startSentence(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void inWord(StringBuilder buffer, char ch) {
                buffer.append(Character.toLowerCase(ch));
            }

            @Override
            protected void startDigitSentence(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void startDigitWord(StringBuilder buffer, char ch) {
                if (!isDelimiter(buffer.charAt(buffer.length() - 1))) {
                    buffer.append(UNDERSCORE);
                }

                buffer.append(ch);
            }

            @Override
            protected void inDigitWord(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }

            @Override
            protected void inDelimiter(StringBuilder buffer, char ch) {
                buffer.append(ch);
            }
        }.parse(str);
    }




    private abstract static class WordTokenizer {
        static final char UNDERSCORE = '_';

        String parse(String str) {
            if (StringUtil.isEmpty(str)) {
                return str;
            }

            int length = str.length();
            StringBuilder buffer = new StringBuilder(length);

            for (int index = 0; index < length; index++) {
                char ch = str.charAt(index);

                if (Character.isWhitespace(ch)) {
                    continue;
                }

                if (Character.isUpperCase(ch)) {
                    int wordIndex = index + 1;

                    while (wordIndex < length) {
                        char wordChar = str.charAt(wordIndex);

                        if (Character.isUpperCase(wordChar)) {
                            wordIndex++;
                        } else if (Character.isLowerCase(wordChar)) {
                            wordIndex--;
                            break;
                        } else {
                            break;
                        }
                    }

                    if (wordIndex == length || wordIndex > index) {
                        index = parseUpperCaseWord(buffer, str, index, wordIndex);
                    } else {
                        index = parseTitleCaseWord(buffer, str, index);
                    }

                    continue;
                }

                if (Character.isLowerCase(ch)) {
                    index = parseLowerCaseWord(buffer, str, index);
                    continue;
                }

                if (Character.isDigit(ch)) {
                    index = parseDigitWord(buffer, str, index);
                    continue;
                }

                inDelimiter(buffer, ch);
            }

            return buffer.toString();
        }

        private int parseUpperCaseWord(StringBuilder buffer, String str, int index, int length) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            for (; index < length; index++) {
                ch = str.charAt(index);
                inWord(buffer, ch);
            }

            return index - 1;
        }

        private int parseLowerCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseTitleCaseWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startSentence(buffer, ch);
            } else {
                startWord(buffer, ch);
            }

            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isLowerCase(ch)) {
                    inWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        private int parseDigitWord(StringBuilder buffer, String str, int index) {
            char ch = str.charAt(index++);

            if (buffer.length() == 0) {
                startDigitSentence(buffer, ch);
            } else {
                startDigitWord(buffer, ch);
            }

            int length = str.length();

            for (; index < length; index++) {
                ch = str.charAt(index);

                if (Character.isDigit(ch)) {
                    inDigitWord(buffer, ch);
                } else {
                    break;
                }
            }

            return index - 1;
        }

        boolean isDelimiter(char ch) {
            return !Character.isUpperCase(ch) && !Character.isLowerCase(ch) && !Character.isDigit(ch);
        }

        protected abstract void startSentence(StringBuilder buffer, char ch);

        protected abstract void startWord(StringBuilder buffer, char ch);

        protected abstract void inWord(StringBuilder buffer, char ch);

        protected abstract void startDigitSentence(StringBuilder buffer, char ch);

        protected abstract void startDigitWord(StringBuilder buffer, char ch);

        protected abstract void inDigitWord(StringBuilder buffer, char ch);

        protected abstract void inDelimiter(StringBuilder buffer, char ch);
    }



    public static String longToString(long longValue) {
        return longToString(longValue, false);
    }


    private static String longToString(long longValue,boolean noCase) {
        char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
        int digitsLength = digits.length;

        if (longValue == 0) {
            return String.valueOf(digits[0]);
        }

        if (longValue < 0) {
            longValue = -longValue;
        }

        StringBuilder strValue = new StringBuilder();

        while (longValue != 0) {
            int digit = (int) (longValue % digitsLength);
            longValue = longValue / digitsLength;

            strValue.append(digits[digit]);
        }

        return strValue.toString();
    }


    public static String bytesToString(byte[] bytes) {
        return bytesToString(bytes, false);
    }


    private static String bytesToString(byte[] bytes,boolean noCase) {
        char[] digits = noCase ? DIGITS_NOCASE : DIGITS;
        int digitsLength = digits.length;

        if (ArrayUtil.isEmpty(bytes)) {
            return String.valueOf(digits[0]);
        }

        StringBuilder strValue = new StringBuilder();
        int value = 0;
        int limit = Integer.MAX_VALUE >>> 8;
        int i = 0;

        do {
            while (i < bytes.length && value < limit) {
                value = (value << 8) + (0xFF & bytes[i++]);
            }

            while (value >= digitsLength) {
                strValue.append(digits[value % digitsLength]);
                value = value / digitsLength;
            }
        } while (i < bytes.length);

        if (value != 0 || strValue.length() == 0) {
            strValue.append(digits[value]);
        }

        return strValue.toString();
    }


    public static boolean endsWithChar(String str, char ch) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }

        return str.charAt(str.length() - 1) == ch;
    }


    public static boolean startsWithChar(String str, char ch) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }

        return str.charAt(0) == ch;
    }


    public static int indexOfChars(String string, String chars) {
        return indexOfChars(string, chars, 0);
    }

    private static int indexOfChars(String string,String chars,int startindex) {
        if (string == null || chars == null) {
            return -1;
        }

        int stringLen = string.length();
        int charsLen = chars.length();

        if (startindex < 0) {
            startindex = 0;
        }

        for (int i = startindex; i < stringLen; i++) {
            char c = string.charAt(i);
            for (int j = 0; j < charsLen; j++) {
                if (c == chars.charAt(j)) {
                    return i;
                }
            }
        }

        return -1;
    }


    public static int indexOfChars(String string, char[] chars) {
        return indexOfChars(string, chars, 0);
    }

    private static int indexOfChars(String string,char[] chars,int startindex) {
        if (string == null || chars == null) {
            return -1;
        }

        int stringLen = string.length();
        int charsLen = chars.length;

        for (int i = startindex; i < stringLen; i++) {
            char c = string.charAt(i);
            for (char aChar : chars) {
                if (c == aChar) {
                    return i;
                }
            }
        }

        return -1;
    }


    public static String toString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static String toString(Collection<String> collection) {
        return toString(collection, " ");
    }

    public static String toString(Collection<String> collection, String split) {
        if (collection == null || split == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder();
        for (Object object : collection) {
            builder.append(object).append(split);
        }

        builder.setLength(builder.length() - split.length());
        return builder.toString();
    }

    public static boolean isDigits(String string) {
        int size = string.length();
        for (int i = 0; i < size; i++) {
            char c = string.charAt(i);
            if (!CharUtil.isDigit(c)) {
                return false;
            }
        }
        return true;
    }


    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return endsWith(str, suffix);
    }

    private static boolean endsWith(String str,String suffix) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(true, strOffset, suffix, 0, suffix.length());
    }


    public static int converInt(String str, int defaultValue) {
        if (null == str) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static String unhtmlspecialchars(String str) {
        str = str.replaceAll("&amp;", "&");
        str = str.replace("&#039;", "\"");
        str = str.replace("&quot;", "\"");
        str = str.replace("&lt;", "<");
        str = str.replace("&gt;", ">");

        return str;
    }

    public static List<String> getParamToArray(String param, int minSize) {
        return getParamToArray(param, false, minSize);
    }

    public static List<String> getParamToArray(String param) {
        return getParamToArray(param, false);
    }


    private static List<String> getParamToArray(String param,boolean acceptEmptyElements,int minSize) {
        List<String> paramToArray = getParamToArray(param, acceptEmptyElements);
        if (paramToArray.size() < minSize) {
            paramToArray.add("");
        }
        return paramToArray;
    }


    private static List<String> getParamToArray(String param,boolean acceptEmptyElements) { // ,
        // String
        // opt
        // =//
        // null)
        // {
        List<String> paramArray = new ArrayList<String>();

        if (!Strings.isNullOrEmpty(param)) {

            if (param.contains(PARAM_DELIMITER)) {

                String[] params = param.split(PARAM_DELIMITER, -1);

                for (String value : params) {
                    value = value.trim();
                    if (!value.isEmpty() || acceptEmptyElements) {
                        paramArray.add(unhtmlspecialchars(value));
                    }
                }
            } else {
                paramArray.add(param);
            }

        }
        return paramArray;
    }

    private static boolean isNull(String param) {
        return param == null || param.trim().length() < 1;
    }

    public static boolean isNullNotStr(Object param) {
        if (param == null) {
            return true;
        } else if (param instanceof String) {
            return StringUtil.isNull(param.toString());
        } else {
            return false;
        }
    }

    public static String implode(String delimeter, Map<String, Object> params) {
        if (params == null || params.size() == 0) {
            return "";
        } else {
            return String.join(delimeter, params.keySet());
        }
    }

    private static List<String> explode(String delimeter,String params) {
        if (params == null || params.length() < 1) {
            return new ArrayList<String>();
        } else {
            return Arrays.asList(params.split(delimeter));
        }
    }

    public static List<String> explodeUnique(String delimeter, String params) {
        List<String> returnList = StringUtil.explode(delimeter, params);

        if (returnList.size() < 1)
            return returnList;
        else {
            return new ArrayList<String>(new LinkedHashSet<String>(returnList));
        }
    }

    private static final String[] YN_LIST = new String[] { "Y", "N" };

    public static boolean isYorN(String data) {
        return ArrayUtils.contains(YN_LIST, data);
    }

    public static String strrev(String value) {
        if (value == null || value.trim().length() < 1) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder(value);
            return sb.reverse().toString();
        }
    }

    public static int arraySum(List<String> stringIntList) {
        int rv = 0;
        for (String strInt : stringIntList) {
            if (NumberUtils.isDigits(strInt)) {
                rv += Integer.parseInt(strInt);
            }
        }
        return rv;
    }

    public static String implode(String[] arr, String seprator) {
        return StringUtils.join(arr, seprator);
    }

    public static String implode(List<String> list, String seprator) {
        return StringUtils.join(list, seprator);
    }

    public static Boolean pregMatch(String pattern, String subject) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(subject);
        return m.matches();
    }

    public static String pregReplace(String pattern, String changeWord, String subject) {
        return subject.replaceAll(pattern, changeWord);
    }

    public static boolean empty(String str) {
        return StringUtils.isEmpty(str);
    }

    public static boolean empty(Collection<?> coll) {
        return CollectionUtils.isEmpty(coll);
    }

    public static boolean empty(Object[] array) {
        return ArrayUtils.isEmpty(array);
    }

    public static String ellipsis(String text, int length) {
        String ellipsisString = "...";
        String outputString = text;

        if(text.length()>0 && length>0){
            if(text.length()>length){
                outputString = text.substring(0, length);
                outputString += ellipsisString;
            }
        }
        return outputString;
    }


    /**
     * is hexadecimal
     *
     * @param value
     * @return
     */
    private static final Pattern HEXSTRING = Pattern.compile("^[0-9A-Fa-f]+$");
    public static boolean isHexadecimal(String value) {
        if (isEmpty(value)) {
            return false;
        }

        if (value.length() == 2 && StringUtils.startsWith(value,"0x")) {
            return true;
        }

        if (StringUtils.startsWith(value,"0x")) {
            value = StringUtils.substring(value,2);
        }

        Matcher matcher = HEXSTRING.matcher(value);
        return (matcher.find());
    }


    /**
     * hex to BigInteger
     *
     * @param hex
     * @return
     */
    public static BigInteger hexToBigInteger(String hex) {
        if (hex.length() == 2 && StringUtils.startsWith(hex,"0x")) {
            return BigInteger.ZERO;
        }

        if (isHexadecimal(hex)) {
            hex = substring(hex,2);
        }

        return new BigInteger(hex,16);
    }


    private StringUtil() {
        throw new IllegalStateException("Utility Class");
    }
}