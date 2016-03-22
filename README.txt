
This is a very, very simple web crawler.

It does not re-invent the wheel, but used tried and tested libraries from google commons

It uses a regular expression to extract links within the page. This expression will cover most

use cases, bit given the short time I allocated to this exercise, I cannot guarantee that

it will cover 100% of use cases. Other approaches can be used to extract links from the page

content as well.

To run, type:

mvn clean install exec:java -Dexec.mainClass="com.wipro.crawler.Launcher"
