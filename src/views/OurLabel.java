package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;

public class OurLabel extends JLabel{

	public OurLabel(String s) {
		super(s);
		this.setPreferredSize(new Dimension(250, 50));
		this.setFont(new Font("Consolas", Font.PLAIN, 30));
		this.setForeground(new Color(219, 50, 50));
		this.setBackground(Color.black);
		this.setOpaque(true);
		this.setHorizontalAlignment(JLabel.CENTER);
	}


}
