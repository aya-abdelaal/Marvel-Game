package views;

import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import model.abilities.Ability;
import model.world.Champion;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import engine.Game;

public class TeamChoices extends OurFrame {
	private JPanel Characters;

	private ArrayList<JButton> Champs;
	private ArrayList<JButton> Champions1List;
	private ArrayList<JButton> Champions2List;
	private ArrayList<String> images;

	private JPanel Team1;
	private JPanel Team2;
	private JPanel Champions1;
	private JPanel Champions2;
	private JButton done;
	private JButton currChamp;
	private JButton add1;
	private JButton add2;
	private JButton leader1;
	private JButton leader2;

	public TeamChoices() {
		
		Color blue = new Color(137, 216, 245);
		
		this.setLayout(new BorderLayout());
		Champions1List = new ArrayList<JButton>();
		Champions2List = new ArrayList<JButton>();

		Champs = new ArrayList<JButton>();
		Characters = new JPanel();
		Characters.setBackground(blue);
		Team1 = new JPanel(new BorderLayout());
		Team2 = new JPanel(new BorderLayout());
		Team1.setBackground(blue);
		Team2.setBackground(blue);
		images = new ArrayList<String>();
		
		JPanel temp = new JPanel();
		temp.setLayout(new BorderLayout());
		JLabel title = new OurLabel("Choose Teams");
		title.setVerticalAlignment(JLabel.CENTER);
		temp.add(title,BorderLayout.PAGE_START);
		JLabel help = new OurLabel("Hover over champion to view info");
		help.setFont(new Font("Consolas", Font.PLAIN, 12));
		temp.add(help,BorderLayout.CENTER);
		temp.setBackground(Color.black);
		this.add(temp, BorderLayout.PAGE_START);

		JLabel label1 = new OurLabel("TEAM 1");
		Team1.add(label1, BorderLayout.PAGE_START);

		JLabel label2 = new OurLabel("TEAM 2");
		Team2.add(label2, BorderLayout.PAGE_START);

		leader1 = new JButton("Choose Leader");
		leader2 = new JButton("Choose Leader");
		Team1.add(leader1, BorderLayout.PAGE_END);
		Team2.add(leader2, BorderLayout.PAGE_END);

		Champions1 = new JPanel();
		Champions2 = new JPanel();
		Champions1.setLayout(new BoxLayout(Champions1, BoxLayout.Y_AXIS));
		Champions2.setLayout(new BoxLayout(Champions2, BoxLayout.Y_AXIS));
		Champions1.setBackground(blue);
		Champions2.setBackground(blue);
		Team1.add(Champions1, BorderLayout.CENTER);
		Team2.add(Champions2, BorderLayout.CENTER);

		this.add(Team1, BorderLayout.LINE_START);
		this.add(Team2, BorderLayout.LINE_END);

		Characters.setLayout(new GridLayout(3, 5));
		Characters.setSize(new Dimension(1000, 450));

		images = new ArrayList<String>();
		images.add("resources/captain.png");
		images.add("resources/deadpool.png");
		images.add("resources/drstrange.png");
		images.add("resources/electro.png");
		images.add("resources/ghostrider.png");
		images.add("resources/hela.png");
		images.add("resources/hulk.png");
		images.add("resources/iceman.png");
		images.add("resources/ironman.png");
		images.add("resources/loki.png");
		images.add("resources/quicksilver.png");
		images.add("resources/spiderman.png");
		images.add("resources/thor.png");
		images.add("resources/venom.png");
		images.add("resources/yellowjacket.png");

		for (int i = 0; i < images.size(); i++) {
			JButton x = new JButton();
			x.setIcon(new ImageIcon(images.get(i)));
			Champion c = Game.getAvailableChampions().get(i);
			x.setToolTipText("<html>" +c.toString().replace("\n", "<br/>") + "</html>");
			Characters.add(x);
			Champs.add(x);
		}

		this.add(Characters, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		bottom.setBackground(blue);
		add1 = new JButton("Add to Team 1");
		add1.setFocusable(false);
		bottom.add(add1);
		done = new JButton("Submit");
		done.setFocusable(false);
		bottom.add(done);
		add2 = new JButton("Add to Team 2");
		add2.setFocusable(false);
		bottom.add(add2);
		this.add(bottom, BorderLayout.PAGE_END);


	}

	public void setCurrChamp(JButton currChamp) {
		this.currChamp = currChamp;
	}

	public JButton getCurrChamp() {
		return currChamp;
	}

	public ArrayList<JButton> getChamps() {
		return Champs;
	}

	public JPanel getChampions1() {
		return Champions1;
	}

	public JPanel getChampions2() {
		return Champions2;
	}

	public JButton getDone() {
		return done;
	}

	public JButton getAdd1() {
		return add1;
	}

	public JButton getAdd2() {
		return add2;
	}

	public JButton getLeader1() {
		return leader1;
	}

	public JButton getLeader2() {
		return leader2;
	}

	public ArrayList<JButton> getChampions1List() {
		return Champions1List;
	}

	public ArrayList<JButton> getChampions2List() {
		return Champions2List;
	}


	public void showChampInTeam(int i, ActionListener teamButtonsListener) {
		JButton p = new JButton();
		p.setIcon(new ImageIcon(images.get(Champs.indexOf(currChamp))));
		p.addActionListener(teamButtonsListener);
		if (i == 0) {
			Champions1.add(p);
			Champions1List.add(p);
		} else {
			Champions2.add(p);
			Champions2List.add(p);
		}
		this.revalidate();
		this.repaint();

	}

	public void addButtonListeners(ActionListener a) {
		done.addActionListener(a);
		leader1.addActionListener(a);
		leader2.addActionListener(a);
		add1.addActionListener(a);
		add2.addActionListener(a);
		for (JButton x : Champs)
			x.addActionListener(a);

	}

}
