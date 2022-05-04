package com.board.domain;

public class BoardVO {
	
	private int seqno;
	private int seq;
	private String writer;
	private String title;
	private String regdate;
	private String filename;
	private String content;
	private long filesize;
	public int getSeq() {
		return seq;
	}
	
	public int getSeqno() {
		return seqno;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setSeqno(int seqno) {
		this.seqno = seqno;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public long getFilesize() {
		return filesize;
	}
	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}
	
	
	
	
}
