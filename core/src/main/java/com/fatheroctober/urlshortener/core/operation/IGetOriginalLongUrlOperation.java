package com.fatheroctober.urlshortener.core.operation;

public interface IGetOriginalLongUrlOperation {
    String longUrl(String shortUrlId);
}
