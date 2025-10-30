package com.langrsoft.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtil {
    public <T> T mapAccessorToString(Method method, Object receiver, Class<T> toType) {
        try {
            return toType.cast(method.invoke(receiver));
        }
        catch (InvocationTargetException | IllegalAccessException e) {
            throw new ReflectException(e);
        }
    }

    public <T> Method getMethod(String methodName, Class<T> type) {
        try {
            return type.getMethod(methodName);
        }
        catch (NoSuchMethodException e) {
            throw new ReflectException(e);
        }
    }
}
