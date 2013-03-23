package org.nsyang.web;

import java.util.Vector;

public class LinkList {
	Vector<LinkInfo> list = new Vector<LinkInfo>();
	
	public void add(LinkInfo link){
		list.add(link);
	}
	
	public LinkInfo get(int i){
		return (LinkInfo)list.get(i);
	}
	
	public int size(){
		return list.size();
	}
	
	public void print(){
		for(LinkInfo l:list){
			System.out.println(l.text+"   "+l.href);
		}
	}
}
