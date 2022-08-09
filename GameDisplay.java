import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;


import common.Location;

public class GameDisplay {


	public GameDisplay(GameController controller, int numMachines) {
		buttonStrings = new ArrayList<String>();
		for(int i=0; i< numMachines; i++) {
			buttonStrings.add("" + i);
		}
		correctMachines = new ArrayList<>(numMachines);
		for(int i=0;i<numMachines;i++)
			correctMachines.add(true);
		
		theController = controller;
	}

	void drawRect(Location p, int w, int h) {
		if (panelG != null)
			panelG.drawRect(p.getX() + Width/2, Height/2 - p.getY(), w, h);
	}

	void drawCircle(Location c, int rad) {
		if (panelG != null) {
			//System.out.println("Circle at " + c + " radius " + rad);
			panelG.drawOval(c.getX() -rad/2 + Width/2,
					Height/2 - (c.getY() - rad/2) , rad, rad);
		}
	}

	void drawText(Location p, String text) {
		if (panelG != null)
			panelG.drawString(text, p.getX() + Width/2, Height/2- p.getY());
	}
	
	void drawLine(Location start, Location end) {
		if (panelG != null)
			panelG.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				setUpGUI();
			}
		});
	}

	
	public void clear() {
		if (imagePanel != null) {
			imagePanel.removeAll();
		}
	}

	
	public void update() {
		if (imagePanel != null) {
			//System.out.println("Updating view");
			imagePanel.repaint();
		}
	}

	@SuppressWarnings("serial")
	private void setUpGUI() {
		System.out.println("Set up GUI");

		JFrame frame = new JFrame("Game Demo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		topPanel = new JPanel();

		topPanel.setPreferredSize(new Dimension(Width + 200, Height + 10));
		topPanel.setLayout(new FlowLayout());
		Border bborder = BorderFactory.createLineBorder(Color.red);
		frame.add(topPanel);

		imagePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				panelG = g;
				//System.out.println("Repainting");
				theController.updateView();
			}
		};
		imagePanel.setPreferredSize(new Dimension(Width, Height));
		imagePanel.setBorder(bborder);
		topPanel.add(imagePanel);
		

		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.PAGE_AXIS));
		sidePanel.setBorder(bborder);
		topPanel.add(sidePanel);

		JLabel l1 = new JLabel("Select Leader");
		sidePanel.add(l1);
		
		addLeaderButtons(sidePanel);
		
		JLabel l2 = new JLabel("Select Faulty Machines");
		sidePanel.add(l2);
		
		addFaultyCheckBoxes(sidePanel);
		
		JButton startButton = new JButton("Start Phase");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Start Phase");
				new Thread() {
					public void run() {
						System.out.println("Leader: " + leaderId);
						for(int i=0;i< correctMachines.size();i++) {
							System.out.println(i + ": " + correctMachines.get(i));
						}
						theController.startPhase(leaderId, correctMachines);
					}
				}.start();
			}

		});
		sidePanel.add(startButton);

		JButton exitButton = new JButton("Exit");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Exit");
				System.exit(0);
			}

		});
		sidePanel.add(exitButton);

		// Display the window.
		frame.pack();
		frame.setVisible(true);

	}

	private void addLeaderButtons(JPanel panel) {
		bg = new ButtonGroup();
		
		ActionListener watchAction = 
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						String id = e.getActionCommand();
						leaderId = Integer.parseInt(id);
					}
		};
		
		for(String bString: buttonStrings) {
			JRadioButton b = new JRadioButton(bString);
			b.setActionCommand(bString);
			b.addActionListener(watchAction);
			bg.add(b);
			panel.add(b);
			
		}
		
	}
	
	private void addFaultyCheckBoxes(JPanel panel) {
		
		ItemListener watchAction = 
				new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						Object box = e.getItem();
						int mID = checkboxes.indexOf(box);
						if(e.getStateChange() == ItemEvent.DESELECTED) {
							correctMachines.set(mID, true);
						} else {
							correctMachines.set(mID, false);
						}
					}
		};
		
		checkboxes = new ArrayList<>();
		for(String bString: buttonStrings) {
			JCheckBox b = new JCheckBox(bString, false);
			b.setActionCommand(bString);
			b.addItemListener(watchAction);
			panel.add(b);
			checkboxes.add(b);
			
		}
	}
	private GameController theController;
	private int leaderId;
	private ArrayList<Boolean> correctMachines;
	
	private JPanel topPanel, imagePanel;
	private ButtonGroup bg;
	private Graphics panelG;
	private final int Height = 600;
	private final int Width = 900;
		
	private ArrayList<String> buttonStrings;
	private ArrayList<JCheckBox> checkboxes;

}

