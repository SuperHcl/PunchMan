package com.umpaytest.util;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

import java.util.Date;

/**
 * @className: DateNullAdapterFactory
 * @description: TODO 类描述
 * @author: zhangjinfeng
 * @date: 2023/1/3 15:11
 **/

public class DateNullAdapterFactory<T> implements TypeAdapterFactory {

    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != Date.class) {
            return null;
        }
        return (TypeAdapter<T>) new DateNullAdapter();
    }
}
