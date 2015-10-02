package se.umu.cs._5dv147_proj_.gui.Contents;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class SettingsFrame {
	private JFrame settingsFrame;
	private JTextField nServerName;
	private JTextField nServerPort;
	private JPanel settingsPanel;

	private JButton accept;

	public SettingsFrame(String nameServerAdress, String nameServerPort) {
		this.settingsFrame = new JFrame("Connect to nameserver");
		this.settingsFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.settingsFrame.setResizable(false);
		this.settingsPanel = new JPanel(new SpringLayout());
		this.settingsFrame.setContentPane(this.settingsPanel);

		buildSettingsPanel(nameServerAdress, nameServerPort);

		this.settingsFrame.setVisible(true);
		this.settingsFrame.pack();
		this.settingsFrame.setLocationRelativeTo(null);
	}

	private void buildSettingsPanel(String nameServerAdress, String nameServerPort) {
		this.nServerName = new JTextField(nameServerAdress, 30);
		this.nServerPort = new JTextField(nameServerPort);

		this.accept = new JButton("Connect");

		JLabel portLabel = new JLabel("Port: ", JLabel.TRAILING);
		JLabel nServNameLabel = new JLabel("Address: ", JLabel.TRAILING);
		JLabel emptyLabel = new JLabel("", JLabel.TRAILING);

		//Nameserver
		this.settingsPanel.add(nServNameLabel);
		nServNameLabel.setLabelFor(this.nServerName);
		this.settingsPanel.add(this.nServerName);

		//Port
		this.settingsFrame.add(portLabel);
		portLabel.setLabelFor(this.nServerPort);
		this.settingsFrame.add(this.nServerPort);

		this.settingsFrame.add(emptyLabel);
		emptyLabel.setLabelFor(this.accept);
		this.settingsFrame.add(this.accept);

		this.nServerName.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent ke) {}

			@Override
			public void keyPressed(KeyEvent ke) {}

			@Override
			public void keyReleased(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER){
					nServerPort.requestFocusInWindow();
				}
			}
		});
		this.nServerPort.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent ke) {}

			@Override
			public void keyPressed(KeyEvent ke) {}

			@Override
			public void keyReleased(KeyEvent ke) {
				if(ke.getKeyCode() == KeyEvent.VK_ENTER){
					accept.doClick();
				}
			}
		});
		this.accept.addActionListener(ae -> settingsFrame.dispose());

		SpringUtilities.makeCompactGrid(this.settingsPanel, 3, 2, 5, 5, 5, 5);
	}

	public String getNameServerAdress(){
		return this.nServerName.getText();
	}

	public String getNameServerPort(){
		return this.nServerPort.getText();
	}

	public void waitUntilDisposed() {
		while(this.settingsFrame.isDisplayable()){
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
