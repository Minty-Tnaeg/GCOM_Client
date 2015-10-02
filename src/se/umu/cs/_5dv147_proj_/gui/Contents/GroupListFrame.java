package se.umu.cs._5dv147_proj_.gui.Contents;


import se.umu.cs._5dv147_proj.Middleware;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Created by c12slm on 2015-10-02.
 */
public class GroupListFrame {
    private JFrame serverFrame;
    private JPanel tablePanel;
    private JTable groupTable;
    private Middleware mw;
    private String[][] groupList;
    private static String[] header = {"Groupname", "Leader"};

    public GroupListFrame(Middleware mw) {
        this.mw = mw;
        this.serverFrame = new JFrame("Available groups");
        this.serverFrame.setResizable(true);
        this.tablePanel = new JPanel(new BorderLayout());
        this.serverFrame.add(this.tablePanel);
        this.tablePanel.setPreferredSize(new Dimension(500, 300));
        this.groupList = mw.getGroups();
        buildGroupTable();
        buildConnectButton();

        this.serverFrame.setVisible(true);
        this.serverFrame.pack();
        this.serverFrame.setLocationRelativeTo(null);
    }

    private void buildGroupTable() {
        Object[][] dataTable = groupList;
        DefaultTableModel tableModel = new DefaultTableModel(dataTable, header);

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        groupTable = new JTable(tableModel);
        groupTable.setAutoCreateRowSorter(true);
        groupTable.setGridColor(Color.GRAY);
        groupTable.setShowGrid(true);
        groupTable.setRowHeight(20);
    }

    private void buildConnectButton(){
        JButton button = new JButton("Connect");
        button.addActionListener(ae -> {
            mw.joinGroup(groupList[groupTable.getSelectedRow()][0]);
            serverFrame.dispose();
        });
        tablePanel.add(button, BorderLayout.SOUTH);
    }
}