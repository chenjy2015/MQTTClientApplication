package com.example.baselibrary.utils;

import com.example.baselibrary.BaseViewModel;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class PUtils {

    public static <T> T create(Object object) {
        Class clz = object.getClass();
        Type type = clz.getGenericSuperclass();

        ParameterizedType pt = (ParameterizedType) type;
        Class modelClass = null;

        try {
            for (Type t : pt.getActualTypeArguments()) {
                if (BaseViewModel.class.isAssignableFrom((Class<?>) t)) {
                    modelClass = (Class) t;
                    break;
                }
            }
            return (T) modelClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
