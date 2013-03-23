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
	
	// Ʈ���� ������ Ŭ���� ..
	private TrayIconApp m_trayIconApp;
	
	public TrayIconFrame(String strWindowName)
	{
		super(strWindowName);
				
		init();
		start();
		
		initFrame(750, 550);
	
		// Ʈ���� ������ �ν��Ͻ� ���� ..
		m_trayIconApp = new TrayIconApp(this, "JDIC_TrayIcon");
	}
	
	// TrayIconFrame�� �����츦 �ı��ϴ� �ڵ带 ������ �ʾұ� ������ �������� 'X' ��ư�� �����ų� Alt+F4Ű�� ������ �����찡 ȭ�鿡��
	// ������� ���μ��� ��ü�� �ı����� �ʽ��ϴ�. �̴� 'X' ��ư�� �����ų� Alt+F4Ű�� �������� �����찡 ȭ�鿡�� ������� �Ͽ� �۾�ǥ���ٿ�
	// Ʈ���̾����ܸ� ���̰� �ϴ� ȿ���� ���� ���� ���Դϴ�.
	// ���� �����츦 �ı� ��Ű�� ���� ������� �޴��� Quit�� TrayIconAppŬ������ �˾� �޴��� Quit�� �ִµ� �̶� �� �޴��� �̺�Ʈ �ڵ鷯����
	// �� �Լ��� ȣ���� �ݴϴ�. ^^
	protected void exitApp()
    {
		dispose();
		System.exit(0);
	}
	
	
	// TrayIconFrame�� �� ������Ʈ�� �ʱ�ȭ �մϴ�.
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
	
	// �޴��� �����մϴ�.
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
	
	// TrayIconFrame�� ����� �����ϰ� ȭ���� �߾ӿ� ��Ÿ������ �մϴ�. 
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
	
	// �� �÷����� �ش��ϴ� ������� ǥ���մϴ�. �� �Լ��� static���� ���� ������ ���� Ŭ������ JFrame�� ��ӹް� �ִµ� �� Ŭ������ �ν��Ͻ�ȭ �Ǳ�
	// ���� �� �Լ��� ȣ���� �־�� ������� ����Ǳ� �����Դϴ�.
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

