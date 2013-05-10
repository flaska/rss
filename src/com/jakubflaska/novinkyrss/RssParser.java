package com.jakubflaska.novinkyrss;

import java.io.StringReader;
import java.sql.Date;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RssParser {
	String iXmlInputFileContent;
	ArrayList<RssFeed> iOutputRssFeeds = new ArrayList<RssFeed>(); 
	ArrayList<RssFeed> iFailedFeeds = new ArrayList<RssFeed>(); 
	Integer iMinFeedFieldLength = 10;
	public RssParser(String content){
		this.iXmlInputFileContent = content;
	}
	public void setXmlInputFile(String content) {
		this.iXmlInputFileContent = content;
	}
	public boolean parseRssInputFile(){
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			DefaultHandler handler = new DefaultHandler(){
				boolean parsingItem = false;
				boolean parsingTitle = false;
				boolean parsingContent = false;
				boolean parsingLink = false;
				boolean parsingDate = false;
				RssFeed iActualFeed;
				public void startElement(String uri, String localName,String qName, Attributes attributes) throws SAXException {
					if (qName.equalsIgnoreCase("item")) {
						parsingItem = true;
						iActualFeed = new RssFeed();
					}
					if (qName.equalsIgnoreCase("title")&&parsingItem) {
						parsingTitle = true;
					}
					if (qName.equalsIgnoreCase("description")&&parsingItem) {
						parsingContent = true;
					}	
					if (qName.equalsIgnoreCase("link")&&parsingItem) {
						parsingLink = true;
					}					
				}
				public boolean testFeed(RssFeed feed){
					if (feed.getiFeedTitle()==null
						||feed.getiFeedContent()==null
						||feed.getiFeedLink()==null)
						return false;
					if (feed.getiFeedTitle().length()<iMinFeedFieldLength
						|| feed.getiFeedContent().length()<iMinFeedFieldLength	
						|| feed.getiFeedLink().length()<iMinFeedFieldLength){
						System.out.print("Feed too short.");
						return false;
					}
					if (!feed.getiFeedLink().startsWith(new String("http://")))
						return false;
					return true;
				}
				public void endElement(String uri, String localName, String qName) throws SAXException {			 
					if (qName.equalsIgnoreCase("item")) {
						if (testFeed(iActualFeed))
							iOutputRssFeeds.add(iActualFeed);
						else 
							iFailedFeeds.add(iActualFeed);
						iActualFeed = null;
					}
					if (qName.equalsIgnoreCase("title")&&parsingItem) {
						parsingTitle = false;
					}						
					if (qName.equalsIgnoreCase("description")&&parsingItem) {
						parsingContent = false;
					}	
					if (qName.equalsIgnoreCase("link")&&parsingItem) {
						parsingLink = false;
					}					
				}	
				public void characters(char ch[], int start, int length) throws SAXException {
					String cropped = new String(ch,start,length);
					if (parsingTitle){
						if (iActualFeed.getiFeedTitle()==null){
							iActualFeed.setiFeedTitle(cropped);
						} else {
							StringBuffer cont = new StringBuffer(iActualFeed.getiFeedTitle());
							cont.append(cropped);
							iActualFeed.setiFeedTitle(cont.toString());
						}
					}					
					if (parsingContent){
						if (iActualFeed.getiFeedContent()==null){
							iActualFeed.setiFeedContent(cropped);
						} else {
							StringBuffer cont = new StringBuffer(iActualFeed.getiFeedContent());
							cont.append(cropped);
							iActualFeed.setiFeedContent(cont.toString());
						}
					}
					if (parsingLink){
						if (iActualFeed.getiFeedLink()==null){
							iActualFeed.setiFeedLink(cropped);
						} else {
							StringBuffer cont = new StringBuffer(iActualFeed.getiFeedLink());
							cont.append(cropped);
							iActualFeed.setiFeedLink(cont.toString());
						}
					}
				}				
			};			
			StringReader sr = new StringReader(iXmlInputFileContent);
			InputSource is = new InputSource(sr);
			is.setEncoding("UTF-8");			
			saxParser.parse(is,handler);			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		if (!this.iFailedFeeds.isEmpty()){
			System.out.print("Some feeds weren't successfuly loaded");
		}
		return true;
	}
	public ArrayList<RssFeed> getRssFeedsList(){
		return iOutputRssFeeds;
	}
}

