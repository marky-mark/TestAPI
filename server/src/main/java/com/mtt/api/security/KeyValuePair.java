package com.mtt.api.security;

/**
 * Immutable tuple class
 * @param <K> key type
 * @param <V> value type
 */
public final class KeyValuePair<K, V> {
    private K key;
    private V value;
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }
    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }
}
