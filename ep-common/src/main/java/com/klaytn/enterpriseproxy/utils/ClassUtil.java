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
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.util.*;


public class ClassUtil {
    private static final char RESOURCE_SEPARATOR_CHAR = '/';
    private static final char PACKAGE_SEPARATOR_CHAR = '.';
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    private static final Map<Class<?>, TypeInfo> TYPE_MAP = Collections.synchronizedMap(new WeakHashMap<Class<?>, TypeInfo>());




    public static String getClassNameAsResource(String className) {
        if (StringUtil.isBlank(className)) {
            return null;
        }

        return className.replace(PACKAGE_SEPARATOR_CHAR, RESOURCE_SEPARATOR_CHAR) + ".class";
    }

    private static final Map<String, PrimitiveInfo<?>> PRIMITIVES = CollectionUtil.createHashMap();

    static {
        addPrimitive(boolean.class, "Z", Boolean.class, "booleanValue", false);
        addPrimitive(short.class, "S", Short.class, "shortValue", (short) 0);
        addPrimitive(int.class, "I", Integer.class, "intValue", 0);
        addPrimitive(long.class, "J", Long.class, "longValue", 0L);
        addPrimitive(float.class, "F", Float.class, "floatValue", 0F);
        addPrimitive(double.class, "D", Double.class, "doubleValue", 0D);
        addPrimitive(char.class, "C", Character.class, "charValue", '\0');
        addPrimitive(byte.class, "B", Byte.class, "byteValue", (byte) 0);
        addPrimitive(void.class, "V", Void.class, null, null);
    }

    private static <T> void addPrimitive(Class<T> type, String typeCode, Class<T> wrapperType, String unwrapMethod,
                                         T defaultValue) {
        PrimitiveInfo<T> info = new PrimitiveInfo<T>(type, typeCode, wrapperType, unwrapMethod, defaultValue);

        PRIMITIVES.put(type.getName(), info);
        PRIMITIVES.put(wrapperType.getName(), info);
    }


    private static class PrimitiveInfo<T> {
        final Class<T> type;
        final String typeCode;
        final Class<T> wrapperType;
        final String unwrapMethod;
        final T defaultValue;

        public PrimitiveInfo(Class<T> type, String typeCode, Class<T> wrapperType, String unwrapMethod, T defaultValue) {
            this.type = type;
            this.typeCode = typeCode;
            this.wrapperType = wrapperType;
            this.unwrapMethod = unwrapMethod;
            this.defaultValue = defaultValue;
        }
    }


    public static Class<?> getPrimitiveType(String name) {
        PrimitiveInfo<?> info = PRIMITIVES.get(name);

        if (info != null) {
            return info.type;
        }

        return null;
    }


    public static Class<?> getPrimitiveType(Class<?> type) {
        return getPrimitiveType(type.getName());
    }


    public static <T> Class<T> getWrapperTypeIfPrimitive(Class<T> type) {
        if (type == null) {
            return null;
        }

        if (type.isPrimitive()) {
            return ((PrimitiveInfo<T>) PRIMITIVES.get(type.getName())).wrapperType;
        }

        return type;
    }


    public static <T> T getPrimitiveDefaultValue(Class<T> type) {
        if (type == null) {
            return null;
        }

        PrimitiveInfo<T> info = (PrimitiveInfo<T>) PRIMITIVES.get(type.getName());

        if (info != null) {
            return info.defaultValue;
        }

        return null;
    }


    public static boolean isPrimitiveOrWrapper(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        return PRIMITIVES.get(clazz.getName()) != null;

    }


    public static Class<?> getArrayClass(Class<?> componentType) {
        return getArrayClass(componentType, 1);
    }


    public static Class<?> getArrayClass(Class<?> componentClass, int dimension) {
        if (componentClass == null) {
            return null;
        }

        switch (dimension) {
            case 1:
                return Array.newInstance(componentClass, 0).getClass();

            case 0:
                return componentClass;

            default:
                return Array.newInstance(componentClass, new int[dimension]).getClass();
        }
    }


    public static Class<?> getArrayComponentType(Class<?> type) {
        if (type == null) {
            return null;
        }

        return getTypeInfo(type).getArrayComponentType();
    }


    public static boolean isInstance(Class<?> clazz, Object object) {
        if (clazz == null || object == null) {
            return false;
        }

        if (clazz.isPrimitive()) {
            PrimitiveInfo<?> info = PRIMITIVES.get(clazz.getName());

            return info.wrapperType.isInstance(object);
        }

        return clazz.isInstance(object);
    }


    public static <T> boolean canInstatnce(Class<T> clazz) {
        if (clazz == null) {
            return false;
        }
        if (clazz.isPrimitive()) {
            return clazz != void.class;
        }

        if (clazz.isArray() && Modifier.isPublic(getArrayComponentType(clazz).getModifiers())) {
            return true;
        }

        int modifier = clazz.getModifiers();

        if (Modifier.isAbstract(modifier) || Modifier.isInterface(modifier)) {
            return false;
        }

        Constructor<?>[] constructors = clazz.getConstructors();
        for (Constructor<?> constructor : constructors) {
            if (Modifier.isPublic(constructor.getModifiers())) {
                return true;
            }
        }

        return false;

    }


    protected static TypeInfo getTypeInfo(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Parameter clazz should not be null");
        }

        TypeInfo classInfo;

        synchronized (TYPE_MAP) {
            classInfo = TYPE_MAP.get(type);

            if (classInfo == null) {
                classInfo = new TypeInfo(type);
                TYPE_MAP.put(type, classInfo);
            }
        }

        return classInfo;
    }


    protected static class TypeInfo {
        private Class<?> type;
        private Class<?> componentType;
        private int dimension;
        private List<Class<?>> superclasses = CollectionUtil.createArrayList(2);
        private List<Class<?>> interfaces = CollectionUtil.createArrayList(2);

        private TypeInfo(Class<?> type) {
            this.type = type;

            Class<?> componentType = null;

            if (type.isArray()) {
                componentType = type;

                do {
                    componentType = componentType.getComponentType();
                    dimension++;
                } while (componentType.isArray());
            }

            this.componentType = componentType;

            if (dimension > 0) {
                componentType = getNonPrimitiveType(componentType);

                Class<?> superComponentType = componentType.getSuperclass();

                if ((superComponentType == null) && !Object.class.equals(componentType)) {
                    superComponentType = Object.class;
                }

                if (superComponentType != null) {
                    Class<?> superclass = getArrayClass(superComponentType, dimension);

                    superclasses.add(superclass);
                    superclasses.addAll(getTypeInfo(superclass).superclasses);
                } else {
                    for (int i = dimension - 1; i >= 0; i--) {
                        superclasses.add(getArrayClass(Object.class, i));
                    }
                }
            } else {
                type = getNonPrimitiveType(type);

                Class<?> superclass = type.getSuperclass();

                if (superclass != null) {
                    superclasses.add(superclass);
                    superclasses.addAll(getTypeInfo(superclass).superclasses);
                }
            }

            if (dimension == 0) {
                Class<?>[] typeInterfaces = type.getInterfaces();
                List<Class<?>> set = CollectionUtil.createArrayList();

                for (int i = 0; i < typeInterfaces.length; i++) {
                    Class<?> typeInterface = typeInterfaces[i];

                    set.add(typeInterface);
                    set.addAll(getTypeInfo(typeInterface).interfaces);
                }

                for (Iterator<Class<?>> i = superclasses.iterator();i.hasNext();) {
                    Class<?> typeInterface = i.next();

                    set.addAll(getTypeInfo(typeInterface).interfaces);
                }

                for (Iterator<Class<?>> i = set.iterator(); i.hasNext();) {
                    Class<?> interfaceClass = i.next();

                    if (!interfaces.contains(interfaceClass)) {
                        interfaces.add(interfaceClass);
                    }
                }
            } else {
                for (Iterator<Class<?>> i = getTypeInfo(componentType).interfaces.iterator(); i.hasNext();) {
                    Class<?> componentInterface = i.next();

                    interfaces.add(getArrayClass(componentInterface, dimension));
                }
            }
        }


        private Class<?> getNonPrimitiveType(Class<?> type) {
            if (type.isPrimitive()) {
                return ((PrimitiveInfo<?>) PRIMITIVES.get(type.getName())).wrapperType;
            }

            return type;
        }


        public Class<?> getType() {
            return type;
        }


        public Class<?> getArrayComponentType() {
            return componentType;
        }


        public int getArrayDimension() {
            return dimension;
        }


        public List<Class<?>> getSuperclasses() {
            return Collections.unmodifiableList(superclasses);
        }


        public List<Class<?>> getInterfaces() {
            return Collections.unmodifiableList(interfaces);
        }
    }


    public static List<Class<?>> getAllSuperclasses(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        List<Class<?>> classes = CollectionUtil.createArrayList();
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null && superclass != Object.class) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes;
    }


    public static Class<?>[] getAllSuperclassesAsArray(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        List<Class<?>> classes = CollectionUtil.createArrayList();
        Class<?> superclass = clazz.getSuperclass();
        while (superclass != null && superclass != Object.class) {
            classes.add(superclass);
            superclass = superclass.getSuperclass();
        }
        return classes.toArray(new Class<?>[0]);
    }


    public static List<Class<?>> getAllInterfaces(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        List<Class<?>> interfacesFound = CollectionUtil.createArrayList();
        getAllInterfaces(clazz, interfacesFound);

        return interfacesFound;
    }


    public static Class<?>[] getAllInterfacesAsArray(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        List<Class<?>> interfacesFound = CollectionUtil.createArrayList();
        getAllInterfaces(clazz, interfacesFound);

        return interfacesFound.toArray(new Class<?>[0]);
    }


    public static Set<Class<?>> getAllInterfacesAsSet(Class<?> clazz, ClassLoader classLoader) {
        if (clazz == null)
            return null;
        if (clazz.isInterface() && isVisible(clazz, classLoader)) {
            Set<Class<?>> set = CollectionUtil.createHashSet(1);
            set.add(clazz);
            return set;
        }
        Set<Class<?>> interfaces = CollectionUtil.createLinkedHashSet();
        while (clazz != null) {
            Class<?>[] ifcs = clazz.getInterfaces();
            for (Class<?> ifc : ifcs) {
                interfaces.addAll(getAllInterfacesAsSet(ifc, classLoader));
            }
            clazz = clazz.getSuperclass();
        }
        return interfaces;
    }


    private static void getAllInterfaces(Class<?> clazz, List<Class<?>> interfacesFound) {
        while (clazz != null) {
            Class<?>[] interfaces = clazz.getInterfaces();

            for (int i = 0; i < interfaces.length; i++) {
                if (!interfacesFound.contains(interfaces[i])) {
                    interfacesFound.add(interfaces[i]);
                    getAllInterfaces(interfaces[i], interfacesFound);
                }
            }

            clazz = clazz.getSuperclass();
        }
    }


    public static boolean isVisible(Class<?> clazz, ClassLoader classLoader) {
        if (classLoader == null) {
            return true;
        }
        try {
            Class<?> actualClass = classLoader.loadClass(clazz.getName());
            return (clazz == actualClass);
            // Else: different interface class found...
        } catch (ClassNotFoundException ex) {
            // No interface class found...
            return false;
        }
    }


    public static boolean isCacheSafe(Class<?> clazz, ClassLoader classLoader) {
        if (clazz == null) {
            return false;
        }
        ClassLoader target = clazz.getClassLoader();
        if (target == null) {
            return false;
        }
        ClassLoader cur = classLoader;
        if (cur == target) {
            return true;
        }
        while (cur != null) {
            cur = cur.getParent();
            if (cur == target) {
                return true;
            }
        }
        return false;
    }


    public static boolean isInterfaceImpl(Class<?> thisClass, Class<?> targetInterface) {
        if (thisClass == null || targetInterface == null) {
            return false;
        }
        for (Class<?> x = thisClass; x != null; x = x.getSuperclass()) {
            Class<?>[] interfaces = x.getInterfaces();
            for (Class<?> i : interfaces) {
                if (i == targetInterface) {
                    return true;
                }
                if (isInterfaceImpl(i, targetInterface)) {
                    return true;
                }
            }
        }
        return false;
    }


    public static boolean isSubClass(Class<?> thisClass, Class<?> target) {
        if (thisClass == null || target == null) {
            return false;
        }
        if (target.isInterface() != false) {
            return isInterfaceImpl(thisClass, target);
        }
        for (Class<?> x = thisClass; x != null; x = x.getSuperclass()) {
            if (x == target) {
                return true;
            }
        }
        return false;
    }


    public static boolean isAssignable(Class<?>[] classes, Class<?>[] fromClasses) {
        if (!ArrayUtil.isSameLength(fromClasses, classes)) {
            return false;
        }

        if (fromClasses == null) {
            fromClasses = ObjectUtil.EMPTY_CLASS_ARRAY;
        }

        if (classes == null) {
            classes = ObjectUtil.EMPTY_CLASS_ARRAY;
        }

        for (int i = 0; i < fromClasses.length; i++) {
            if (isAssignable(classes[i], fromClasses[i]) == false) {
                return false;
            }
        }

        return true;
    }


    public static boolean isAssignable(Class<?> clazz, Class<?> fromClass) {
        if (clazz == null) {
            return false;
        }

        if (fromClass == null) {
            return !clazz.isPrimitive();
        }

        if (clazz.isAssignableFrom(fromClass)) {
            return true;
        }

        if (clazz.isPrimitive()) {
            return assignmentTable.get(clazz).contains(fromClass);
        }

        return false;
    }

    private final static Map<Class<?>, Set<Class<?>>> assignmentTable = CollectionUtil.createHashMap();

    static {
        assignmentTable.put(boolean.class,assignableSet(boolean.class));
        assignmentTable.put(byte.class,assignableSet(byte.class));
        assignmentTable.put(char.class,assignableSet(char.class));
        assignmentTable.put(short.class,assignableSet(short.class, byte.class));
        assignmentTable.put(int.class,assignableSet(int.class, byte.class, short.class, char.class));
        assignmentTable.put(long.class,assignableSet(long.class, int.class, byte.class, short.class, char.class));
        assignmentTable.put(float.class,assignableSet(float.class, long.class, int.class, byte.class, short.class, char.class));
        assignmentTable.put(double.class,assignableSet(double.class, float.class, long.class, int.class, byte.class, short.class, char.class));
    }

    private static Set<Class<?>> assignableSet(Class<?>...types) {
        Set<Class<?>> assignableSet = CollectionUtil.createHashSet();

        for (Class<?> type : types) {
            assignableSet.add(getPrimitiveType(type));
            assignableSet.add(getWrapperTypeIfPrimitive(type));
        }

        return assignableSet;
    }


    public static String locateClass(Class<?> clazz) {
        return locateClass(clazz.getName(), clazz.getClassLoader());
    }


    public static String locateClass(String className) {
        return locateClass(className, null);
    }


    public static String locateClass(String className, ClassLoader loader) {
        className = StringUtil.trimToNull(className);

        if (loader == null) {
            loader = Thread.currentThread().getContextClassLoader();
        }

        String classFile = className.replace('.', '/') + ".class";
        URL locationURL = loader.getResource(classFile);
        String location = null;

        if (locationURL != null) {
            location = locationURL.toExternalForm();

            if (location.endsWith(classFile)) {
                location = location.substring(0, location.length() - classFile.length());
            }

            location = location.replaceAll("^(jar|zip):|!/$", StringUtil.EMPTY_STRING);
        }

        return location;
    }


    public static String getFriendlyClassNameForObject(Object object) {
        if (object == null) {
            return null;
        }

        String javaClassName = object.getClass().getName();

        return toFriendlyClassName(javaClassName, true, javaClassName);
    }


    public static String getFriendlyClassName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        String javaClassName = clazz.getName();

        return toFriendlyClassName(javaClassName, true, javaClassName);
    }


    public static String getFriendlyClassName(String javaClassName) {
        return toFriendlyClassName(javaClassName, true, javaClassName);
    }


    static String toFriendlyClassName(String javaClassName, boolean processInnerClass, String defaultIfInvalid) {
        String name = StringUtil.trimToNull(javaClassName);

        if (name == null) {
            return defaultIfInvalid;
        }

        if (processInnerClass) {
            name = name.replace('$', '.');
        }

        int length = name.length();
        int dimension = 0;

        for (int i = 0; i < length; i++, dimension++) {
            if (name.charAt(i) != '[') {
                break;
            }
        }

        if (dimension == 0) {
            return name;
        }

        if (length <= dimension) {
            return defaultIfInvalid;
        }

        StringBuilder componentTypeName = new StringBuilder();

        switch (name.charAt(dimension)) {
            case 'Z':
                componentTypeName.append("boolean");
                break;

            case 'B':
                componentTypeName.append("byte");
                break;

            case 'C':
                componentTypeName.append("char");
                break;

            case 'D':
                componentTypeName.append("double");
                break;

            case 'F':
                componentTypeName.append("float");
                break;

            case 'I':
                componentTypeName.append("int");
                break;

            case 'J':
                componentTypeName.append("long");
                break;

            case 'S':
                componentTypeName.append("short");
                break;

            case 'L':
                if (name.charAt(length - 1) != ';' || length <= dimension + 2) {
                    return defaultIfInvalid;
                }

                componentTypeName.append(name.substring(dimension + 1, length - 1));
                break;

            default:
                return defaultIfInvalid;
        }

        for (int i = 0; i < dimension; i++) {
            componentTypeName.append("[]");
        }

        return componentTypeName.toString();
    }


    public static String getSimpleClassNameForObject(Object object) {
        if (object == null) {
            return null;
        }

        return getSimpleClassName(object.getClass().getName());
    }


    public static String getSimpleClassNameForObject(Object object, boolean processInnerClass) {
        if (object == null) {
            return null;
        }

        return getSimpleClassName(object.getClass().getName(), processInnerClass);
    }


    public static String getSimpleClassName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return getSimpleClassName(clazz.getName());
    }


    public static String getSimpleClassName(Class<?> clazz, boolean proccessInnerClass) {
        if (clazz == null) {
            return null;
        }

        return getSimpleClassName(clazz.getName(), proccessInnerClass);
    }


    public static String getSimpleClassName(String javaClassName) {
        return getSimpleClassName(javaClassName, true);
    }


    public static String getSimpleClassName(String javaClassName, boolean proccesInnerClass) {
        String friendlyClassName = toFriendlyClassName(javaClassName, false, null);

        if (friendlyClassName == null) {
            return javaClassName;
        }

        if (proccesInnerClass) {
            char[] chars = friendlyClassName.toCharArray();
            int beginIndex = 0;

            for (int i = chars.length - 1; i >= 0; i--) {
                if (chars[i] == '.') {
                    beginIndex = i + 1;
                    break;
                } else if (chars[i] == '$') {
                    chars[i] = '.';
                }
            }

            return new String(chars, beginIndex, chars.length - beginIndex);
        } else {
            return friendlyClassName.substring(friendlyClassName.lastIndexOf(".") + 1);
        }
    }


    public static String getSimpleMethodSignature(Method method) {
        return getSimpleMethodSignature(method, false, false, false, false);
    }


    public static String getSimpleMethodSignature(Method method, boolean withClassName) {
        return getSimpleMethodSignature(method, false, false, withClassName, false);
    }


    public static String getSimpleMethodSignature(Method method, boolean withModifiers, boolean withReturnType,
                                                  boolean withClassName, boolean withExceptionType) {
        if (method == null) {
            return null;
        }

        StringBuilder buf = new StringBuilder();

        if (withModifiers) {
            buf.append(Modifier.toString(method.getModifiers())).append(' ');
        }

        if (withReturnType) {
            buf.append(getSimpleClassName(method.getReturnType())).append(' ');
        }

        if (withClassName) {
            buf.append(getSimpleClassName(method.getDeclaringClass())).append('.');
        }

        buf.append(method.getName()).append('(');

        Class<?>[] paramTypes = method.getParameterTypes();

        for (int i = 0; i < paramTypes.length; i++) {
            Class<?> paramType = paramTypes[i];

            buf.append(getSimpleClassName(paramType));

            if (i < paramTypes.length - 1) {
                buf.append(", ");
            }
        }

        buf.append(')');

        if (withExceptionType) {
            Class<?>[] exceptionTypes = method.getExceptionTypes();

            if (ArrayUtil.isNotEmpty(exceptionTypes)) {
                buf.append(" throws ");

                for (int i = 0; i < exceptionTypes.length; i++) {
                    Class<?> exceptionType = exceptionTypes[i];

                    buf.append(getSimpleClassName(exceptionType));

                    if (i < exceptionTypes.length - 1) {
                        buf.append(", ");
                    }
                }
            }
        }

        return buf.toString();
    }


    public static String getShortClassName(String className) {
        if (StringUtil.isEmpty(className)) {
            return className;
        }

        className = getSimpleClassName(className, false);

        char[] chars = className.toCharArray();
        int lastDot = 0;

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == PACKAGE_SEPARATOR_CHAR) {
                lastDot = i + 1;
            } else if (chars[i] == INNER_CLASS_SEPARATOR_CHAR) {
                chars[i] = PACKAGE_SEPARATOR_CHAR;
            }
        }

        return new String(chars, lastDot, chars.length - lastDot);
    }


    public static String getShortClassName(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        return getShortClassName(clazz.getName());
    }
}