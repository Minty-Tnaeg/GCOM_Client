package se.umu.cs._5dv147_proj_.gui;

import se.umu.cs._5dv147_proj.Middleware;
import se.umu.cs._5dv147_proj_.gui.Contents.GroupListFrame;
import se.umu.cs._5dv147_proj_.gui.Contents.SettingsFrame;
import se.umu.cs._5dv147_proj_.gui.Contents.SmartScroller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.event.KeyListener;
import java.io.IOException;

public class ClientGUI {
    private JFrame frame;
    private JPanel chatPanel;
    private JTextArea chatWindow;
    private JTextField chatMessage;
    private DefaultTableModel userTable;
    private Middleware mw;

    public ClientGUI() {
        this.frame= new JFrame("ChatClient2000");
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.chatPanel = new JPanel(new BorderLayout());
        this.frame.add(this.chatPanel);

        buildUserWindow();
        buildChatWindow();
        buildChatMessage();

        this.frame.setVisible(true);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);

        SettingsFrame sf = new SettingsFrame("localhost", "33400");
        sf.waitUntilDisposed();

        mw = new Middleware(new String[]{"-a", sf.getNameServerAdress(), "-p", sf.getNameServerPort(), "-u", sf.getNickName()});

        GroupListFrame glf = new GroupListFrame(mw);

        mw.registerActionListener(e -> {
            if(e.getActionCommand().equals("TextMessage")){
                chatWindow.append("TEMPORARY STRING - SHOULD USE mw.getMessage()\n");
            }
        });
    }

    private void buildChatWindow(){
        this.chatWindow = new JTextArea();
        this.chatWindow.setBackground(Color.WHITE);
        this.chatWindow.setEditable(false);

        JScrollPane scroll = new JScrollPane(this.chatWindow);
        scroll.setPreferredSize(new Dimension(800, 600));
        new SmartScroller(scroll);

        this.chatPanel.add(scroll, BorderLayout.CENTER);
    }

    private void buildChatMessage(){
        this.chatMessage = new JTextField();
        this.chatMessage.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent ke) {}

            @Override
            public void keyPressed(KeyEvent ke) {}

            @Override
            public void keyReleased(KeyEvent ke) {
                if(ke.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        mw.sendMessage(chatMessage.getText());
                        chatMessage.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        this.chatMessage.setPreferredSize(new Dimension(500,20));
        this.chatPanel.add(this.chatMessage, BorderLayout.SOUTH);
    }

    private void buildUserWindow(){
        String[] header = new String[] {"Name"};

        this.userTable = new DefaultTableModel(header, 0){
            private static final long serialVersionUID = -6564664247927106428L;

            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        JTable userWindow = new JTable(this.userTable);
        userWindow.setAutoCreateRowSorter(true);
        userWindow.setGridColor(Color.GRAY);
        userWindow.setShowGrid(true);
        userWindow.setRowHeight(20);
        userWindow.setBackground(Color.WHITE);

        this.chatPanel.add(userWindow, BorderLayout.EAST);
    }
}
