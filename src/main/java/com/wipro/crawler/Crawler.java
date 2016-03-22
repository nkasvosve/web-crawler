package com.wipro.crawler;

import crawlercommons.fetcher.FetchedResult;
import crawlercommons.fetcher.http.BaseHttpFetcher;
import crawlercommons.fetcher.http.SimpleHttpFetcher;
import crawlercommons.fetcher.http.UserAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author nickk
 *         Crawles a web site. It prints the output to the console
 *         Does not re-invent the wheel !!
 */
public class Crawler {

    public static final UserAgent USER_AGENT = new UserAgent("none", "none@domain.com", "http://none.domain.com");

    public void crawl(String address) {

        BaseHttpFetcher fetcher = new SimpleHttpFetcher(5, USER_AGENT);
        fetcher.setRedirectMode(BaseHttpFetcher.RedirectMode.FOLLOW_NONE);

        try {
            FetchedResult fetchedResult = fetcher.get(address);
            String content = new String(fetchedResult.getContent());

            LOG.debug("Finished fetching content");
            List<String> urls = extractUrls(content);
            StringBuilder builder = new StringBuilder(PROC_ISTR);
            builder.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">").append("urls");
            for (String url : urls) {
                builder.append("<url><loc>").append(url).append("</loc></url>");
            }
            builder.append("</urlset>");

            System.out.println(builder);

        } catch (Exception e) {
            LOG.error("Error", e);
        }
    }

    /**
     * credit: http://stackoverflow.com/questions/1806017/extracting-urls-from-a-text-document-using-java-regular-expressions
     *
     * @param input
     * @return a list or urls
     */
    private List<String> extractUrls(String input) {
        List<String> result = new ArrayList<>();

        Pattern pattern = Pattern.compile(
                "\\b(((ht|f)tp(s?)\\:\\/\\/|~\\/|\\/)|www.)" +
                        "(\\w+:\\w+@)?(([-\\w]+\\.)+(com|org|net|gov" +
                        "|mil|biz|info|mobi|name|aero|jobs|museum" +
                        "|travel|[a-z]{2}))(:[\\d]{1,5})?" +
                        "(((\\/([-\\w~!$+|.,=]|%[a-f\\d]{2})+)+|\\/)+|\\?|#)?" +
                        "((\\?([-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)" +
                        "(&(?:[-\\w~!$+|.,*:]|%[a-f\\d{2}])+=?" +
                        "([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)*)*" +
                        "(#([-\\w~!$+|.,*:=]|%[a-f\\d]{2})*)?\\b");

        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result;
    }

    private static final Logger LOG = LoggerFactory.getLogger(Crawler.class);
    private static final String PROC_ISTR = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
}
