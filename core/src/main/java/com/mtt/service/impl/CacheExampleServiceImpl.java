package com.mtt.service.impl;

import com.googlecode.ehcache.annotations.Cacheable;
import com.mtt.service.CacheExampleService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CacheExampleServiceImpl implements CacheExampleService
{
    @Cacheable(cacheName="cacheStringsMethod", selfPopulating = true)
    @Override
    public List<String> cacheStrings() {
        List<String> cachedStrings = new ArrayList<String>();

        for (int i =0; i < 100; i++) {
            cachedStrings.add("cached value " + i);
        }

        return cachedStrings;
    }
}
