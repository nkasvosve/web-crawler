package com.wipro.crawler;

/**
 * @author nickk
 *
 * Launches a web crawler
 */
public class Launcher {

    public static void main(String[] args) {

        Crawler crawler = new Crawler();
        String siteMap = crawler.crawl("http://wiprodigital.com");
        System.out.println(siteMap);
    }
}
