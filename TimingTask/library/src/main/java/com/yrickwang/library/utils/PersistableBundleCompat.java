/*
 * Copyright 2007-present Evernote Corporation.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.yrickwang.library.utils;

import android.os.PersistableBundle;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Compat class which provides similar features like {@link PersistableBundle}. Besides a boolean array
 * all methods are available.
 *
 * @author rwondratschek
 */
@SuppressWarnings("unused")
public final class PersistableBundleCompat {

    private static final String UTF_8 = "UTF-8";

    private final Map<String, Object> mValues;

    public PersistableBundleCompat() {
        this(new HashMap<String, Object>());
    }

    public PersistableBundleCompat(PersistableBundleCompat bundle) {
        this(new HashMap<>(bundle.mValues));
    }

    private PersistableBundleCompat(Map<String, Object> values) {
        mValues = values;
    }

    public void putBoolean(String key, boolean value) {
        mValues.put(key, value);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        Object value = mValues.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        } else {
            return defaultValue;
        }
    }

    public void putInt(String key, int value) {
        mValues.put(key, value);
    }

    public int getInt(String key, int defaultValue) {
        Object value = mValues.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else {
            return defaultValue;
        }
    }

    public void putIntArray(String key, int[] value) {
        mValues.put(key, value);
    }

    public int[] getIntArray(String key) {
        Object value = mValues.get(key);
        if (value instanceof int[]) {
            return (int[]) value;
        } else {
            return null;
        }
    }

    public void putLong(String key, long value) {
        mValues.put(key, value);
    }

    public long getLong(String key, long defaultValue) {
        Object value = mValues.get(key);
        if (value instanceof Long) {
            return (Long) value;
        } else {
            return defaultValue;
        }
    }

    public void putLongArray(String key, long[] value) {
        mValues.put(key, value);
    }

    public long[] getLongArray(String key) {
        Object value = mValues.get(key);
        if (value instanceof long[]) {
            return (long[]) value;
        } else {
            return null;
        }
    }

    public void putDouble(String key, double value) {
        mValues.put(key, value);
    }

    public double getDouble(String key, double defaultValue) {
        Object value = mValues.get(key);
        if (value instanceof Double) {
            return (Double) value;
        } else {
            return defaultValue;
        }
    }

    public void putDoubleArray(String key, double[] value) {
        mValues.put(key, value);
    }

    public double[] getDoubleArray(String key) {
        Object value = mValues.get(key);
        if (value instanceof double[]) {
            return (double[]) value;
        } else {
            return null;
        }
    }

    public void putString(String key, String value) {
        mValues.put(key, value);
    }

    public String getString(String key, String defaultValue) {
        Object value = mValues.get(key);
        if (value instanceof String) {
            return (String) value;
        } else {
            return defaultValue;
        }
    }

    public void putStringArray(String key, String[] value) {
        mValues.put(key, value);
    }

    public String[] getStringArray(String key) {
        Object value = mValues.get(key);
        if (value instanceof String[]) {
            return (String[]) value;
        } else {
            return null;
        }
    }

    public void putPersistableBundleCompat(String key, PersistableBundleCompat value) {
        mValues.put(key, value == null ? null : value.mValues);
    }

    @SuppressWarnings("unchecked")
    public PersistableBundleCompat getPersistableBundleCompat(String key) {
        Object value = mValues.get(key);
        if (value instanceof Map) {
            return new PersistableBundleCompat((Map<String, Object>) value);
        } else {
            return null;
        }
    }

    public void clear() {
        mValues.clear();
    }

    public boolean containsKey(String key) {
        return mValues.containsKey(key);
    }

    public Object get(String key) {
        return mValues.get(key);
    }

    public boolean isEmpty() {
        return mValues.isEmpty();
    }

    public Set<String> keySet() {
        return mValues.keySet();
    }

    public void putAll(PersistableBundleCompat bundle) {
        mValues.putAll(bundle.mValues);
    }

    public void remove(String key) {
        mValues.remove(key);
    }

    public int size() {
        return mValues.size();
    }


}