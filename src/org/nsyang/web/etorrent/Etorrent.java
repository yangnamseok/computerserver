package org.nsyang.web.etorrent;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.nsyang.util.NSUtil;
import org.nsyang.web.LinkInfo;
import org.nsyang.web.LinkList;
import org.nsyang.web.WebBasic;

public class Etorrent extends WebBasic{
	final String BASE_URL="http://www.etorrent.co.kr/";
	final String BOARD_KEY="torrent_mana";
	
	public Etorrent() {
		login();
		LinkList linkList = new LinkList();
//		goBoard(linkList,BOARD_KEY,"상남",5);	
		goBoard(linkList,BOARD_KEY,"",5);	
		
		/*
		if(linkList.size()>0 && down(linkList.get(0).getHref())){
			System.out.println("success");				
		}else{
			System.out.println("no");
		}
		*/			
		System.out.println("close============");
		close();
	}
	
	/**
	 * 로그인
	 */
	public void login(){
		String url = "https://www.etorrent.co.kr/bbs/login_check2.php";
		String[][] params ={
				{"url", "http://www.etorrent.co.kr%2F"},
				{"mb_id", "nsyang1228"},
				{"mb_password", "yns123"},
				{"x", "0"},
				{"y", "0"}						
		};
		
		try {
			exec(METHOD.POST,url, params);
		} catch (IOException e) {
			e.printStackTrace();
		}
		print(false);
	}
	
	/**
	 * 목록 가져오기
	 * @param key
	 * @return
	 */
	//mw_basic_page
	
	public void goBoard(LinkList linkList,String board,String search,int page){
		url = "http://www.etorrent.co.kr/bbs/board.php?bo_table="+board+"&sca=&sfl=wr_subject&";
		if(search!=null){
			url+="stx="+NSUtil.getUnicode(search)+"&";
		}
		goBoard(linkList,url,page);
	}
	public void goBoard(LinkList linkList,String url,int page){
		
		System.out.println("==============================");
		System.out.println("page >> "+page+"      Url >> "+url);
		try {
			exec(METHOD.GET,url,null);
		} catch (IOException e) {
			e.printStackTrace();
		}		
		String html = print(false);
		Document doc = Jsoup.parse(html);
		Elements list = doc.select(".mw_basic_list_num");
		for(Element e: list){
			Elements elist = e.parents().parents();
			elist = elist.select(".mw_basic_list_subject").select("a[href]");
			if(elist.size()>1){
				String link = elist.get(1).attr("href");
				if(link.indexOf("wr_id")>0 && link.substring(0,2).equals("..")){
					//System.out.println("link >> "+link);
					link = BASE_URL+link.substring(3,link.length());
					String text = elist.get(1).text();
					//System.out.println("text >> "+text);
					linkList.add(new LinkInfo(link,text));					
				}
			}
		}		
		list = doc.select(".mw_basic_page");
		System.out.println("html >> "+list.html());
		list = list.select("a[href]");
		page--;
		if(list.size()>0 && page>0){
			String link;
			if(list.size()==1)link=list.get(0).attr("href");
			else link=list.get(1).attr("href");
			String tempUrl = BASE_URL+"bbs"+link.substring(1,link.length());
			System.out.println("==============================");
			goBoard(linkList,tempUrl,page);
		}
	}
	
	/**
	 * 파일 가져오기
	 * @param url
	 * @return
	 */
	public boolean down(String url){
		boolean isresult=false;
		System.out.println(url);
		String wr_id = NSUtil.getUrlToParamValue(url,"wr_id");

		//System.out.println(wr_id);
		//System.out.println(url);
		try {
			exec(METHOD.GET,url,null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String html = print(false);
		Document doc = Jsoup.parse(html);		
		Elements list = doc.select(".mw_basic_view_file a[href]");
		for(Element e: list){
			String link = e.attr("href");
			String[] downlink = link.split("\'");
			if(downlink.length>1){
				String downurl = BASE_URL+"bbs"+downlink[1].substring(1, downlink[1].length());
				String downname = downlink[3];
				
				System.out.println("download >> "+downname);
				
				try {
					exec(METHOD.GET,downurl,null);
				} catch (IOException ex) {
					ex.printStackTrace();
				}

				download(downname);
				isresult=true;
			}
		}
		return isresult;
	}
	public static void main(String argv[]){
		new Etorrent();
	}
}
