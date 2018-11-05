package com.fatheroctober.urlshortener.core.operation;

import com.fatheroctober.urlshortener.core.UrlNotFoundException;
import com.fatheroctober.urlshortener.dao.Dao;
import com.fatheroctober.urlshortener.dao.model.IUrl;
import com.fatheroctober.urlshortener.dao.model.IUrlFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Configuration
public class ShortenerOperationRegistry {
    private static Logger logger = LoggerFactory.getLogger(ShortenerOperationRegistry.class);

    @Autowired
    private Dao<IUrl> dao;

    @Autowired
    private IUrlFactory urlFactory;

    @Bean
    public IShortenUrlOperation shortenUrlOperation() {
        return (localUrl, longUrl) -> {
            logger.info("Shortening {}", longUrl);
            String sanitizedLong = sanitizeURL(longUrl);
            Long id = dao.save(urlFactory.create(sanitizedLong));
            String uniqueId = IDConverterUtil.createUniqueID(id);
            return formatLocalURLFromShortener(localUrl) + uniqueId;
        };
    }

    @Bean
    public IGetOriginalLongUrlOperation getOriginalLongUrlOperation() {
        return shortUrlId -> {
            logger.info("Get original long URL for the short one {}", shortUrlId);
            Long dictionaryKey = IDConverterUtil.getDictionaryKeyFromUniqueID(shortUrlId);
            IUrl original = dao.get(dictionaryKey).orElseThrow(() -> UrlNotFoundException.generateFor(shortUrlId));
            return original.url();
        };
    }

    private static String formatLocalURLFromShortener(String localURL) {
        String[] addressComponents = localURL.split("/");
        return localURL.substring(0, localURL.length() - addressComponents[addressComponents.length - 1].length());
    }

    private static String sanitizeURL(String url) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("URL must be not empty");
        }
        if (url.substring(0, 7).equals("http://"))
            url = url.substring(7);

        if (url.substring(0, 8).equals("https://"))
            url = url.substring(8);

        if (url.charAt(url.length() - 1) == '/')
            url = url.substring(0, url.length() - 1);
        return url;
    }


}
