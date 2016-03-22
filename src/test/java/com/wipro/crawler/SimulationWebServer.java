package com.wipro.crawler;

import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpHandler;
import org.mortbay.http.HttpServer;

public class SimulationWebServer {

    private HttpServer _server;

    public HttpServer startServer(HttpHandler handler, int port) throws Exception {
        _server = new HttpServer();
        _server.addListener(":" + port);
        HttpContext context = _server.getContext("/");
        context.addHandler(handler);
        _server.start();
        return _server;
    }

    public void stopServer() throws InterruptedException {
        if (_server != null) {
            _server.stop();
        }
    }

    public HttpServer getServer() {
        return _server;
    }
}
