package org.nsyang.computer_server;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.Image;

// 이 녀석들이 SE 6.0에서 제공하는 트레이 아이콘 관련 클래스 들입니다. 
import java.awt.SystemTray;
import java.awt.TrayIcon;


public class TrayIconApp implements ActionListener
{
	// SystemTray클래스를 얻어옵니다.
	private SystemTray m_tray = SystemTray.getSystemTray();
    private TrayIcon m_ti;

    private JFrame m_frame;
    
    String m_strTrayTitle;
    
    public TrayIconApp(JFrame frame, String strTrayTitle)
    {
    	m_frame = frame;
    	m_strTrayTitle = strTrayTitle;
    	
    	initTray();
    }
    
    // 트레이 아이콘의 초기설정을 해줍니다.
    private void initTray()
    {
    	// 트레이 아이콘의 아이콘 역할을 할 이미지 입니다. 
    	Image image = Toolkit.getDefaultToolkit().getImage("images/duke.gif");
    	
    	// TrayIcon을 생성합니다.
    	m_ti = new TrayIcon(image, m_strTrayTitle, createPopupMenu());
    	
    	m_ti.setImageAutoSize(true);
    	// 트레이 아이콘 자체를 클릭했을때 일어날 이벤트에 대한 동작을 구현합니다. 현재 동작은 TrayIconFrame 윈도우가 숨겨져 있으면 
    	// 보여주고, 나타나 있으면 숨겨줍니다 :)
    	m_ti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	m_frame.setVisible(!m_frame.isVisible());
            }
        });
        
    	// 위에서 얻어온 SystemTray에 방금 막 생성한 TrayIcon의 인스턴스를 인자로 넣어주면 완성 ~~
    	try 
    	{
			m_tray.add(m_ti);
		} 
    	catch (AWTException e1) 
    	{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    }
    
    // 트레이 아이콘에서 사용할 팝업 매뉴를 만듭니다.
    private PopupMenu createPopupMenu()
    {
        PopupMenu popupMenu = new PopupMenu("My Menu");
        
        MenuItem miShow = new MenuItem("Show TrayIconFrame");
        MenuItem miQuit = new MenuItem("Quit");
        miShow.addActionListener(this);
        miQuit.addActionListener(this);
        
        popupMenu.add(miShow);
        popupMenu.addSeparator();
        popupMenu.add(miQuit);
        
        return popupMenu;
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getActionCommand() == "Show TrayIconFrame")
    	{
    		// 여기서는 숨겨두었던 TrayIconFrame 윈도우를 보여줍니다.
    		m_frame.setVisible(true);
    	}
    	else if(e.getActionCommand() == "Quit")
    	{
    		// 여기서 TrayIconFrame에 만들어 두었던 exitApp()함수를 호출하고 있습니다.
    		((TrayIconFrame) m_frame).exitApp();
    	}
    }
}
