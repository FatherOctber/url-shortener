package com.fatheroctober.urlshortener.dao.model.impl;

import com.fatheroctober.urlshortener.dao.model.IUrl;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * Url aggregator for Redis storage.
 */
@EqualsAndHashCode
@ToString
public class RedisUrl implements IUrl {
    @Getter
    @Setter
    private String value;

    public RedisUrl() {
    }

    public RedisUrl(String value) {
        this.value = value;
    }

    @Override
    public String url() {
        return value;
    }
}
