package com.langrsoft.util;

import java.util.List;

public class ListUtil {
    private final ReflectUtil reflectUtil = new ReflectUtil();
    public <F, T> List<T> map(
            List<F> list,
            String methodName,
            Class<F> listClass,
            Class<T> toClass) {
        var method = reflectUtil.getMethod(methodName, listClass);
        return list.stream()
                .map(each -> reflectUtil.mapAccessorToString(method, each, toClass))
                .toList();
    }
}
