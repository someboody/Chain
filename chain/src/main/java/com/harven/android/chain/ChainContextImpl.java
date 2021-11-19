package com.harven.android.chain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * ChainContextImpl
 *
 * @author pc
 * @date 2021/11/11 19:16
 */
class ChainContextImpl implements ChainContext {

    private final Map<String, Object> properties = new HashMap<>();

    ChainImpl chain;

    public ChainContextImpl(ChainImpl chain) {
        this.chain = chain;
    }

    @Override
    public <P> P getParam() {
        try {
            return (P) chain.param;
        }catch (ClassCastException ignored){}
        return null;
    }

    @Override
    public <T> void setProperty(@NonNull String key, @NonNull T value) {
        properties.put(key, value);
    }

    @Nullable
    @Override
    public <T> T getProperty(@NonNull String key) {
        Object value = properties.get(key);
        try {
            return ((T) value);
        } catch (ClassCastException ignored) {
        }
        return null;
    }

    @Override
    public void removeProperty(@NonNull String key) {
        properties.remove(key);
    }
}
