package com.example.baselibrary;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * @author Chenjy
 *
 * @create by 2019/9/20
 *
 * @description 防快速点击
 */
public class DoubleClickObservableTransformer implements ObservableTransformer {

    private static final int interval = 500; //精确到毫秒

    @Override
    public ObservableSource apply(Observable upstream) {
        return upstream.throttleFirst(interval, TimeUnit.MILLISECONDS);
    }
}
