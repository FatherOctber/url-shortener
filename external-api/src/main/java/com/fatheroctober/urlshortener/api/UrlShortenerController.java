package com.fatheroctober.urlshortener.api;

import com.fatheroctober.urlshortener.core.IShortenedUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/shorturl/v1", produces = "application/json")
public class UrlShortenerController {

    @Autowired
    private IShortenedUrlService shortenedUrlService;

    @GetMapping("/{shortUrl}")
    @ResponseBody
    public RedirectView forward(@PathVariable String shortUrl) {
        String originalLong = shortenedUrlService.originalLongUrl(shortUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + originalLong);
        return redirectView;
    }


    @PostMapping("/shorten")
    public ShortURL shorten(@Valid @RequestBody LongURL longURL, HttpServletRequest request) {
        String localURL = request.getRequestURL().toString();
        String shortUrl = shortenedUrlService.shorten(localURL, longURL.getValue());
        return new ShortURL().value(shortUrl);
    }
}
