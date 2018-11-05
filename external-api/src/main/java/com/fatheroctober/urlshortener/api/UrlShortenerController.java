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

    @GetMapping("/{shortUrlID}")
    @ResponseBody
    public RedirectView forward(@PathVariable String shortUrlID) {
        String originalLong = shortenedUrlService.originalLongUrl(shortUrlID);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://" + originalLong);
        return redirectView;
    }


    @PostMapping("/shorten")
    public URL shorten(@Valid @RequestBody URL longURL, HttpServletRequest request) {
        String localURL = request.getRequestURL().toString();
        String shortUrl = shortenedUrlService.shorten(localURL, longURL.getValue());
        return new URL().value(shortUrl);
    }
}
