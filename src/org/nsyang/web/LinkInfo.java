package org.nsyang.web;

public class LinkInfo {
	String href="";
	String text="";
	String date="";

	String etc1="";
	String etc2="";
	String etc3="";
	String etc4="";
	
	public LinkInfo(String href,String text) {
		this.href=href;
		this.text=text;
	}
	
	public String getHref(){
		return href;
	}
}
