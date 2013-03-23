package org.nsyang.computer_server;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class TrayIconFrame extends JFrame
{
	private static final long serialVersionUID = 1L;

	private Container m_con;

	private static JTextArea m_taConsole = new JTextArea();
	private JScrollPane m_jsp = new JScrollPane(m_taConsole);
	
	private JButton m_btHide = new JButton("Hide");
	
	// 트레이 아이콘 클래스 ..
	private TrayIconApp m_trayIconApp;
	
	public TrayIconFrame(String strWindowName)
	{
		super(strWindowName);
				
		init();
		start();
		
		initFrame(750, 550);
	
		// 트레이 아이콘 인스턴스 생성 ..
		m_trayIconApp = new TrayIconApp(this, "JDIC_TrayIcon");
	}
	
	// TrayIconFrame은 윈도우를 파괴하는 코드를 만들지 않았기 때문에 윈도우의 'X' 버튼을 누르거나 Alt+F4키를 눌러도 윈도우가 화면에서
	// 사라질뿐 프로세스 자체가 파괴되지 않습니다. 이는 'X' 버튼을 누르거나 Alt+F4키를 눌렀을때 윈도우가 화면에서 사라지게 하여 작업표시줄에
	// 트레이아이콘만 보이게 하는 효과를 내기 위한 것입니다.
	// 실제 윈도우를 파괴 시키기 위한 방법으로 메뉴의 Quit와 TrayIconApp클래스의 팝업 메뉴의 Quit가 있는데 이때 각 메뉴의 이벤트 핸들러에서
	// 이 함수를 호출해 줍니다. ^^
	protected void exitApp()
    {
		dispose();
		System.exit(0);
	}
	
	
	// TrayIconFrame의 각 콤포넌트를 초기화 합니다.
	private void init()
	{
		setJMenuBar(createMenuBar());
		
		m_con = getContentPane();
		m_con.setLayout(new BorderLayout());
			
		JPanel jPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		m_btHide.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				setVisible(!isVisible());
			}
		});
		
		jPanel.add(m_btHide);
		
		m_taConsole.setBorder(new BevelBorder(BevelBorder.LOWERED));
		
		m_con.add("Center", m_jsp);
		m_con.add("South", jPanel);
	}
	
	// 메뉴를 생성합니다.
	private JMenuBar createMenuBar()
	{
		JMenuBar jmb = new JMenuBar();

		JMenu mnuFile = new JMenu("File");

		JMenuItem jmiQuit = new JMenuItem("Quit");
		jmiQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				exitApp();
			}
		});
		
		mnuFile.add(jmiQuit);
		jmb.add(mnuFile);
		return jmb;
	}
	
	private void start()
	{
	}
	
	// TrayIconFrame의 사이즈를 설정하고 화면의 중앙에 나타나도록 합니다. 
	private void initFrame(int width, int height)
	{
		if(0 == width || 0 == height)
		{
			pack();	
		}
		else
		{
			setSize(width, height);
		}
		
		ImageIcon im = new ImageIcon("images/74120-1.jpg");
		setIconImage(im.getImage());
		
		Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameDimension = getSize();
		
		int iXpos = (int)(screenDimension.getWidth() / 2 - frameDimension.getWidth() / 2);
		int iYpos = (int)(screenDimension.getHeight() / 2 - frameDimension.getWidth() / 2);
		
		setLocation(iXpos, iYpos);
		setVisible(true);
	}
	
	// 각 플랫폼에 해당하는 룩앤필을 표현합니다. 이 함수를 static으로 만든 이유는 현재 클래스가 JFrame을 상속받고 있는데 이 클래스가 인스턴스화 되기
	// 전에 이 함수를 호출해 주어야 룩앤필이 적용되기 때문입니다.
	public static void setComponentLookAndFeel() 
    {
	    try 
	    {
	      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	    } 
    }
}

