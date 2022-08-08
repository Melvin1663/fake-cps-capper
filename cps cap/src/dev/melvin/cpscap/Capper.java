package dev.melvin.cpscap;

import java.util.Timer;
import java.util.TimerTask;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class Capper implements NativeMouseInputListener {
	Timer counter = new Timer();
	
	Capper () {
		try {
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex) {
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());

			System.exit(1);
		}
		
		GlobalScreen.addNativeMouseListener(this);
		GlobalScreen.addNativeMouseMotionListener(this);
		
		counter.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
			     if (Main.isCapping == false) return;  
			     Main.actualCps = Main.currentCps;
			     Main.currentCps = 0;
			}
		}, 0, 1000);
	}
	
	public void nativeMousePressed(NativeMouseEvent e) {
		if (Main.isCapping == false) return;
		
		Main.currentCps++;
//		System.out.println("Mouse Pressed: " + Main.actualCps);
	}

//	public void nativeMouseClicked(NativeMouseEvent e) {
//		System.out.println("Mouse Clicked: " + e.getButton());
//	}

//	public void nativeMouseReleased(NativeMouseEvent e) {
//		System.out.println("Mouse Released: " + e.getButton());
//	}

//	public void nativeMouseMoved(NativeMouseEvent e) {
//		System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
//	}

//	public void nativeMouseDragged(NativeMouseEvent e) {
//		System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
//	}
}
