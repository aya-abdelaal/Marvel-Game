package controllers;

import views.PlayerChoices;
import views.Startup;
import views.TeamChoices;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;

import controllers.GameController.actionButtonsListener;
import engine.Game;
import engine.Player;
import exceptions.AbilityUseException;
import exceptions.ChampionDisarmedException;
import exceptions.InvalidTargetException;
import exceptions.LeaderAbilityAlreadyUsedException;
import exceptions.LeaderNotCurrentException;
import exceptions.NotEnoughResourcesException;
import exceptions.UnallowedMovementException;
import model.abilities.Ability;
import model.abilities.AreaOfEffect;
import model.world.Champion;
import model.world.Direction;
import views.BoardView;

public class GameController {
	private Startup startView;
	private PlayerChoices nameView;
	private TeamChoices teamView;
	private static BoardView boardView;
	private Game game;
	private Player first;
	private Player second;

	private static boolean flag;

	public GameController(Startup s, PlayerChoices nameView) {
		startView = s;
		this.nameView = nameView;
		flag = false;
		s.getButton().addActionListener(new startupListener());
		nameView.addButtonListener(new playerNamesListener());
	}

	public Game getGame() {
		return game;
	}

	class startupListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				Game.loadAbilities("Abilities.csv");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			try {
				Game.loadChampions("Champions.csv");
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			startView.dispose();
			nameView.display();

		}
	}

	class playerNamesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == nameView.getButton1()) {
				first = new Player(nameView.getField1().getText());
				nameView.getButton1().setEnabled(false);
				nameView.getField1().setEditable(false);
			}

			if (e.getSource() == nameView.getButton2()) {
				second = new Player(nameView.getField2().getText());
				nameView.getButton2().setEnabled(false);
				nameView.getField2().setEditable(false);
			}
			checkdone();

		}

		public void checkdone() {
			if (!nameView.getButton1().isEnabled() && !nameView.getButton2().isEnabled()) {
				nameView.dispose();
				teamView = new TeamChoices();
				teamView.addButtonListeners(new TeamButtonsListener());
				teamView.display();
			}
		}
	}

	class TeamButtonsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == teamView.getAdd1() || e.getSource() == teamView.getAdd2()) {
				Champion c = Game.getAvailableChampions().get(teamView.getChamps().indexOf(teamView.getCurrChamp()));
				if (first.getTeam().contains(c) || second.getTeam().contains(c))
					return;
				Player p = e.getSource() == teamView.getAdd1() ? first : second;
				p.getTeam().add(c);
				if (p.getTeam().size() == 3)
					((JButton) e.getSource()).setEnabled(false);
				teamView.showChampInTeam(e.getSource() == teamView.getAdd1() ? 0 : 1, new TeamButtonsListener());
			} else if (e.getSource() == teamView.getDone()) {
				if (first.getTeam().size() != 3 || second.getTeam().size() != 3)
					return;
				if (teamView.getLeader1().isEnabled() || teamView.getLeader2().isEnabled())
					return;
				game = new Game(first, second);
				teamView.dispose();
				boardView = new BoardView();
				boardView.OnboardUpdated(game.getBoard(), first, second, game);
				boardView.setFocusable(true);
				boardView.addKeyListener(new keyboardListener());
				boardView.addButtonListeners(new actionButtonsListener());
				boardView.addButtonListeners2(new actionButtonsListener());
				boardView.display();
			}

			else if (e.getSource() == teamView.getLeader1() || e.getSource() == teamView.getLeader2()) {
				Player p = e.getSource() == teamView.getLeader1() ? first : second;
				ArrayList<JButton> temp = e.getSource() == teamView.getLeader1() ? teamView.getChampions1List()
						: teamView.getChampions2List();
				Champion c = p.getTeam().get(temp.indexOf(teamView.getCurrChamp()));
				p.setLeader(c);
				((JButton) e.getSource()).setEnabled(false);
			}

			else {
				if (!teamView.getChamps().contains(e.getSource())
						&& !teamView.getChampions1List().contains(e.getSource())
						&& !teamView.getChampions2List().contains(e.getSource()))
					return;
				if (teamView.getCurrChamp() != null) {
					Border emptyBorder = BorderFactory.createEmptyBorder();
					teamView.getCurrChamp().setBorder(emptyBorder);
				}
				teamView.setCurrChamp((JButton) e.getSource());
				Border blueLine = BorderFactory.createLineBorder(Color.blue);
				((JComponent) e.getSource()).setBorder(blueLine);
			}

		}

	}

	class keyboardListener implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {

			Direction x = null;

			if (e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN)
				x = Direction.DOWN;
			else if (e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP)
				x = Direction.UP;
			else if (e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_RIGHT)
				x = Direction.LEFT;
			else if (e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_LEFT)
				x = Direction.RIGHT;
			else
				return;

			try {
				game.move(x);

			} catch (NotEnoughResourcesException e1) {
				boardView.showException(e1.getMessage());

			} catch (UnallowedMovementException e2) {
				boardView.showException(e2.getMessage());
			} finally {
				boardView.OnboardUpdated(game.getBoard(), first, second, game);
				boardView.addButtonListeners2(new actionButtonsListener());
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	public class actionButtonsListener implements ActionListener {
		private static Ability a;

		@Override
		public void actionPerformed(ActionEvent e) {

			if (e.getSource() == boardView.getUseLeaderAbility()) {
				try {
					game.useLeaderAbility();
					boardView.OnboardUpdated(game.getBoard(), first, second, game);
					boardView.addButtonListeners2(new actionButtonsListener());

				} catch (LeaderNotCurrentException e1) {
					boardView.showException(e1.getMessage());
				} catch (LeaderAbilityAlreadyUsedException e1) {
					boardView.showException(e1.getMessage());
				}
			} else if (e.getSource() == boardView.getEndTurn()) {
				game.endTurn();
				boardView.OnboardUpdated(game.getBoard(), first, second, game);
				boardView.addButtonListeners2(new actionButtonsListener());
			} else if (e.getSource() == boardView.getAttackDown() || e.getSource() == boardView.getAttackLeft()
					|| e.getSource() == boardView.getAttackRight() || e.getSource() == boardView.getAttackUp()) {
				Direction x = null;

				if (e.getSource() == boardView.getAttackUp())
					x = Direction.UP;
				else if (e.getSource() == boardView.getAttackDown())
					x = Direction.DOWN;
				else if (e.getSource() == boardView.getAttackLeft())
					x = Direction.LEFT;
				else if (e.getSource() == boardView.getAttackRight())
					x = Direction.RIGHT;

				try {
					game.attack(x);
					boardView.OnboardUpdated(game.getBoard(), first, second, game);
					boardView.addButtonListeners2(new actionButtonsListener());
				} catch (NotEnoughResourcesException e1) {
					boardView.showException(e1.getMessage());
				} catch (ChampionDisarmedException e1) {
					boardView.showException(e1.getMessage());
				} catch (InvalidTargetException e1) {
					boardView.showException(e1.getMessage());
				}
			}

			else if (e.getSource() == boardView.getAbility1() || e.getSource() == boardView.getAbility2()
					|| e.getSource() == boardView.getAbility3() || e.getSource() == boardView.getAbility4()) {

				int z;
				if (e.getSource() == boardView.getAbility1())
					z = 0;
				else if (e.getSource() == boardView.getAbility2())
					z = 1;
				else if (e.getSource() == boardView.getAbility3())
					z = 2;
				else
					z = 3;

				a = game.getCurrentChampion().getAbilities().get(z);

				if (a.getCastArea() == AreaOfEffect.TEAMTARGET || a.getCastArea() == AreaOfEffect.SELFTARGET
						|| a.getCastArea() == AreaOfEffect.SURROUND) {
					try {
						game.castAbility(a);
						a = null;
						boardView.OnboardUpdated(game.getBoard(), first, second, game);
						boardView.addButtonListeners2(new actionButtonsListener());
					} catch (NotEnoughResourcesException e1) {
						boardView.showException(e1.getMessage());
					} catch (AbilityUseException e2) {
						boardView.showException(e2.getMessage());
					} catch (CloneNotSupportedException e3) {
						boardView.showException(e3.getMessage());
					}
				} else if (a.getCastArea() == AreaOfEffect.DIRECTIONAL) {

					String[] responses = { "Left", "Right", "Up", "Down" };
					int x = JOptionPane.showOptionDialog(boardView, "Choose a direction", "Directional Ability", 0,
							JOptionPane.INFORMATION_MESSAGE, null, responses, 0);
					switch (x) {
					case 0: {
						try {
							game.castAbility(a, Direction.LEFT);
							a = null;
							boardView.OnboardUpdated(game.getBoard(), first, second, game);
							boardView.getPn().setFocusable(true);
							boardView.addButtonListeners2(new actionButtonsListener());
						} catch (NotEnoughResourcesException e1) {
							boardView.showException(e1.getMessage());

						} catch (AbilityUseException e2) {
							boardView.showException(e2.getMessage());

						} catch (CloneNotSupportedException e3) {
							boardView.showException(e3.getMessage());

						}
					}
						break;
					case 1: {
						try {
							game.castAbility(a, Direction.RIGHT);
							a = null;
							boardView.OnboardUpdated(game.getBoard(), first, second, game);
							boardView.addButtonListeners2(new actionButtonsListener());
						} catch (NotEnoughResourcesException e1) {
							boardView.showException(e1.getMessage());

						} catch (AbilityUseException e2) {
							boardView.showException(e2.getMessage());

						} catch (CloneNotSupportedException e3) {
							boardView.showException(e3.getMessage());

						}
					}
						break;
					case 2: {
						try {
							game.castAbility(a, Direction.UP);
							a = null;
							boardView.OnboardUpdated(game.getBoard(), first, second, game);
							boardView.addButtonListeners2(new actionButtonsListener());
						} catch (NotEnoughResourcesException e1) {
							boardView.showException(e1.getMessage());

						} catch (AbilityUseException e2) {
							boardView.showException(e2.getMessage());

						} catch (CloneNotSupportedException e3) {
							boardView.showException(e3.getMessage());

						}
					}
						break;
					case 3: {
						try {
							game.castAbility(a, Direction.DOWN);
							a = null;
							boardView.OnboardUpdated(game.getBoard(), first, second, game);
							boardView.addButtonListeners2(new actionButtonsListener());
						} catch (NotEnoughResourcesException e1) {
							boardView.showException(e1.getMessage());

						} catch (AbilityUseException e2) {
							boardView.showException(e2.getMessage());

						} catch (CloneNotSupportedException e3) {
							boardView.showException(e3.getMessage());

						}
					}
						break;
					}
				} else if (a.getCastArea() == AreaOfEffect.SINGLETARGET) {
					boardView.showException("Please select your target");
					flag = true;

					for (int i = 4; i >= 0; i--) {
						for (int j = 0; j < 5; j++) {
							boardView.getBoardButtons()[i][j].addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									if (flag) {
										JButton[][] boardbuttons = boardView.getBoardButtons();
										for (int i = 4; i >= 0; i--) {
											for (int j = 0; j < boardbuttons[i].length; j++) {
												if (e.getSource() == boardbuttons[i][j]) {
													try {
														game.castAbility(a, i, j);
														a = null;
														boardView.OnboardUpdated(game.getBoard(), first, second, game);

														boardView.addButtonListeners2(new actionButtonsListener());

													} catch (NotEnoughResourcesException | AbilityUseException
															| InvalidTargetException | CloneNotSupportedException e1) {
														boardView.showException(e1.getMessage());
													}

												}
											}
										}
										flag = false;
									}

								}
							});
						}
					}
				}

			}

			if (game.checkGameOver() != null) {
				boardView.gameOver(game.checkGameOver());
				boardView.dispose();
			}
		}
	}

	public static void playAudio(String filepath) {
		File audio;

		audio = new File(filepath);
		try {
			AudioInputStream audioInput = AudioSystem.getAudioInputStream(audio);
			Clip clip = AudioSystem.getClip();
			clip.open(audioInput);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

	}

	public static void main(String[] args) {
		Startup s = new Startup();
		PlayerChoices pc = new PlayerChoices();
		GameController pController = new GameController(s, pc);

		s.display();
		playAudio("resources/theme song.wav");

	}

}
