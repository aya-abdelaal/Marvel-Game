package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.Border;

import controllers.GameController.actionButtonsListener;
import engine.Game;
import engine.Player;
import engine.PriorityQueue;
import model.abilities.Ability;
import model.effects.Effect;
import model.world.Champion;
import model.world.Cover;
import model.world.Hero;
import model.world.Villain;

public class BoardView extends OurFrame {
	JPanel Team1;
	JPanel Team2;
	JButton boardButtons[][];
	JPanel pn;
	JButton endTurn;
	JButton useLeaderAbility;
	JButton attackUp;
	JButton attackDown;
	JButton attackRight;
	JButton attackLeft;

	JPanel actionButtons;
	JPanel chooseability;
	JPanel castabilitydirection;
	JPanel castabilitysingle;
	JButton Ability1;
	JButton Ability2;
	JButton Ability3;
	JButton Ability4;
	JPanel Direction;
	JButton Up;
	JButton Down;
	JButton Right;
	JButton Left;

	JPanel first;
	JPanel second;

	JTextPane playerInfo;
	JTextPane currChampInfo;
	JTextPane turnOrder;
	JTextPane help;

	public BoardView() {
		super();

		boardButtons = new JButton[5][5];
		this.setLayout(new BorderLayout());

		first = new JPanel();
		first.setLayout(new BoxLayout(first, BoxLayout.Y_AXIS));
		first.setFocusable(false);
		first.setBackground(new Color(173, 216, 230));
		
		second = new JPanel();
		second.setFocusable(false);
		second.setBackground(new Color(173, 216, 230));
		second.setLayout(new BoxLayout(second, BoxLayout.Y_AXIS));
		
		pn = new JPanel();
		pn.setLayout(new GridLayout(5, 5));
		
		
		actionButtons = new JPanel();
		attackUp = new JButton("Attack UP");
		attackUp.setFocusable(false);
		attackDown = new JButton("Attack DOWN");
		attackDown.setFocusable(false);
		attackRight = new JButton("Attack RIGHT");
		attackRight.setFocusable(false);
		attackLeft = new JButton("Attack LEFT");
		attackLeft.setFocusable(false);
		useLeaderAbility = new JButton("Use Leader Ability");
		useLeaderAbility.setFocusable(false);
		endTurn = new JButton("End Turn");
		endTurn.setFocusable(false);
		actionButtons.add(attackUp);
		actionButtons.add(attackDown);
		actionButtons.add(attackRight);
		actionButtons.add(attackLeft);
		actionButtons.add(useLeaderAbility);
		actionButtons.add(endTurn);

		chooseability = new JPanel();
		Direction = new JPanel();
		Up = new JButton("UP");
		Up.setFocusable(false);
		Down = new JButton("DOWN");
		Down.setFocusable(false);
		Right = new JButton("RIGHT");
		Right.setFocusable(false);
		Left = new JButton("LEFT");
		Left.setFocusable(false);

		this.add(actionButtons, BorderLayout.PAGE_START);
		this.add(chooseability, BorderLayout.PAGE_END);
		this.add(second, BorderLayout.LINE_END);
		this.add(first, BorderLayout.LINE_START);
		this.add(pn, BorderLayout.CENTER);

	}

	public JButton[][] getBoardButtons() {
		return boardButtons;
	}

	public JPanel getPn() {
		return pn;
	}

	public JPanel getDirection() {
		return Direction;
	}

	public JPanel getActionButtons() {
		return actionButtons;
	}

	public JPanel getChooseability() {
		return chooseability;
	}

	public JButton getEndTurn() {
		return endTurn;
	}

	public JButton getUseLeaderAbility() {
		return useLeaderAbility;
	}

	public JButton getAttackUp() {
		return attackUp;
	}

	public JButton getAttackDown() {
		return attackDown;
	}

	public JButton getAttackRight() {
		return attackRight;
	}

	public JButton getAttackLeft() {
		return attackLeft;
	}

	public JButton getUp() {
		return Up;
	}

	public JButton getDown() {
		return Down;
	}

	public JButton getRight() {
		return Right;
	}

	public JButton getLeft() {
		return Left;
	}

	public JButton getAbility1() {
		return Ability1;
	}

	public JButton getAbility2() {
		return Ability2;
	}

	public JButton getAbility3() {
		return Ability3;
	}

	public JButton getAbility4() {
		return Ability4;
	}

	public JTextPane getPlayerInfo() {
		return playerInfo;
	}

	public JTextPane getCurrChampInfo() {
		return currChampInfo;
	}

	public JTextPane getTurnOrder() {
		return turnOrder;
	}

	public JTextPane getHelp() {
		return help;
	}

	public void OnboardUpdated(Object[][] board, Player player1, Player player2, Game g) {
		pn.removeAll();
		chooseability.removeAll();
		first.removeAll();
		second.removeAll();
		for (int i = 4; i >= 0; i--) {
			for (int j = 0; j < board[i].length; j++) {
				if (board[i][j] == null) {
					JButton x = new JButton();
					x.setFocusable(false);
					pn.add(x);
					boardButtons[i][j] = x;

				} else if (board[i][j] instanceof Champion) {
					JButton x = new JButton(
							((Champion) board[i][j]).getName() + " " + ((Champion) board[i][j]).getCurrentHP());
					if(((Champion) board[i][j]).equals(g.getCurrentChampion()))
						x.setForeground(Color.green);
					else if (g.getFirstPlayer().getTeam().contains((Champion) board[i][j]))
						x.setForeground(new Color(219, 50, 50));
					else
						x.setForeground(Color.blue);
					x.setFocusable(false);
					x.setToolTipText("<html>" + ((Champion) board[i][j]).effectToString().replace("\n", "<br/>") + "</html>");

					pn.add(x);
					boardButtons[i][j] = x;

				} else if (board[i][j] instanceof Cover) {
					JButton x = new JButton("Cover HP:" + ((Cover) board[i][j]).getCurrentHP());
					x.setFocusable(false);
					pn.add(x);
					boardButtons[i][j] = x;
				}

			}

		}

		PriorityQueue temp = new PriorityQueue(6);
		String s = "<html> <h1>Turn Order: </h1><p style=\"color:white\"><b>";
		while (!g.getTurnOrder().isEmpty()) {
			Champion c = (Champion) g.getTurnOrder().remove();
			if (!g.hasEffect(c, "Stun")) {
				s += c.getName() + "<br>";
			}
			temp.insert(c);

		}
		s += "</b></p></html>";
		while (!temp.isEmpty()) {
			Champion c = (Champion) temp.remove();
			g.getTurnOrder().insert(c);
		}
		turnOrder = new JTextPane();
		turnOrder.setEditable(false);
		turnOrder.setFocusable(false);
		turnOrder.setOpaque(false);
		turnOrder.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		turnOrder.setContentType("text/html");
		turnOrder.setText(s);
		second.add(turnOrder);

		s = "<html> <h1 style=\"color:rgb(219, 50, 50)\">" + g.getFirstPlayer().getName()
				+ ": </h1><p style=\"color:white\"><b>";
		s += "Leader Ability Used </b> : " + g.isFirstLeaderAbilityUsed() + "<br><b>";
		for (Champion x : g.getFirstPlayer().getTeam())
			s += x.getName() + "</b> : " + x.getCondition() + "<br><b>";
		s += "</b></p><h1 style=\"color:blue\">" + g.getSecondPlayer().getName()
				+ ": </h1><p style=\"color:white\"><b>";
		s += "Leader Ability Used </b> : " + g.isSecondLeaderAbilityUsed() + "<br><b>";
		for (Champion x : g.getSecondPlayer().getTeam())
			s += x.getName() + "</b> : " + x.getCondition() + "<br><b>";
		s += "</b></p></html>";
		playerInfo = new JTextPane();
		playerInfo.setEditable(false);
		playerInfo.setFocusable(false);
		playerInfo.setOpaque(false);
		playerInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		playerInfo.setContentType("text/html");
		playerInfo.setText(s);
		first.add(playerInfo);

		Champion c = g.getCurrentChampion();
		s = "<html> <h1>Current Champion : </h1><p style=\"color:white\"><b>";
		s += c.getName() + "<br> ";
		if (c instanceof Hero)
			s += "Hero <br>";
		else if (c instanceof Villain)
			s += "Villain <br>";
		else
			s += "AntiHero <br>";
		s += "HP : </b>" + c.getCurrentHP() + "<br><b>Mana : </b>" + c.getMana() + "<br><b>Action Points : </b>"
				+ c.getCurrentActionPoints() + "<br><b>Abilities : </b><br>";
		for (Ability a : c.getAbilities())
			s += a.getName() + "<br>";
		s += "<b> Effects : </b><br>";
		for (Effect e : c.getAppliedEffects()) {
			s += e.getName() + "   " + e.getDuration() + "<br>";
		}
		s += "<b>Attack Damage : </b>" + c.getAttackDamage() + "<br><b>Attack Range : </b>" + c.getAttackRange()
				+ "</p></html>";
		currChampInfo = new JTextPane();
		currChampInfo.setEditable(false);
		currChampInfo.setFocusable(false);
		currChampInfo.setOpaque(false);
		currChampInfo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		currChampInfo.setContentType("text/html");
		currChampInfo.setText(s);
		first.add(currChampInfo);
		
		s = "<html><h1>Help : </h1><p><b>Use AWSD keys to move</b></p>"
				+ "<p><b>Hover over champion or ability to display info</b></p></html>";
		help = new JTextPane();
		help.setEditable(false);
		help.setFocusable(false);
		help.setOpaque(false);
		help.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		help.setContentType("text/html");
		help.setText(s);
		second.add(help);

		Ability1 = new JButton(c.getAbilities().get(0).getName());
		Ability1.setToolTipText("<html>" + c.getAbilities().get(0).toString().replace("\n", "<br/>") + "</html>");
		Ability2 = new JButton(c.getAbilities().get(1).getName());
		Ability2.setToolTipText("<html>" + c.getAbilities().get(1).toString().replace("\n", "<br/>") + "</html>");
		Ability3 = new JButton(c.getAbilities().get(2).getName());
		Ability3.setToolTipText("<html>" + c.getAbilities().get(2).toString().replace("\n", "<br/>") + "</html>");

		Ability1.setFocusable(false);
		Ability2.setFocusable(false);
		Ability3.setFocusable(false);
		chooseability.add(Ability1);
		chooseability.add(Ability2);
		chooseability.add(Ability3);
		if (g.hasEffect(g.getCurrentChampion(), "Disarm")) {
			Ability4 = new JButton(g.getCurrentChampion().getAbilities().get(3).getName());
			Ability4.setToolTipText("<html>"
					+ g.getCurrentChampion().getAbilities().get(3).toString().replace("\n", "<br/>") + "</html>");
			Ability4.setFocusable(false);
			chooseability.add(Ability4);
		}
		
		this.revalidate();
		this.repaint();

	}

	public void showException(String e) {
		JOptionPane.showMessageDialog(this, e);
		this.revalidate();
		this.repaint();
	}

	public void addButtonListeners(ActionListener x) {
		attackUp.addActionListener(x);
		attackDown.addActionListener(x);
		attackRight.addActionListener(x);
		attackLeft.addActionListener(x);

		endTurn.addActionListener(x);
		useLeaderAbility.addActionListener(x);
	}

	public void addButtonListeners2(ActionListener x) {

		Ability1.addActionListener(x);
		Ability2.addActionListener(x);
		Ability3.addActionListener(x);
		if (Ability4 != null)
			Ability4.addActionListener(x);

	}

	public void gameOver(Player checkGameOver) {
		if (checkGameOver != null) {
			JOptionPane.showMessageDialog(this, "Congrats! Winner : " + checkGameOver.getName());

		}
	}
}
