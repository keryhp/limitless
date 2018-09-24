package org.uk.ubs.limitless.api;

import de.l3s.boilerpipe.BoilerpipeProcessingException;

import java.net.MalformedURLException;

public interface GrabNewsArticle {
    void extractNews(String url);
    void extractNewsArticle(String urlLink) throws BoilerpipeProcessingException, MalformedURLException;
}
