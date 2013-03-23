package org.nsyang.util.hardward_controller;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.image.BufferedImage;

public class Hardward_controller {
	enum Mouse{
		LEFT,
		CENTER,
		RIGHT
	};
	
	// 하드웨어 지연 시간
	final int MS = 10;
	
	Robot robot;
	Rectangle area;
	
	public Hardward_controller() {
		try {
			robot = new Robot();
			robot.setAutoDelay(MS);
			area = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
	public void type(String s){
	    byte[] bytes = s.getBytes();
	    for (byte b : bytes){
	      int code = b;
	      // keycode only handles [A-Z] (which is ASCII decimal [65-90])
	      if (code > 96 && code < 123) code = code - 32;
	      robot.delay(40);
	      robot.keyPress(code);
	      robot.keyRelease(code);
	    }
	}
	
	public void putKey(int keycode){
		robot.keyPress(keycode);
		robot.keyRelease(keycode);
	}
	
	public void click(Mouse mouse){
		switch (mouse) {
		case LEFT:			
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			break;
		case CENTER:			
			robot.mousePress(InputEvent.BUTTON2_MASK);
			robot.mouseRelease(InputEvent.BUTTON2_MASK);
			break;
		case RIGHT:			
			robot.mousePress(InputEvent.BUTTON3_MASK);
			robot.mouseRelease(InputEvent.BUTTON3_MASK);
			break;			
		default:
			break;
		}
	}
	
	public void move(int x,int y){
		robot.mouseMove(x,y);
	}
	
	public void move(Point p){
		move(p.x,p.y);
	}
	
	public void drag(Point sP,Point eP){
		move(sP);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		move(eP);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public BufferedImage capture(){
		return robot.createScreenCapture(area);
	}
	
	public BufferedImage capture(int x,int y,int width,int height){
		return robot.createScreenCapture(new Rectangle(x,y,width,height));
	}
	
	public void delay(){
		delay(MS);
	}

	public void delay(int ms){
		robot.delay(ms);
	}

	public Color getPixcelColor(int x,int y){
		return robot.getPixelColor(x, y);
	}
}
