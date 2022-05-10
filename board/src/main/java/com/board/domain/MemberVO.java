package com.board.domain;

public class MemberVO {
	
	private String userid;
	private String username;
	private String userpassword;
	private String telno;
	private String email;
    private String regdate;
    private String authority_code;
    private String lastlogindate;
    private String pwmodidate;
    private String pwwaitdate;
    private String org_img;
    private String stored_img;
    private Long img_size;

    public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpassword() {
		return userpassword;
	}
	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getLastlogindate() {
		return lastlogindate;
	}
	public void setLastlogindate(String lastlogindate) {
		this.lastlogindate = lastlogindate;
	}
	public String getPwmodidate() {
		return pwmodidate;
	}
	public void setPwmodidate(String pwmodidate) {
		this.pwmodidate = pwmodidate;
	}
	public String getPwwaitdate() {
		return pwwaitdate;
	}
	public void setPwwaitdate(String pwwaitdate) {
		this.pwwaitdate = pwwaitdate;
	}
	public String getOrg_img() {
		return org_img;
	}
	public void setOrg_img(String org_img) {
		this.org_img = org_img;
	}
	public String getStored_img() {
		return stored_img;
	}
	public void setStored_img(String stored_img) {
		this.stored_img = stored_img;
	}
	public Long getImg_size() {
		return img_size;
	}
	public void setImg_size(Long img_size) {
		this.img_size = img_size;
	}
	public String getAuthority_code() {
		return authority_code;
	}
	public void setAuthority_code(String authority_code) {
		this.authority_code = authority_code;
	}
    
}
