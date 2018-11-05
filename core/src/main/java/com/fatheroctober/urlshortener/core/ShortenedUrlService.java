package com.fatheroctober.urlshortener.core;

import com.fatheroctober.urlshortener.core.operation.IGetOriginalLongUrlOperation;
import com.fatheroctober.urlshortener.core.operation.IShortenUrlOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortenedUrlService implements IShortenedUrlService {

    @Autowired
    private IShortenUrlOperation shortenUrlOperation;

    @Autowired
    private IGetOriginalLongUrlOperation getOriginalLongUrlOperation;

    @Override
    public String shorten(String localUrl, String longUrl) {
        return shortenUrlOperation.shorten(localUrl, longUrl);
    }

    @Override
    public String originalLongUrl(String shortUrl) {
        return getOriginalLongUrlOperation.longUrl(shortUrl);
    }
}
