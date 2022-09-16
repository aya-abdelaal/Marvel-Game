package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

public class Startup extends OurFrame{
	private JLabel label;
	private ImageIcon image;
	private ImageIcon logoimage;//
	private JButton button;

	public Startup() {
		this.setLayout(new FlowLayout());
		button = new JButton(); // this.setResizable(true);
		label = new JLabel();
		label.setVerticalAlignment(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		image = new ImageIcon("resources/cutepic.png");
		logoimage = new ImageIcon("resources/marvellogo.png");
		
		this.setTitle("Marvel Ultimate War");
		this.getContentPane().setBackground(new Color(0,0,0));
		this.setIconImage(logoimage.getImage());
		label.setIcon(image);
		this.add(label);
		button.setBounds(420, 670, 125, 75);
		button.setBackground(new Color(219, 50, 50));
		button.setForeground(Color.WHITE);
		button.setText("START");
		button.setFocusable(false);
		
		label.add(button);

	}


	public JButton getButton() {
		return button;
	}


}
