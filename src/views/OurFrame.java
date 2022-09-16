package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class OurFrame extends JFrame {

	public OurFrame() {
		super();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1000,800);
		this.getContentPane().setBackground(new Color(137, 216, 245));
	}

	public void dispose() {
		super.dispose();
	}

	public void display() {
		//this.pack();
		this.revalidate();
		this.repaint();
		this.setVisible(true);
	}

}
