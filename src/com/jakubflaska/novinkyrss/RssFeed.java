package com.jakubflaska.novinkyrss;

import java.sql.Date;


public class RssFeed {
	String iFeedTitle;
	String iFeedLink;
	String iFeedContent;
	Date iPublishedDate;
	public String getiFeedTitle() {
		return iFeedTitle;
	}
	public void setiFeedTitle(String iFeedTitle) {
		this.iFeedTitle = iFeedTitle;
	}
	public String getiFeedLink() {
		return iFeedLink;
	}
	public void setiFeedLink(String iFeedLink) {
		this.iFeedLink = iFeedLink;
	}
	public String getiFeedContent() {
		return iFeedContent;
	}
	public void setiFeedContent(String iFeedContent) {
		this.iFeedContent = iFeedContent;
	}
	public Date getiPublishedDate() {
		return iPublishedDate;
	}
	public void setiPublishedDate(Date iPublishedDate) {
		this.iPublishedDate = iPublishedDate;
	}
}
