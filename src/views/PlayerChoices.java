package views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Game;
import engine.Player;

public class PlayerChoices extends OurFrame{
	private JButton button;
	private JTextField textField;
	private JButton button2;
	private JTextField textField2;
	private Game game;

	public PlayerChoices() {

		JPanel one = new JPanel();
		JPanel two = new JPanel();
		one.setOpaque(false);
		two.setOpaque(false);

		this.setLayout(new FlowLayout());

		button = new JButton("Submit");

		textField = new JTextField();
		textField.setPreferredSize(new Dimension(250, 40));
		textField.setFont(new Font("Consolas", Font.PLAIN, 30));
		textField.setForeground(new Color(219, 50, 50));
		textField.setBackground(Color.black);
		textField.setCaretColor(Color.white);
		textField.setText("player 1");

		button2 = new JButton("Submit");


		textField2 = new JTextField();
		textField2.setPreferredSize(new Dimension(250, 40));
		textField2.setFont(new Font("Consolas", Font.PLAIN, 30));
		textField2.setForeground(new Color(219, 50, 50));
		textField2.setBackground(Color.black);
		textField2.setCaretColor(Color.white);
		textField2.setText("player 2");
		button.setFocusable(false);
		button2.setFocusable(false);
		one.add(textField);
		one.add(button);
		two.add(textField2);
		two.add(button2);

		this.add(one);
		this.add(two);

	}
	
	public JButton getButton1() {
		return button;
	}
	
	public JButton getButton2() {
		return button2;
	}
	
	public JTextField getField1() {
		return textField;
	}
	
	public JTextField getField2() {
		return textField2;
	}
	
	public void addButtonListener(ActionListener bl) {
		button.addActionListener(bl);
		button2.addActionListener(bl);
		
	}
	


}
