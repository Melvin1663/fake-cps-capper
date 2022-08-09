package dev.melvin.cpscap;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.formdev.flatlaf.*;

public class Main {
	public static int maxCps = 15;
	public static boolean isCapping = true;
	public static int currentCps = 0;
	public static int actualCps = 0;
	public static long click1 = 0L;
	public static boolean isPressed = false;
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new FlatDarkLaf());
		new Frame();
	} 
}
