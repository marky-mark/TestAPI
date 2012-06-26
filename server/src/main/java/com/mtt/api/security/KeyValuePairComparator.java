package com.mtt.api.security;

import java.util.Comparator;

/**
 * Comparator that orders both on the key and value
 */
public class KeyValuePairComparator implements Comparator<KeyValuePair<String, String>> {
    @Override
    public final int compare(KeyValuePair<String, String> a, KeyValuePair<String, String> b) {
        int result = a.getKey().compareTo(b.getKey());
        if (result == 0) {
            result = a.getValue().compareTo(b.getValue());
        }
        return result;
    }

    @Override
    public final boolean equals(Object o) {
        return this == o;
    }
}
