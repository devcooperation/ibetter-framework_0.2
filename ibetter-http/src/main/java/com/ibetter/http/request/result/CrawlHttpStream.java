package com.ibetter.http.request.result;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class CrawlHttpStream {
	
	private InputStream in;
	private String fileType;
	
	public InputStream getIn() {
		return in;
	}
	public void setIn(InputStream in) {
		this.in = in;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public CrawlHttpStream() {
		super();
	}
	public CrawlHttpStream(InputStream in, String fileType) {
		super();
		this.in = in;
		this.fileType = fileType;
	}
	
	public void close(){
		IOUtils.closeQuietly(in);
	}
}
