package com.jakubflaska.novinkyrss;

import java.io.BufferedReader;				// InputStreamReader return only one line, so you need a buffered reader to do a string
import java.io.InputStreamReader;			// URL can return an input stream reader
import java.io.UnsupportedEncodingException;
import java.net.URL; 						// Downloads a RSS file from web
import java.net.MalformedURLException; 		// Exception for URL
import java.nio.charset.Charset;
import java.io.IOException; 				// Exception for readers

import android.util.Log;

/* 
 * 
 * */

public class RssSourceFile {
	Boolean iResult = false;
	URL iSourceUrl = null;
	String iLastErrorMessage = null;
	String iXmlFileContent = null;
	String iEncoding = "UTF8";
	static String iErrorMsgInvalidUrl = new String("Cannot open provided URL.\n");
	static String iErrorMsgReading = new String("Cannot open provided URL.\n");
	/* Constructor, creates new URL object. */
	public RssSourceFile(String url, String encoding){
		try{
			iSourceUrl = new URL(url);
	    } catch (MalformedURLException e) {
	    	iResult = false;
	    	iLastErrorMessage = iErrorMsgInvalidUrl;
	    	return;
	    }
		iResult = true;
		iEncoding = encoding;
	}
	
	/* Get a Boolean describing if last action went successful */
	public Boolean getResult(){
		return iResult;
	}
	
	/* Get an error message describing possible fail */
	String getLastErrMsg(){
		return iLastErrorMessage;
	}
	
	/* Download the content of a XML file from web.
	 * */
	public Boolean DownloadXmlFileContent() {
		if (iSourceUrl == null) {
			iResult = false;
			iLastErrorMessage = iErrorMsgReading;
			return false;
		}
		InputStreamReader isr;
		BufferedReader br;
		StringBuffer output;
		try {
			isr = new InputStreamReader(iSourceUrl.openStream(),iEncoding);
			br = new BufferedReader(isr);
			output = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				output.append(line); 
			}
		} catch (IOException e) {
			iResult = false;
			iLastErrorMessage = iErrorMsgReading;
			return false;
		}
		iXmlFileContent = output.toString();
		/*if(iXmlFileContent.substring(0,100).toLowerCase().contains("windows-125")) {			
			try {
				byte[] b;
				b =	iXmlFileContent.getBytes("");
				iXmlFileContent = new String(b,"windows-1250");
			}
			catch (UnsupportedEncodingException e){}	
		}*/
		iLastErrorMessage = null;
		iResult = true;
		System.out.print("XML file successfuly readed:"+iXmlFileContent.substring(0,30)+"\n");		
		return true;
	};
	
	public String GetXmlFileContent(){
		return iXmlFileContent;
	}
}