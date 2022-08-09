package dev.melvin.cpscap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.mouse.NativeMouseEvent;
import com.github.kwhat.jnativehook.mouse.NativeMouseInputListener;

public class Frame extends JFrame implements ActionListener, NativeMouseInputListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageIcon icon = new ImageIcon(Main.class.getResource("/assets/icon.png"));
	Font font = new Font("Segoe UI Semibold", Font.PLAIN, 15);
	
	SpinnerModel model = new SpinnerNumberModel(15, 0, 1000, 1);
	JSpinner cps = new JSpinner(model);
	JLabel currCps = new JLabel("Current Cap: " + Main.maxCps);
	JLabel enterCps = new JLabel("Enter Max CPS");
	JLabel displayCps = new JLabel("CPS: " + Main.actualCps);
	JLabel cpsInfo = new JLabel("(Click anywhere)");
	JButton capIt = new JButton();
	JButton apply = new JButton();
	
	Timer counter = new Timer();

	Frame() {
		enterCps.setBounds(20, 3, 300, 20);
		enterCps.setFont(font);
		
		displayCps.setBounds(20, 43, 300, 20);
		displayCps.setFont(font);
		
		cpsInfo.setBounds(120, 43, 300, 20);
		cpsInfo.setFont(font);
		
		counter.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run(){
				if (Main.actualCps > Main.maxCps) Main.actualCps = Main.maxCps;
			     displayCps.setText("CPS: " + Main.actualCps);
			}
		}, 0, 1000);
		
		currCps.setBounds(20, 23, 300, 20);
		currCps.setFont(font);
		
		cps.setBounds(130, 5, 100, 20);
		cps.setFont(font);
		cps.setFocusable(false);
		
		capIt.setBounds(22, 69, 100, 25);
		capIt.setText("Cap it!");
		capIt.setFocusable(false);
		capIt.addActionListener(this);
		capIt.setFont(font);
		
		apply.setBounds(127, 69, 100, 25);
		apply.setText("Apply");
		apply.setFocusable(false);
		apply.setFont(font);
		apply.addActionListener(this);
		
		this.setTitle("CPS Capper");
		this.setSize(270, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setIconImage(icon.getImage());
		this.setLayout(null);
		this.add(cps);
		this.add(enterCps);
		this.add(currCps);
		this.add(displayCps);
		this.add(cpsInfo);
		this.add(capIt);
		this.add(apply);
		this.setVisible(true);
		
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
	
	public void paint(Graphics g) {
		super.paint(g);
		if (!Main.isPressed) g.setColor(Color.GRAY);
		else g.setColor(Color.WHITE);
		g.fillRect(25,138,215,90);
	}
	
	public void nativeMousePressed(NativeMouseEvent e) {
		if (Main.isCapping == false) return;
		
		Main.currentCps++;
		Main.isPressed = true;
		repaint();
	}
	
	public void nativeMouseReleased(NativeMouseEvent e) {
		Main.isPressed = false;
		repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object button = e.getSource();
		
		if (button == capIt) {
			if (capIt.getText().equals("Cap it!")) {
//				Main.isCapping = true;
				capIt.setText("Uncap it!");
			} else if (capIt.getText().equals("Uncap it!")) {
//				Main.isCapping = false;
				capIt.setText("Cap it!");
			}
		} 
		if (button == apply) {
			int newCps = (int) cps.getValue();
			if (newCps > 0 && newCps < 1001) {
				Main.maxCps = newCps;
				currCps.setText("Current Cap: " + String.valueOf(newCps));
			} else {
				JOptionPane.showMessageDialog(this, "Max CPS value should be 1-1000");
			}
		}
	}

}
