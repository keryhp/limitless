# 2018-UK-limitless-limitless
http server start:
limitless-ui\src\main\app>python -m http.server

# Welcome to Limitless!

This sample proof of concept hackathon project provides a way of intelligently analyzing news information and explaining why something progressed as expected or deviated. This is an explanatory engine which could be used to explain the P&L, Client Portfolio Position etc.

# The Concept

The concept involves the use of twitter and rss feed information, and structuring the information through the use of NLP and modeling the system to keep learning. The output of this categorized data is used to tag across the behavior of certain trades/positions which the consumer might be interested in. We can breakdown the information at various levels - geographic/sector based, for example.

## Technology Stack

**Spring-boot**
**Java, REST, JSON**
**Stanford NLP Core**
**Web2.0 and Javascript**

## APIS
http://localhost:8080/fetchItems
Fetches the country and the corresponding news items in store

http://localhost:8080/fetchAllFeeds
Fetches all the available feeds info

http://localhost:8080/fetchSpecificFeed?key=1  (onwards for twitter)
http://localhost:8080/fetchSpecificFeed?key=1001 (1001 onwards for RSS Feeds)
Fetches the feed by id


## Sample BBC RSS Feed 
http://feeds.bbci.co.uk/news/business/rss.xml
https://www.bbc.co.uk/news/business-45626881

## HTML Content extractor
https://github.com/kohlschutter/boilerpipe

## Stanford Core NLP Home
https://nlp.stanford.edu/software/

http://corenlp.run/
http://nlp.stanford.edu:8080/corenlp/
https://interviewbubble.com/getting-started-with-stanford-corenlp-a-stanford-corenlp-tutorial/

## Summarizer API sample
http://api.smmry.com/&SM_API_KEY=057B55ED57&SM_URL=https://www.bbc.co.uk/news/business-45626881

https://github.com/lipiji/App-DL#text-summarization
