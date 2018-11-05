package com.fatheroctober.urlshortener.dao.model.impl;

import com.fatheroctober.urlshortener.dao.model.IUrl;
import com.fatheroctober.urlshortener.dao.model.IUrlFactory;
import org.springframework.stereotype.Service;

@Service
public class RedisUrlFactory implements IUrlFactory {

    @Override
    public IUrl create(String value) {
        return new RedisUrl(value);
    }
}
