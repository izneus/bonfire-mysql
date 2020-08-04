package com.izneus.bonfire.common.util;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Izneus
 * @date 2020/08/04
 */
public class BeanCopyUtils {

    @FunctionalInterface
    public interface OnCopyProperties<S, T> {
        void onCopyProperties(S s, T t);
    }

    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        return copyListProperties(sources, target, null);
    }

    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target,
                                                    OnCopyProperties<S, T> callback) {
        List<T> list = new ArrayList<>(sources.size());
        for (S source : sources) {
            T t = target.get();
            BeanUtils.copyProperties(source, t);
            if (callback != null) {
                // 回调
                callback.onCopyProperties(source, t);
            }
            list.add(t);
        }
        return list;
    }
}
