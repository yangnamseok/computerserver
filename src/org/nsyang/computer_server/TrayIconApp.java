package org.nsyang.computer_server;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.Image;

// �� �༮���� SE 6.0���� �����ϴ� Ʈ���� ������ ���� Ŭ���� ���Դϴ�. 
import java.awt.SystemTray;
import java.awt.TrayIcon;


public class TrayIconApp implements ActionListener
{
	// SystemTrayŬ������ ���ɴϴ�.
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
    
    // Ʈ���� �������� �ʱ⼳���� ���ݴϴ�.
    private void initTray()
    {
    	// Ʈ���� �������� ������ ������ �� �̹��� �Դϴ�. 
    	Image image = Toolkit.getDefaultToolkit().getImage("images/duke.gif");
    	
    	// TrayIcon�� �����մϴ�.
    	m_ti = new TrayIcon(image, m_strTrayTitle, createPopupMenu());
    	
    	m_ti.setImageAutoSize(true);
    	// Ʈ���� ������ ��ü�� Ŭ�������� �Ͼ �̺�Ʈ�� ���� ������ �����մϴ�. ���� ������ TrayIconFrame �����찡 ������ ������ 
    	// �����ְ�, ��Ÿ�� ������ �����ݴϴ� :)
    	m_ti.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	m_frame.setVisible(!m_frame.isVisible());
            }
        });
        
    	// ������ ���� SystemTray�� ��� �� ������ TrayIcon�� �ν��Ͻ��� ���ڷ� �־��ָ� �ϼ� ~~
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
    
    // Ʈ���� �����ܿ��� ����� �˾� �Ŵ��� ����ϴ�.
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
    		// ���⼭�� ���ܵξ��� TrayIconFrame �����츦 �����ݴϴ�.
    		m_frame.setVisible(true);
    	}
    	else if(e.getActionCommand() == "Quit")
    	{
    		// ���⼭ TrayIconFrame�� ����� �ξ��� exitApp()�Լ��� ȣ���ϰ� �ֽ��ϴ�.
    		((TrayIconFrame) m_frame).exitApp();
    	}
    }
}
