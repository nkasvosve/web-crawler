package com.wipro.crawler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mortbay.http.HttpHandler;
import org.mortbay.http.HttpServer;

/**
 * @author nickk
 */
public class CrawlerTest {

    private SimulationWebServer _webServer;

    @Test
    public void showCrawlerFetchesContent() throws Exception{
        startServer(new FileResponseHandler(), 9000);

        Crawler crawler = new Crawler();
        String siteMap = crawler.crawl("http://localhost:9000/");
        stopServer();
        org.junit.Assert.assertNotNull(siteMap);

        org.junit.Assert.assertTrue(siteMap.contains("<urlset"));
        org.junit.Assert.assertTrue(siteMap.contains("<loc>https://api.w.org</loc>"));
        org.junit.Assert.assertTrue(siteMap.contains("<url><loc>http://wiprodigital.com/wp-json</loc></url>"));
        org.junit.Assert.assertTrue(siteMap.contains("</urlset>"));
    }

    @Before
    public void setUp() throws Exception {
        _webServer = new SimulationWebServer();
    }

    @After
    public void shutDown() throws Exception {
        _webServer.stopServer();
    }

    private void startServer(HttpHandler handler, int port) throws Exception {
        _webServer.startServer(handler, port);
    }

    private void stopServer() throws InterruptedException {
        _webServer.stopServer();
    }

    private HttpServer getServer() {
        return _webServer.getServer();
    }

}
