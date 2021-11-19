package com.harven.android.chain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * ChainContext
 *
 * @author pc
 * @date 2021/11/11 18:53
 */
public interface ChainContext {
    /** 获取参数 */
    <P> P getParam();

    /** 向上下文中设置属性 */
    <T> void setProperty(@NonNull String key, @NonNull T value);

    /** 从上下文获取属性<br/>没有对应的值或类型不匹配时 return null */
    @Nullable
    <T> T getProperty(@NonNull String key);

    /** 从上下文移除属性 */
    void removeProperty(@NonNull String key);
}
