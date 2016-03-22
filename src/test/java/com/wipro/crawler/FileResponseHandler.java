package com.wipro.crawler;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.mortbay.http.HttpException;
import org.mortbay.http.HttpRequest;
import org.mortbay.http.HttpResponse;
import org.mortbay.http.handler.AbstractHttpHandler;

import java.io.FileInputStream;
import java.io.IOException;

public class FileResponseHandler extends AbstractHttpHandler {

    @Override
    public void handle(String pathInContext, String pathParams, HttpRequest request,
                       HttpResponse response) throws HttpException, IOException {

        response.setStatus(HttpStatus.SC_OK);
        response.setContentType("text/plain");

        String content = IOUtils.toString(getClass().getResourceAsStream("/canned-response.txt"));

        response.setContentLength(content.length());
        response.getOutputStream().write(content.getBytes("UTF-8"));
    }
}
